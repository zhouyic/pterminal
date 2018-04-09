package communicate.pkmgmt.ols;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.StringUtil;
import communicate.common.exception.SqlException;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlConst;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class RecOrderAck {
	private static Logger log = communicate.common.Logger.getLogger(RecOrderAck.class);

	public byte[] parseData(byte[] recBuf, int len, int offset) {
		log.debug("RecOrderAck() recBuf:"+StringUtil.byteArrayToHexString(recBuf));
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID).toUpperCase();

		List<String> list = new ArrayList<String>();
		String[] orderIds = tlvs.getAllStringValues(TLVTag.OrderId);
		if (orderIds != null) {
			int size = orderIds.length;
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					list.add(orderIds[i]);
				}
			}
		}
		log.info("------OLS: RecOrderAck order ACK!\n------tmlId="+tmlId+", orderIds="+list);
		OrderMapMemory map = OrderMapMemory.getInstance();

		//ITmlService service = new TmlServiceImpl();
		if (list != null && list.size() != 0) {
			map.removeOrder(tmlId, list);
			//List<DownStatus> list0 = new ArrayList<DownStatus>();
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i);
				/*DownStatus s =  new DownStatus();
				s.setId(id);
				s.setStatus(TmlConst.orderStatus.hav_send);
				list0.add(s);*/
				map.updOrderStatus(id, TmlConst.orderStatus.hav_send);
			}
			/*if (list0.size() != 0) {
				service.updateOrder(list0);
			}*/
		}

		String adListName =  tlvs.getStringValue(TLVTag.listName);
		if (adListName != null) {
			String[] ad = map.getAdRecord();
			String uad = map.getU_adRecord();
			if (ad != null && ad[2].equals(adListName)) {
				ITmlJDBC db = new TmlJDBCImpl();
				try {
					db.updTml(tmlId, ad[0], TmlConst.adType.ad);
					map.removeAdMac(tmlId.toUpperCase());
				} catch (SqlException e) {
					log.error(this,e);
				}
			} else if (uad != null && uad.trim().equals(adListName)) {
				ITmlJDBC db = new TmlJDBCImpl();
				try {
					db.updTml(tmlId, uad, TmlConst.adType.uad);
					map.removeUAdMac(tmlId.toUpperCase());
				} catch (SqlException e) {
					log.error(this,e);
				}
			}

		}

		return null;
	}
}