package communicate.pkmgmt;

import java.util.List;

import communicate.pkmgmt.dto.List_Server;
import communicate.pkmgmt.dto.Order;
import communicate.pkmgmt.dto.StatusDTO;

/**
 * 构造数据包
 * @author jinj
 */
public interface ITmlService {
	// LR: License-Request
	// LC: License

	// DRM
	// 向DRM请求许可证文件
	public byte[] createLRBufToDRM(String tmlId, String contendId, int count, String startTime, String endTime);

	// 回复验证码，Result-Code=0，成功；其他，失败。
	public byte[] createResultCodeBuf(int code, int resultCode);

	// 回复STBID，验证码，Result-Code=0，成功；其他，失败。
	public byte[] createResultCodeBuf(int code, String tmlId, int resultCode);

	// SPE
	// 回复SPE许可证文件，Result-Code=0，请求成功
	public byte[] createLCBufToSPE(int resultCode, byte[] license);

	// 回复SPE验证终端合法性请求，Result-Code=0，合法终端；Result-Code=其它，非法终端
	public byte[] creatCertifyACKToSPE(int resultCode);

	// 回复SPE订购单
	public byte[] createOrderBufToSPE(String orderId, int code);

	/**
	 * 在downloadcmp数据库中取得文件名，文件大小，影片类型，影片名称，海报路径，资源列表名和播控文件名
	 * @param cntId
	 * @param order
	 * @return OrderEntity order
	 */
	//public Order getDetail(int type, String cntId, Order order);

	/**
	 * 在downloadcmp数据库中取得文件名，文件大小，影片类型，
	 * 影片名称，海报路径，资源列表名，播控文件名，电视剧总集数，节目上映时间
	 * 压缩文件名，节目类别
	 * @param list
	 * @return List<Order> list
	 */
	public List<Order> getDetails(List<Order> list);

	/**
	 * 找出节目的节目类型
	 * @param contentId
	 * @return List list
	 */
	public List<String> getMovieCategory(String contentId);

	/**
	 * 保存订购单
	 * @param list
	 * @return flg
	 */
	public boolean insertOrder(List<Order> list);

	/**
	 * 更新订购单
	 * @param list
	 * @return flg
	 */
	public boolean updateOrder(List<StatusDTO> list);

	/**
	 * 得到SPE/OLS及STBPortal URL列表
	 * @return list
	 */
	public List<List_Server> getList(int netType, boolean conSpe);

	/**
	 * 得到单个服务器的URL
	 * @return List_Server
	 */
	public List_Server getServerURL(int tag, int netType);

	/**
	 * 验证终端合法性
	 * @param tmlId
	 * @param userName
	 * @param encryptedPassword
	 * @return flg
	 */
	public boolean certifySTB(String tmlId, String userName, String encryptedPassword);

	/**
	 * 更新节目的用户下载次数
	 * @param list
	 * @return flg
	 */
	public boolean updateDownTimes(List<StatusDTO> list);

	/**
	 * 根据节目名找到contentName和programName
	 * @param contentFile
	 * @return
	 */
	public String getContentName(String contentFile);

	/**
	 * 检查某个STB某个影片十分钟内的下载情况
	 * @param tmlId
	 * @param contentId
	 * @param time 十分钟前的时间
	 * @return flg
	 */
	public boolean checkLastOrder(String tmlId, String contentId,String time);

	/**
	 * 获取该节目下的所有上线的内容
	 * @param programId
	 * @param programType
	 * @return List<Order> order
	 */
	public List<Order> getOnlineContents(String programId, int programType);

	/**
	 * 获取该节目下的所有上线的内容ID,maxPlayTimes
	 * @param programId
	 * @param programType
	 * @return List<String> contengs
	 */
	public List<Order> getContentsByPid(String programId, int programType);

	/**
	 * 获取该内容下的maxPlayTimes
	 * @param programId
	 * @param programType
	 * @return Order order
	 */
	public Order getContentsByCid(String contentId, int programType);

	/**
	 * 根据名称获取信息
	 * @param name
	 * @return String configureName
	 */
	public String getConfigureValue(String name);
}
