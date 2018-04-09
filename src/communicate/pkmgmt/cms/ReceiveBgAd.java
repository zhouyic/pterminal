package communicate.pkmgmt.cms;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.exception.SqlException;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.OrderMapMemory;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.SelectTml;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class ReceiveBgAd {
	private static Logger log = communicate.common.Logger.getLogger(ReceiveBgAd.class);

	public byte[] parseData(byte[] recBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		int type = tlvs.getIntValue(TLVTag.Service_Type);

		String outDate = tlvs.getStringValue(TLVTag.endTime);
		String listName = tlvs.getStringValue(TLVTag.listName);
		String CXMLName = "";
		if (type == PackageConstant.service_Type.bg_ad) {
			CXMLName = tlvs.getStringValue(TLVTag.CXMLName);
		}

		// ERROR ACK
		ITmlService service = new TmlServiceImpl();
		if (listName == null || listName.trim().length() == 0) {
			service.createResultCodeBuf(PackageConstant.code.deliver_ad_Ack, PackageConstant.result_code.error);
		}

		if (type == PackageConstant.service_Type.bg_ad 
				&& (CXMLName == null && CXMLName.trim().length() == 0)) {
			service.createResultCodeBuf(PackageConstant.code.deliver_ad_Ack, PackageConstant.result_code.error);
		}

		// 入库，维持
		ITmlJDBC jdbc = new TmlJDBCImpl();
		String adId = ""+System.currentTimeMillis();
		int ret = jdbc.addADRecord(adId, type, listName, CXMLName, outDate);

		OrderMapMemory map = OrderMapMemory.getInstance();
		map.setAdRecord(adId, type, listName, CXMLName, outDate);

		try {
			List<SelectTml> list = jdbc.getTmls();

			List<String> macs = new ArrayList<String>();
			if (list != null && list.size() != 0) {
				for (SelectTml tml : list) {
					macs.add(tml.getTmlId());
				}
				map.initAdMac(macs);
			}
		} catch (SqlException e) {
			log.error(this,e);
		}

		// send
		map.sendAd();
		log.info("############SEND to CMS ret:"+ret);
		// OK ACK
		return service.createResultCodeBuf(PackageConstant.code.deliver_ad_Ack, ret);
	}
}