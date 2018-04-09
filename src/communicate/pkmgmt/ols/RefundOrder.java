package communicate.pkmgmt.ols;

import org.apache.log4j.Logger;

import communicate.common.exception.SqlException;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlConst;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class RefundOrder {
	private static Logger log = communicate.common.Logger.getLogger(RefundOrder.class);

	public byte[] parseData(byte[] recBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}


		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		log.info("------OLS: refund order pk!\n------tmlId="+tmlId+", tmlType="+tmlType);

		// 从map移除
		OrderMapMemory map = OrderMapMemory.getInstance();
		map.removeOutlineMac(tmlId);

		ITmlJDBC db = new TmlJDBCImpl();
		try {
			db.updTml(tmlId, TmlConst.tmlStatus.outline);
			db.updateTmlDoCnt(tmlId, null);
		} catch (SqlException e) {
			log.error("RefundOrder(): update terminal status error!");
			log.error(this,e);
		}

		int result = 0;
		return createSendBuf(tmlId, result);
	}

	private byte[] createSendBuf(String tmlId, int result) {
		// tmlId TLV
		byte[] tmlIdByte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdByte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		// result TLV
		byte[] abytes = new byte[4];
		abytes[0] = (byte)((result >> 24) & 0xff);
		abytes[1] = (byte)((result >> 16) & 0xff);
		abytes[2] = (byte)((result >> 8) & 0xff);
		abytes[3] = (byte)(result & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, abytes);
		byte[] result_b = resultTLV.getTLV();
		int result_b_len = result_b.length;

		int len = PackageConstant.MIN_PACKAGE_LEN + tmlId_b_len + result_b_len;
		byte[] sendBuf = new byte[len];

		long pkId = System.currentTimeMillis()/1000;
		int offset = 0;

		offset += 16;
		int code = PackageConstant.code.refund_order_ACK;
		sendBuf[offset++] = (byte)((code >> 8) & 0xff);
		sendBuf[offset++] = (byte)(code & 0xff);
		sendBuf[offset++] = (byte)((pkId >> 8) & 0xff);
		sendBuf[offset++] = (byte)(pkId & 0xff);

		sendBuf[offset++] = (byte)((len >> 8) & 0xff);
		sendBuf[offset++] = (byte)(len & 0xff);

		System.arraycopy(tmlId_b, 0, sendBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(result_b, 0, sendBuf, offset, result_b_len);
		offset = offset + result_b_len;
		return sendBuf;
	}

	public static void main(String[] main) {
		String tmlId = "0016E8F871F6";
		ITmlJDBC db = new TmlJDBCImpl();
		try {
			db.updTml(tmlId, TmlConst.tmlStatus.outline);
		} catch (SqlException e) {
			log.error("TakeOrder(): update terminal status error!");
			e.printStackTrace();
		}
	}
}
