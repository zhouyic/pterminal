package communicate.pkmgmt;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import communicate.pkmgmt.dto.TmlBase;
import communicate.pkmgmt.dto.TmlConfDto;
import communicate.pkmgmt.dto.TmlDto;
import communicate.pkmgmt.dto.TmlHistory;

public interface IThaiService {
	public boolean updateAppStatus(String tmlId, int status);
	public List<String> getAllTmlIds();
	/**
	 * 根据终端Id和状态修改 状态值
	 * @author:zhouyc
	 * @date:2017年6月23日
	 * @param tmlId：终端Id,selectType:查询的状态值，updateType：要修改的状态值
	 * @return boolean 是否修改成功
	 */
	public boolean batchUpdateAppInfosLastesMonths(List<String> tmlIds, String selectType,String updateType);
	public boolean updateCurrentVersion(String tmlId, String version);
	
	public boolean updateFirstLoginTime(String tmlId, String firstlogin);
	
	public boolean isTmlExist(String tmlId);
	
	public TmlConfDto getTmlConfDtoByTmlId(String tmlId);
	
	public String getGroupIdDByTmId(String tmlId);
	public String getHeartBeatRateByTmlId(String tmlId);
	public String getCndurlDByGroupId(String GroupId);
	
	
	public String getCdnUrlDByRegion(String hardware,String country,String region,String groupId);
	
	public String getCdnUrlDByHardware(String hardware,String groupId);
	
	public String getCdnUrlDByHardwareonly(String hardwareId);
	
	public String getSerialIdDByTmlId(String tmlId);
	
	public String getpreUrlDByDefault(String groupId);
	
	public String getCdnUrlDByDefault(String groupId) ;
	public String getCdnUrlDBySerial(String usercodeId,String promodelId,String probatchId,String groupId) ;
	
	public String getCdnUrlDByCountry(String hardware,String country,String groupId);
	
	public boolean updateTmlPlayMsg(String tmlId, String contentName,String remoteIP,String country, int serviceType);
	
	public boolean updateLoginTime(String tmlId, String loginTime);
	
	public boolean login(String tmlId, String tmlSN);
	
	public boolean logout(String tmlId);
	public boolean batchLogout(List<String> tmlId);
	
	public void batchUpdateLoginTime(Collection<TmlDto> tmls);
	
	public TmlConfDto getTmlConfDtoByGroupId(String groupId);
	
	public String gethardwareDByhardwareId(String hardwareId) ;
	
	public String getcountryDBycountryId(String countryId);
	
	public String getregionDByregionId(String regionId);
	
	public String selectFirstLogin(String tmlId);
	
	public String selectFirstLoginEn(String tmlId);
	
	public boolean updateTmlbaseByTmlId(String tmlId, String hardwareId,
			String countryId,String currentversion,String TimeZone,String Language,int tmlType ,String country,String hardware);
	public int addTmlBase(String tmlId, String hardwareId,
			String countryId,String regionId,String currentversion,String TimeZone,String Language,int tmlType ,String tmlSN,String country,String region,String hardware,String time);
	public int addTmlBaseEn(String tmlId, String hardwareId,
			String countryId,String regionId,String currentversion,String TimeZone,String Language,int tmlType ,String tmlSN,String country,String region,String hardware,String time);
	public TmlBase getTmlbaseDtoByTmlId(String tmlId);
	public TmlHistory getTmlHistoryByConf(String tmlId,String type);
	public List<TmlHistory> findTmlHistory(String tmlId,String type);
	public List<TmlHistory> findTmlHistory(String tmlId, String type,
			String startTime,String endTime, String order, int count);
	public String selectFirstFlag(String tmlId);
//	public int addTmlHistoryEn(String tmlId,String time,String type);
	public int addTmlHistory(String tmlId,String time,String type);
	
	public int batchAddTmlHistory(List<String> tmlId,String time,String type);
	
	public void updateFirstFlag(String tmlId);
	
	public void getAllOnTerminal();
	public List<String> getAllOnlineTerminal();
	public boolean updateSysInfo(String tmlId, String version, String ableMem, String portalUrl, String logServerUrl, String ntpUrl, int heartBeatRate,String upgradeServerUrl,String upgradeInfoUrl,String appInfoUrl);
	public List<String> getRetailerList();
	public List<String> getRetailerListInLoginTbl(String time);
	public List<String> getRetailerListInActivationTbl(String time);
	public int countLoginTotal(String retailerName, String date);
	public int countLoginTotalDay(String date);
	public int countRegisterTotal(String retailerName, String date);
	public int countRegisterTotalDay(String date);
	
	public void batchInsertStbActivationAnalysisTblDay(Map<String, Integer> map, String recordTime);
	public void batchInsertStbLoginAnalysisTblDay(Map<String, Integer> map, String recordTime);
	public void batchInsertStbLoginAnalysisTblMonth(Map<String, Integer> map, String recordTime);
	public void batchInsertStbActivationAnalysisTblMonth(Map<String, Integer> map, String recordTime);

	public void insertStbActivationAnalysisTblDayTotal(int num, String recordTime);	
	public void insertStbLoginAnalysisTblDayTotal(int num, String recordTime);

	public boolean updateOperateHistory(int cmdNo, String time,
			String result);
	
	public void batchUpdateStbLoginAnalysisTblMonth(Map<String, Integer> map, String recordTime);
	public void batchUpdateStbActivationAnalysisTblMonth(Map<String, Integer> map, String recordTime);
	
	
	public void insertOrUpdateStbActivationAnalysisTblMonthTotal(int num, String recordTime);
	public void insertOrUpdateStbLoginAnalysisTblMonthTotal(int num, String recordTime);

	//批量保存终端历史数据
	public Boolean batchSaveTmlHisoty(List<String> tmlIds,String nfTime, String Type);
	
}
