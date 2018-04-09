package communicate.pkmgmt.stb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.common.Constants;

public class HeartBeat_bank {
	public static int header_len = 22;
	private static Logger log = communicate.common.Logger.getLogger(HeartBeat_bank.class);
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
	public byte[] parseData(byte[] leftBuf, int len, int offset) {
		
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}
		
 		log.debug("———————————测试心跳包————————————————————");
		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		log.info("parseData HeartBeat————TML_ID=" +tmlId);
		
 		log.debug("获取的tmlId:"+tmlId);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		
		
 		log.debug("获取的tmlType:"+tmlType);
 		log.debug("———————————心跳开始————————————————————");
		
		String net_CheckResult = "aaa";
		String CmdResult = "bbb";
		//String net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult).toString();
		net_CheckResult = tlvs.getStringValue(TLVTag.NetCheckResult);	
		
 		log.debug("11111CmdResult:"+CmdResult);
		if (net_CheckResult != null){
			System.out.println("获取的net_CheckResult:"+net_CheckResult);
		}
	
		CmdResult = tlvs.getStringValue(TLVTag.CmdResult);
			if (CmdResult != null){
			System.out.println("获取的CmdResult:"+CmdResult);
		 }
 		log.debug("22222CmdResult:"+CmdResult);

		String contentName = tlvs.getStringValue(TLVTag.Content_Name);
//		int serviceType = tlvs.getIntValue(TLVTag.Service_Type);
//		log.info("------STB: 心跳获取的tlv数据\n------tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+", serviceType="+serviceType);
 		log.debug("------STB:心跳获取的tlv数据-----tmlId="+tmlId+", tmlType="+tmlType+", contentName="+contentName+",net_CheckResult="+net_CheckResult+", CmdResult="+CmdResult);
// 		log.debug("步骤2222");
		thaiService = new ThaiServiceImpl();
// 		log.debug("步骤333");
//		thaiService.updateTmlPlayMsg(tmlId, contentName, 0);
// 		log.debug("步骤444");
		int cmdNo=0;
//		String cmdResult="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time=df.format(new Date());// new Date()为获取当前系统时间
// 		log.debug("步骤444111111");
		if(net_CheckResult != null){
			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(net_CheckResult);
			cmdNo=Integer.parseInt(jo.get("cmdNo").toString());
			String result=jo.get("Result").toString();
			log.debug("net_CheckResult获取的result："+result);
			thaiService.updateOperateHistory(cmdNo, time, result);
		}
// 		log.debug("步骤4442222");
		if(CmdResult!= null){

			com.alibaba.fastjson.JSONObject jo = JSON.parseObject(CmdResult);

			String resultString=jo.get("result").toString();

			String result="";
			 JSONArray jsonArr = JSONArray.fromObject(resultString);
			   for (int i = 0; i < jsonArr.size(); i++) {
			cmdNo=Integer.parseInt(jsonArr.getJSONObject(i).getString("cmdNo").toString());

			result=jsonArr.getJSONObject(i).getString("isSucceed").toString();

			thaiService.updateOperateHistory(cmdNo, time, result);
			   }		
		}		

		int processCode = 0;
		int code=1020;
		String processStr="";
		String code1010=null;
		String code1020=null;
		String code1030=null;
		String code1040=null;
		String code1050=null;
		String code1060=null;
		String code1070=null;
		String code1080=null;
		String code1090=null;
		String code1100=null;
		
		for(int i=0;i<Constants.listmap.size();i++){
//			log.debug("步骤6666");
			log.debug("心跳size大小"+Constants.listmap.size());
			log.debug("!!!!!!!!!!!!!!!!!!!!!!!step0000,第"+i+"个");
			log.debug("心跳size大小"+Constants.listmap.size());	
//			log.info("!!!!!!!!!!!!!!!!!!!!!!!step0000,第"+i+"个");	
//	   		 for (String s : Constants.listmap.get(i).values()) {
//				 log.info("values:" + s);
//			 }
			 for (String s : Constants.listmap.get(i).keySet()) {
//    			 log.info("key:" + s);
//    			 log.info("values:" + Constants.listmap.get(i).get(s));
					log.debug("key:" + s);
					log.debug("values:" + Constants.listmap.get(i).get(s));
    			 if(s.equals(tmlId)){
//    		    System.out.println("step0001");
				String processString =Constants.listmap.get(i).get(s);
//			   System.out.println("获取的value："+processString);
				com.alibaba.fastjson.JSONObject jo = JSON.parseObject(processString);
				String processcodestr=jo.get("processcode").toString();
//				 System.out.println("获取的操作码processcodestr："+processcodestr);
				Constants.listmap.remove(i);
				i--;
				if(processcodestr.equals("1010")){
//			   System.out.println("step1111");
				String cmdnocallback=jo.get("cmdnocallback").toString();
				 Map map = new HashMap();  
			     map.put("cmdnocallback", cmdnocallback);  
			     JSONObject jsonObject  = JSONObject.fromObject(map);
//			     log.info("jsonObject:"+jsonObject);
			     code1010=jsonObject.toString();
			     log.info("code1010:"+code1010);
			     code=1010;			 	
					}
				if(processcodestr.equals("1020")){
//				System.out.println("step2222");
					String cmdnocallback=jo.get("cmdnocallback").toString();
//					String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
					log.info("cmdnocallback:"+cmdnocallback);
//					log.info("logdebugupdateUrl:"+logdebugupdateUrl);
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);   
//				     map.put("logdebugupdateUrl", logdebugupdateUrl);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1020=jsonObject.toString();
				     log.info("code1020:"+code1020);
				     code=1020;				 
					}
				if(processcodestr.equals("1030")){
//					 System.out.println("step3333");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1030=jsonObject.toString();
				     log.info("code1030:"+code1030);
				     code=1030;				 
					}
				if(processcodestr.equals("1040")){
//					 System.out.println("step4444");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1040=jsonObject.toString();
				     log.info("code1040:"+code1040);				 	
					}
				if(processcodestr.equals("1050")){
//					 System.out.println("step5555");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String cmdLine=jo.get("cmdLine").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     map.put("cmdLine", cmdLine);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1050=jsonObject.toString();
				     log.info("code1050:"+code1050);				 	
				}
				if(processcodestr.equals("1060")){
//					 System.out.println("step6666");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1060=jsonObject.toString();
				     log.info("code1060:"+code1060);
				 
				}
				if(processcodestr.equals("1070")){
//					System.out.println("step7777");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1070=jsonObject.toString();
				     log.info("code1070:"+code1070);
				 
				}
				if(processcodestr.equals("1080")){
//					System.out.println("step8888");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String isActived=jo.get("isActived").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     map.put("isActived", isActived);
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1080=jsonObject.toString();
				     log.info("code1080:"+code1080);
				 
				}
				
				if(processcodestr.equals("1090")){
//					System.out.println("step9999");
					String cmdnocallback=jo.get("cmdnocallback").toString();
					String mSG=jo.get("MSG").toString();
					 Map map = new HashMap();  
				     map.put("cmdnocallback", cmdnocallback);  
				     map.put("MSG", mSG);
				     JSONObject jsonObject  = JSONObject.fromObject(map);
//				     log.info("jsonObject:"+jsonObject);
				     code1090=jsonObject.toString();
				     log.info("code1090:"+code1090);
				}	
				if(processcodestr.equals("1100")){
//					    System.out.println("调试日志上传");
						String cmdnocallback=jo.get("cmdnocallback").toString();
						String logdebugupdateUrl=jo.get("logdebugupdateUrl").toString();
//						System.out.println("cmdnocallback:"+cmdnocallback);
//						System.out.println("logdebugupdateUrl:"+logdebugupdateUrl);
						 Map map = new HashMap();  
					     map.put("cmdnocallback", cmdnocallback);   
					     map.put("logdebugupdateUrl", logdebugupdateUrl);  
					     JSONObject jsonObject  = JSONObject.fromObject(map);
//					     log.info("jsonObject:"+jsonObject);
					     code1100=jsonObject.toString();
					     System.out.println("code1100:"+code1100);
					     code=1100;				 
						}
			 }//if
			 }	
		}	
		long time2 = System.currentTimeMillis();
		heartbeatMap.put(tmlId, time2);
 		log.debug("———————————测试心跳包发送————————————————————");
 		log.debug("操作结束之后的code:" + code);
		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code,code1010,code1020,code1030,code1040,code1050,code1060,code1070,code1080,code1090,code1100);
//		return createProcessCodeBuf(PackageConstant.code.refund_order_ACK, tmlId, code);
	}

	public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode,String code1010,String code1020,String code1030,String code1040,String code1050,String code1060,String code1070,String code1080,String code1090,String code1100) {
//	public static byte[] createProcessCodeBuf(int code, String tmlId, int processCode) {	
	// tmlId TLV
		
				byte[] tmlIdbyte = tmlId.getBytes();
				TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
				byte[] tmlId_b = tmlIdTLV.getTLV();
				int tmlId_b_len = tmlId_b.length;
				 log.debug("tmlId_b_len:"+tmlId_b_len);
				// count TLV
				byte[] abytes = new byte[4];
				abytes[0] = (byte)((processCode >> 24) & 0xff);
				abytes[1] = (byte)((processCode >> 16) & 0xff);
				abytes[2] = (byte)((processCode >> 8) & 0xff);
				abytes[3] = (byte)(processCode & 0xff);
				TLVPair countTLV = new TLVPair(TLVTag.infoType, abytes);
				byte[] count_b = countTLV.getTLV();
				int count_b_len = count_b.length;
//				 log.info("count_b_len:"+count_b_len);
				 
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
			 if(code1020!=null){
			    byte[] code1020byte = code1020.getBytes();
			    TLVPair code1020TLV = new TLVPair(TLVTag.Code1020, code1020byte);
			    code1020_b = code1020TLV.getTLV();
			    code1020_b_len = code1020_b.length;
			   
			 }
			    
				  int code1030_b_len =0;
				  byte[] code1030_b=null;
			 if(code1030!=null){
				byte[] code1030byte = code1030.getBytes();
				
				TLVPair code1030TLV = new TLVPair(TLVTag.Code1030, code1030byte);
			        code1030_b = code1030TLV.getTLV();
				    code1030_b_len = code1030_b.length;
				
			 }
				 
				  int code1040_b_len =0;
				  byte[] code1040_b=null;
			 if(code1040!=null){
				byte[] code1040byte = code1040.getBytes();
				TLVPair code1040TLV = new TLVPair(TLVTag.Code1040, code1040byte);
				code1040_b = code1040TLV.getTLV();
				code1040_b_len = code1040_b.length;
				
			 }
				 
				 int code1050_b_len =0;
				 byte[] code1050_b=null;
			 if(code1050!=null){
			byte[] code1050byte = code1050.getBytes();
			TLVPair code1050TLV = new TLVPair(TLVTag.Code1050, code1050byte);
			code1050_b = code1050TLV.getTLV();
			code1050_b_len = code1050_b.length;
			
			 }
			
			 int code1060_b_len =0;
			 byte[] code1060_b=null;
		 if(code1060!=null){
			byte[] code1060byte = code1060.getBytes();
			TLVPair code1060TLV = new TLVPair(TLVTag.Code1060, code1060byte);
		    code1060_b = code1060TLV.getTLV();
			code1060_b_len = code1060_b.length;
			
		 }
			 
			 int code1070_b_len =0;
			 byte[] code1070_b=null;
		 if(code1070!=null){
			byte[] code1070byte = code1070.getBytes();
			TLVPair code1070TLV = new TLVPair(TLVTag.Code1070, code1070byte);
		    code1070_b = code1070TLV.getTLV();
			code1070_b_len = code1070_b.length;
			
		 }
		 int code1080_b_len =0;
		 byte[] code1080_b=null;
	   if(code1080!=null){
			byte[] code1080byte = code1080.getBytes();
			TLVPair code1080TLV = new TLVPair(TLVTag.Code1080, code1080byte);
			 code1080_b = code1080TLV.getTLV();
		     code1080_b_len = code1080_b.length;
		   
	  }
		  
		    int code1090_b_len =0;
			byte[] code1090_b=null;
		   if(code1090!=null){
			byte[] code1090byte = code1090.getBytes();
			TLVPair code1090TLV = new TLVPair(TLVTag.Code1090, code1090byte);
			code1090_b = code1090TLV.getTLV();
			code1090_b_len = code1090_b.length;
			
		   }	
		   
		    int code1100_b_len =0;
		    byte[] code1100_b=null;
			 if(code1100!=null){
			  byte[] code1100byte = code1100.getBytes();
			  TLVPair code1100TLV = new TLVPair(TLVTag.Code1100, code1100byte);
			  code1100_b = code1100TLV.getTLV();
			  code1100_b_len = code1100_b.length;				
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
		 if(code1020!=null){
		System.arraycopy(code1020_b, 0, toBuf, offset, code1020_b_len);
		offset = offset + code1020_b_len;
		
		 }
		 
		 if(code1030!=null){
		System.arraycopy(code1030_b, 0, toBuf, offset, code1030_b_len);
		offset = offset + code1030_b_len;
	
		 }
		 if(code1040!=null){
		System.arraycopy(code1040_b, 0, toBuf, offset, code1040_b_len);
		offset = offset + code1040_b_len;
	
		 }
		 if(code1050!=null){
		System.arraycopy(code1050_b, 0, toBuf, offset, code1050_b_len);
		offset = offset + code1050_b_len;
		 }
		 
		 if(code1060!=null){
		System.arraycopy(code1060_b, 0, toBuf, offset, code1060_b_len);
		offset = offset + code1060_b_len;
		
		 }
		 if(code1070!=null){
		System.arraycopy(code1070_b, 0, toBuf, offset, code1070_b_len);
		offset = offset + code1070_b_len;
	
		 }
		 if(code1080!=null){
		System.arraycopy(code1080_b, 0, toBuf, offset, code1080_b_len);
		offset = offset + code1080_b_len;
		
		 }
		 if(code1090!=null){
		System.arraycopy(code1090_b, 0, toBuf, offset, code1090_b_len);
		offset = offset + code1090_b_len;
		 }
		 if(code1100!=null){
				System.arraycopy(code1100_b, 0, toBuf, offset, code1100_b_len);
				offset = offset + code1100_b_len;
				 }
		return toBuf;
	}
	
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
