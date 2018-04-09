package communicate.pkmgmt.portal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.OlsDTO;
import communicate.pkmgmt.dto.Order;
import communicate.pkmgmt.ols.SendOrder;
import communicate.pkmgmt.tmljdbc.TmlConst;

/**
 * 收到Portal服务器下发的订购单，并返回确认包
 * @author jinj
 */
public class ReceiveOrder {
	private static Logger log = communicate.common.Logger.getLogger(ReceiveOrder.class);
	private ITmlService service = new TmlServiceImpl();

	/**
	 * 解析数据
	 * @param leftBuf
	 * @param len
	 * @param offset
	 * @param sendBuf
	 * @return
	 */
	public byte[] receive(byte[] leftBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}
		int portalType = tlvs.getIntValue(TLVTag.portalType);
		if (portalType!=0 && portalType != 1 && portalType != 2) {
			return service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.error);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		if (tmlId == null || tmlId.trim().equals("")){
			return service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.error);
		} else {
			tmlId = tmlId.toUpperCase();
		}

		byte[][] orders = tlvs.getAllBinaryValues(TLVTag.Order_Msg);
		if(orders == null){
			return service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.error);
		}

		int orderLen = orders.length;
		List<Order> list = new ArrayList<Order>();
		ITmlService service = new TmlServiceImpl();
		for(int j = 0; j < orderLen; j++){
			tlvs.loadTLVs(orders[j], 0, orders[j].length);
			String orderId = tlvs.getStringValue(TLVTag.OrderId);
			String contentId = tlvs.getStringValue(TLVTag.Content_ID);
			int programType = tlvs.getIntValue(TLVTag.Service_Type);

			Order order = new Order();
			order.setLicenseName(orderId+".license");
			order.setContentId(contentId);
			order.setTmlId(tmlId);
			order.setOrderId(orderId);
			order.setPortalType(portalType);
			order.setServiceType(programType);

			list.add(order);
		}
		log.debug("ReceiveOrder: orders="+list);
		// 在downloadcmp数据库中查找对应的contentId,snap和listName
		list = service.getDetails(list);
		log.debug("------Portal: getDetails!\n------orders="+list);

		if (list.size() != 0) {
			service.insertOrder(list);

			if (portalType == TmlConst.portalType.pcPortal || portalType == TmlConst.portalType.MPortal) {
				OrderMapMemory map = OrderMapMemory.getInstance();

				OlsDTO dto = new OlsDTO();
				dto = map.getOls(tmlId);
				log.debug("OlsDTO="+dto);

				if (dto != null) {
					String ip = dto.getIp();
					int port = dto.getPort();

					if (ip != null && ip.trim().length() > 0 && port != 0) {
						SendOrder send = new SendOrder();
						send.createSendOrderBuf(tmlId, list, ip, port);
					}
				} else {
					map.addOrder(tmlId, list);
				}
			}
		}

		return service.createResultCodeBuf(PackageConstant.code.portal_tml_order_Ack, PackageConstant.result_code.ok);
	}
}