package communicate.timer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import redis.clients.util.RedisInputStream;
import util.DateUtils;
import util.DynamoDBConnenctUtil;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.StatusDTO;
import communicate.pkmgmt.dto.TmlDto;
import communicate.pkmgmt.dto.TmlHistory;
import communicate.pkmgmt.stb.HeartBeat;
import communicate.pkmgmt.thai.ThaiLogin;
import entity.SingleStbOnlineDuration;
import entity.SingleStbOnlineDurationTotal;

public class TimerSchedule {
	private static Logger log = communicate.common.Logger.getLogger(TimerSchedule.class);
	//private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private Timer timer;
	private static Date stbTimterDate;//首次执行终端上线率统计和终端激活统计定时任务的日期
	private IThaiService thaiService = null;
	private static boolean running_flag_checkTmlStatus = false;
	private static boolean running_flag_stb_analysis = false;
	// 每10分钟检查一次订购单的状态，入库
	// 每10分钟更新一次下载次数
	private  static  final  int CHECK_TIME = 10*60*1000;

	// 每10秒钟检查一次计费单，入库
	//private  static  final  int CHECK_BILL_TIME = 10*1000;

	// 每天检查广告是否过期
	private  static  final  int CLEAN_TIME = 60*60*1000;

	// 每1分钟检查接收到心跳情况
	private  static  final  int CHECK_TML_STATUS_TIME = 1*60*1000;
	// 心跳过期时间，超过设置状态为下线，1分钟
//	private  static  final  int TML_OUT_TIME = 60*1000;
	// 每10秒更新终端上线时间
	private  static  final  int UPDATE_LOGIN_TIME = 10*1000;
	private  static  final  int ONE_HOUR = 60*60*1000;
	private  static  final  long ONE_DAY = 1*24*60*60*1000L;
	public TimerSchedule(){
		timer=new Timer();
	}

	public void start() {
		long stbTimer= getExecuteAtMidnightPerDay();
		Long onlineHoursAnalysisTime = getMsTimeToNextHour();
		log.debug("***********现在的时间是："+new Date()+"*************");
		log.debug("**************************执行终端上线率统计和终端激活统计 定时任务的首次日期是："+stbTimterDate+",也就是在:"+stbTimer+"毫秒值后执行定时任务***************************");
		log.debug("**************************在线时长每小时启动定时定时任务：在:"+onlineHoursAnalysisTime.longValue()+"毫秒值后执行定时任务***************************");
		timer.schedule(new CheckTmlStatus(), 10*1000, CHECK_TML_STATUS_TIME);
		timer.schedule(new OnlineHoursAnalysis(), onlineHoursAnalysisTime.longValue(), 60*60*1000);
		timer.schedule(new StbAnalysisOneDay(), stbTimer, ONE_DAY);
//		timer.schedule(new OnlineHoursAnalysis(), stbTimer, ONE_HOUR);
		
	}

	public void cancel(){
		if(timer==null){
			return;
		}else{
			timer.cancel();
		}
	}
	private class CheckTmlStatus extends TimerTask {
		public synchronized void run() {
			if(TimerSchedule.running_flag_checkTmlStatus) {
				log.info("task is already running");
				return;
			}
			TimerSchedule.running_flag_checkTmlStatus = true;
			log.info("##########CheckTmlStatus############");
			thaiService = new ThaiServiceImpl();
			Map<String,Long> map = HeartBeat.heartbeatMap;
			log.info("在线终端的map : " + map);
			Set<String> tmlIds = map.keySet();
			List<String> tmlIdsList = thaiService.getAllTmlIds();
			if(tmlIdsList!=null&&tmlIdsList.size()>0){
				List<String> offlineTmlIds=new ArrayList<String>();//下线的终端Id
				for(String tmlId : tmlIds) {
					if(tmlIdsList.contains(tmlId)){
						log.debug("check stb : " + tmlId);
						//判断终端是否下线
						if(isTmlOutdate(map.get(tmlId),tmlId)) {
							log.debug("remove stb : " + tmlId);
							map.remove(tmlId);
							offlineTmlIds.add(tmlId);
							ThaiLogin.loginTime.remove(tmlId);
						}
						
					}
				}
				if(offlineTmlIds.size()>0){
					log.debug("**********存在下线的终端Id："+offlineTmlIds+"  开始进行批量更新操作***************");
					//批量保存下线的终端信息
					thaiService.batchSaveTmlHisoty(offlineTmlIds,DateUtils.getCurrentTime(),"offline");
					//批量更新业务日志数据
					thaiService.batchUpdateAppInfosLastesMonths(offlineTmlIds, "run", "exit");
					//批量更新终端状态
					thaiService.batchLogout(offlineTmlIds);
					
				}
				TimerSchedule.running_flag_checkTmlStatus = false;
			}
			
		}
		
	}
	private class OnlineHoursAnalysis extends TimerTask {
		@Override
		public synchronized void run() {
			
			ThaiServiceImpl thaiService=new ThaiServiceImpl();
			Map<String,Long> map = HeartBeat.heartbeatMap;
			log.info("##########OnlineHoursAnalysis############  在线终端的map : " + map);
//			log.info("##########CheckTmlStatus############  在线终端的map : " + map);
			//
			List<String> allOnlineTmlIdsByHour=new ArrayList<String>();
			Set<String> tmlIds = map.keySet();
			/*
			 * 查找当前在线map
			 * for{
			 * 	查找最近一次的2点前的最近一次online时间
			 * 	如果online时间在1点到2点之间，在线时间即为2点减去online
			 * 	如果online时间在1点前，在线时间即为2点到1点都在线，即在线时长增加一个小时
			 *	 时间是按小时累加
			 * }
			 * 
			 * 
			 */
			
			
			//第一步 ：统计当前时间前的在线的终端的在线时长
			Date currentDate = new Date();
			//tmlIds为此时在线的终端的Id的集合 
			String currenTime = DateUtils.getCurrentTime();
			String createTime=currenTime;
			String currenTimeHour=currenTime.substring(0, 13)+":00:00";   //格式: 2017-07-26 12
			String reduceTimeHour=DateUtils.calculateHours(currentDate, -1).substring(0, 13)+":00:00";   //
			long onlineDuration=0L;    //在线时长
			int isBelongHours=0;     //1表示终端不是在当天上线，2表示终端的在线时间在当前时间1小时内 3表示终端的在线时间在当前时间1小时外
			List<SingleStbOnlineDuration> singleStbOnlineDurationList=new ArrayList<SingleStbOnlineDuration>();
			String tmpOnlineTime="";
			String tmpOfflineTime="";
			for (String tmlId : tmlIds) {
				isBelongHours=0;
				List<TmlHistory> tmlHistoryList = thaiService.findTmlHistory(tmlId, "", "", currenTime, "DESC", 1);
				
				if(tmlHistoryList!=null&&tmlHistoryList.size()>0){
					TmlHistory tmlHistory = tmlHistoryList.get(0);
					//终端目前仍在线
					if("online".equals(tmlHistory.getType())){
						String onlineTime=tmlHistory.getNftime();//上线时间
							//终端不是在当天上线
						if(!onlineTime.substring(0, 10).equals(currenTime.substring(0, 10))){
							//将上线时间视为当天0点
							isBelongHours=1;   
							
//							onlineTime=currenTime.substring(0, 10)+" 00:00:00";  
//							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime); //在线时长=当前时间-上线时间
							
							if(!"00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
								tmpOnlineTime=currenTime.substring(0, 10)+" 00:00:00";
								onlineDuration=DateUtils.getMs(currenTimeHour, tmpOnlineTime); //在线时长=当前时间-上线时间
//								createTime=DateUtils.calculateDays(currentDate, -1);
//								onlineTime=tmlHistory.getNftime();
							}else{
								tmpOfflineTime=currenTime.substring(0, 10)+" 00:00:00";
								onlineDuration=DateUtils.getMs(tmpOfflineTime, onlineTime);
							}
						}//终端的在线时间在当前时间1小时内
						else if(onlineTime.compareTo(reduceTimeHour)>0&&onlineTime.compareTo(currenTimeHour)<0){
							isBelongHours=2;
							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime); //在线时长=当前时间-上线时间
						}//终端的在线时间在当前时间1小时外
						else{
							isBelongHours=3;
							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime);
						}
						String tmpCreateTime="";
						if("00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
							createTime=DateUtils.calculateDays(currentDate, -1);
							tmpCreateTime=createTime.substring(0,10)+" 23:59:59";
//							onlineTime=tmlHistory.getNftime();
						}
						if(!StringUtils.isEmpty(tmpOnlineTime)){
							onlineTime=tmpOnlineTime;
						}
						SingleStbOnlineDuration singleStbOnlineDuration = getSingleStbOnlineDuration(tmlId,createTime,onlineTime);
						if(singleStbOnlineDuration==null){
							singleStbOnlineDuration=new SingleStbOnlineDuration();
							singleStbOnlineDuration.setTmlId(tmlId);
							singleStbOnlineDuration.setTmlCount("1");
							singleStbOnlineDuration.setCreateTime(createTime);
							if(!StringUtils.isEmpty(tmpCreateTime)){
								singleStbOnlineDuration.setCreateTime(tmpCreateTime);
							}
							singleStbOnlineDuration.setOnlineTime(onlineTime);
//							if(1==isBelongHours){
//								if(!createTime.substring(0, 10).equals(currenTime.substring(0, 10))){
	//
//									singleStbOnlineDuration.setOfflineTime(currenTime.substring(0, 10)+" 00:00:00");
//								}
//							}
							singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
						}else{
							
							if(2==isBelongHours){
								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
							}else if(3==isBelongHours){ 
								onlineDuration=Long.valueOf(singleStbOnlineDuration.getOnlineDuration());
								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration+60*60*1000));
							}
						}
						if(1==isBelongHours){
							if(!StringUtils.isEmpty(tmpOfflineTime)){
								singleStbOnlineDuration.setOfflineTime(tmpOfflineTime);
								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
							}
							if(!StringUtils.isEmpty(tmpOnlineTime)){
								singleStbOnlineDuration.setOnlineTime(tmpOnlineTime);
								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
							}
						}
						singleStbOnlineDuration.setTmlCount("1");
						singleStbOnlineDurationList.add(singleStbOnlineDuration);
					}
				}
			}
			
			//第二步：统计当前时间1小时内 下线的盒子
			List<TmlHistory> offlineTmlHistoryList = thaiService.findTmlHistory("", "offline", reduceTimeHour, currenTime, "DESC", 0);
			if(offlineTmlHistoryList!=null&&offlineTmlHistoryList.size()>0){
				String onlineTime="";
				for(int k=0;k<offlineTmlHistoryList.size();k++){
					String offlineTime=offlineTmlHistoryList.get(k).getNftime();  //下线时间
					String offlineTmlId=offlineTmlHistoryList.get(k).getTmlId();  //下线终端的ID
					allOnlineTmlIdsByHour.add(offlineTmlId);
					List<TmlHistory> onlineTmlHistoryList = thaiService.findTmlHistory(offlineTmlId, "online", reduceTimeHour, offlineTime, "DESC", 1);
					//说明在当前1小时内该下线的终端前存在一个online的数据
					if(onlineTmlHistoryList!=null&&onlineTmlHistoryList.size()>0){
						TmlHistory onlineTmlHistory=onlineTmlHistoryList.get(0);
						onlineTime=onlineTmlHistory.getNftime();//上线时间
						
						
					}//说明在当前1小时内该下线的终端不存在一个online的数据
					else{
						onlineTmlHistoryList = thaiService.findTmlHistory(offlineTmlId, "online", "",reduceTimeHour, "DESC", 1);
						if(onlineTmlHistoryList!=null&&onlineTmlHistoryList.size()>0){
							TmlHistory onlineTmlHistory=onlineTmlHistoryList.get(0);
							onlineTime=onlineTmlHistory.getNftime();//上线时间
							//上线时间不在当天
							if(!onlineTime.substring(0, 10).equals(currenTime.substring(0, 10))){
								if(!"00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
									onlineTime=currenTime.substring(0, 10)+" 00:00:00";
								}
							}
						}
					}
					onlineDuration=DateUtils.getMs(offlineTime, onlineTime);  //在线时长
					SingleStbOnlineDuration singleStbOnlineDuration = getSingleStbOnlineDuration(offlineTmlId,createTime,onlineTime);
					if(singleStbOnlineDuration==null){
						singleStbOnlineDuration=new SingleStbOnlineDuration();
						singleStbOnlineDuration.setTmlId(offlineTmlId);
						singleStbOnlineDuration.setCreateTime(createTime);
						singleStbOnlineDuration.setOnlineTime(onlineTime);
						
						singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
					}else{
						singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
					}
					if(!StringUtils.isEmpty(offlineTime)){
						
						singleStbOnlineDuration.setOfflineTime(offlineTime);
					}
					singleStbOnlineDurationList.add(singleStbOnlineDuration);
				}
			}
			
			log.debug("************单终端在线时长集合singleStbOnlineDurationList："+singleStbOnlineDurationList);
			//批量保存单终端在线时长 数据
			singleStbOnlineDurationList=new ArrayList<SingleStbOnlineDuration>(new LinkedHashSet<SingleStbOnlineDuration>(singleStbOnlineDurationList));//去重
			if(singleStbOnlineDurationList!=null&&singleStbOnlineDurationList.size()>0){
				try {
					DynamoDBConnenctUtil.getMappper().batchSave(singleStbOnlineDurationList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("********保存失败："+e);
				}
			}
			if(tmlIds!=null&&tmlIds.size()>0){
				allOnlineTmlIdsByHour.addAll(tmlIds);
			}
			allOnlineTmlIdsByHour=new ArrayList<String>(new LinkedHashSet<String>(allOnlineTmlIdsByHour)); //给集合的元素去重
			log.debug("************需要统计单终端在线时长total数据并保存的终端id的集合 allOnlineTmlIdsByHour:"+allOnlineTmlIdsByHour);
			List<SingleStbOnlineDurationTotal> singleTmlTotalList=new ArrayList<SingleStbOnlineDurationTotal>();
			for(String tmlId2:allOnlineTmlIdsByHour){
				SingleStbOnlineDurationTotal singleStbOnlineDurationTotal = statisticalTmlByDay(tmlId2,createTime);
				singleTmlTotalList.add(singleStbOnlineDurationTotal);
			}
			log.debug("************单终端在线时长total 非all的数据集合singleTmlTotalList："+singleTmlTotalList);
			//批量保存单终端total在线时长 数据
			if(singleTmlTotalList!=null&&singleTmlTotalList.size()>0){
				try {
					DynamoDBConnenctUtil.getMappper().batchSave(singleTmlTotalList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("********保存失败："+e);
				}
			}
			//保存所有终端在线时长totoal all的  数据
			SingleStbOnlineDurationTotal allStbOnlineDurationTotal=getTmlDurationTotalByDay(createTime.substring(0, 10));
			log.debug("************单终端在线时长total all的数据 allStbOnlineDurationTotal："+allStbOnlineDurationTotal);
			if(allStbOnlineDurationTotal!=null){
				try {
					DynamoDBConnenctUtil.getMappper().save(allStbOnlineDurationTotal);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("********保存失败："+e);
				}
			}
			/*1 搜2点到1点间的tml_history 所有下线记录
			2 for (每一条下线记录) {
				查改条下线记录的最近一次上线时间。
				如果最近一次上线时间，在1点到两点之间，本次下线记录的在线时长可计算。
				如果最近一次的上线时间，在1点之前，本次下线记录的在线时长可计算为下线时间减去1点。
			}
			
			
			如果当前时间是0点，当前计算的时长，加到前一天去。
			
			*
			*
			*/
			
			
		}
	}

	/**
	 * 从dynamoDB中查询单终端数据
	 * @author:zhouyc
	 * @date:2017年7月26日
	 * @param 
	 * @return
	 */
	public static SingleStbOnlineDuration getSingleStbOnlineDuration(String tmlId,String createTime,String onlineTime){
		log.debug("************查询终端Id  tmlId为:"+tmlId+" 的单终端在线时长数据*******************  ");
		SingleStbOnlineDuration singleStbOnlineDuration=null;
		try {
			Map<String,AttributeValue> expressionAttriMap=new HashMap<String, AttributeValue>();
			expressionAttriMap.put(":val1",new AttributeValue().withS(tmlId));
			expressionAttriMap.put(":val2",new AttributeValue().withS(onlineTime));
			expressionAttriMap.put(":val3",new AttributeValue().withS(createTime.substring(0, 10)+" 00:00:00"));
			expressionAttriMap.put(":val4",new AttributeValue().withS(createTime.substring(0, 10)+" 23:59:59"));
			ScanRequest scanRequest = new ScanRequest()
			.withTableName("Singel_Tml_Online_Duration_Test")
			.withFilterExpression("tmlId =:val1 AND onlineTime=:val2 AND createTime >=:val3 AND createTime <=:val4")
			.withExpressionAttributeValues(expressionAttriMap);
			ScanResult result = DynamoDBConnenctUtil.getAmazonDynamoDBClient().scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()){
				singleStbOnlineDuration=new SingleStbOnlineDuration();
				singleStbOnlineDuration.setTmlId(item.get("tmlId").getS()==null?"":item.get("tmlId").getS());
				singleStbOnlineDuration.setCreateTime(item.get("createTime")==null?"":item.get("createTime").getS());
				singleStbOnlineDuration.setOnlineDuration(item.get("onlineDuration")==null?"":item.get("onlineDuration").getS());
				singleStbOnlineDuration.setTmlCount(item.get("tmlCount")==null?"":item.get("tmlCount").getS());
				singleStbOnlineDuration.setOnlineTime(item.get("onlineTime")==null?"":item.get("onlineTime").getS());
				singleStbOnlineDuration.setOfflineTime(item.get("offlineTime")==null?"":item.get("offlineTime").getS());
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("查询单终端在线时长数据失败："+e);
		}
		return singleStbOnlineDuration;
	}
	/**
	 * 统计单终端在线时长
	 * @author:zhouyc
	 * @date:2017年7月27日
	 * @param 
	 * @return
	 */
	public static SingleStbOnlineDurationTotal statisticalTmlByDay(String tmlId,String date){
		log.debug("********查询tmlId:"+tmlId+"date为"+date+"的单终端在线时长数据并封装单终端total在线时长数据 date："+date+"************");
		Map<String,AttributeValue> expressionAttriMap=new HashMap<String, AttributeValue>();
		String timeString=date.substring(0, 10);
		StringBuilder filterExpression=new StringBuilder();
//		Stirng filterExpression="tmlId=:val1 AND createTime>=:val2 AND createTime<=:val3";
		try {
			if(null==tmlId){
				filterExpression.append("createTime>=:val2 AND createTime<=:val3");
			}else{
				expressionAttriMap.put(":val1",new AttributeValue().withS(tmlId));
				filterExpression.append("tmlId =:val1 AND createTime>=:val2 AND createTime<=:val3");
			}
			expressionAttriMap.put(":val2",new AttributeValue().withS(timeString+" 00:00:00"));
			expressionAttriMap.put(":val3",new AttributeValue().withS(timeString+" 23:59:59"));
			ScanRequest scanRequest = new ScanRequest()
			    .withTableName("Singel_Tml_Online_Duration_Test")
			    .withFilterExpression(filterExpression.toString())
			    .withExpressionAttributeValues(expressionAttriMap);
			ScanResult result = DynamoDBConnenctUtil.getAmazonDynamoDBClient().scan(scanRequest);
			List<Map<String, AttributeValue>> itemsMap = result.getItems();
			if(itemsMap!=null&&itemsMap.size()>0){
				int onlineDuration=0;
				int tmlCount=0;
				for (Map<String, AttributeValue> item : result.getItems()){
						//上线次数累加操作
						if(item.get("tmlCount")!=null){
							if(StringUtils.isNotEmpty(item.get("tmlCount").getS())){
								tmlCount+=Integer.valueOf(item.get("tmlCount").getS());
							}
						}
						//在线时长累加操作
						if(item.get("onlineDuration")!=null){
							if(StringUtils.isNotEmpty(item.get("onlineDuration").getS())){
								onlineDuration+=Integer.valueOf(item.get("onlineDuration").getS());
							}
						}
				}
				SingleStbOnlineDurationTotal singleStbOnlineDurationTotal=new SingleStbOnlineDurationTotal();
				if(tmlId==null){
					singleStbOnlineDurationTotal.setTmlId("all");
				}else{
					singleStbOnlineDurationTotal.setTmlId(tmlId);
				}
				
				singleStbOnlineDurationTotal.setOnlineDuration(String.valueOf(onlineDuration));
				singleStbOnlineDurationTotal.setTmlCount(String.valueOf(tmlCount));
				singleStbOnlineDurationTotal.setCreateTime(timeString);
				return singleStbOnlineDurationTotal;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("********封装单终端在线时长数据失败："+e);
		}
		
		return null;
	}
	
	
	public SingleStbOnlineDurationTotal getTmlDurationTotalByDay(String date){
		log.debug("**************根据日期date:"+date+"统计单终端在线时长All的数据******************");
		Map<String,AttributeValue> expressionAttriMap=new HashMap<String, AttributeValue>();
		StringBuilder filterExpression=new StringBuilder();
//		Stirng filterExpression="tmlId=:val1 AND createTime>=:val2 AND createTime<=:val3";
		filterExpression.append("createTime>=:val2 AND createTime<=:val3");
		expressionAttriMap.put(":val2",new AttributeValue().withS(date));
		expressionAttriMap.put(":val3",new AttributeValue().withS(date));
		try {
			ScanRequest scanRequest = new ScanRequest()
			    .withTableName("Single_Tml_Online_Duration_Total_Test")
			    .withFilterExpression(filterExpression.toString())
			    .withExpressionAttributeValues(expressionAttriMap);
			ScanResult result = DynamoDBConnenctUtil.getAmazonDynamoDBClient().scan(scanRequest);
			List<Map<String, AttributeValue>> itemsMap = result.getItems();
			if(itemsMap!=null&&itemsMap.size()>0){
				int onlineDuration=0;
				int tmlCount=0;
				for (Map<String, AttributeValue> item : result.getItems()){
					if(!"all".equals(item.get("tmlId").getS())){
						//长线次数累加操作
						if(item.get("tmlCount")!=null){
							if(StringUtils.isNotEmpty(item.get("tmlCount").getS())){
								tmlCount+=Integer.valueOf(item.get("tmlCount").getS());
							}
						}
						//在线时长累加操作
						if(item.get("onlineDuration")!=null){
							if(StringUtils.isNotEmpty(item.get("onlineDuration").getS())){
								onlineDuration+=Integer.valueOf(item.get("onlineDuration").getS());
							}
						}
					}
				}
				SingleStbOnlineDurationTotal singleStbOnlineDurationTotal=new SingleStbOnlineDurationTotal();
				singleStbOnlineDurationTotal.setTmlId("all");
				singleStbOnlineDurationTotal.setOnlineDuration(String.valueOf(onlineDuration));
				singleStbOnlineDurationTotal.setTmlCount(String.valueOf(tmlCount));
				singleStbOnlineDurationTotal.setCreateTime(date);
				return singleStbOnlineDurationTotal;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			log.error("**************统计单终端在线时长All的数据失败："+e);
		}
		
		return null;
	}
	private static long getTimeMillis(String time) {
		    try {
		      DateFormat dateFormat = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
		      DateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		      Date curDate = dateFormat.parse(dayFormat.format(new Date(new Date().getTime()+24*60*60*1000)) + " " + time);
		      stbTimterDate=curDate;
		      return curDate.getTime();
		    } catch (ParseException e) {
		      e.printStackTrace();
		    }
		    return 0L;
		  }
	public long getExecuteAtMidnightPerDay() {
		    long oneDay =24 * 60 * 60 * 1000;
		    long initDelay = getTimeMillis("00:00:00") - System.currentTimeMillis();
		    initDelay = initDelay >=0L ? initDelay : oneDay + initDelay;
		    return initDelay;
		  }
	private class StbAnalysisOneDay extends TimerTask {
		public synchronized void run() {
			if(TimerSchedule.running_flag_stb_analysis) {
				log.info("stb_analysis_task is already running");
				return;
			}
			TimerSchedule.running_flag_stb_analysis = true;
			log.info("##########StbAnalysis############");
			int login_total_num = 0;
			int register_total_num = 0;
			thaiService = new ThaiServiceImpl();
			Calendar   cal   =   Calendar.getInstance();
			cal.add(Calendar.DATE,   -1);
			String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
			List<String> retailerList = thaiService.getRetailerList();
			log.info("*************************终端上线率统计进入 if 条件开始***********************************");
			if(retailerList != null && retailerList.size()>0) {
				log.info("*************************终端上线率统计开始***********************************");
				//对于昨天的统计--按天
				log.info("retailer size : " + retailerList.size());
				Map<String, Integer> register_retailer_num_map = new HashMap<String, Integer>();
				Map<String, Integer> login_retailer_num_map = new HashMap<String, Integer>();
				for(String retailer :retailerList) {
					if(retailer == null || retailer.trim().length()==0)
						continue;
					int login_tmp_num  = thaiService.countLoginTotal(retailer, yesterday);

					login_retailer_num_map.put(retailer, login_tmp_num);
					
					int register_tmp_num  = thaiService.countRegisterTotal(retailer, yesterday);
					
					register_retailer_num_map.put(retailer, register_tmp_num);
				}
				login_total_num = thaiService.countLoginTotalDay(yesterday);
				register_total_num = thaiService.countRegisterTotalDay(yesterday);
				log.info("day_login_total_num  = " + login_total_num);
				log.info("day_register_total_num  = " + register_total_num);
				thaiService.batchInsertStbLoginAnalysisTblDay(login_retailer_num_map, yesterday);
				thaiService.insertStbLoginAnalysisTblDayTotal(login_total_num, yesterday);
				thaiService.batchInsertStbActivationAnalysisTblDay(register_retailer_num_map, yesterday);
				thaiService.insertStbActivationAnalysisTblDayTotal(register_total_num, yesterday);
			
				//对于昨天的统计--按月
				Map<String, Integer> register_retailer_num_map_insert = new HashMap<String, Integer>();
				Map<String, Integer> login_retailer_num_map_insert = new HashMap<String, Integer>();
				Map<String, Integer> register_retailer_num_map_update = new HashMap<String, Integer>();
				Map<String, Integer> login_retailer_num_map_update = new HashMap<String, Integer>();
				login_total_num = 0;
				register_total_num = 0;
				String lastMonth = new SimpleDateFormat( "yyyy-MM").format(cal.getTime());
				List<String> login_existing_list = thaiService.getRetailerListInLoginTbl(lastMonth);
				List<String> activation_existing_list = thaiService.getRetailerListInActivationTbl(lastMonth);
				
				for(String retailer :retailerList) {
					if(retailer == null || retailer.trim().length()==0)
						continue;
					int login_tmp_num  = thaiService.countLoginTotal(retailer, lastMonth);
					if(login_existing_list != null && login_existing_list.contains(retailer)) 
						login_retailer_num_map_update.put(retailer, login_tmp_num);
					else
						login_retailer_num_map_insert.put(retailer, login_tmp_num);
					int register_tmp_num  = thaiService.countRegisterTotal(retailer, lastMonth);
					if(activation_existing_list != null && activation_existing_list.contains(retailer)) 
						register_retailer_num_map_update.put(retailer, register_tmp_num);
					else
						register_retailer_num_map_insert.put(retailer, register_tmp_num);
				}
				login_total_num = thaiService.countLoginTotalDay(lastMonth);
				register_total_num = thaiService.countRegisterTotalDay(lastMonth);
				
				log.info("month_login_total_num  = " + login_total_num);
				log.info("month_register_total_num  = " + register_total_num);
				thaiService.batchInsertStbLoginAnalysisTblMonth(login_retailer_num_map_insert, lastMonth);
				thaiService.batchInsertStbActivationAnalysisTblMonth(register_retailer_num_map_insert, lastMonth);
				thaiService.batchUpdateStbLoginAnalysisTblMonth(login_retailer_num_map_update, lastMonth);
				thaiService.batchUpdateStbActivationAnalysisTblMonth(register_retailer_num_map_update, lastMonth);
				
				thaiService.insertOrUpdateStbLoginAnalysisTblMonthTotal(login_total_num, lastMonth);
				thaiService.insertOrUpdateStbActivationAnalysisTblMonthTotal(register_total_num, lastMonth);
				log.info("*************************终端上线率统计结束***********************************");
			}

			
			TimerSchedule.running_flag_stb_analysis = false;
		}
		
	}
	private class updateTmlLoginTime extends TimerTask {
		public synchronized void run() {
			log.info("##########updateTmlLoginTime############");
			thaiService = new ThaiServiceImpl();
			Map<String,TmlDto> map = ThaiLogin.loginTime;
			thaiService.batchUpdateLoginTime(map.values());
		}
	}
	private boolean isTmlOutdate(long date,String tmlId){
    	long nowTime = System.currentTimeMillis();
    	//从该终端tmlId所属的分组查询心跳间隔
    	Integer tmlOutTime= 60;
    	//以下代码效率太低下，因此先注释掉，心跳周期默认就是60s
//    	if(StringUtils.isEmpty(thaiService.getHeartBeatRateByTmlId(tmlId))){
//    		tmlOutTime=60;
//    	}else{
//    		tmlOutTime=Integer.valueOf(thaiService.getHeartBeatRateByTmlId(tmlId));
//    	};
    	if(nowTime - date > 3*tmlOutTime*1000)
    		return true;
    	else
    		return false;
    }
	

    private boolean isOutdate(long date){
    	long nowTime = System.currentTimeMillis();
    	if(nowTime - date > 5*60*1000)
    		return true;
    	else
    		return false;
    }
	private class CheckAD extends TimerTask{
		@Override
		public synchronized void run() {
			Calendar calendar = Calendar.getInstance();
			if(calendar.get(Calendar.HOUR_OF_DAY) == 3) {
				log.info("##########CHECK AD############");
				OrderMapMemory o = OrderMapMemory.getInstance();
				String[] ad = o.getAdRecord();
				if (ad != null && ad.length >= 5) {
					String outDate = ad[4];
					log.info("outDate:"+ad[4]+", "+ad[0]);
					if (outDate != null) {
						String now = PackageConstant.sdfYMD.format(new Date());
						if (distanceDate(now, outDate) > 0) {
							o.cleanAdMac();
							o.setAdRecord();
						}
					}
				}
			}
		}
	}

	/**
	 * 每10分钟检查一次订购单的状态，入库
	 */
	private class CheckDownStatus extends TimerTask {
		@Override
		public synchronized void run() {
			OrderMapMemory map = OrderMapMemory.getInstance();
			List<StatusDTO> needUpd = new ArrayList<StatusDTO>();

			// 检查订购单的状态
			needUpd = map.getDownOkOrders();
			if (needUpd != null && needUpd.size() != 0) {
				boolean flg = true;
				ITmlService service = new TmlServiceImpl();
				flg = service.updateOrder(needUpd);
				//log.info("[TimerSchedule]:update orders:needUpd="+needUpd+", flg="+flg);
				if (flg) {
					List<StatusDTO> ok = new ArrayList<StatusDTO>();
					for (StatusDTO d : needUpd) {
						ok.add(d);
					}
					map.remUpdOkOrders(ok);
				}
			}

			// 更新影片下载次数
			List<StatusDTO> needUpd0 = new ArrayList<StatusDTO>();

			List<StatusDTO> needUpdVideo = new ArrayList<StatusDTO>();
			needUpdVideo = map.getVideoDownTimes();
			needUpd0.addAll(needUpdVideo);

			List<StatusDTO> needUpdMusic = new ArrayList<StatusDTO>();
			needUpdMusic = map.getMusicDownTimes();
			needUpd0.addAll(needUpdMusic);

			if (needUpd0 != null && needUpd0.size() != 0) {
				boolean flg = true;
				ITmlService service = new TmlServiceImpl();
				flg = service.updateDownTimes(needUpd0);
				log.info("[TimerSchedule]:update times: flg="+flg);
				if (flg) {
					/*List<StatusDTO> ok = new ArrayList<StatusDTO>();
					for (StatusDTO d : needUpd0) {
						ok.add(d);
					}*/
					map.remVideoDownTimes(needUpdVideo);
					map.remMusicDownTimes(needUpdMusic);
				}
			}
		}
	}

	private long distanceDate(String startDate, String endDate) {
		if (startDate == null || startDate.trim().equals("")
				|| endDate == null || endDate.trim().equals("")) return 0;
		long distanceMin = 0;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 

		Date date1=null; 
		Date date2=null;

		try {
			date1=format.parse(startDate);
			date2 = format.parse(endDate);

			Calendar ca1 = Calendar.getInstance(); 
			Calendar ca2 = Calendar.getInstance(); 
			ca1.setTime(date1); 
			ca2.setTime(date2);

			distanceMin = ( ca2.getTimeInMillis()- ca1.getTimeInMillis())/(1000*60*60*24); 
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return distanceMin;
	}
	
	
	
	
	
	
	
	private static Long getMsTimeToNextHour(){
		try {
			Calendar calendar=Calendar.getInstance();
			long timeInMillis = calendar.getTimeInMillis();
			SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse=null;
			parse = sdFormat.parse(DateUtils.calculateHours(new Date(), +1).substring(0, 13)+":00:00:00");
			calendar.setTime(parse);
			long curretMsTime = calendar.getTimeInMillis();
			return curretMsTime-timeInMillis;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("*****计算失败*****"+e);
		}
		return null;
	}
	
	
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getMsTimeToNextHour());
		
		
//
//			ThaiServiceImpl thaiService=new ThaiServiceImpl();
//			Map<String,Long> map = HeartBeat.heartbeatMap;
//			log.info("##########CheckTmlStatus############  在线终端的map : " + map);
//			//
//			List<String> allOnlineTmlIdsByHour=new ArrayList<String>();
//			Set<String> tmlIds = map.keySet();
//			tmlIds=new HashSet<String>();
//			tmlIds.add("8810361FEC63");
//			/*
//			 * 查找当前在线map
//			 * for{
//			 * 	查找最近一次的2点前的最近一次online时间
//			 * 	如果online时间在1点到2点之间，在线时间即为2点减去online
//			 * 	如果online时间在1点前，在线时间即为2点到1点都在线，即在线时长增加一个小时
//			 *	 时间是按小时累加
//			 * }
//			 * 
//			 * 
//			 */
//			
//			
//			//统计当前时间前的在线的终端的在线时长
//			Date currentDate = new Date();
//			//tmlIds为此时在线的终端的Id的集合 
////			String currenTime = DateUtils.getCurrentTime();
//			String currenTime = "2017-07-28 03:00:00";
//			String createTime=currenTime;
//			String currenTimeHour=currenTime.substring(0, 13)+":00:00";   //格式: 2017-07-26 12
//			String reduceTimeHour=DateUtils.calculateHours(currentDate, -1).substring(0, 13)+":00:00";   //
//			long onlineDuration=0L;    //在线时长
//			int isBelongHours=0;     //1表示终端不是在当天上线，2表示终端的在线时间在当前时间1小时内 3表示终端的在线时间在当前时间1小时外
//			List<SingleStbOnlineDuration> singleStbOnlineDurationList=new ArrayList<SingleStbOnlineDuration>();
//			String tmpOnlineTime="";
//			String tmpOfflineTime="";
//			for (String tmlId : tmlIds) {
//				isBelongHours=0;
//				List<TmlHistory> tmlHistoryList = thaiService.findTmlHistory(tmlId, "", "", currenTime, "DESC", 1);
//				
//				if(tmlHistoryList!=null&&tmlHistoryList.size()>0){
//					TmlHistory tmlHistory = tmlHistoryList.get(0);
//					//终端目前仍在线
//					if("online".equals(tmlHistory.getType())){
//						String onlineTime=tmlHistory.getNftime();//上线时间
//							//终端不是在当天上线
//						if(!onlineTime.substring(0, 10).equals(currenTime.substring(0, 10))){
//							//将上线时间视为当天0点
//							isBelongHours=1;   
//							
////							onlineTime=currenTime.substring(0, 10)+" 00:00:00";  
////							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime); //在线时长=当前时间-上线时间
//							
//							if(!"00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
//								tmpOnlineTime=currenTime.substring(0, 10)+" 00:00:00";
//								onlineDuration=DateUtils.getMs(currenTimeHour, tmpOnlineTime); //在线时长=当前时间-上线时间
////								createTime=DateUtils.calculateDays(currentDate, -1);
////								onlineTime=tmlHistory.getNftime();
//							}else{
//								tmpOfflineTime=currenTime.substring(0, 10)+" 00:00:00";
//								onlineDuration=DateUtils.getMs(tmpOfflineTime, onlineTime);
//							}
//						}//终端的在线时间在当前时间1小时内
//						else if(onlineTime.compareTo(reduceTimeHour)>0&&onlineTime.compareTo(currenTimeHour)<0){
//							isBelongHours=2;
//							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime); //在线时长=当前时间-上线时间
//						}//终端的在线时间在当前时间1小时外
//						else{
//							isBelongHours=3;
//							onlineDuration=DateUtils.getMs(currenTimeHour, onlineTime);
//						}
//						if("00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
//							createTime=DateUtils.calculateDays(currentDate, -1);
////							onlineTime=tmlHistory.getNftime();
//						}
//						if(!StringUtils.isEmpty(tmpOnlineTime)){
//							onlineTime=tmpOnlineTime;
//						}
//						SingleStbOnlineDuration singleStbOnlineDuration = getSingleStbOnlineDuration(tmlId,createTime,onlineTime);
//						if(singleStbOnlineDuration==null){
//							singleStbOnlineDuration=new SingleStbOnlineDuration();
//							singleStbOnlineDuration.setTmlId(tmlId);
//							singleStbOnlineDuration.setCreateTime(createTime);
//							singleStbOnlineDuration.setOnlineTime(onlineTime);
////							if(1==isBelongHours){
////								if(!createTime.substring(0, 10).equals(currenTime.substring(0, 10))){
////
////									singleStbOnlineDuration.setOfflineTime(currenTime.substring(0, 10)+" 00:00:00");
////								}
////							}
//							singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//						}else{
//							
//							if(2==isBelongHours){
//								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//							}else if(3==isBelongHours){ 
//								onlineDuration=Long.valueOf(singleStbOnlineDuration.getOnlineDuration());
//								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration+60*60*1000));
//							}
//						}
//						if(1==isBelongHours){
//							if(!StringUtils.isEmpty(tmpOfflineTime)){
//								singleStbOnlineDuration.setOfflineTime(tmpOfflineTime);
//								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//							}
//							if(!StringUtils.isEmpty(tmpOnlineTime)){
//								singleStbOnlineDuration.setOnlineTime(tmpOnlineTime);
//								singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//							}
//						}
//						singleStbOnlineDurationList.add(singleStbOnlineDuration);
//					}
//				}
//			}//end 148 if(tmlHistoryList!=null&&tmlHistoryList.size()>=1){
//			
//			///////////////////////////////////
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			
//			/////////////////////////////////////////
//			
//			
//			
//			//统计当前时间1小时内 下线的盒子
//			List<TmlHistory> offlineTmlHistoryList = thaiService.findTmlHistory("", "offline", reduceTimeHour, currenTime, "DESC", 0);
//			if(offlineTmlHistoryList!=null&&offlineTmlHistoryList.size()>0){
//				String onlineTime="";
//				for(int k=0;k<offlineTmlHistoryList.size();k++){
//					String offlineTime=offlineTmlHistoryList.get(k).getNftime();  //下线时间
//					String offlineTmlId=offlineTmlHistoryList.get(k).getTmlId();  //下线终端的ID
//					allOnlineTmlIdsByHour.add(offlineTmlId);
//					List<TmlHistory> onlineTmlHistoryList = thaiService.findTmlHistory(offlineTmlId, "online", reduceTimeHour, offlineTime, "DESC", 1);
//					//说明在当前1小时内该下线的终端前存在一个online的数据
//					if(onlineTmlHistoryList!=null&&onlineTmlHistoryList.size()>0){
//						TmlHistory onlineTmlHistory=onlineTmlHistoryList.get(0);
//						onlineTime=onlineTmlHistory.getNftime();//上线时间
//						
//						
//					}//说明在当前1小时内该下线的终端不存在一个online的数据
//					else{
//						onlineTmlHistoryList = thaiService.findTmlHistory(offlineTmlId, "online", "",reduceTimeHour, "DESC", 1);
//						TmlHistory onlineTmlHistory=onlineTmlHistoryList.get(0);
//						onlineTime=onlineTmlHistory.getNftime();//上线时间
//						 //上线时间不在当天
//						if(!onlineTime.substring(0, 10).equals(currenTime.substring(0, 10))){
//							if(!"00:00:00".equals(currenTimeHour.substring(11, 19))){  //零点时间
//								onlineTime=currenTime.substring(0, 10)+" 00:00:00";
//							}
//						}
//						
//					}
//					onlineDuration=DateUtils.getMs(offlineTime, onlineTime);  //在线时长
//					SingleStbOnlineDuration singleStbOnlineDuration = getSingleStbOnlineDuration(offlineTmlId,createTime,onlineTime);
//					if(singleStbOnlineDuration==null){
//						singleStbOnlineDuration=new SingleStbOnlineDuration();
//						singleStbOnlineDuration.setTmlId(offlineTmlId);
//						singleStbOnlineDuration.setCreateTime(createTime);
//						singleStbOnlineDuration.setOnlineTime(onlineTime);
//						
//						singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//					}else{
//						singleStbOnlineDuration.setOnlineDuration(String.valueOf(onlineDuration));
//					}
//					if(!StringUtils.isEmpty(offlineTime)){
//						
//						singleStbOnlineDuration.setOfflineTime(offlineTime);
//					}
//					singleStbOnlineDurationList.add(singleStbOnlineDuration);
//				}
//			}
//			
//			
//			//批量保存单终端在线时长 数据
//			if(singleStbOnlineDurationList!=null&&singleStbOnlineDurationList.size()>0){
//				try {
//					DynamoDBConnenctUtil.getMappper().batchSave(singleStbOnlineDurationList);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					log.error("********保存失败："+e);
//				}
//			}
//			if(tmlIds!=null&&tmlIds.size()>0){
//				allOnlineTmlIdsByHour.addAll(tmlIds);
//			}
//			allOnlineTmlIdsByHour=new ArrayList<String>(new LinkedHashSet<String>(allOnlineTmlIdsByHour)); //给集合的元素去重
//			List<SingleStbOnlineDurationTotal> singleTmlTotalList=new ArrayList<SingleStbOnlineDurationTotal>();
//			for(String tmlId:allOnlineTmlIdsByHour){
//				SingleStbOnlineDurationTotal singleStbOnlineDurationTotal = statisticalTmlByDay(tmlId,createTime);
//				singleTmlTotalList.add(singleStbOnlineDurationTotal);
//			}
//			//批量保存单终端total在线时长 数据
//			if(singleTmlTotalList!=null&&singleTmlTotalList.size()>0){
//				try {
//					DynamoDBConnenctUtil.getMappper().batchSave(singleTmlTotalList);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					log.error("********保存失败："+e);
//				}
//			}
//			/*1 搜2点到1点间的tml_history 所有下线记录
//			2 for (每一条下线记录) {
//				查改条下线记录的最近一次上线时间。
//				如果最近一次上线时间，在1点到两点之间，本次下线记录的在线时长可计算。
//				如果最近一次的上线时间，在1点之前，本次下线记录的在线时长可计算为下线时间减去1点。
//			}
//			
//			
//			如果当前时间是0点，当前计算的时长，加到前一天去。
//			
//			*
//			*
//			*/
//	
	}
}
