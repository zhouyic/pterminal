package communicate.pkmgmt.thai;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.stb.HeartBeat;

public class ThaiLogout {
	private static Logger log = Logger.getLogger(ThaiLogout.class);
	private IThaiService thaiService = null;
	/**
	 * 解析收到的数据包
	 * @param leftBuf
	 * @param len
	 * @param offset
	 * @param sendBuf
	 * @return
	 */
	public byte[] parseData(byte[] leftBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		tmlId = tmlId.toUpperCase();

//		String tmlVersion = tlvs.getStringValue(TLVTag.tmlVersion);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);

		//如果需要添加业务逻辑，从这里添加
		log.info("parseData  logout : tmlId="+tmlId+", tmlType="+tmlType);

		thaiService = new ThaiServiceImpl();
		thaiService.logout(tmlId);
		// new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		String type="offline";
//		System.out.println("----下线的时候插入数据库----");
		thaiService.addTmlHistory(tmlId, time, type);
//		thaiService.addTmlHistoryEn(tmlId, time, type);	
		Map<String, Long> map = HeartBeat.heartbeatMap;
		if(map.containsKey(tmlId)) {
			map.remove(tmlId);
		}
		log.debug("logout -- end");
		return null;
	}
}
