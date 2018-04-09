package communicate.pkmgmt.tmljdbc;

public class TmlConst {
	public static final String EXCEL_URL = "/tmlform/";
	public static final int isSpe_false = 0;
	public static final int isSpe_true = 1;
	public static final int tms_port = 10190;
	public static final String TMLCONFIG_URL = "TMLCONFIG_URL";

	public static final class billStatus {
		// 未结算订单
		public static final int open_account = 0;
		// 已结算订单：扣除，1；取消扣除，2
		public static final int closing_account_ok = 1;
		public static final int closing_account_cancel = 2;

		// 与boss预留费用确认扣除失败
		public static final int closing_account_confirm_fail = 3;
	}

	public static final class customerType {
		public static final String common = "普通";
		public static final String VIP = "VIP";
	}

	public static final class portalType {
		public static final int STBPortal = 0;
		public static final int pcPortal = 1;
		public static final int MPortal = 2;
		public static final int BOSSPortal = 9;
	}

	/**
	 * 订购单及影片的状态
	 */
	public static final class orderStatus {
		public static final int stb_download_fail = 1; //影片下载失败
		public static final int stb_download_success = 0; //影片下载完成
		public static final int stb_download_fail_del = 4; //影片下载失败，用户删除

		// STB return
		public static final int un_send = 0;// 订购单未发送
		public static final int hav_send = 1;// 订购单已发送
		public static final int download_fail = 2; //影片下载失败
		public static final int download_success = 3; //影片下载完成
		public static final int download_fail_del = 4; //影片下载失败，用户删除
	}

	public static final class tmlType {
		public static final int all = 0;
		public static final int no_hard = 1;
		public static final int has_hard = 2;
		public static final int NOVA_Client = 3;

		public static final String all_str = "";
		public static final String no_hard_str = "无硬盘STB";
		public static final String has_hard_str = "带硬盘STB";
		public static final String NOVA_Client_str = "NOVA Client";

		public static int getIntTmlType(String str) {
			int ret = 0;

			if (str == null) return ret;

			if (str.equals(no_hard_str)) ret = no_hard;

			if (str.equals(has_hard_str)) ret = has_hard;

			if (str.equals(NOVA_Client_str)) ret = NOVA_Client;

			return ret;
		}

		public static String getStrTmlType(int type) {
			String ret = "";

			if (type == 1) ret = no_hard_str;

			if (type == 2) ret = has_hard_str;

			if (type == 3) ret = NOVA_Client_str;

			return ret;
		}
	}

	public static final class tmlStatus {
		public static final int all = -1;
		public static final int outline = 0;
		public static final int sleep = 1;
		public static final int play = 2;
		public static final int online = 3;
		public static final int download = 4;

		public static final String all_str = "";
		public static final String outline_str = "离线";
		public static final String sleep_str = "待机";
		public static final String play_str = "正在播放";
		public static final String online_str = "在线";
		public static final String download_str = "正在下载";

		public static String getStrTmlStatus(int status) {
			String ret = "";

			if (status == 0) ret = outline_str;

			if (status == 1) ret = sleep_str;

			if (status == 2) ret = play_str;

			if (status == 3) ret = online_str;

			if (status == 4) ret = download_str;

			return ret;
		}
	}

	public static final class netType {
		public final static int lineIp = 0;
		public final static int publicIp = 1;

		public final static String lineIp_str = "内网";
		public final static String publicIp_str = "公网";

		public static int getIntNetType(String str) {
			int ret = 0;

			if (str == null) return ret;

			if (str.equals(lineIp_str)) ret = lineIp;
			if (str.equals(publicIp_str)) ret = publicIp;

			return ret;
		}

		public static String getStrNetType(int type) {
			String ret = "";

			if (type == publicIp) ret = publicIp_str;

			if (type == lineIp) ret = lineIp_str;

			return ret;
		}
	}

	public static final class infoType {
		public static final int tml_sys_info = 1;
		public static final int get_log_info = 2;
		public static final int tml_file_info = 3;
	}

	public static final class adType {
		public final static int ad = 0;
		public final static int uad = 1;
	}

	public static final class serverType {
		public static final int all = -1;
		public static final int ols = 0;
		public static final int spe = 1;
		public static final int AppPortal_URL = 2;
		public static final int STBPortal_URL = 3;
		public static final int APP_FTP_URL = 4;
		public static final int Upgrade_FTP_URL = 5;
		public static final int PicServer_URL = 6;
		public static final int Detail_URL = 7;
		public static final int LogServerFtp = 8;
		public static final int CCGW = 9;
	    public static final int SessionServer = 10;
		public static final int GuBei_index = 251;
		public static final int SIS_IP_Port = 2015;

		public static final String all_str = "";
		public static final String ols_str = "OLS";
		public static final String spe_str = "SPE";
		public static final String AppPortal_URL_str = "AppPortal_URL";
		public static final String STBPortal_URL_str = "STBPortal_URL";
		public static final String APP_FTP_URL_str = "APP_FTP_URL";
		public static final String Upgrade_FTP_URL_str = "Upgrade_FTP_URL";
		public static final String PicServer_URL_str = "PicServer_URL";
		public static final String Detail_URL_str = "Detail_URL";
		public static final String LogServerFtp_str = "Log_Ftp_URL";
		public static final String CCGW_str = "CCGW_URL";
	    public static final String SessionServer_str = "SessionServer_URL";
		public static final String GuBei_index_str = "GuBei_Index_URL";
		public static final String SIS_IP_Port_str = "SIS_URL";

		public static int getIntServerType(String str) {
			int ret = 0;

			if (str == null) return ret;
			else if (str.equals(ols_str)) ret = ols;
			else if (str.equals(spe_str)) ret = spe;
			else if (str.equals(AppPortal_URL_str)) ret = AppPortal_URL;
			else if (str.equals(STBPortal_URL_str)) ret = STBPortal_URL;
			else if (str.equals(APP_FTP_URL_str)) ret = APP_FTP_URL;
			else if (str.equals(Upgrade_FTP_URL_str)) ret = Upgrade_FTP_URL;
			else if (str.equals(PicServer_URL_str)) ret = PicServer_URL;
			else if (str.equals(Detail_URL_str)) ret = Detail_URL;
			else if (str.equals(LogServerFtp_str)) ret = LogServerFtp;
			else if (str.equals(GuBei_index_str)) ret = GuBei_index;
			else if (str.equals(CCGW_str)) ret = CCGW;
			else if (str.equals(SessionServer_str)) ret = SessionServer;
			else if (str.equals(SIS_IP_Port_str)) ret = SIS_IP_Port;
			return ret;
		}

		public static String getStrServerType(int type) {
			String ret = "";

			if (type == ols) ret = ols_str;
			else if (type == spe) ret = spe_str;
			else if (type == AppPortal_URL) ret = AppPortal_URL_str;
			else if (type == STBPortal_URL) ret = STBPortal_URL_str;
			else if (type == APP_FTP_URL) ret = APP_FTP_URL_str;
			else if (type == Upgrade_FTP_URL) ret = Upgrade_FTP_URL_str;
			else if (type == PicServer_URL) ret = PicServer_URL_str;
			else if (type == Detail_URL) ret = Detail_URL_str;
			else if (type == LogServerFtp) ret = LogServerFtp_str;
			else if (type == GuBei_index) ret = GuBei_index_str;
			else if (type == SessionServer) ret = SessionServer_str;
			else if (type == CCGW) ret = CCGW_str;
			else if (type == SIS_IP_Port) ret = SIS_IP_Port_str;
			return ret;
		}
	}

	public static final class downrate {
		public static final String ALL = "";
		// B/S
		public static final String ZERO_BS = "0B/S";
		public static final String FIVEH_BS = "500B/S";

		// KB/S
		public static final String ONE_KBS = "1KB/S";
		public static final String ONEH_KBS = "100KB/S";
		public static final String FIVEH_KBS = "500KB/S";

		// M/S
		public static final String ONE_MS = "1M/S";
	}
}