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

public class ReceiveUAd {
	private static Logger log = communicate.common.Logger.getLogger(ReceiveUAd.class);

	public byte[] parseData(byte[] recBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String listName = tlvs.getStringValue(TLVTag.listName);
		log.info("############ReceiveUAd listName:"+listName);

		// ERROR ACK
		ITmlService service = new TmlServiceImpl();
		if (listName == null || listName.trim().length() == 0) {
			service.createResultCodeBuf(PackageConstant.code.deliver_Uad_Ack, PackageConstant.result_code.error);
		}

		// 入库，维持
		ITmlJDBC jdbc = new TmlJDBCImpl();
		String uadId = "U"+System.currentTimeMillis();
		int ret = jdbc.addUAD(uadId, listName);
		log.info("############ReceiveUAd ret:"+ret);

		OrderMapMemory map = OrderMapMemory.getInstance();
		map.setU_adRecord(listName);

		try {
			List<SelectTml> list = jdbc.getTmls();

			List<String> macs = new ArrayList<String>();
			if (list != null && list.size() != 0) {
				for (SelectTml tml : list) {
					macs.add(tml.getTmlId());
				}
				map.initUadMac(macs);
			}
		} catch (SqlException e) {
			log.info(e,e);
		}

		// send
		map.sendUAd();
		log.info("############SEND to CMS ret:"+ret);
		// OK ACK
		return service.createResultCodeBuf(PackageConstant.code.deliver_Uad_Ack, ret);
	}
}
