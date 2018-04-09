package communicate.pkmgmt.stb;

import org.apache.log4j.Logger;

import communicate.common.TimeMgmt;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.tmljdbc.ITmlJDBC;
import communicate.pkmgmt.tmljdbc.TmlConst;
import communicate.pkmgmt.tmljdbc.TmlJDBCImpl;

public class StbBindTelNo {
	//STB手机绑定接口
	private static Logger log = communicate.common.Logger.getLogger(StbBindTelNo.class);
	private TmlServiceImpl ser = new TmlServiceImpl();
	private int code = PackageConstant.code.stb_bindTelNo_Ack;
	private ITmlJDBC db = new TmlJDBCImpl();
	private int tmlResult = PackageConstant.result_code.ok;

	public byte[] parseData(byte[] recBuf, int len, int offset, String ip) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(recBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		String telNo = tlvs.getStringValue(TLVTag.telNO);

		
		if (tmlId == null || tmlId.trim().equals("")) {
			log.warn("StbBindTelNo param: tmlId is null!");
			return ser.createResultCodeBuf(code, tmlId, PackageConstant.result_code.register_param_error);
		}
		tmlId = tmlId.toUpperCase();
		// 用户名，密码
		String userName = tlvs.getStringValue(TLVTag.Username);
		String password = tlvs.getStringValue(TLVTag.Password);
		log.debug("------STB: StbBindTelNo pk!\n------tmlId="+tmlId+", telNo="+telNo+", U="+userName+", P="+password);

		if (userName == null || userName.trim().equals("")
				|| password == null || password.trim().equals("")) {
			log.warn("StbBindTelNo param: userName or password is null!");
			return ser.createResultCodeBuf(code, tmlId, PackageConstant.result_code.register_param_error);
		}
		if (telNo == null || telNo.trim().equals("")) {
			log.warn("StbBindTelNo param: telNo is null!");
			return ser.createResultCodeBuf(code, tmlId, PackageConstant.result_code.register_param_error);
		}
		tmlResult = db.bindTelNo(userName, password, tmlId, telNo);

		return ser.createResultCodeBuf(code, tmlId, tmlResult);
	}

	public byte[] createResult(String tmlId, int resultCode, String reason) {
		// tmlId TLV
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();
		int result_b_len = result_b.length;

		// reason
		byte[] reasonbyte = reason.getBytes();
		TLVPair reasonTLV = new TLVPair(TLVTag.description, reasonbyte);
		byte[] reason_b = reasonTLV.getTLV();
		int reason_b_len = reason_b.length;

		int offset = 0;
		int header_len = 22;
		int allLen = header_len + tmlId_b_len + result_b_len + reason_b_len;

		byte[] toBuf = ser.createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(result_b, 0, toBuf, offset, result_b_len);
		offset = offset + result_b_len;

		System.arraycopy(reason_b, 0, toBuf, offset, reason_b_len);
		offset = offset + reason_b_len;
		return toBuf;
	}

	public static void main(String[] a) {
		StbBindTelNo r = new StbBindTelNo();

		String tmlId ="0016e81fed66";
		tmlId = tmlId.toUpperCase();
		String userName="0109001005000006";
		String password="092196";
		//BossClient boss = new BossClient();
		/*Response res = boss.activate(userName, password, tmlId);
		String result = res.getResult();
		log.info("[Register] result:"+result);
		if (!result.equals(PackageConstant.BossResultCode.SUCCESS)) {
			String reason = res.getReason();
			log.info("[Register] reason:"+reason);
		}*/
		TimeMgmt t = new TimeMgmt();
		String inTime = t.getCurDate(5);
		ITmlJDBC db0 = new TmlJDBCImpl();
		db0.registerTml(userName, "", TmlConst.customerType.common, tmlId, 2, "", inTime, "auto", "");
	}
}
