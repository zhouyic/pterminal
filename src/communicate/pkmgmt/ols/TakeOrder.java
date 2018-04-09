package communicate.pkmgmt.ols;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.exception.SqlException;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.dto.Order;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlConst;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class TakeOrder {
	private static Logger log = communicate.common.Logger.getLogger(TakeOrder.class);

	public byte[] parseData(byte[] recBuf, int len, int offset, String ip) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		int olsPort = tlvs.getIntValue(TLVTag.olsPort);
		log.info("------OLS: TAKE ORDER PK! tmlId="+tmlId+", tmlType="+tmlType+", ip="+ip+", port="+olsPort);

		if (olsPort == 0 || tmlId == null || tmlId.trim().equals("")) {
			log.warn("rec a error package! package rec port="+olsPort);
			return null;
		}
		tmlId = tmlId.toUpperCase();
		ITmlJDBC db = new TmlJDBCImpl();
		try {
			db.updTml(tmlId, TmlConst.tmlStatus.online);
		} catch (SqlException e) {
			log.error(this,e);
		}
		// ols信息存map
		OrderMapMemory map = OrderMapMemory.getInstance();
		List<Order> list = new ArrayList<Order>();
		list = map.addOnlineMac(tmlId, ip, olsPort);

		// 若有订购单,发送订购单;若无,发送空报头
		SendOrder send = new SendOrder();
		send.createSendOrderBuf(tmlId, list, ip, olsPort);

		return null;
	}

	public static void main(String[] main) {
		String tmlId = "0016E8F871F6";
		ITmlJDBC db = new TmlJDBCImpl();
		try {
			db.updTml(tmlId, TmlConst.tmlStatus.online);
		} catch (SqlException e) {
			log.error("TakeOrder(): update terminal status error!");
			e.printStackTrace();
		}
	}
}
