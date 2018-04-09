package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.TmlServiceImpl;

public class SysInfoTest {
	public static int header_len = 22;
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket tcpClient = new Socket("192.168.47.16",10190);
        OutputStream out = tcpClient.getOutputStream();
		byte[]	ret = createResultCodeBuf(41, "tml5", 1,"version","ableMem","portal_url","log_server_url","ntp_server",0);//service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.ok);
		System.out.println(ret);
        out.write(ret);
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
	public static byte[] createResultCodeBuf(int code, String tmlId, int tmlType, String tmlVersion,String ableMem, String portal_url, String log_url,String ntp_url, int heart_beat_rate) {
		// tmlId TLV
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;
		
		// count TLV
		byte[] abytes = new byte[4];
		abytes[0] = (byte)((tmlType >> 24) & 0xff);
		abytes[1] = (byte)((tmlType >> 16) & 0xff);
		abytes[2] = (byte)((tmlType >> 8) & 0xff);
		abytes[3] = (byte)(tmlType & 0xff);
		TLVPair countTLV = new TLVPair(TLVTag.TML_Type, abytes);
		byte[] count_b = countTLV.getTLV();
		int count_b_len = count_b.length;
		
		//tmlVersion TLV
		byte[] tmlVersionByte = tmlVersion.getBytes();
		TLVPair tmlVersionTLV = new TLVPair(TLVTag.tmlVersion, tmlVersionByte);
		byte[] tmlVersion_b = tmlVersionTLV.getTLV();
		int tmlVersion_b_len = tmlVersion_b.length;
		
		//ableMem TLV
		byte[] ableMemByte = ableMem.getBytes();
		TLVPair ableMemTLV = new TLVPair(TLVTag.ableMem, ableMemByte);
		byte[] ableMem_b = ableMemTLV.getTLV();
		int ableMem_b_len = ableMem_b.length;
		
		//portal_url TLV
		byte[] portal_urlByte = portal_url.getBytes();
		TLVPair portal_urlTLV = new TLVPair(TLVTag.portalUrl, portal_urlByte);
		byte[] portal_url_b = portal_urlTLV.getTLV();
		int portal_url_b_len = portal_url_b.length;
		
		//log_url TLV
		byte[] log_urlByte = log_url.getBytes();
		TLVPair log_urlTLV = new TLVPair(TLVTag.logFtpURL, log_urlByte);
		byte[] log_url_b = log_urlTLV.getTLV();
		int log_url_b_len = log_url_b.length;
		
		//ntp_url TLV
		byte[] ntp_urlByte = ntp_url.getBytes();
		TLVPair ntp_urlTLV = new TLVPair(TLVTag.ntp_server, ntp_urlByte);
		byte[] ntp_url_b = ntp_urlTLV.getTLV();
		int ntp_url_b_len = ntp_url_b.length;
							
		// heart_beat_rate TLV
		byte[] heart_beat_ratebytes = new byte[4];
		heart_beat_ratebytes[0] = (byte)((heart_beat_rate >> 24) & 0xff);
		heart_beat_ratebytes[1] = (byte)((heart_beat_rate >> 16) & 0xff);
		heart_beat_ratebytes[2] = (byte)((heart_beat_rate >> 8) & 0xff);
		heart_beat_ratebytes[3] = (byte)(heart_beat_rate & 0xff);
		TLVPair heart_beat_rateTLV = new TLVPair(TLVTag.heart_beat_rate, heart_beat_ratebytes);
		byte[] heart_beat_rate_b = heart_beat_rateTLV.getTLV();
		int heart_beat_rate_b_len = heart_beat_rate_b.length;
		
		int offset = 0;
		int allLen = header_len + tmlId_b_len + count_b_len + tmlVersion_b_len + ableMem_b_len + portal_url_b_len + log_url_b_len + ntp_url_b_len + heart_beat_rate_b_len;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(count_b, 0, toBuf, offset, count_b_len);
		offset = offset + count_b_len;
		
		System.arraycopy(tmlVersion_b, 0, toBuf, offset, tmlVersion_b_len);
		offset = offset + tmlVersion_b_len;
		
		System.arraycopy(ableMem_b, 0, toBuf, offset, ableMem_b_len);
		offset = offset + ableMem_b_len;
		
		System.arraycopy(portal_url_b, 0, toBuf, offset, portal_url_b_len);
		offset = offset + portal_url_b_len;
		
		System.arraycopy(log_url_b, 0, toBuf, offset, log_url_b_len);
		offset = offset + log_url_b_len;
		
		System.arraycopy(ntp_url_b, 0, toBuf, offset, ntp_url_b_len);
		offset = offset + ntp_url_b_len;
		
		System.arraycopy(heart_beat_rate_b, 0, toBuf, offset, heart_beat_rate_b_len);
		offset = offset + heart_beat_rate_b_len;
		
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
