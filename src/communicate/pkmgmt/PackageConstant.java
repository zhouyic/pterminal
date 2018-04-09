package communicate.pkmgmt;

import java.text.SimpleDateFormat;

public class PackageConstant {
	public static final SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdfno_YMD = new SimpleDateFormat("yyyyMMdd");

	public final static String default_userName = "0000";

	public static final int MIN_PACKAGE_LEN = 22;

	public static final int LOG_REQ = 4;

	public static final int LOG_RES_OK = 5;

	public static final int LOG_RES_ERROR = 6;


	public final static int TML_PORT = 10190;

	//public final static int TML_STB_PORT = 10192;

	//public final static int DRM_PORT = 10190;
	public final static int TML_KEEP_ALIVE_PORT = 10300;

	public final static int MEM_PORT = 11211;

	public final static String DRM_IP = "DRM_IP";

	public final static String DRM_PORT = "DRM_PORT";

	public final static String LOCAL_DRM_IP = "LOCAL_DRM_IP";

	public static final class netType {
		public final static String line_ip_str = "192.168.";
		public final static int lineIp = 0;
		public final static int publicIp = 1;
	}

	public static final class PortalOrderStatus {
		public final static int ok = 0;
		public final static int system_error = 501;
		public final static int BOSS_AAA_error = 502;
	}

	public static final class STBPwdMode {
		public final static int plain = 0;
		public final static int md5 = 1;
	}

	public static final class code {
		// LR: License_Request
		// TML-DRM
		public final static int tml_drm_LR = 201;
		public final static int drm_tml_LR_Ack_ok = 202;
		public final static int drm_tml_LR_Ack_fail = 203;

		// TML-SPE
		public final static int spe_tml_certify = 101;
		public final static int spe_tml_certify_Ack = 102;
		public final static int spe_tml_LR = 103;
		public final static int spe_tml_LR_Ack = 104;
		public final static int spe_tml_order_request = 105;
		public final static int spe_tml_order_Ack = 106;

		// TML-PORTAL
		public final static int portal_tml_order = 301;
		public final static int portal_tml_order_Ack = 302;

		public final static int portal_tml_getPrice = 303;
		public final static int portal_tml_getPrice_Ack = 304;

		public final static int portal_tml_deduct = 305;
		public final static int portal_tml_deduct_Ack = 306;

		public final static int portal_tml_licenseReq = 307;
		public final static int portal_tml_licenseReq_Ack = 308;

		// STB
		public final static int register = 1;
		public final static int register_ACK = 2;
		public final static int heart_beat = 7;
		public final static int list_request = 10;
		public final static int list_request_ACK = 11;
		public final static int AppPortalURL_request = 12;
		public final static int AppPortalURL_request_ACK = 13;

		public final static int rec_sys_info = 41;
		public final static int rec_sys_info_ACK = 42;

		public final static int stb_tml_getPrice = 43;
		public final static int stb_tml_getPrice_Ack = 44;

		public final static int stb_tml_deduct = 45;
		public final static int stb_tml_deduct_Ack = 46;

		public final static int stb_tml_order_request = 47;
		public final static int stb_tml_order_Ack = 48;

		public final static int stb_bindTelNo = 49;
		public final static int stb_bindTelNo_Ack = 50;
		
		// OLS
		// 上报节目下载状态    OLS —> 终端管理
		public static final int dowload_status = 15;

		// 订阅订购单    OLS —> 终端管理
		public static final int take_order = 16;

		// 派送订购单       终端管理 —> OLS
		public static final int deliver_order = 17;

		// 收到订购单确认      OLS —> 终端管理
		public static final int deliver_order_ACK = 18;

		// 退订订购单         OLS —> 终端管理
		public static final int refund_order = 19;

		// 退订订购单确认       终端管理 —> OLS
		public static final int refund_order_ACK = 20;

		public final static int sys_info_request = 403;

		// CMS
		public static final int deliver_ad = 401;
		public static final int deliver_ad_Ack = 402;

		public static final int deliver_Uad = 404;
		public static final int deliver_Uad_Ack = 405;

		// BOSS
		public static final int down_inform = 406;
		public static final int down_inform_Ack = 407;

		// CEP
		public static final int cep_get_license = 408;
		public static final int cep_get_license_Ack = 409;

		//TMS
		public static final int tms_get_sysinfo = 410;
		public static final int tms_get_sysinfo_Ack = 411;
		
		// 多屏互动长连接code
		public static final int STB_LOGIN = 451;
		public static final int STB_LOGIN_RESULT = 452;
		public static final int STB_STATUS_MESSAGE = 453;
		public static final int STB_STATUS_GET = 454;
		public static final int STB_TRANSLATE_SCREEN = 455;
		public static final int STB_TRANSLATE_SCREEN_RESULT = 456;
		public static final int STB_TRANSLATE_SCREEN_COMMAND = 457;
		public static final int STB_OFFLINE = 458;
		
		
		//泰国TMS code
		public final static int thai_register = 1;
		public static final int thai_logout = 701;
	}

	public static final class result_code {
		// ok
		public final static int ok = 0;

		// DRM-TML LR-ResultCode-error
		public final static int format_error = 200;
		public final static int STBID_error = 201;
		public final static int ContentId_error = 202;
		public final static int no_license = 203;
		public final static int certificate_invalid = 204;
		public final static int sign_error = 205;
		public final static int other_error = 206;
		public final static int data_error = 207;

		// TML-SPE
		public final static int ubknown_Order = 300;
		public final static int ubknown_stb = 301;
		public final static int ubknown_drm = 302;

		// TML-STB
		public final static int register_param_error = 401;
		public final static int certify_error = 403;
		public final static int db_error = 405;
		public final static int NOT_ENOUGH_MONEY_ERROR = 555;
		
		public final static int stb_exist = 402;

		// TML-CMS
		public final static int error = 501;

		public final static int connect_refused = 601;

		public static String getResultStr(int resultCode) {
			String reason = "";

			if (resultCode == PackageConstant.result_code.format_error) {
				reason = "报文格式错误！";
			} else if (resultCode == PackageConstant.result_code.STBID_error) {
				reason = "无效的用户终端号！";
			} else if (resultCode == PackageConstant.result_code.ContentId_error) {
				reason = "无效的影片节目号！";
			} else if (resultCode == PackageConstant.result_code.no_license) {
				reason = "无授权！";
			} else if (resultCode == PackageConstant.result_code.certificate_invalid) {
				reason = "证书已过期！";
			} else if (resultCode == PackageConstant.result_code.sign_error) {
				reason = "SIGN签名验证错误！";
			} else if (resultCode == PackageConstant.result_code.other_error) {
				reason = "其他错误！";
			}

			return reason;
		}
	}

	public static final class BossResultCode{
		// 成功
		public static final int SUCCESS = 200;
		// 登录名错误或者不存在
		public static final int LOGINNAME_ERROR = 551;
		// 密码错误
		public static final int PASSWORD_ERROR = 552;
		// 内容错误或者不存在
		public static final int CONTENT_ERROR = 553;
		// 余额不足
		public static final int NOT_ENOUGH_MONEY_ERROR = 554;
		// 未订购业务或者业务暂停
		public static final int NOT_ORDER_SERVICE_ERROR = 555;
		// 业务不存在
		public static final int UNKNOWN_SERVICE_ERROR = 556;
		// 业务资费策略不存在
		public static final int UNKNOWN_SERVICE_COUNT_ERROR = 557;
		// 产品资费策略不存在
		public static final int UNKNOWN_PRODUCT_COUNT_ERROR = 558;
		// 其它原因
		public static final int UNKNOWN_ERROR = 999;
	}

	public static final class service_Type {
		public final static int movie = 4;
		public final static int teleplay = 7;
		public final static int music_album = 8;
		public final static int music_single = 9;
		public final static int bg_ad = 10;
		public final static int pic = 11;
		public static final int SPECIAL_SINGLE=12;
		public static final int SPECIAL_MULTI=13;

		public final static String movie_str = "电影";
		public final static String teleplay_str = "电视剧";
		public final static String music_album_str = "音乐专辑";
		public final static String music_single_str = "音乐单曲";
		public final static String bg_ad_str = "背景广告";
		public final static String pic_str = "图片";
		public static final String SPECIAL_SINGLE_str="单集专题";
		public static final String SPECIAL_MULTI_str="多集专题";

		// 歌手分类
		public final static String male_singer = "11";
		public final static String female_singer= "12";
		public final static String band= "13";

		public final static String male_singer_str = "男歌手";
		public final static String female_singer_str = "女歌手";
		public final static String band_str = "乐队组合";

		public static String getString(String type) {
			String ret = "";
			if (type == null || type.trim().equals("")) return ret;
			if (type.trim().equals(band)) ret = band_str;
			if (type.trim().equals(male_singer)) ret = male_singer_str;
			if (type.trim().equals(female_singer)) ret = female_singer_str;
			return ret;
		}

		public static String getProgramTypeStr(int type) {
			String ret = "";

			if (type == movie)ret = movie_str;
			else if (type == teleplay)ret = teleplay_str;
			else if (type == music_album)ret = music_album_str;
			else if (type == music_single)ret = music_single_str;
			else if (type == bg_ad)ret = bg_ad_str;
			else if (type == pic)ret = pic_str;
			else if (type == SPECIAL_SINGLE)ret = SPECIAL_SINGLE_str;
			else if (type == SPECIAL_MULTI)ret = SPECIAL_MULTI_str;

			return ret;
		}
	}

	public static String toString(String[] strs) {
		String ret = "";

		if (strs !=null && strs.length > 0) {
			ret = strs[0];
			for (int i = 1; i < strs.length; i++) {
				ret+="|"+strs[i];
			}
		}

		return ret;
	}
}
