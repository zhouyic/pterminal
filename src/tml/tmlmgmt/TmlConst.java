package tml.tmlmgmt;

public class TmlConst
{
  public static final String EXCEL_URL = "/tmlform/";
  public static final int isSpe_false = 0;
  public static final int isSpe_true = 1;
  public static final int tms_port = 10190;
  public static final String TMLCONFIG_URL = "TMLCONFIG_URL";

  public static final class groupName
  {
    public static final String ALL = "全部";
    public static final int ALL_GROUPID = -1;
    public static final String NONE = "无分组";
    public static final int NONE_GROUPID = 0;
  }

  public static final class downrate
  {
    public static final String ALL = "";
    public static final String ZERO_BS = "0B/S";
    public static final String FIVEH_BS = "500B/S";
    public static final String ONE_KBS = "1KB/S";
    public static final String ONEH_KBS = "100KB/S";
    public static final String FIVEH_KBS = "500KB/S";
    public static final String ONE_MS = "1M/S";
  }

  public static final class serverType
  {
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

    public static int getIntServerType(String paramString)
    {
      int i = 0;
      if (paramString == null)
        return i;
      if (paramString.equals("OLS"))
        i = 0;
      else if (paramString.equals("SPE"))
        i = 1;
      else if (paramString.equals("AppPortal_URL"))
        i = 2;
      else if (paramString.equals("STBPortal_URL"))
        i = 3;
      else if (paramString.equals("APP_FTP_URL"))
        i = 4;
      else if (paramString.equals("Upgrade_FTP_URL"))
        i = 5;
      else if (paramString.equals("PicServer_URL"))
        i = 6;
      else if (paramString.equals("Detail_URL"))
        i = 7;
      else if (paramString.equals("Log_Ftp_URL"))
        i = 8;
      else if (paramString.equals("GuBei_Index_URL"))
        i = 251;
      else if (paramString.equals("CCGW_URL"))
        i = 9;
      else if (paramString.equals("SessionServer_URL"))
        i = 10;
      else if (paramString.equals("SIS_URL"))
        i = 2015;
      return i;
    }

    public static String getStrServerType(int paramInt)
    {
      String str = "";
      if (paramInt == 0)
        str = "OLS";
      else if (paramInt == 1)
        str = "SPE";
      else if (paramInt == 2)
        str = "AppPortal_URL";
      else if (paramInt == 3)
        str = "STBPortal_URL";
      else if (paramInt == 4)
        str = "APP_FTP_URL";
      else if (paramInt == 5)
        str = "Upgrade_FTP_URL";
      else if (paramInt == 6)
        str = "PicServer_URL";
      else if (paramInt == 7)
        str = "Detail_URL";
      else if (paramInt == 8)
        str = "Log_Ftp_URL";
      else if (paramInt == 251)
        str = "GuBei_Index_URL";
      else if (paramInt == 10)
        str = "SessionServer_URL";
      else if (paramInt == 9)
        str = "CCGW_URL";
      else if (paramInt == 2015)
        str = "SIS_URL";
      return str;
    }
  }

  public static final class adType
  {
    public static final int ad = 0;
    public static final int uad = 1;
  }

  public static final class infoType
  {
    public static final int tml_sys_info = 1;
    public static final int get_log_info = 2;
    public static final int tml_file_info = 3;
  }

  public static final class netType
  {
    public static final int lineIp = 0;
    public static final int publicIp = 1;
    public static final String lineIp_str = "内网";
    public static final String publicIp_str = "公网";

    public static int getIntNetType(String paramString)
    {
      int i = 0;
      if (paramString == null)
        return i;
      if (paramString.equals("内网"))
        i = 0;
      if (paramString.equals("公网"))
        i = 1;
      return i;
    }

    public static String getStrNetType(int paramInt)
    {
      String str = "";
      if (paramInt == 1)
        str = "公网";
      if (paramInt == 0)
        str = "内网";
      return str;
    }
  }

  public static final class tmlStatus
  {
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

    public static String getStrTmlStatus(int paramInt)
    {
      String str = "";
      if (paramInt == 0)
        str = "离线";
      if (paramInt == 1)
        str = "待机";
      if (paramInt == 2)
        str = "正在播放";
      if (paramInt == 3)
        str = "在线";
      if (paramInt == 4)
        str = "正在下载";
      return str;
    }
  }

  public static final class tmlType
  {
    public static final int all = 0;
    public static final int no_hard = 1;
    public static final int has_hard = 2;
    public static final int NOVA_Client = 3;
    public static final String all_str = "";
    public static final String no_hard_str = "无硬盘STB";
    public static final String has_hard_str = "带硬盘STB";
    public static final String NOVA_Client_str = "NOVA Client";

    public static int getIntTmlType(String paramString)
    {
      int i = 0;
      if (paramString == null)
        return i;
      if (paramString.equals("无硬盘STB"))
        i = 1;
      if (paramString.equals("带硬盘STB"))
        i = 2;
      if (paramString.equals("NOVA Client"))
        i = 3;
      return i;
    }

    public static String getStrTmlType(int paramInt)
    {
      String str = "";
      if (paramInt == 1)
        str = "无硬盘STB";
      if (paramInt == 2)
        str = "带硬盘STB";
      if (paramInt == 3)
        str = "NOVA Client";
      return str;
    }
  }

  public static final class orderStatus
  {
    public static final int stb_download_fail = 1;
    public static final int stb_download_success = 0;
    public static final int stb_download_fail_del = 4;
    public static final int un_send = 0;
    public static final int hav_send = 1;
    public static final int download_fail = 2;
    public static final int download_success = 3;
    public static final int download_fail_del = 4;
  }

  public static final class portalType
  {
    public static final int STBPortal = 0;
    public static final int pcPortal = 1;
    public static final int MPortal = 2;
    public static final int BOSSPortal = 9;
  }

  public static final class customerType
  {
    public static final String common = "普通";
    public static final String VIP = "VIP";
  }

  public static final class billStatus
  {
    public static final int open_account = 0;
    public static final int closing_account_ok = 1;
    public static final int closing_account_cancel = 2;
    public static final int closing_account_confirm_fail = 3;
  }
}