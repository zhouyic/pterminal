package communicate.pkmgmt;

import org.apache.log4j.Logger;

import communicate.common.net.IHandlePacket;
import communicate.pkmgmt.cms.ReceiveBgAd;
import communicate.pkmgmt.cms.ReceiveUAd;
import communicate.pkmgmt.ols.DownloadStatus;
import communicate.pkmgmt.ols.RecOrderAck;
import communicate.pkmgmt.ols.RefundOrder;
import communicate.pkmgmt.ols.StbSysInfo;
import communicate.pkmgmt.ols.TakeOrder;
import communicate.pkmgmt.portal.ReceiveOrder;
import communicate.pkmgmt.stb.AppPortalURLRequest;
import communicate.pkmgmt.stb.HeartBeat;
import communicate.pkmgmt.stb.RecSysInfo;
import communicate.pkmgmt.stb.StbBindTelNo;
import communicate.pkmgmt.stb.StbOrderRequest;
import communicate.pkmgmt.thai.ThaiLogin;
import communicate.pkmgmt.thai.ThaiLogout;

public class DefaultHandlePacket implements IHandlePacket {

	private static final Logger log = communicate.common.Logger.getLogger(DefaultHandlePacket.class);
	public byte[] handlePacket(byte[] recBuf, int len, String remoteIP, int remotePort) {

		if (recBuf == null) {
			log.warn("rec a error package! package len =" + len);
			return null;
		}

		if(len < PackageConstant.MIN_PACKAGE_LEN){
			log.warn("rec a error package! package len =" + len);
			return null;
		}

		int offset = 0;
		int code = 0;

		offset = 16;

		code = (recBuf[offset++] & 0xff) << 8 | recBuf[offset++] & 0xff;
		int pkId = (recBuf[offset++] & 0xff) << 8 | recBuf[offset++] & 0xff;
		int pkLen = (recBuf[offset++] & 0xff) << 8 | recBuf[offset++] & 0xff;

		if(pkLen != len){
			log.warn("rec a error package! package rec len =" + len + " package len=" + pkLen);
			return null;
		}

		byte[] ret = null;
		log.info(" handle Client Packet PK: remoteIP=" + remoteIP+", remotePort="+remotePort+ ", data len=" 
		 + len  +" code=" +code);

		switch (code) {
		// SPE
//		case  PackageConstant.code.spe_tml_certify:
//			CertifyVerified r_spe_cer = new CertifyVerified();
//			ret = r_spe_cer.receive(recBuf, len, offset);
//			break;
//
//		case  PackageConstant.code.spe_tml_order_request:
//			OrderRequest r_spe_order = new OrderRequest();
//			ret = r_spe_order.receive(recBuf, len, offset);
//			break;
//
//		case  PackageConstant.code.spe_tml_LR:
//			ReceiveLRFromSPE r_spe_LR = new ReceiveLRFromSPE();
//			ret = r_spe_LR.receive(recBuf, len, offset);
//			break;
//
//			// portal
//		case  PackageConstant.code.portal_tml_getPrice:
//			GetPrice r_portal_getprice = new GetPrice();
//			ret = r_portal_getprice.receive(recBuf, len, offset);
//			break;
//
//		case  PackageConstant.code.portal_tml_deduct:
//			DeductFee r_portal_deduct = new DeductFee();
//			ret = r_portal_deduct.receive(recBuf, len, offset);
//			break;
//应用关闭接口
		case PackageConstant.code.thai_logout:
			ThaiLogout logout = new ThaiLogout();
			ret = logout.parseData(recBuf, len, offset);
		
			
			
		case  PackageConstant.code.portal_tml_order:
			ReceiveOrder r_portal_order = new ReceiveOrder();
			ret = r_portal_order.receive(recBuf, len, offset);
			break;

//		case PackageConstant.code.portal_tml_licenseReq:
//			PortalLicenseReq portal_license = new PortalLicenseReq();
//			ret = portal_license.receive(recBuf, len, offset, pkId);
//			break;
//
			// STB
		case  PackageConstant.code.register:
			ThaiLogin reg_stb_list = new ThaiLogin();
		ret = reg_stb_list.parseData(recBuf, len, offset);
			break;
//
//		case  PackageConstant.code.list_request:
//			ListRequest r_stb_list = new ListRequest();
//			ret = r_stb_list.parseData(recBuf, len, offset, remoteIP);
//			break;

		case  PackageConstant.code.heart_beat://.AppPortalURL_request:
			HeartBeat r_stb_app = new HeartBeat();
			ret = r_stb_app.parseData(recBuf, len, offset,remoteIP);
			break;
//系统上报
		case  PackageConstant.code.rec_sys_info:
			RecSysInfo stb_info = new RecSysInfo();
			ret = stb_info.parseData(recBuf, len, offset);
			break;

		case  PackageConstant.code.stb_tml_order_request:
			StbOrderRequest r_stb_order = new StbOrderRequest();
			ret = r_stb_order.receive(recBuf, len, offset);
			break;


			// OLS
		case  PackageConstant.code.take_order:
			if (remoteIP == null || remoteIP.trim().equals("")) {
				log.warn("rec a error package! package rec remoteIP =" + remoteIP + " remotePort=" + remotePort);
				return null;
			}
			TakeOrder t = new TakeOrder();
			ret = t.parseData(recBuf, len, offset, remoteIP);
			break;

		case  PackageConstant.code.deliver_order_ACK:
			RecOrderAck r = new RecOrderAck();
			ret = r.parseData(recBuf, len, offset);
			break;

		case  PackageConstant.code.refund_order:
			RefundOrder re = new RefundOrder();
			ret = re.parseData(recBuf, len, offset);
			break;

		case PackageConstant.code.dowload_status:
			DownloadStatus ds = new DownloadStatus();
			ret = ds.parseData(recBuf, len, offset);
			//ret = recBuf;
			break;

			// CMS
		case PackageConstant.code.deliver_ad:
			ReceiveBgAd ad = new ReceiveBgAd();
			ret = ad.parseData(recBuf, len, offset);
			break;
		case PackageConstant.code.deliver_Uad:
			ReceiveUAd uad = new ReceiveUAd();
			ret = uad.parseData(recBuf, len, offset);
			break;

			// BOSS
//		case PackageConstant.code.down_inform:
//			DownloadInform di = new DownloadInform();
//			ret = di.receive(recBuf, len, offset);
//			break;
//
//			// CEP
//		case PackageConstant.code.cep_get_license:
//			CepLicenseReq cep_license = new CepLicenseReq();
//			ret = cep_license.receive(recBuf, len, offset, pkId);
//			break;

			// TMS
		case PackageConstant.code.tms_get_sysinfo:
			StbSysInfo tml_sysinfo = new StbSysInfo();
			ret = tml_sysinfo.receive(recBuf, len, offset);
			break;
		
		case PackageConstant.code.stb_bindTelNo:
			//log.info("StbBindTelNo");
			StbBindTelNo stbBindTelNo = new StbBindTelNo();
			ret = stbBindTelNo.parseData(recBuf, len, offset, remoteIP);
			break;
		default:
			log.warn("Has not find Service!");
		}
		return ret;

	}

	public int handlePacketUdp(byte[] recBuf, int len, String remoteIP, int remotePort, byte[] send) {
		int ret = 0;
		return ret;
	}


}
