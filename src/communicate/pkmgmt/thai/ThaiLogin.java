package communicate.pkmgmt.thai;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.IThaiService;
import communicate.pkmgmt.ITmlService;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.ThaiServiceImpl;
import communicate.pkmgmt.TmlServiceImpl;
import communicate.pkmgmt.dto.TmlBase;
import communicate.pkmgmt.dto.TmlConfDto;
import communicate.pkmgmt.dto.TmlDto;
import communicate.pkmgmt.stb.HeartBeat;

public class ThaiLogin {
	public static int header_len = 22;
	private static Logger log = Logger.getLogger(ThaiLogin.class);
	public static Map<String, TmlDto> loginTime = new HashMap<String, TmlDto>();
	
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
		//取报文
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}
		thaiService = new ThaiServiceImpl();
//		log.info("———————————登陆开始——————————————————TLVTag.TML_ID="+TLVTag.TML_ID);
		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		tmlId = tmlId.toUpperCase();
		log.info("———————————登陆开始—————————————————— parseData----TML Login tmlId："+tmlId);
		String hardware=tlvs.getStringValue(TLVTag.HardwareId);
		log.debug("登陆hardware："+hardware);
		String countryId="";
		String regionId=tlvs.getStringValue(TLVTag.RegionId);
		log.debug("登陆regionId："+regionId);
		String currentversion=tlvs.getStringValue(TLVTag.CurrentVersion);
		JSONArray jsonArray=new JSONArray();
		String TimeZone=tlvs.getStringValue(TLVTag.TimeZone);
		String Language=tlvs.getStringValue(TLVTag.Language);
		log.debug("登陆Language："+Language);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		String tmlSN = tlvs.getStringValue(TLVTag.TML_SN);	
		log.debug("登陆tmlSN："+tmlSN);
//		String hardware=thaiService.gethardwareDByhardwareId(hardwareId);
		
		//heartbeatMap.put(stbId  logintime  heartbeatime)
		
		String groupId=thaiService.getGroupIdDByTmId(tmlId);
		log.debug("!!!!!!!!!!!!!groupId："+groupId);
		log.debug("盒子的版本号："+currentversion);
		String serialnum=thaiService.getSerialIdDByTmlId(tmlId);
		boolean serialflag=true;
		log.debug("!!!!!!!!!!!!!serialnum："+serialnum);
		String usercodeId="";
		String promodelId="";
		String probatchId="";	
		String country="";
		if(serialnum.length()==18){
		countryId=serialnum.substring(0,2);
		log.debug("查询到的countryId："+countryId);
	    country=thaiService.getcountryDBycountryId(countryId);
	    log.debug("查询到的country："+country);
		usercodeId=serialnum.substring(2, 4);
		promodelId=serialnum.substring(4, 5);
		probatchId=serialnum.substring(5, 7);
		}else{
			serialflag=false;
			log.debug("serialflag:"+serialflag);
		}	
		log.debug("*****serialnum:"+serialnum+",usercodeId:"+usercodeId+",promodelId:"+promodelId+",probatchId:"+probatchId);
		log.debug("#########  login : tmlId="+tmlId+", tmlType="+tmlType+", tmlSN=" + tmlSN+", hardwareId=" + hardware+", countryId=" + countryId+", regionId=" + regionId+", currentversion=" + currentversion+", groupId=" + groupId);
		// new Date()为获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		boolean flag=thaiService.isTmlExist(tmlId);
		log.debug("-------------flag:"+flag);
		String firstlogin=thaiService.selectFirstLogin(tmlId);
		log.debug("-------------firstlogin:"+firstlogin);
		if(flag==true){	
			log.debug("-------------step3:");
		if(firstlogin==null||firstlogin.length()<=0||firstlogin.equals("null")){
			log.debug("-------------更新中文首次登陆时间-------");
			thaiService.updateFirstLoginTime(tmlId, time);
		}
		log.debug("-------------更新上报信息开始-------");
		//如果盒子上报的当前版本没有lancher的版本，就分装一个默认为0版本
		if(!StringUtils.isEmpty(currentversion)){
			JSONArray parseArray = JSONArray.parseArray(currentversion);
			if(parseArray!=null&&parseArray.size()<=2&&currentversion.indexOf("1003")==-1){
				JSONObject jsonObj=new JSONObject();
				jsonObj.put("ID", "1003");
				jsonObj.put("Version", "0");
				parseArray.add(jsonObj);
				currentversion=parseArray.toJSONString();
			};
		}
		thaiService.updateTmlbaseByTmlId(tmlId, hardware,
		countryId,currentversion,TimeZone, Language,tmlType , country, hardware);
		log.debug("-------------插入上线时间开始-------");
		String type="online";
		thaiService.addTmlHistory(tmlId, time,type);
		}
		log.debug("#########  login : tmlId="+tmlId+", tmlType="+tmlType+", tmlSN=" + tmlSN+", hardware=" + hardware+", countryId=" + countryId+", regionId=" + regionId+", currentversion=" + currentversion);
		long t = System.currentTimeMillis();
		HeartBeat.heartbeatMap.put(tmlId, t);
		log.debug("-------------判断盒子有没有注册:"+thaiService.isTmlExist(tmlId));
		String portalUrl="";
		String logurl="";
		String ntpurl="";
		int heartbeart=0;
		String groupname="";
		String cdnUrl="";
		String registerurl="";
		String preurl="";
		String upgradeInfourl="";
		String appInfourl="";
			if(thaiService.isTmlExist(tmlId)) {
//				log.debug("盒子存在here:");
				//判断first_login是不是存在 不存在的话就插入当下时间 
				TmlDto dto = new TmlDto();
				dto.setOnlineTime(time);
				dto.setTmlId(tmlId);
				dto.setTmlSN(tmlSN);
				thaiService.login(tmlId, tmlSN);
				loginTime.put(tmlId, dto);
				TmlConfDto tmlConfDto = thaiService.getTmlConfDtoByTmlId(tmlId);
				log.info("tmlConfDto2: "+tmlConfDto);
				
			    preurl=thaiService.getpreUrlDByDefault(groupId);
			    cdnUrl=usercodeId+"/"+promodelId+"/"+groupId+"/";
//				cdnUrl=thaiService.getCndurlDByGroupId(groupId);
				log.debug("！！！！！！cdnUrl为:"+cdnUrl);
				log.debug("最终的升级url为:"+preurl+cdnUrl);
//				if(serialflag){
//				cdnUrl = thaiService.getCdnUrlDBySerial(usercodeId, promodelId, probatchId, groupId);
//				log.info("-----------进入正常情况下url:"+cdnUrl);
//					if(cdnUrl.equals("")||cdnUrl==null){
//						log.info("-----------进入第一种default模式下url:"+cdnUrl);
//					 cdnUrl = thaiService.getCdnUrlDByDefault(groupId);
//					}
//				}else {
//		     	cdnUrl = thaiService.getCdnUrlDByDefault(groupId);
//		     	log.info("-----------进入第二种default模式下url:"+cdnUrl);
//				}			
//				log.info("------------盒子注册之后的url:"+cdnUrl);		
			 portalUrl=tmlConfDto.getPortalUrl();
			 logurl=tmlConfDto.getLogUrl();
			 ntpurl=tmlConfDto.getNtpUrl();
		     heartbeart=tmlConfDto.getHeartbeatRate();
			 groupname=tmlConfDto.getGroupName();
	         registerurl=tmlConfDto.getRegisterUrl();
	         upgradeInfourl=tmlConfDto.getUpgradeInfoUrl();
	         log.debug("----------upgradeInfourl-------:"+upgradeInfourl);
	         appInfourl=tmlConfDto.getAppInfoUrl();
	 		if(portalUrl==null){
				portalUrl="";
			}
			if(logurl==null){
				logurl="";
			}
			if(ntpurl==null){
				ntpurl="";
			}			
			if(groupname==null){
				groupname="";
			}
			if(preurl==null){
				preurl="";
			}
			if(cdnUrl==null){
				cdnUrl="";
			}
			if(registerurl==null){
				registerurl="";
			}
			if(upgradeInfourl==null){
				upgradeInfourl="";
			}
			if(appInfourl==null){
				appInfourl="";
			}
			} 
			log.debug("———————————测试登陆结束————————————————————");
			return createResultCodeBuf(PackageConstant.code.register_ACK, tmlId, portalUrl, logurl,ntpurl,heartbeart,groupname,preurl+cdnUrl,registerurl,upgradeInfourl,appInfourl);
			
	}
	
	public static byte[] createResultCodeBuf(int code, String tmlId, String portal_url, String log_url,String ntp_url, int heart_beat_rate,String group_type,String upgrade_url,String register_url,String upgradeInfo_url,String appInfourl) {
		// tmlId TLV
		log.debug("code=" + code + 
				"  tmlId="+ tmlId + 
				"  portal_url= "+ portal_url + 
				"  log_url="+ log_url + 
				"  ntp_url="+ ntp_url + 
				"  heart_beat_rate="+ heart_beat_rate + 
				"  group_type="+ group_type + 
				"  upgrade_url="+ upgrade_url + 
				"  upgradeInfo_url="+ upgradeInfo_url + 
				"  appInfourl="+ appInfourl + 
				"  register_url="+ register_url 
				);
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;
	
		//portal_url TLV
		byte[] portal_urlByte = portal_url.getBytes();
		TLVPair portal_urlTLV = new TLVPair(TLVTag.portalUrl, portal_urlByte);
		byte[] portal_url_b = portal_urlTLV.getTLV();
		int portal_url_b_len = portal_url_b.length;
		
		//log_url TLV
		byte[] log_urlByte = log_url.getBytes();
		TLVPair log_urlTLV = new TLVPair(TLVTag.logFtpURL, log_urlByte);
		byte[] log_url_b = log_urlTLV.getTLV();
		int log_url_b_len = log_url_b.length;
		
		//ntp_url TLV
		byte[] ntp_urlByte = ntp_url.getBytes();
		TLVPair ntp_urlTLV = new TLVPair(TLVTag.ntp_server, ntp_urlByte);
		byte[] ntp_url_b = ntp_urlTLV.getTLV();
		int ntp_url_b_len = ntp_url_b.length;
		
		
		//upgrade_url
		byte[] upgrade_urlByte = upgrade_url.getBytes();
		TLVPair upgrade_urlTLV = new TLVPair(TLVTag.upgrade_server, upgrade_urlByte);
		byte[] upgrade_url_b = upgrade_urlTLV.getTLV();
		int upgrade_url_b_len =upgrade_url_b.length;
		//upgradeInfourl
		byte[] upgradeInfo_urlByte = upgradeInfo_url.getBytes();
		TLVPair upgradeInfo_urlTLV = new TLVPair(TLVTag.Upgrade_Info_server_url, upgradeInfo_urlByte);
		byte[] upgradeInfo_url_b = upgradeInfo_urlTLV.getTLV();
		int upgradeInfo_url_b_len =upgradeInfo_url_b.length;
		
		//appInfourl
		byte[] appInfourlByte = appInfourl.getBytes();
		TLVPair appInfourl_urlTLV = new TLVPair(TLVTag.App_info_server_url, appInfourlByte);
		byte[] appInfourl_url_b = appInfourl_urlTLV.getTLV();
		int appInfourl_url_b_len =appInfourl_url_b.length;
		
		
		//register_url
		byte[] register_urlByte = register_url.getBytes();
		TLVPair register_urlTLV = new TLVPair(TLVTag.register_server, register_urlByte);
		byte[] register_url_b = register_urlTLV.getTLV();
		int register_url_b_len =register_url_b.length;
		// groupType TLV
		byte[] groupTypebyte = group_type.getBytes();
		TLVPair groupTypeTLV = new TLVPair(TLVTag.group_type, groupTypebyte);
		byte[] groupType_b = groupTypeTLV.getTLV();
		int groupType_b_len = groupType_b.length;
		
							
		// heart_beat_rate TLV
		byte[] heart_beat_ratebytes = new byte[4];
		heart_beat_ratebytes[0] = (byte)((heart_beat_rate >> 24) & 0xff);
		heart_beat_ratebytes[1] = (byte)((heart_beat_rate >> 16) & 0xff);
		heart_beat_ratebytes[2] = (byte)((heart_beat_rate >> 8) & 0xff);
		heart_beat_ratebytes[3] = (byte)(heart_beat_rate & 0xff);
		TLVPair heart_beat_rateTLV = new TLVPair(TLVTag.heart_beat_rate, heart_beat_ratebytes);
		byte[] heart_beat_rate_b = heart_beat_rateTLV.getTLV();
		int heart_beat_rate_b_len = heart_beat_rate_b.length;
		
		int offset = 0;
		int allLen = header_len + tmlId_b_len + portal_url_b_len + log_url_b_len + ntp_url_b_len + heart_beat_rate_b_len+upgrade_url_b_len+groupType_b_len+register_url_b_len+upgradeInfo_url_b_len+appInfourl_url_b_len;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		
		
		System.arraycopy(portal_url_b, 0, toBuf, offset, portal_url_b_len);
		offset = offset + portal_url_b_len;
		
		System.arraycopy(log_url_b, 0, toBuf, offset, log_url_b_len);
		offset = offset + log_url_b_len;
		
		System.arraycopy(ntp_url_b, 0, toBuf, offset, ntp_url_b_len);
		offset = offset + ntp_url_b_len;
		
		System.arraycopy(upgrade_url_b, 0, toBuf, offset, upgrade_url_b_len);
		offset = offset + upgrade_url_b_len;
		
		System.arraycopy(register_url_b, 0, toBuf, offset, register_url_b_len);
		offset = offset + register_url_b_len;
		
		System.arraycopy(groupType_b, 0, toBuf, offset, groupType_b_len);
		offset = offset + groupType_b_len;
		
		System.arraycopy(heart_beat_rate_b, 0, toBuf, offset, heart_beat_rate_b_len);
		offset = offset + heart_beat_rate_b_len;
		
		System.arraycopy(upgradeInfo_url_b, 0, toBuf, offset, upgradeInfo_url_b_len);
		offset = offset + upgradeInfo_url_b_len;
		
		System.arraycopy(appInfourl_url_b, 0, toBuf, offset, appInfourl_url_b_len);
		offset = offset + appInfourl_url_b_len;
		
//		log.debug("返回数据===========toBuf===================="+toBuf);
		return toBuf;
	}
	public static void main(String[] args) {
		String versionString="[{'ID':'1001','Version':'91'},{'ID':'1002','Version':'91'}]";
		JSONArray parseArray = JSONArray.parseArray(versionString);
		if(parseArray!=null&&parseArray.size()<=2&&versionString.indexOf("1003")==-1){
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("ID", "1003");
			jsonObj.put("Version", "0");
			parseArray.add(jsonObj);
		};
		System.out.println(parseArray.toJSONString());
	}
	
	public static byte[] createByte(int code, int len) {
		long pkId = System.currentTimeMillis()/1000;
		int offset = 0;
		byte[] headerByte = new byte[len];

		offset += 16;
		headerByte[offset++] = (byte)((code >> 8) & 0xff);
		headerByte[offset++] = (byte)(code & 0xff);
		headerByte[offset++] = (byte)((pkId >> 8) & 0xff);
		headerByte[offset++] = (byte)(pkId & 0xff);

		headerByte[offset++] = (byte)((len >> 8) & 0xff);
		headerByte[offset++] = (byte)(len & 0xff);

		return headerByte;
	}}

