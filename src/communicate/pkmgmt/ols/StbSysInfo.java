package communicate.pkmgmt.ols;

import java.io.IOException;

import org.apache.log4j.Logger;

import communicate.common.net.TCPClient;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.OlsDTO;
import communicate.pkmgmt.tmljdbc.TmlConst;

public class StbSysInfo {
	private static final Logger log = communicate.common.Logger.getLogger(StbSysInfo.class);

	public byte[] receive(byte[] recBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		int type = tlvs.getIntValue(TLVTag.infoType);
		log.info("------TMS:Get StbSysInfo! tmlId="+tmlId+", type="+type);

		tmlId = tmlId.toUpperCase();

		createSendOrderBuf(tmlId, type);
		return null;
	}

	public void createSendOrderBuf(String tmlId, int infoType) {
		if (tmlId == null) return;

		int offset = 0;

		byte[] tmlIdByte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdByte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		byte[] abytes = new byte[4];
		abytes[0] = (byte)((infoType >> 24) & 0xff);
		abytes[1] = (byte)((infoType >> 16) & 0xff);
		abytes[2] = (byte)((infoType >> 8) & 0xff);
		abytes[3] = (byte)(infoType & 0xff);
		TLVPair infoTypeTLV = new TLVPair(TLVTag.infoType, abytes);
		byte[] infoType_b = infoTypeTLV.getTLV();
		int infoType_b_len = infoType_b.length;

		int allLen = PackageConstant.MIN_PACKAGE_LEN + tmlId_b_len+infoType_b_len;

		TmlServiceImpl t = new TmlServiceImpl();
		byte[] send = t.createByte(PackageConstant.code.sys_info_request, allLen);
		offset = 22;

		System.arraycopy(tmlId_b, 0, send, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(infoType_b, 0, send, offset, infoType_b_len);
		offset = offset + infoType_b_len;

		OrderMapMemory map = OrderMapMemory.getInstance();
		OlsDTO ols = map.getOls(tmlId);
		if (ols != null) {
			String olsIp = ols.getIp();
			int olsPort = ols.getPort();
			log.info("_____StbSysInfo______olsIp="+olsIp+", olsPort="+olsPort);
			TCPClient tcp = new TCPClient(olsIp, olsPort);
			try {
				tcp.sendAsync(send, offset);
			} catch (IOException e) {
				log.error(this,e);
			}
		}
	}

	public static void main(String[] a) {
		StbSysInfo stbSysInfo = new StbSysInfo();
		stbSysInfo.createSendOrderBuf("001240712862",TmlConst.infoType.tml_sys_info);
	}
}