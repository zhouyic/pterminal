package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.dto.TmlConfDto;
import communicate.pkmgmt.dto.TmlDto;

class SendThread extends Thread {
	private IThaiService thaiService = null;
//	public static String hostIp= "127.0.0.1";
//	public static String hostIp= "52.59.222.251";
//	public static String hostIp= "54.93.118.155";
	public static String hostIp= "192.168.101.40";
	public static int port = 10190;
	public static int header_len = 22;
	public static  int count = 0;
	public static long start = 0L;
	//private byte[] recBuf = new byte[4096];
	String tmlId = "DEADBEEFA120";
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	private  synchronized void addCount(){
		count++;
	}
	public void parseData(byte[] leftBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		tmlId = tmlId.toUpperCase();
		String Portal_url = tlvs.getStringValue(205);
		String Log_server_url = tlvs.getStringValue(149);
		String Ntp_server_url = tlvs.getStringValue(300);
		int Heart_beat_rate = tlvs.getIntValue(301);
		System.out.println("count:" + count + "  #########  login return: tmlId="+tmlId+", Portal_url="+Portal_url+", Log_server_url=" + Log_server_url+", Ntp_server_url="+Ntp_server_url+", Heart_beat_rate="+Heart_beat_rate);
		//System.out.println("count:" + count + "  time: " + (System.currentTimeMillis()-SendThread.start) + "#########  login return: tmlId="+tmlId+", Portal_url="+Portal_url+", Log_server_url=" + Log_server_url+", Ntp_server_url="+Ntp_server_url+", Heart_beat_rate="+Heart_beat_rate);
		addCount();
		
	}
	public void run(){
		try{
			byte[] recBuf = new byte[4096];
			Socket tcpClient = new Socket(hostIp,port);
	        OutputStream out = tcpClient.getOutputStream();
			byte[]	ret = createResultCodeBuf(1, tmlId, 1,"aa");//service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.ok);
			System.out.println(tmlId + " send before");
			System.out.println("");
			for(int i=0; i<ret.length ;i++) {
				System.out.print(ret[ret.length-i-1]);
			}
			System.out.println("");		
			out.write(ret);
	        System.out.println(tmlId + " send over");
	        InputStream in = tcpClient.getInputStream();
	        if(tcpClient.isClosed() || !tcpClient.isConnected() || tcpClient.isInputShutdown()) {
	        	System.err.println("error, connection closed" + tmlId);
	        	return;
	        }
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
	       // System.out.println(tmlId + " , send over");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static byte[] createResultCodeBuf(int code, String tmlId, int tmlType, String tmlSN) {
		// tmlId TLV
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((tmlType >> 24) & 0xff);
		result[1] = (byte)((tmlType >> 16) & 0xff);
		result[2] = (byte)((tmlType >> 8) & 0xff);
		result[3] = (byte)(tmlType & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.TML_Type, result);
		byte[] result_b = resultTLV.getTLV();
		int result_b_len = result_b.length;
		byte[] tmlSNbyte = tmlSN.getBytes();
		TLVPair tmlSNTLV = new TLVPair(TLVTag.TML_SN, tmlSNbyte);
		byte[] tmlSN_b = tmlSNTLV.getTLV();
		int tmlSN_b_len = tmlSN_b.length;		
		int offset = 0;
		int allLen = header_len + tmlId_b_len + result_b_len+tmlSN_b_len;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(result_b, 0, toBuf, offset, result_b_len);
		offset = offset + result_b_len;
		
		System.arraycopy(tmlSN_b, 0, toBuf, offset, tmlSN_b_len);
		offset = offset + tmlSN_b_len;
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
public class LoginTest {
	
	
	public static void main(String[] args)  {
		System.out.println("测试开始！");
		int size = 1;
		SendThread.start = System.currentTimeMillis();
		List<String> tmlIds = new ArrayList<String>();
		tmlIds.add("DEADBEEF0102");
		try{
			for(int i=0; i<size ;i++) {
				SendThread tmpThread = new SendThread();
				//tmpThread.setTmlId("TML_TEST_0");
				tmpThread.setTmlId(tmlIds.get(i%tmlIds.size()));
			    //tmpThread.setTmlId(tmlIds.get(i%tmlIds.size()));
				//tmpThread.setTmlId("001803ff6e89");
				tmpThread.start();
				Thread.sleep(5);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
     }
	
}
