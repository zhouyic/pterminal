package communicate.pkmgmt.ols;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.net.TCPClient;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.Order;

/**
 * 派送订购单至OLS
 * @author jinj
 */
public class SendOrder {
	private static Logger log = communicate.common.Logger.getLogger(SendOrder.class);

	public boolean createSendOrderBuf(String tmlId, List<Order> orders, String olsIp, int olsPort) {
		if (tmlId == null || tmlId.trim().equals("") 
				|| olsIp == null ||olsIp.trim().equals("") 
				|| olsPort == 0) return false;

		int offset = 0;
		byte[] tmlIdByte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdByte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		int allLen = PackageConstant.MIN_PACKAGE_LEN + tmlId_b_len ;
		byte[] order_b = null;
		int order_len = 0;

		if (orders != null && orders.size() != 0) {
			order_b = createOrderBuf(orders);
			if (order_b != null) {
				order_len = order_b.length;
				allLen += order_len;
				log.info("***********SEND ORDERS: tmlId="+tmlId+", orders="+orders+", olsIp="+olsIp+", olsPort="+olsPort);
			}
		}

		// AD
		byte[] ad_b = null;
		int ad_b_len = 0;
		OrderMapMemory map = OrderMapMemory.getInstance();
		String[] ad = map.getAdRecord();
		if (ad != null) {
			log.info("ad:"+ad[0]);
			boolean flg = map.checkAdMac(tmlId);
			if (flg) {
				ad_b = createAdBuf(ad);
				log.info("***********SEND AD: adId="+ad[0]);
			}
		}

		if (ad_b != null) {
			ad_b_len = ad_b.length;
			allLen += ad_b_len;
		}

		// UAD
		byte[] uad_b = null;
		int uad_b_len = 0;
		String uad = map.getU_adRecord();
		if (uad != null) {
			boolean flg = map.checkUAdMac(tmlId);
			if (flg) {
				uad_b = createUAdBuf(uad);
				log.info("***********SEND UAD: uad="+uad);
			}
		}

		if (uad_b != null) {
			uad_b_len = uad_b.length;
			allLen += uad_b_len;
		}

		TmlServiceImpl t = new TmlServiceImpl();
		byte[] send = t.createByte(PackageConstant.code.deliver_order, allLen);
		offset = 22;

		System.arraycopy(tmlId_b, 0, send, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;
		if (order_b != null) {
			System.arraycopy(order_b, 0, send, offset, order_len);
			offset = offset + order_len;
		}

		if (ad_b != null) {
			System.arraycopy(ad_b, 0, send, offset, ad_b_len);
			offset = offset + ad_b_len;
		}

		if (uad_b != null) {
			System.arraycopy(uad_b, 0, send, offset, uad_b_len);
			offset = offset + uad_b_len;
		}

		TCPClient tcp = new TCPClient(olsIp, olsPort);
		try {
			tcp.sendAsync(send, offset);
		} catch (IOException e) {
			log.error(this,e);
		}

		return true;
	}

	private byte[] createOrderBuf(List<Order> list) {
		byte[] order_MSG_TLV = null;
		if (list != null && list.size() != 0) {
			TLVPairList tlvs = new TLVPairList();
			for (Order dto : list) {
				TLVPairList msgTLV = new TLVPairList();

				String pName = dto.getParentName();
				if (pName == null) pName = "";
				msgTLV.addTLV(TLVTag.OrderId, dto.getOrderId());
				msgTLV.addTLV(TLVTag.Service_Type, dto.getServiceType());
				msgTLV.addTLV(TLVTag.Parent_Name, pName, "UTF-8");
				msgTLV.addTLV(TLVTag.movieName, dto.getMovieName(), "UTF-8");

				byte[] tmp = msgTLV.createBlock();
				tlvs.addTLV(TLVTag.Order_Msg, tmp);
			}

			order_MSG_TLV = tlvs.createBlock();
		}

		return order_MSG_TLV;
	}

	private byte[] createAdBuf(String[] ad) {
		TLVPairList tlvs = new TLVPairList();
		TLVPairList msgTLV = new TLVPairList();
		int adType = Integer.valueOf(ad[1]);
		msgTLV.addTLV(TLVTag.Service_Type, adType);
		msgTLV.addTLV(TLVTag.listName, ad[2]);

		if (adType == PackageConstant.service_Type.bg_ad) {
			msgTLV.addTLV(TLVTag.CXMLName, ad[3]);
		}


		byte[] tmp = msgTLV.createBlock();
		tlvs.addTLV(TLVTag.AD_MSG, tmp);

		return tlvs.createBlock();
	}

	private byte[] createUAdBuf(String uad) {
		TLVPairList tlvs = new TLVPairList();
		TLVPairList msgTLV = new TLVPairList();
		msgTLV.addTLV(TLVTag.listName, uad);

		byte[] tmp = msgTLV.createBlock();
		tlvs.addTLV(TLVTag.UAD_MSG, tmp);

		return tlvs.createBlock();
	}

	/*private byte[] createOrderIdBuf(List<String> list) {
		int len = 0;
		int size = list.size();
		byte[] initBuf = new byte[size*32];
		for (int i = 0; i < list.size(); i++) {
			String orderId = (String) list.get(i);
			log.debug("orderId="+orderId);
			byte[] orderBuf = orderId.getBytes();
			TLVPair orderTLV = new TLVPair(TLVTag.OrderId, orderBuf);
			byte[] order_b = orderTLV.getTLV();

			System.arraycopy(order_b, 0, initBuf, len, order_b.length);
			len += order_b.length;
		}

		byte[] ret = new byte[len];
		System.arraycopy(initBuf, 0, ret, 0, len);
		log.debug("ret="+StringUtil.byteArrayToHexString(ret));
		return ret;
	}*/
}
