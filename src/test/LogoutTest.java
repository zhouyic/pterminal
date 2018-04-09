package test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;

public class LogoutTest {
	public static int header_len = 22;
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket tcpClient = new Socket("192.168.47.16",10190);
        OutputStream out = tcpClient.getOutputStream();
		byte[]	ret = createResultCodeBuf(701, "tml5", 1);//service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.ok);
		System.out.println(ret);
        out.write(ret);
        
        tcpClient.close();
     }
	public static byte[] createResultCodeBuf(int code, String tmlId, int tmlType) {
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
		

				
				
				int offset = 0;
				int allLen = header_len + tmlId_b_len + result_b_len;

				byte[] toBuf = createByte(code, allLen);

				offset += header_len;

				System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
				offset = offset + tmlId_b_len;

				System.arraycopy(result_b, 0, toBuf, offset, result_b_len);
				offset = offset + result_b_len;
				
				return toBuf;
	}
//	public static byte[] createResultCodeBuf(int code, String tmlId, int resultCode) {
//		// tmlId TLV
//		byte[] tmlIdbyte = tmlId.getBytes();
//		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
//		byte[] tmlId_b = tmlIdTLV.getTLV();
//		int tmlId_b_len = tmlId_b.length;
//
//		// result-code TLV
//		byte[] result = new byte[4];
//		result[0] = (byte)((resultCode >> 24) & 0xff);
//		result[1] = (byte)((resultCode >> 16) & 0xff);
//		result[2] = (byte)((resultCode >> 8) & 0xff);
//		result[3] = (byte)(resultCode & 0xff);
//		TLVPair resultTLV = new TLVPair(TLVTag.tmlVersion, result);
//		byte[] result_b = resultTLV.getTLV();
//		int result_b_len = result_b.length;
//
//		int offset = 0;
//		int allLen = header_len + tmlId_b_len + result_b_len;
//
//		byte[] toBuf = createByte(code, allLen);
//
//		offset += header_len;
//
//		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
//		offset = offset + tmlId_b_len;
//
//		System.arraycopy(result_b, 0, toBuf, offset, result_b_len);
//		offset = offset + result_b_len;
//		return toBuf;
//	}
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
