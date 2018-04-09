package communicate.pkmgmt.stb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.GetAddressByIp;
import util.IpDirctionaryImpl;
import util.ip.IpHelper;

import com.alibaba.fastjson.JSON;

import communicate.common.Constants;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.dto.TmlBase;

public class HeartBeat {
	public static int header_len = 22;
	private static Logger log = communicate.common.Logger.getLogger(HeartBeat.class);
	private IThaiService thaiService = null;
	public static Map<String, Long> heartbeatMap = new ConcurrentHashMap<String, Long>();
	/**
	 * 解析收到的数据包
	 * @param leftBuf
	 * @param len
	 * @param offset
	 * @param sendBuf
	 * @return
	 */
	public byte[] parseData(byte[] leftBuf, int len, int offset,String remoteIP) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}
		//log.debug("———————————测试心跳包————————————————————");
		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		
		log.debug("获取的tmlId:"+tmlId +"  获取的tmlType:"+tmlType + "———————————心跳开始————————————————————");
		
		String net_CheckResult = "aaa";
		String CmdResult = "bbb";
		//String net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult).toString();
		net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult);	
		log.debug("11111  CmdResult:"+CmdResult);
		if (net_CheckResult != null){
			log.debug("获取的net_CheckResult:"+net_CheckResult);
		}
	
		CmdResult = tlvs.getStringValue(TLVTag.CmdResult);
		if (CmdResult != null){
			log.debug("获取的CmdResult:"+CmdResult);
		 }
		log.debug("22222CmdResult:"+CmdResult);

		String contentName = tlvs.getStringValue(TLVTag.Content_Name);
//		int serviceType = tlvs.getIntValue(TLVTag.Service_Type);
//		log.info("------STB: 心跳获取的tlv数据\n------tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+", serviceType="+serviceType);
		log.debug("------STB:心跳获取的tlv数据\n------tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+",net_CheckResult="+net_CheckResult+", CmdResult="+CmdResult);
		String country="";
		try {
			country = IpDirctionaryImpl.getIpAndCourty(remoteIP);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.debug("*********根据remoteIP："+remoteIP+"解析国家",e);
		}
		thaiService = new ThaiServiceImpl();

		thaiService.updateTmlPlayMsg(tmlId, contentName,remoteIP,country, 0);

		int cmdNo=0;
//		String cmdResult="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time=df.format(new Date());// new Date()为获取当前系统时间

		if(net_CheckResult != null){
			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(net_CheckResult);
			cmdNo=Integer.parseInt(jo.get("cmdNo").toString());
			String result=jo.get("Result").toString();
			log.debug("net_CheckResult获取的result："+result);
			thaiService.updateOperateHistory(cmdNo, time, result);
		}

		if(CmdResult!= null){

			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(CmdResult);

			String resultString=jo.get("result").toString();

			log.debug("resultString:"+resultString);
			String result="";
			JSONArray jsonArr = JSONArray.fromObject(resultString);
		    for (int i = 0; i < jsonArr.size(); i++) {
		    	//移入移除黑名单处理
		    	TmlBase tmlbaseDtoByTmlId = thaiService.getTmlbaseDtoByTmlId(tmlId);
				if(tmlbaseDtoByTmlId!=null){
					if(2==tmlbaseDtoByTmlId.getAppStatus()){
						thaiService.updateAppStatus(tmlId, 0);
					}else if(1==tmlbaseDtoByTmlId.getAppStatus()){
						thaiService.updateAppStatus(tmlId, 3);
					}
				}
		    	cmdNo=Integer.parseInt(jsonArr.getJSONObject(i).getString("cmdNo").toString());
				log.info("第"+i+"个cmdNo："+cmdNo);
//				result=jsonArr.getJSONObject(i).getString("isSucceed").toString();
//				log.info("第"+i+"个result："+result);
				thaiService.updateOperateHistory(cmdNo, time, "yes");
//				thaiService.updateOperateHistory(cmdNo, time, result);
		    }		
		}		
	    log.debug("步骤5555");
		int processCode = 0;
		int code=1020;
		String processStr="";
		List<String> code1010List=new ArrayList<String>();
		List<String> code1020List=new ArrayList<String>();
		List<String> code1030List=new ArrayList<String>();
		List<String> code1040List=new ArrayList<String>();
		List<String> code1050List=new ArrayList<String>();
		List<String> code1060List=new ArrayList<String>();
		List<String> code1070List=new ArrayList<String>();
		List<String> code1080List=new ArrayList<String>();
		List<String> code1090List=new ArrayList<String>();
		List<String> code1100List=new ArrayList<String>();
		Map<String, List<String>> tmlProcessMap=Constants.tmlProcessMap;
		if(tmlProcessMap.containsKey(tmlId)){
			List<String> processList = tmlProcessMap.get(tmlId);
			for(int i=0;i<processList.size();i++){
				log.debug("step0001");
				String processString =processList.get(i);
			    log.debug("获取的value："+processString);
				com.alibaba.fastjson.JSONObject jo = JSON.parseObject(processString);
				String processcodestr=jo.get("processcode").toString();
				log.debug("获取的操作码processcodestr："+processcodestr);
//				Constants.tmlProcessMap.remove(i);
//				i--;
				//升级操作
				if(processcodestr.equals("1010")){
					log.debug("step1111");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
	//			    log.info("jsonObject:"+jsonObject);
				    String code1010=jsonObject.toString();
				    log.info("code1010:"+code1010);
				    code=1010;		
				    code1010List.add(code1010);
				}
				//设备信息上传
				if(processcodestr.equals("1020")){
					log.debug("step2222");
					String cmdnocallback=jo.get("cmdnocallback").toString();
//					String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
					log.info("cmdnocallback:"+cmdnocallback);
//					log.info("logdebugupdateUrl:"+logdebugupdateUrl);
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);   
//				     map.put("logdebugupdateUrl", logdebugupdateUrl);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				    String code1020=jsonObject.toString();
				    log.info("code1020:"+code1020);
				    code=1020;		
				    code1020List.add(code1020);
				}
				//日志上传
				if(processcodestr.equals("1030")){
					 System.out.println("step3333");
					 String cmdnocallback=jo.get("cmdnocallback").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     String code1030=jsonObject.toString();
				     log.info("code1030:"+code1030);
				     code=1030;	
				     code1030List.add(code1030);
				}
				//配置下发指令
				if(processcodestr.equals("1040")){
					log.debug("step4444");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String code1040=jsonObject.toString();
				    code1040List.add(code1040);
				    log.info("code1040:"+code1040);				 	
				}
				//终端指令下发/ping
				if(processcodestr.equals("1050")){
					log.debug("step5555");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String cmdLine=jo.get("cmdLine").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    map.put("cmdLine", cmdLine);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String code1050=jsonObject.toString();
				    code1050List.add(code1050);
				    log.info("code1050:"+code1050);				 	
				}
				//重启
				if(processcodestr.equals("1060")){
					log.debug("step6666");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String code1060=jsonObject.toString();
				    code1060List.add(code1060);
				    log.info("code1060:"+code1060);
				 
				}
				//恢复出厂设置
				if(processcodestr.equals("1070")){
					log.debug("step7777");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String  code1070=jsonObject.toString();
				    code1070List.add(code1070);
				    log.info("code1070:"+code1070);
				 
				}
				//黑名单
				if(processcodestr.equals("1080")){
					log.debug("step8888");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String isActived=jo.get("isActived").toString();
				    Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    map.put("isActived", isActived);
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String code1080=jsonObject.toString();
				    code1080List.add(code1080);
				    log.info("code1080:"+code1080);
				}
				//推送信息
				if(processcodestr.equals("1090")){
					log.debug("step9999");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String mSG=jo.get("MSG").toString();
					Map map = new HashMap();  
				    map.put("cmdnocallback", cmdnocallback);  
				    map.put("MSG", mSG);
				    JSONObject jsonObject  = JSONObject.fromObject(map);
//				    log.info("jsonObject:"+jsonObject);
				    String code1090=jsonObject.toString();
				    code1090List.add(code1090);
				    log.info("code1090:"+code1090);
				}	
				//调试日志上传
				if(processcodestr.equals("1100")){
					log.debug("调试日志上传");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
					log.debug("cmdnocallback:"+cmdnocallback);
					log.debug("logdebugupdateUrl:"+logdebugupdateUrl);
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);   
				     map.put("logdebugupdateUrl", logdebugupdateUrl);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//					 log.info("jsonObject:"+jsonObject);
				     String code1100=jsonObject.toString();
				     code1100List.add(code1100);
				     log.debug("code1100:"+code1100);
				     code=1100;				 
				}
				//匹配完指令后，要移除该指令，避免重复执行该指令
				processList.remove(i);
			}
		}
		long time2 = System.currentTimeMillis();
	
//		if(!heartbeatMap.containsKey(tmlId)){
//			thaiService.addTmlHistory(tmlId, DateUtils.getCurrentTime(), "online");
//		}

		heartbeatMap.put(tmlId, time2);
		
		log.debug("———————————测试心跳包发送————————————————————");
		log.debug("操作结束之后的code:" + code);
		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code,code1010List,code1020List,code1030List,code1040List,code1050List,code1060List,code1070List,code1080List,code1090List,code1100List);
//		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code);
	}

	
//	public byte[] parseData(byte[] leftBuf, int len, int offset) {
//		TLVPairList tlvs = new TLVPairList();
//		try {
//			tlvs.loadTLVs(leftBuf, offset, len-offset);
//		} catch (Exception e) {
//			log.error(e, e);
//		}
//		System.out.println("———————————测试心跳包————————————————————");
//		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
//		System.out.println("获取的tmlId:"+tmlId);
//		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
//		System.out.println("获取的tmlType:"+tmlType);
//		System.out.println("———————————心跳开始————————————————————");
//		
//		String net_CheckResult = "aaa";
//		String CmdResult = "bbb";
//		//String net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult).toString();
//		net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult);	
//		System.out.println("11111CmdResult:"+CmdResult);
//		if (net_CheckResult != null){
//			System.out.println("获取的net_CheckResult:"+net_CheckResult);
//		}
//	
//		CmdResult = tlvs.getStringValue(TLVTag.CmdResult);
//			if (CmdResult != null){
//			System.out.println("获取的CmdResult:"+CmdResult);
//		 }
//		System.out.println("22222CmdResult:"+CmdResult);
//		System.out.println("———————————1111————————————————————");
//		String contentName = tlvs.getStringValue(TLVTag.Content_Name);
////		int serviceType = tlvs.getIntValue(TLVTag.Service_Type);
////		log.info("------STB: 心跳获取的tlv数据\n------tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+", serviceType="+serviceType);
//		System.out.println("------STB:心跳获取的tlv数据\n------tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+",net_CheckResult="+net_CheckResult+", CmdResult="+CmdResult);
//		System.out.println("步骤2222");
//		thaiService = new ThaiServiceImpl();
//		System.out.println("步骤333");
//		thaiService.updateTmlPlayMsg(tmlId, contentName, 0);
//		System.out.println("步骤444");
//		int cmdNo=0;
////		String cmdResult="";
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//		String time=df.format(new Date());// new Date()为获取当前系统时间
//		System.out.println("步骤444111111");
//		if(net_CheckResult != null){
//			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(net_CheckResult);
//			cmdNo=Integer.parseInt(jo.get("cmdNo").toString());
//			String result=jo.get("Result").toString();
//			System.out.println("net_CheckResult获取的result："+result);
//			thaiService.updateOperateHistory(cmdNo, time, result);
//		}
//		System.out.println("步骤4442222");
//		if(CmdResult!= null){
//			log.info("step2222:");
//			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(CmdResult);
//			log.info("step333:");
//			String resultString=jo.get("result").toString();
//			log.info("step444:");
//			System.out.println("resultString:"+resultString);
//			String result="";
//			 JSONArray jsonArr = JSONArray.fromObject(resultString);
//			   for (int i = 0; i < jsonArr.size(); i++) {
//			cmdNo=Integer.parseInt(jsonArr.getJSONObject(i).getString("cmdNo").toString());
//			log.info("第"+i+"个cmdNo："+cmdNo);
//			result=jsonArr.getJSONObject(i).getString("isSucceed").toString();
//			log.info("第"+i+"个result："+result);
//			thaiService.updateOperateHistory(cmdNo, time, result);
//			   }		
//		}		
//		System.out.println("步骤5555");
//		int processCode = 0;
//		int code=1020;
//		String processStr="";
//		String code1010=null;
//		String code1020=null;
//		String code1030=null;
//		String code1040=null;
//		String code1050=null;
//		String code1060=null;
//		String code1070=null;
//		String code1080=null;
//		String code1090=null;
//		String code1100=null;
//		for(int i=0;i<Constants.listmap.size();i++){
//			System.out.println("步骤6666");
//			System.out.println("心跳size大小"+Constants.listmap.size());
//			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!step0000,第"+i+"个");
//			log.info("心跳size大小"+Constants.listmap.size());	
//			log.info("!!!!!!!!!!!!!!!!!!!!!!!step0000,第"+i+"个");	
////	   		 for (String s : Constants.listmap.get(i).values()) {
////				 log.info("values:" + s);
////			 }
//			 for (String s : Constants.listmap.get(i).keySet()) {
////    			 log.info("key:" + s);
////    			 log.info("values:" + Constants.listmap.get(i).get(s));
//    			 System.out.println("key:" + s);
//    			 System.out.println("values:" + Constants.listmap.get(i).get(s));
//    			 if(s.equals(tmlId)){
//		    		    System.out.println("step0001");
//						String processString =Constants.listmap.get(i).get(s);
//					   System.out.println("获取的value："+processString);
//						com.alibaba.fastjson.JSONObject jo = JSON.parseObject(processString);
//						String processcodestr=jo.get("processcode").toString();
//						 System.out.println("获取的操作码processcodestr："+processcodestr);
//						Constants.listmap.remove(i);
//						i--;
//						if(processcodestr.equals("1010")){
//					   System.out.println("step1111");
//						String cmdnocallback=jo.get("cmdnocallback").toString();
//						 Map map = new HashMap();  
//					     map.put("cmdnocallback", cmdnocallback);  
//					     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//			     log.info("jsonObject:"+jsonObject);
//					     code1010=jsonObject.toString();
//					     log.info("code1010:"+code1010);
//					     code=1010;			 	
//							}
//						if(processcodestr.equals("1020")){
//						System.out.println("step2222");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//		//					String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
//							log.info("cmdnocallback:"+cmdnocallback);
//		//					log.info("logdebugupdateUrl:"+logdebugupdateUrl);
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);   
//		//				     map.put("logdebugupdateUrl", logdebugupdateUrl);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1020=jsonObject.toString();
//						     log.info("code1020:"+code1020);
//						     code=1020;				 
//							}
//						if(processcodestr.equals("1030")){
//							 System.out.println("step3333");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1030=jsonObject.toString();
//						     log.info("code1030:"+code1030);
//						     code=1030;				 
//							}
//						if(processcodestr.equals("1040")){
//							 System.out.println("step4444");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1040=jsonObject.toString();
//						     log.info("code1040:"+code1040);				 	
//							}
//						if(processcodestr.equals("1050")){
//							 System.out.println("step5555");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							String cmdLine=jo.get("cmdLine").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     map.put("cmdLine", cmdLine);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1050=jsonObject.toString();
//						     log.info("code1050:"+code1050);				 	
//						}
//						if(processcodestr.equals("1060")){
//							 System.out.println("step6666");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1060=jsonObject.toString();
//						     log.info("code1060:"+code1060);
//						 
//						}
//						if(processcodestr.equals("1070")){
//							System.out.println("step7777");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1070=jsonObject.toString();
//						     log.info("code1070:"+code1070);
//						 
//						}
//						if(processcodestr.equals("1080")){
//							System.out.println("step8888");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							String isActived=jo.get("isActived").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     map.put("isActived", isActived);
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1080=jsonObject.toString();
//						     log.info("code1080:"+code1080);
//						 
//						}
//						
//						if(processcodestr.equals("1090")){
//							System.out.println("step9999");
//							String cmdnocallback=jo.get("cmdnocallback").toString();
//							String mSG=jo.get("MSG").toString();
//							 Map map = new HashMap();  
//						     map.put("cmdnocallback", cmdnocallback);  
//						     map.put("MSG", mSG);
//						     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//				     log.info("jsonObject:"+jsonObject);
//						     code1090=jsonObject.toString();
//						     log.info("code1090:"+code1090);
//						}	
//						if(processcodestr.equals("1100")){
//							    System.out.println("调试日志上传");
//								String cmdnocallback=jo.get("cmdnocallback").toString();
//								String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
//								System.out.println("cmdnocallback:"+cmdnocallback);
//								System.out.println("logdebugupdateUrl:"+logdebugupdateUrl);
//								 Map map = new HashMap();  
//							     map.put("cmdnocallback", cmdnocallback);   
//							     map.put("logdebugupdateUrl", logdebugupdateUrl);  
//							     JSONObject jsonObject  = JSONObject.fromObject(map);
//		//					     log.info("jsonObject:"+jsonObject);
//							     code1100=jsonObject.toString();
//							     System.out.println("code1100:"+code1100);
//							     code=1100;				 
//								}
//			 }//if
//			 }	
//		}	
//		long time2 = System.currentTimeMillis();
//		heartbeatMap.put(tmlId, time2);
//		System.out.println("———————————测试心跳包发送————————————————————");
//		System.out.println("操作结束之后的code:" + code);
//		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code,code1010,code1020,code1030,code1040,code1050,code1060,code1070,code1080,code1090,code1100);
////		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code);
//	}
	
	public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode,List<String> code1010List,List<String> code1020List,List<String> code1030List,List<String> code1040List,List<String> code1050List,List<String> code1060List,List<String> code1070List,List<String> code1080List,List<String> code1090List,List<String> code1100List) {
//	public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode) {	
	// tmlId TLV
		
				byte[] tmlIdbyte = tmlId.getBytes();
				TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
				byte[] tmlId_b = tmlIdTLV.getTLV();
				int tmlId_b_len = tmlId_b.length;
				 log.info("tmlId_b_len:"+tmlId_b_len);
				// count TLV
				byte[] abytes = new byte[4];
				abytes[0] = (byte)((processCode >> 24) & 0xff);
				abytes[1] = (byte)((processCode >> 16) & 0xff);
				abytes[2] = (byte)((processCode >> 8) & 0xff);
				abytes[3] = (byte)(processCode & 0xff);
				TLVPair countTLV = new TLVPair(TLVTag.infoType, abytes);
				byte[] count_b = countTLV.getTLV();
				int count_b_len = count_b.length;
				 log.info("count_b_len:"+count_b_len);
				 
		// code1010
				
//				 byte[] code1010byte = code1010.getBytes();
//				 log.info("step122:");
//				 TLVPair code1010TLV = new TLVPair(TLVTag.Code1010, code1010byte);
//				 log.info("step133:");
//				 byte[] code1010_b = code1010TLV.getTLV();
//				 log.info("step144:");
//				 int code1010_b_len = code1010_b.length;
//				 log.info("step155:");
//				 log.info("code1010_b_len:"+code1010_b_len);  
				
				  int code1020_b_len =0;
				  byte[] code1020_b=null;
			 if(code1020List!=null&&code1020List.size()>0){
			    byte[] code1020byte = code1020List.get(0).getBytes();
			    TLVPair code1020TLV = new TLVPair(TLVTag.Code1020, code1020byte);
			    code1020_b = code1020TLV.getTLV();
			    code1020_b_len = (code1020_b.length)*code1020List.size();
			   
			 }
			    
			  int code1030_b_len =0;
			  byte[] code1030_b=null;
			 if(code1030List!=null&&code1030List.size()>0){
				byte[] code1030byte = code1030List.get(0).getBytes();
				
				TLVPair code1030TLV = new TLVPair(TLVTag.Code1030, code1030byte);
			        code1030_b = code1030TLV.getTLV();
				    code1030_b_len = (code1030_b.length)*code1030List.size();
				
			 }
				 
			 int code1040_b_len =0;
			 byte[] code1040_b=null;
			 if(code1040List!=null&&code1040List.size()>0){
				byte[] code1040byte = code1040List.get(0).getBytes();
				TLVPair code1040TLV = new TLVPair(TLVTag.Code1040, code1040byte);
				code1040_b = code1040TLV.getTLV();
				code1040_b_len = (code1040_b.length)*code1040List.size();
				
			 }
				 
				 int code1050_b_len =0;
				 byte[] code1050_b=null;
			 if(code1050List!=null&&code1050List.size()>0){
				byte[] code1050byte = code1050List.get(0).getBytes();
				TLVPair code1050TLV = new TLVPair(TLVTag.Code1050, code1050byte);
				code1050_b = code1050TLV.getTLV();
				code1050_b_len = (code1050_b.length)*code1050List.size();
			 }
			
			 int code1060_b_len =0;
			 byte[] code1060_b=null;
		 if(code1060List!=null&&code1060List.size()>0){
			byte[] code1060byte = code1060List.get(0).getBytes();
			TLVPair code1060TLV = new TLVPair(TLVTag.Code1060, code1060byte);
		    code1060_b = code1060TLV.getTLV();
			code1060_b_len =(code1060_b.length)*code1060List.size();
			
		 }
			 
			 int code1070_b_len =0;
			 byte[] code1070_b=null;
		 if(code1070List!=null&&code1070List.size()>0){
			byte[] code1070byte = code1070List.get(0).getBytes();
			TLVPair code1070TLV = new TLVPair(TLVTag.Code1070, code1070byte);
		    code1070_b = code1070TLV.getTLV();
			code1070_b_len = (code1070_b.length)*code1070List.size();
			
		 }
		 int code1080_b_len =0;
		 byte[] code1080_b=null;
	   if(code1080List!=null&&code1080List.size()>0){
			byte[] code1080byte = code1080List.get(0).getBytes();
			TLVPair code1080TLV = new TLVPair(TLVTag.Code1080, code1080byte);
			code1080_b = code1080TLV.getTLV();
		    code1080_b_len = (code1080_b.length)*code1080List.size();
		   
	   }
		  
	   int code1090_b_len =0;
	   byte[] code1090_b=null;
	   if(code1090List!=null&&code1090List.size()>0){
			byte[] code1090byte = code1090List.get(0).getBytes();
			TLVPair code1090TLV = new TLVPair(TLVTag.Code1090, code1090byte);
			code1090_b = code1090TLV.getTLV();
			code1090_b_len = (code1090_b.length)*code1090List.size();
			
		   }	
		   
		    int code1100_b_len =0;
		    byte[] code1100_b=null;
			 if(code1100List!=null&&code1100List.size()>0){
			  byte[] code1100byte = code1100List.get(0).getBytes();
			  TLVPair code1100TLV = new TLVPair(TLVTag.Code1100, code1100byte);
			  code1100_b = code1100TLV.getTLV();
			  code1100_b_len = (code1100_b.length)*code1100List.size();				
			  }   
		int offset = 0;
		int allLen = header_len + tmlId_b_len + count_b_len+code1020_b_len+code1030_b_len+code1040_b_len
				+code1050_b_len+code1060_b_len+code1070_b_len+code1080_b_len+code1090_b_len+code1100_b_len;
//		int allLen = header_len + tmlId_b_len + count_b_len+code1020_b_len;
		
		
		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(count_b, 0, toBuf, offset, count_b_len);
		offset = offset + count_b_len;

//		System.arraycopy(code1010_b, 0, toBuf, offset, code1010_b_len);
//		offset = offset + code1010_b_len;

//
		 if(code1020List!=null&&code1020_b!=null){
			 System.arraycopy(code1020_b, 0, toBuf, offset, code1020_b_len);
			 offset = offset + code1020_b_len;
		 }
		 
		 if(code1030List!=null&&code1030_b!=null){
			 System.arraycopy(code1030_b, 0, toBuf, offset, code1030_b_len);
			 offset = offset + code1030_b_len;
	
		 }
		 if(code1040List!=null&&code1040_b!=null){
			 System.arraycopy(code1040_b, 0, toBuf, offset, code1040_b_len);
			 offset = offset + code1040_b_len;
	
		 }
		 if(code1050List!=null&&code1050_b!=null){
			 System.arraycopy(code1050_b, 0, toBuf, offset, code1050_b_len);
			 offset = offset + code1050_b_len;
		 }
		 
		 if(code1060List!=null&&code1060_b!=null){
			 System.arraycopy(code1060_b, 0, toBuf, offset, code1060_b_len);
			 offset = offset + code1060_b_len;
		
		 }
		 if(code1070List!=null&&code1070_b!=null){
			 System.arraycopy(code1070_b, 0, toBuf, offset, code1070_b_len);
			 offset = offset + code1070_b_len;
	
		 }
		 if(code1080List!=null&&code1080_b!=null){
			 System.arraycopy(code1080_b, 0, toBuf, offset, code1080_b_len);
			 offset = offset + code1080_b_len;
		
		 }
		 if(code1090List!=null&&code1090_b!=null){
			 System.arraycopy(code1090_b, 0, toBuf, offset, code1090_b_len);
			 offset = offset + code1090_b_len;
		 }
		 if(code1100List!=null&&code1100_b!=null){
			System.arraycopy(code1100_b, 0, toBuf, offset, code1100_b_len);
			offset = offset + code1100_b_len;
		 }
		return toBuf;
	}
	
	
//	public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode,String code1010,String code1020,String code1030,String code1040,String code1050,String code1060,String code1070,String code1080,String code1090,String code1100) {
////		public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode) {	
//		// tmlId TLV
//			
//					byte[] tmlIdbyte = tmlId.getBytes();
//					TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
//					byte[] tmlId_b = tmlIdTLV.getTLV();
//					int tmlId_b_len = tmlId_b.length;
//					 log.info("tmlId_b_len:"+tmlId_b_len);
//					// count TLV
//					byte[] abytes = new byte[4];
//					abytes[0] = (byte)((processCode >> 24) & 0xff);
//					abytes[1] = (byte)((processCode >> 16) & 0xff);
//					abytes[2] = (byte)((processCode >> 8) & 0xff);
//					abytes[3] = (byte)(processCode & 0xff);
//					TLVPair countTLV = new TLVPair(TLVTag.infoType, abytes);
//					byte[] count_b = countTLV.getTLV();
//					int count_b_len = count_b.length;
//					 log.info("count_b_len:"+count_b_len);
//					 
//			// code1010
//					
////					 byte[] code1010byte = code1010.getBytes();
////					 log.info("step122:");
////					 TLVPair code1010TLV = new TLVPair(TLVTag.Code1010, code1010byte);
////					 log.info("step133:");
////					 byte[] code1010_b = code1010TLV.getTLV();
////					 log.info("step144:");
////					 int code1010_b_len = code1010_b.length;
////					 log.info("step155:");
////					 log.info("code1010_b_len:"+code1010_b_len);  
//					
//					  int code1020_b_len =0;
//					  byte[] code1020_b=null;
//				 if(code1020!=null){
//				    byte[] code1020byte = code1020.getBytes();
//				    TLVPair code1020TLV = new TLVPair(TLVTag.Code1020, code1020byte);
//				    code1020_b = code1020TLV.getTLV();
//				    code1020_b_len = code1020_b.length;
//				   
//				 }
//				    
//					  int code1030_b_len =0;
//					  byte[] code1030_b=null;
//				 if(code1030!=null){
//					byte[] code1030byte = code1030.getBytes();
//					
//					TLVPair code1030TLV = new TLVPair(TLVTag.Code1030, code1030byte);
//				        code1030_b = code1030TLV.getTLV();
//					    code1030_b_len = code1030_b.length;
//					
//				 }
//					 
//					  int code1040_b_len =0;
//					  byte[] code1040_b=null;
//				 if(code1040!=null){
//					byte[] code1040byte = code1040.getBytes();
//					TLVPair code1040TLV = new TLVPair(TLVTag.Code1040, code1040byte);
//					code1040_b = code1040TLV.getTLV();
//					code1040_b_len = code1040_b.length;
//					
//				 }
//					 
//					 int code1050_b_len =0;
//					 byte[] code1050_b=null;
//				 if(code1050!=null){
//				byte[] code1050byte = code1050.getBytes();
//				TLVPair code1050TLV = new TLVPair(TLVTag.Code1050, code1050byte);
//				code1050_b = code1050TLV.getTLV();
//				code1050_b_len = code1050_b.length;
//				
//				 }
//				
//				 int code1060_b_len =0;
//				 byte[] code1060_b=null;
//			 if(code1060!=null){
//				byte[] code1060byte = code1060.getBytes();
//				TLVPair code1060TLV = new TLVPair(TLVTag.Code1060, code1060byte);
//			    code1060_b = code1060TLV.getTLV();
//				code1060_b_len = code1060_b.length;
//				
//			 }
//				 
//				 int code1070_b_len =0;
//				 byte[] code1070_b=null;
//			 if(code1070!=null){
//				byte[] code1070byte = code1070.getBytes();
//				TLVPair code1070TLV = new TLVPair(TLVTag.Code1070, code1070byte);
//			    code1070_b = code1070TLV.getTLV();
//				code1070_b_len = code1070_b.length;
//				
//			 }
//			 int code1080_b_len =0;
//			 byte[] code1080_b=null;
//		   if(code1080!=null){
//				byte[] code1080byte = code1080.getBytes();
//				TLVPair code1080TLV = new TLVPair(TLVTag.Code1080, code1080byte);
//				 code1080_b = code1080TLV.getTLV();
//			     code1080_b_len = code1080_b.length;
//			   
//		  }
//			  
//			    int code1090_b_len =0;
//				byte[] code1090_b=null;
//			   if(code1090!=null){
//				byte[] code1090byte = code1090.getBytes();
//				TLVPair code1090TLV = new TLVPair(TLVTag.Code1090, code1090byte);
//				code1090_b = code1090TLV.getTLV();
//				code1090_b_len = code1090_b.length;
//				
//			   }	
//			   
//			    int code1100_b_len =0;
//			    byte[] code1100_b=null;
//				 if(code1100!=null){
//				  byte[] code1100byte = code1100.getBytes();
//				  TLVPair code1100TLV = new TLVPair(TLVTag.Code1100, code1100byte);
//				  code1100_b = code1100TLV.getTLV();
//				  code1100_b_len = code1100_b.length;				
//				  }   
//			   
//			   
//			   
//			   
//			   
//			   
//			   
//			int offset = 0;
//			int allLen = header_len + tmlId_b_len + count_b_len+code1020_b_len+code1030_b_len+code1040_b_len
//					+code1050_b_len+code1060_b_len+code1070_b_len+code1080_b_len+code1090_b_len+code1100_b_len;
////			int allLen = header_len + tmlId_b_len + count_b_len+code1020_b_len;
//			
//			
//			byte[] toBuf = createByte(code, allLen);
//
//			offset += header_len;
//
//			System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
//			offset = offset + tmlId_b_len;
//
//			System.arraycopy(count_b, 0, toBuf, offset, count_b_len);
//			offset = offset + count_b_len;
//
////			System.arraycopy(code1010_b, 0, toBuf, offset, code1010_b_len);
////			offset = offset + code1010_b_len;
//
//	//
//			 if(code1020!=null){
//			System.arraycopy(code1020_b, 0, toBuf, offset, code1020_b_len);
//			offset = offset + code1020_b_len;
//			
//			 }
//			 
//			 if(code1030!=null){
//			System.arraycopy(code1030_b, 0, toBuf, offset, code1030_b_len);
//			offset = offset + code1030_b_len;
//		
//			 }
//			 if(code1040!=null){
//			System.arraycopy(code1040_b, 0, toBuf, offset, code1040_b_len);
//			offset = offset + code1040_b_len;
//		
//			 }
//			 if(code1050!=null){
//			System.arraycopy(code1050_b, 0, toBuf, offset, code1050_b_len);
//			offset = offset + code1050_b_len;
//			 }
//			 
//			 if(code1060!=null){
//			System.arraycopy(code1060_b, 0, toBuf, offset, code1060_b_len);
//			offset = offset + code1060_b_len;
//			
//			 }
//			 if(code1070!=null){
//			System.arraycopy(code1070_b, 0, toBuf, offset, code1070_b_len);
//			offset = offset + code1070_b_len;
//		
//			 }
//			 if(code1080!=null){
//			System.arraycopy(code1080_b, 0, toBuf, offset, code1080_b_len);
//			offset = offset + code1080_b_len;
//			
//			 }
//			 if(code1090!=null){
//			System.arraycopy(code1090_b, 0, toBuf, offset, code1090_b_len);
//			offset = offset + code1090_b_len;
//			 }
//			 if(code1100!=null){
//					System.arraycopy(code1100_b, 0, toBuf, offset, code1100_b_len);
//					offset = offset + code1100_b_len;
//					 }
//			return toBuf;
//		}
	public static byte[] createByte(int code, int len) {
		long pkId = System.currentTimeMillis()/1000;
		int offset = 0;
		byte[] headerByte = new byte[len];

		offset += 16;
		headerByte[offset++] = (byte)((code >> 8) & 0xff);
		headerByte[offset++] = (byte)(code & 0xff);
		headerByte[offset++] = (byte)((pkId >> 8) & 0xff);
		headerByte[offset++] = (byte)(pkId & 0xff);

		headerByte[offset++] = (byte)((len >> 8) & 0xff);
		headerByte[offset++] = (byte)(len & 0xff);

		return headerByte;
	}
}
