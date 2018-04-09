package communicate.pkmgmt.stb;

import org.apache.log4j.Logger;

import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;

public class StbOrderRequest {
	private static Logger log = communicate.common.Logger.getLogger(StbOrderRequest.class);

	/**
	 * 收到SPE服务器的订购单请求，返回订购单
	 * @param leftBuf
	 * @param len
	 * @param offset
	 * @param sendBuf
	 * @return
	 */
	public byte[] receive(byte[] leftBuf, int len, int offset) {
		log.debug("[StbOrderRequest].receive");
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		String orderId = tlvs.getStringValue(TLVTag.OrderId);
		log.debug("------STB: order request pk!\n------tmlId="+tmlId+", orderId="+orderId);

		byte[] buf = null;
		ITmlService service = new TmlServiceImpl();
		buf = service.createOrderBufToSPE(orderId,PackageConstant.code.stb_tml_order_Ack);

		if (buf != null) {
			return buf;
		}

		return null;
	}
}
