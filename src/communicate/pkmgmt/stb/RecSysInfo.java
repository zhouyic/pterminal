package communicate.pkmgmt.stb;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.ThaiServiceImpl;

public class RecSysInfo {
	private static Logger log = Logger.getLogger(RecSysInfo.class);
	private IThaiService service;

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
//		tmlId = tmlId.toUpperCase();
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		String tmlVersion = tlvs.getStringValue(TLVTag.tmlVersion);
		String ableMem = tlvs.getStringValue(TLVTag.ableMem);
		String portalUrl = tlvs.getStringValue(TLVTag.portalUrl);
		String logFtpURL = tlvs.getStringValue(TLVTag.logFtpURL);
		String ntp_server_url = tlvs.getStringValue(TLVTag.ntp_server);
		String upgrade_server_url = tlvs.getStringValue(TLVTag.upgrade_server);
		int heart_beat_rate = tlvs.getIntValue(TLVTag.heart_beat_rate);//秒为单位
		String upgradeInfourl=tlvs.getStringValue(TLVTag.Upgrade_Info_server_url);
		String appInfourl=tlvs.getStringValue(TLVTag.App_info_server_url);
		service = new ThaiServiceImpl();
		service.updateSysInfo(tmlId, tmlVersion, ableMem, portalUrl, logFtpURL, ntp_server_url, heart_beat_rate,upgrade_server_url,upgradeInfourl,appInfourl);
	
		log.info("######### 上报的信息 RecSysInfo : tmlId="+tmlId+", tmlVersion="+tmlVersion+", tmlType="+tmlType
				+", ableMem="+ableMem+", logFtpURL="+logFtpURL+
				", portalUrl="+portalUrl+", ntp_server_url="+ntp_server_url+", heart_beat_rate="+heart_beat_rate
				+", upgrade_server_url="+upgrade_server_url+", upgradeInfourl="+upgradeInfourl+", appInfourl="+appInfourl);	
		
		return null;
	}
}
