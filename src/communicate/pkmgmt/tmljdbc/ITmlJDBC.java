package communicate.pkmgmt.tmljdbc;

import java.util.List;

import communicate.common.exception.DataExistException;
import communicate.common.exception.SqlException;
import communicate.pkmgmt.dto.SelectTml;
import communicate.pkmgmt.dto.ServerList;
import communicate.pkmgmt.dto.TmlDoInfo;

public interface ITmlJDBC {

	/**
	 * 注册单个终端
	 * @param customerId
	 * @param password
	 * @param customerType
	 * @param mac
	 * @param tmlType
	 * @param ip
	 * @param inTime
	 * @param inOperator
	 * @param dec
	 * @throws DataExistException
	 * @throws SqlException
	 */
	public int registerTml(String customerId, 
			String password, 
			String customerType,
			String mac, 
			int tmlType,
			String ip,
			String inTime, 
			String inOperator, 
			String dec);


	/**
	 * 更新终端的状态
	 * @param tmlId
	 * @param status
	 * @throws SqlException
	 */
	public void updTml(String tmlId, int status) throws SqlException;

	/**
	 * 更新终端下载AD状态
	 * @param tmlId
	 * @param adId
	 * @throws SqlException
	 */
	public void updTml(String tmlId, String adId, int adType) throws SqlException;


	/**
	 * 查找所有的终端
	 * @return
	 * @throws SqlException
	 */
	public List<SelectTml> getTmls()  throws SqlException;

	/**
	 * 找到符合查询条件的OLS/SPE服务器
	 * @param tag
	 * @param inTime
	 * @return List<OlsSpeList> list
	 * @throws SqlException
	 */
	public List<ServerList> getServers(int tag, String inTime) throws SqlException;

	public String getSpeIpPort(String mac);

	/**
	 * 测试盒子绑定的升级服务器地址
	 * @param mac
	 * @return
	 */
	public String getUpgradeURL(String mac);

	public int addADRecord(String adId, int type, String listName, String CXMLName, String outDate);

	public int addUAD(String uadId, String listName);

	public void addOrUpdateTmlSysInfo(String tmlId, String tmlVersion, String leftDiskSize, String ableMem,
			String portalVersion, String portalUrl, String tmlPlayling, String tmlDownling, String tmlApps,
			String tmlSpelist);


	/**
	 * 记录STB正在下载播放信息
	 * @param list
	 * @throws SqlException
	 */
	public void updateTmlDoCnt(String tmlId, List<TmlDoInfo> list) throws SqlException;
	
	/**
	 * 获得一段时间内的订购单数
	 * @param startTime
	 * @param endTime
	 * @return orders num
	 * @throws SqlException
	 */
	public int getOrderNum(String startTime, String endTime) throws SqlException;
	
	/**
	 * 获得当前的终端在线数
	 * @param startTime
	 * @param endTime
	 * @return onlines num
	 * @throws SqlException
	 */
	public int getTmsOnlineNum() throws SqlException;
	
	public String getConfigValue(String name);
	
	/**
	 * 
	 * @param customerId 用户名
	 * @param password 密码
	 * @param tmlId MAC大写
	 * @param telNo 手机号码
	 * @return
	 */
	public int bindTelNo(String customerId, 
			String password, 
			String tmlId,
			String telNo);
}
