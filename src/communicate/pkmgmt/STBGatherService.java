package communicate.pkmgmt;

import org.apache.log4j.Logger;

import communicate.common.net.IService;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;

public class STBGatherService implements IService {

	private static Logger log = Logger.getLogger(STBGatherService.class);

	public int respond(byte[] recBuf, int len, String remoteIP, byte[] sendBuf) {

		if(len < PackageConstant.MIN_PACKAGE_LEN){
			log.warn("rec a error package! package len =" + len);
			return createSendBuffer(PackageConstant.LOG_RES_ERROR, 1, null, recBuf, sendBuf);
		}

		int offset = 16;
		int pkType = (recBuf[offset++] & 0xff) << 8 | recBuf[offset++] & 0xff;
		offset = offset+2;
		int pkLen = (recBuf[offset++] & 0xff) << 8 | recBuf[offset++] & 0xff;

		if(pkType != PackageConstant.LOG_REQ){
			log.warn("rec a error package! package type =" + pkType);
			return createSendBuffer(PackageConstant.LOG_RES_ERROR, 1, null, recBuf, sendBuf);
		}

		//log.info(">>>>>>>>>>>>>>>>len = "+len);

		if(pkLen != len){
			log.warn("rec a error package! package rec len =" + len 
					+ " package len=" + pkLen);
			return createSendBuffer(PackageConstant.LOG_RES_ERROR, 1, null, recBuf, sendBuf);
		}

		TLVPairList tlvs = new TLVPairList();
		tlvs.loadTLVs(recBuf, offset, len-offset);
		String username = tlvs.getStringValue(TLVTag.Username);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		if(username==null || tmlType < 1){
			log.warn("log request has not username or tmlType!");
			return createSendBuffer(PackageConstant.LOG_RES_ERROR, 2, null, recBuf, sendBuf);
		}

		byte[][] logs = tlvs.getAllBinaryValues(TLVTag.Do_Msg);

		if(logs == null){
			log.warn("log request has not logs!");
			return createSendBuffer(PackageConstant.LOG_RES_ERROR, 2, null, recBuf, sendBuf);
		}

		//int logLen = logs.length;
		/*List<STBLogDTO> dtos = new ArrayList<STBLogDTO>(logLen);
		for(int j=0; j<logLen;){

			log.info("username:" + username);
			log.info("tmlType:" + tmlType);
			log.info("Service-Type:" + tlvs.getIntValue(TLVTag.Service_Type));
			log.info("Content-NAME:" + tlvs.getStringValue(TLVTag.Content_Name));
			log.info("Channel-IP-Address:" + tlvs.getStringValue(TLVTag.Channel_IP_Address));
			log.info("Channel-Start-Time:" + tlvs.getIntValue(TLVTag.Channel_Start_Time));
			log.info("Channel-Stop-Time:" + tlvs.getIntValue(TLVTag.Channel_Stop_Time));
			log.info("Do-Start-Time:" + tlvs.getIntValue(TLVTag.Do_Start_Time));
			log.info("Do-Stop-Time:" + tlvs.getIntValue(TLVTag.Do_Stop_Time));
			log.info("Do-Type" + tlvs.getIntValue(TLVTag.Do_Type));
			log.info("Do-Result:" + tlvs.getIntValue(TLVTag.Do_Result));

			tlvs.loadTLVs(logs[j], 0, logs[j].length);
			STBLogDTO dto = new STBLogDTO();
			dto.setUsername(username);
			dto.setTmlType(tmlType);
			dto.setServiceType(tlvs.getIntValue(TLVTag.Service_Type));
			dto.setCntName(tlvs.getStringValue(TLVTag.Content_Name));
			dto.setChannelIPAddress(tlvs.getStringValue(TLVTag.Channel_IP_Address));
			dto.setChannelStartTime(tlvs.getIntValue(TLVTag.Channel_Start_Time));
			dto.setChannelStopTime(tlvs.getIntValue(TLVTag.Channel_Stop_Time));
			dto.setDoStartTime(tlvs.getIntValue(TLVTag.Do_Start_Time));
			dto.setDoStopTime(tlvs.getIntValue(TLVTag.Do_Stop_Time));
			dto.setDoType(tlvs.getIntValue(TLVTag.Do_Type));
			dto.setDoResult(tlvs.getIntValue(TLVTag.Do_Result));
			j++;
			dtos.add(dto);
		}

		log.info("STB LOGS:" + dtos);

		if (dtos.size() != 0) {
			IDBService s = new DBImpl();
			s.saveLog(dtos);
		}
		log.info("STB LOGS DB");*/

		return createSendBuffer(PackageConstant.LOG_RES_OK, 0, null, recBuf, sendBuf);
	}

	private int createSendBuffer(int type, 
			int replyCode,
			TLVPairList tlvs,
			byte[] recvBuf,
			byte[] sendbuff) {
		if(tlvs == null){
			tlvs = new TLVPairList();
		}

		tlvs.addTLV(TLVTag.Result_Code, replyCode);
		byte[] tmp = tlvs.createBlock();
		int len = PackageConstant.MIN_PACKAGE_LEN + tmp.length;
		//System.arraycopy(recvBuf, 0, sendbuff, 0, 16);
		int offset = 16;
		sendbuff[offset++] = (byte) ((type >> 8) & 0xff);
		sendbuff[offset++] = (byte) (type & 0xff);
		System.arraycopy(recvBuf, offset, sendbuff, offset, 2);
		offset = offset+2;
		sendbuff[offset++] = (byte) ((len >> 8) & 0xff);
		sendbuff[offset++] = (byte) (len & 0xff);
		System.arraycopy(tmp, 0, sendbuff, offset, tmp.length);

		return len;
	} // sendPacket

}
