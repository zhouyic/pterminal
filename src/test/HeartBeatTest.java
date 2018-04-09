package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;


public class HeartBeatTest {
	public static int header_len = 22;
	public static  int count = 0;
	private  synchronized static void addCount(){
		count++;
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		byte[] recBuf = new byte[4096];
		Socket tcpClient = new Socket("52.57.134.189",10190);
        OutputStream out = tcpClient.getOutputStream();
        String str1="{\"cmdLine\":\"ping 192.168.1.0\",\"isConnected\":\"yes\",\"cmdNo\":\"92\"}";
        String str2="{\"result\":[{\"cmdNo\":\"70\",\"isSucceed\":\"222\"},{\"cmdNo\":\"80\",\"isSucceed\":\"2000\"},{\"cmdNo\":\"90\",\"isSucceed\":\"300\"}]}";
//		String str1="123456";
//		String str2="7891011";
        byte[]	ret = createResultCodeBuf(7, "8810361FEC5E", 1,"tmlname1",20,str1,str2);
//		byte[]	ret = createResultCodeBuf(7, "DEADBEEF0102", 1,"tmlname1",20);
//		byte[]	ret = createResultCodeBuf(7, "DEADBEEFA102", 1,"tmlname1",20);
		System.out.println(ret);
        out.write(ret);
        InputStream in = tcpClient.getInputStream();
    	  while(true) {
	        	int recSize = in.read(recBuf);
	        	System.out.println("recSize=" + recSize);
	        	if(recSize > 0) {
	    			parseData(recBuf,recSize,22);
	        		//System.out.println("count:" + count + "  time: " + (System.currentTimeMillis()-SendThread.start) );
	        		//addCount();
	    			break;
	        	}else{
	        		System.out.println("error recSize=" + recSize);
	        		break;
	        	}
	        }
        tcpClient.close();
     }
	/*
	 * TML_ID	6	Y  终端标识
TML_TYPE	4	Y  终端类型
Version	201	Y  STB版本号
STB-ableMem	203	Y  STB剩余内存
STB-portalURL	205	Y  STB的portal URL
Log_server_url	149	Y  日志服务器地址
Ntp_server_url	300	Y  NTP服务器地址
Heart_beat_rate	301	Y  心跳包频率，0为不发送(单位为秒)

	 */
	public static byte[] createResultCodeBuf(int code, String tmlId, int tmlType, String content_name ,int service_type,String net_checkResult,String cmdResult) {
		// tmlId TLV
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;
		System.out.println("cmdResult=" + cmdResult);
		// count TLV
		byte[] abytes = new byte[4];
		abytes[0] = (byte)((tmlType >> 24) & 0xff);
		abytes[1] = (byte)((tmlType >> 16) & 0xff);
		abytes[2] = (byte)((tmlType >> 8) & 0xff);
		abytes[3] = (byte)(tmlType & 0xff);
		TLVPair countTLV = new TLVPair(TLVTag.TML_Type, abytes);
		byte[] count_b = countTLV.getTLV();
		int count_b_len = count_b.length;
		
		//content_name TLV
		byte[] content_nameByte = content_name.getBytes();
		TLVPair content_nameTLV = new TLVPair(TLVTag.Content_Name, content_nameByte);
		byte[] content_name_b = content_nameTLV.getTLV();
		int content_name_b_len = content_name_b.length;					
		// service_type TLV
		
		byte[] service_typebytes = new byte[4];
		service_typebytes[0] = (byte)((service_type >> 24) & 0xff);
		service_typebytes[1] = (byte)((service_type >> 16) & 0xff);
		service_typebytes[2] = (byte)((service_type >> 8) & 0xff);
		service_typebytes[3] = (byte)(service_type & 0xff);
		TLVPair service_typeTLV = new TLVPair(TLVTag.Service_Type, service_typebytes);
		byte[] service_type_b = service_typeTLV.getTLV();
		int service_type_b_len = service_type_b.length;
		// net_checkResult TLV
		
		byte[] net_checkResultbyte = net_checkResult.getBytes();
		TLVPair net_checkResultTLV = new TLVPair(TLVTag.NetCheckResult, net_checkResultbyte);
		byte[] net_checkResult_b = net_checkResultTLV.getTLV();
		int net_checkResult_b_len = net_checkResult_b.length;
		
		
		// cmdResult TLV
		byte[] cmdResultbyte = cmdResult.getBytes();
		TLVPair cmdResultTLV = new TLVPair(TLVTag.CmdResult, cmdResultbyte);
		byte[] cmdResult_b = cmdResultTLV.getTLV();
		int cmdResult_b_len = cmdResult_b.length;
		
		
		int offset = 0;
		int allLen = header_len + tmlId_b_len + count_b_len + content_name_b_len + service_type_b_len+net_checkResult_b_len+cmdResult_b_len;// + portal_url_b_len + log_url_b_len + ntp_url_b_len + heart_beat_rate_b_len;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(count_b, 0, toBuf, offset, count_b_len);
		offset = offset + count_b_len;
		
		System.arraycopy(content_name_b, 0, toBuf, offset, content_name_b_len);
		offset = offset + content_name_b_len;
		
		System.arraycopy(service_type_b, 0, toBuf, offset, service_type_b_len);
		offset = offset + service_type_b_len;		
		
		System.arraycopy(net_checkResult_b, 0, toBuf, offset, net_checkResult_b_len);
		offset = offset + net_checkResult_b_len;	
		
		System.arraycopy(cmdResult_b, 0, toBuf, offset, cmdResult_b_len);
		offset = offset + cmdResult_b_len;	
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
	
	public static void parseData(byte[] leftBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		tmlId = tmlId.toUpperCase();
		String code1010 = tlvs.getStringValue(160);
		String code1020 = tlvs.getStringValue(161);
		String code1030 = tlvs.getStringValue(158);
		String code1040 = tlvs.getStringValue(159);
		String code1050 = tlvs.getStringValue(153);
		String code1060 = tlvs.getStringValue(154);
		String code1070 = tlvs.getStringValue(155);
		String code1080 = tlvs.getStringValue(156);
		String code1090 = tlvs.getStringValue(157);	
		System.out.println("count:" + count + "  #########  heartbeat return: tmlId="+tmlId+", code1010="+code1010+", code1020=" + code1020+", code1030="+code1030+", code1040="+code1040+", code1050="+code1050+", code1060="+code1060+", code1070="+code1070+", code1080="+code1080+", code1090="+code1090);
		//System.out.println("count:" + count + "  time: " + (System.currentTimeMillis()-SendThread.start) + "#########  login return: tmlId="+tmlId+", Portal_url="+Portal_url+", Log_server_url=" + Log_server_url+", Ntp_server_url="+Ntp_server_url+", Heart_beat_rate="+Heart_beat_rate);
		addCount();
		
	}

}
