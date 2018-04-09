package communicate.pkmgmt;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import communicate.common.DBCommon;
import communicate.pkmgmt.dto.OlsDTO;
import communicate.pkmgmt.dto.Order;
import communicate.pkmgmt.dto.StatusDTO;
import communicate.pkmgmt.ols.SendOrder;
import communicate.pkmgmt.tmljdbc.TmlConst;


public class OrderMapMemory {
	private static Logger log = communicate.common.Logger.getLogger(OrderMapMemory.class);

	private static OrderMapMemory instance = new OrderMapMemory();

	// 维持每个盒子在线时的OLS记录
	private Map<String, OlsDTO> onlines;

	// pc
	private Map<String, List<Order>> orders;

	// ad
	private String[] adRecord;
	private List<String> macs;

	// UAD
	private String u_adRecord;
	private List<String> u_macs;

	// 维持盒子影片下载已成功的订单信息
	private List<StatusDTO> downOkOrders;

	// 维持盒子影片下载未成功的详细订购单信息
	private Map<String, Order> unDownOkOrders;

	// 维持影片下载次数
	private Map<String, Integer> videoDownTimesMap;
	private Map<String, Integer> musicDownTimesMap;

	public static synchronized OrderMapMemory getInstance() {
		return instance;
	}

	public OrderMapMemory() {
	} 

	/**
	 * 初始化四个map
	 */
	public void init() {
		onlines = new HashMap<String, OlsDTO>();
		orders = new HashMap<String, List<Order>>();
		downOkOrders = new ArrayList<StatusDTO>();
		unDownOkOrders = new HashMap<String, Order>();
		videoDownTimesMap = new HashMap<String, Integer>();
		musicDownTimesMap = new HashMap<String, Integer>();
		macs = new ArrayList<String>();
		u_macs = new ArrayList<String>();

		DBCommon db = null;
		ResultSet rs = null;
		// AD
		try {
			String nowDate = PackageConstant.sdfYMD.format(new Date());
			String sql = "SELECT * FROM TML_AD WHERE outDate>='"+nowDate+"' ORDER BY inTime DESC";
			db = new DBCommon(sql);
			rs = db.executeQuery();
			if (rs.next()) {
				adRecord = new String[5];
				adRecord[0] = rs.getString("adId");
				adRecord[1] =  String.valueOf(rs.getInt("adType"));
				adRecord[2] =  rs.getString("listName");
				adRecord[3] =  rs.getString("CXMLName");
				adRecord[4] = rs.getString("outDate");
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			rs = null;
		}

		// UAD
		try {
			String sql = "SELECT * FROM TML_UAD ORDER BY inTime DESC";
			db = new DBCommon(sql);
			rs = db.executeQuery();
			if (rs.next()) {
				u_adRecord = rs.getString("listName");
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			rs = null;
		}

		List<String> tmlIdList  = new ArrayList<String>();
		List<Order> list = null;
		String queryTmlIdSQL = "SELECT * FROM TML_BASE";
		try {
			db = new DBCommon(queryTmlIdSQL);
			rs = db.executeQuery();
			while (rs.next()) {
				String tmlId = rs.getString("tmlId");
				tmlIdList.add(tmlId);

				if (adRecord != null) {
					String tmlAadId = rs.getString("adId");
					if (tmlAadId == null || (!tmlAadId.equals(adRecord[0]))) {
						macs.add(tmlId);
					}
				}

				if (u_adRecord != null && u_adRecord.trim().length() > 0) {
					String uadId = rs.getString("uadId");
					if (uadId == null || (!uadId.trim().equals(u_adRecord))) {
						u_macs.add(tmlId);
					}
				}
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			rs = null;
		}

		int size = tmlIdList.size();

		if (size != 0) {

			String queryOrder = 
				"SELECT * FROM TML_ORDER WHERE tmlId=? AND portalType=? AND status=?";
			for (int i = 0; i < size; i++) {
				list = new ArrayList<Order>();
				String tmlId = tmlIdList.get(i);

				if (tmlId != null && tmlId.trim().length() > 0) {
					try {
						db = new DBCommon(queryOrder);
						db.setString(1, tmlId);
						db.setInt(2, TmlConst.portalType.pcPortal);
						db.setInt(3, TmlConst.orderStatus.un_send);
						rs = db.executeQuery();

						Order order = null;
						while (rs.next()) {
							order = new Order();
							order.setOrderId(rs.getString("orderId"));
							order.setServiceType(rs.getInt("serviceType"));
							order.setParentName(rs.getString("parentName"));
							order.setMovieName(rs.getString("movieName"));
							list.add(order);
						}
					} catch (Exception e) {
						log.error(this,e);
					} finally {
						db.close();
						rs = null;
					}
					orders.put(tmlId, list);
				}
			}
		}

		//log.info("init() orders="+orders);
	}

	public OlsDTO getOls(String tmlId) {
		if (tmlId == null || tmlId.trim().equals("")) {
			log.warn("getOls() tmlId="+tmlId);
			return null;
		}

		OlsDTO dto = null;
		if (onlines != null && onlines.containsKey(tmlId)) {
			dto = onlines.get(tmlId);
		}

		return dto;
	}

	public List<Order> addOnlineMac(String tmlId, String ip, int port) {
		List<Order> ret = new ArrayList<Order>();
		if (tmlId == null 
				|| tmlId.trim().equals("")
				|| ip == null 
				|| ip.trim().equals("") 
				|| port == 0) {
			return ret;
		}

		OlsDTO dto = new OlsDTO();
		dto.setIp(ip);
		dto.setPort(port);
		onlines.put(tmlId, dto);

		List<Order> list = null;
		if (orders.containsKey(tmlId)) {
			list = orders.get(tmlId);
		}

		if (list != null && list.size()!=0) {
			for (int i = 0; i < list.size(); i++) {
				ret.add(list.get(i));
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public void removeOutlineMac(String tmlId) {
		//log.debug("removeOutlineMac() tmlId="+tmlId);

		for (Iterator ir = onlines.entrySet().iterator();ir.hasNext();) {

			Map.Entry map1 = (java.util.Map.Entry) ir.next();

			if (map1.getKey().equals(tmlId)) {
				ir.remove();
			}
		}

		//log.debug("addOnlineMac() onlines="+onlines);
	}

	/**
	 * 针对pcPortal，tml相关的订购单
	 * @param tmlId
	 * @param list
	 */
	public void addOrder(String tmlId, List<Order> list) {
		if (list == null || list.size() == 0) return;

		List<Order> list0 = new ArrayList<Order>();

		if (orders.containsKey(tmlId)) {
			list0 = orders.get(tmlId);
		}

		if (list0.size() != 0) {
			for (Order o :list0) {
				list.add(o);
			}
		}
		orders.put(tmlId, list);
		//log.debug("addOrder() orders="+orders);
	}

	/**
	 * OLS收到返回成功之后，删除记录
	 * @param tmlId
	 * @param orderIds
	 */
	public void removeOrder(String tmlId, List<String> orderIds) {
		List<Order> list = new ArrayList<Order>();

		if (orders != null && orders.containsKey(tmlId)) {
			list = orders.get(tmlId);
		}

		if (list.size() != 0) {
			for (String id : orderIds) {
				for (int i = 0; i < list.size(); i++) {
					Order o = list.get(i);
					if (o.getOrderId().equals(id)) {
						list.remove(i);
					}
				}
			}
		}
		orders.put(tmlId, list);
		//log.debug("removeOrder() orders="+orders);
	}

	/**
	 * 生成详细的订购单信息，影片还未下载成功
	 * @param mac
	 * @param order
	 */
	public void insUnDownOkOrder(String orderId, Order order) {
		unDownOkOrders.put(orderId, order);
		//log.debug("[OrderMapMemory]:insUnDownOkOrder: unDownOkOrders="+unDownOkOrders);
	}

	/**
	 * 返回SPE请求的订购单信息
	 * @param orderId
	 * @return
	 */
	public Order getReqOrder(String orderId) {
		Order ret = null;
		if (orderId == null || orderId.trim().equals("")) {
			log.warn("[OrderMapMemory]:getReqOrder: orderId is null");
			return ret;
		}

		if (unDownOkOrders.containsKey(orderId)) {
			ret = unDownOkOrders.get(orderId);
		}

		return ret;
	}

	/**
	 * 更新订购单影片下载状态
	 * @param orderId
	 */
	public void updOrderStatus(String orderId, int status) {
		log.info("[OrderMapMemory]:updOrderStatus: orderId="+orderId+", status="+status);
		if (orderId == null || orderId.trim().equals("")) {
			log.warn("[OrderMapMemory]:updOrderStatus: orderId is null");
			return;
		}

		if (unDownOkOrders.containsKey(orderId)) {
			Order order = unDownOkOrders.get(orderId);
			order.setStatus(status);

			// 包括影片下载未成功删除状态
			// 订购单下载成功也加入；2010-8-24
			if (status == TmlConst.orderStatus.download_success
					|| status == TmlConst.orderStatus.hav_send) {
				// 影片已下载成功的订购单信息
				StatusDTO s = new StatusDTO();
				s.setId(orderId);
				s.setStatus(status);
				downOkOrders.add(s);
				unDownOkOrders.remove(orderId);
			} else {
				unDownOkOrders.put(orderId, order);
			}
		}
		log.info("[OrderMapMemory]:updOrderStatus: downOkOrders="+downOkOrders);
	}

	public List<StatusDTO> getDownOkOrders() {
		return downOkOrders;
	}

	public void remUpdOkOrders(List<StatusDTO> updOk) {
		if (updOk == null || updOk.size() == 0) return;
		for (int i = 0; i < updOk.size(); i++) {
			String okId = updOk.get(i).getId();

			for (Iterator ir = downOkOrders.iterator();ir.hasNext();) {
				StatusDTO ok = (StatusDTO) ir.next();
				if (ok.getId().equals(okId)) {
					ir.remove();
				}
			}
		}
	}

	public void setDownOkOrders(List<StatusDTO> downOkOrders) {
		this.downOkOrders = downOkOrders;
	}

	public void insVideoDownTimes(String contentId) {
		if (contentId == null || contentId.trim().equals("")) return;

		if (!videoDownTimesMap.containsKey(contentId)) {
			videoDownTimesMap.put(contentId, 1);
		} else {
			videoDownTimesMap.put(contentId, (videoDownTimesMap.get(contentId)+1));
		}
		//log.info("======insDownTimes=====downTimesMap="+downTimesMap);
	}

	public void insMusicDownTimes(String contentId) {
		if (contentId == null || contentId.trim().equals("")) return;

		if (!musicDownTimesMap.containsKey(contentId)) {
			musicDownTimesMap.put(contentId, 1);
		} else {
			musicDownTimesMap.put(contentId, (musicDownTimesMap.get(contentId)+1));
		}
		//log.info("======insDownTimes=====downTimesMap="+downTimesMap);
	}

	public List<StatusDTO> getVideoDownTimes() {
		List<StatusDTO> retList = new ArrayList<StatusDTO>();

		int i = videoDownTimesMap.size();
		Set<String> map = videoDownTimesMap.keySet();
		Iterator<String> keys = map.iterator();

		for(int j=0; j < i; j++){
			String pId = keys.next();

			StatusDTO dto = new StatusDTO();
			dto.setId(pId);
			dto.setStatus(videoDownTimesMap.get(pId));
			dto.setStatus2(MediaConst.ProgramType.FILM);

			retList.add(dto);
		}
		return retList;
	}

	public List<StatusDTO> getMusicDownTimes() {
		List<StatusDTO> retList = new ArrayList<StatusDTO>();

		int i = musicDownTimesMap.size();
		Set<String> map = musicDownTimesMap.keySet();
		Iterator<String> keys = map.iterator();

		for(int j=0; j < i; j++){
			String pId = keys.next();

			StatusDTO dto = new StatusDTO();
			dto.setId(pId);
			dto.setStatus(musicDownTimesMap.get(pId));
			dto.setStatus2(MediaConst.ProgramType.ALBUM);

			retList.add(dto);
		}
		return retList;
	}

	public void remVideoDownTimes(List<StatusDTO> updOk) {
		log.info("======remDownTimes=====updOk="+updOk);
		if (updOk == null || updOk.size() == 0) return;
		for (int i = 0; i < updOk.size(); i++) {
			String okId = updOk.get(i).getId();

			for (Iterator ir = videoDownTimesMap.entrySet().iterator();ir.hasNext();) {
				Map.Entry map1 = (java.util.Map.Entry) ir.next();

				if (map1.getKey().equals(okId)) {
					ir.remove();
				}
			}
		}

		log.info("======remDownTimes=====downTimesMap="+videoDownTimesMap);
	}

	public void remMusicDownTimes(List<StatusDTO> updOk) {
		log.info("======remDownTimes=====updOk="+updOk);
		if (updOk == null || updOk.size() == 0) return;
		for (int i = 0; i < updOk.size(); i++) {
			String okId = updOk.get(i).getId();

			for (Iterator ir = musicDownTimesMap.entrySet().iterator();ir.hasNext();) {
				Map.Entry map1 = (java.util.Map.Entry) ir.next();

				if (map1.getKey().equals(okId)) {
					ir.remove();
				}
			}
		}

		log.info("======remDownTimes=====downTimesMap="+musicDownTimesMap);
	}

	public void setAdRecord(String adId, int Type, String listName, String CXMLName, String outDate) {
		if (adRecord == null) {
			adRecord = new String[5];
		}
		adRecord[0] = adId;
		adRecord[1] = String.valueOf(Type);
		adRecord[2] = listName;
		adRecord[3] = CXMLName;
		adRecord[4] = outDate;
		log.info("adRecord:"+adRecord);
	}

	public void sendAd() {
		log.info(">>>>>>>>>>>>>>>>>> onlines.len:"+onlines.size());
		for (Iterator ir = onlines.entrySet().iterator();ir.hasNext();) {
			Map.Entry map1 = (java.util.Map.Entry) ir.next();
			String tmlId = (String) map1.getKey();
			log.info(">>>>>>>>>>>>>>>>>> onlines.tmlId:"+tmlId+" | macs:"+macs);
			if (macs != null && macs.contains(tmlId)) {
				OlsDTO dto = (OlsDTO) map1.getValue();
				if (dto != null) {
					String ip = dto.getIp();
					int port = dto.getPort();
					if (ip != null && ip.trim().length() > 0 && port != 0) {
						SendOrder send = new SendOrder();
						send.createSendOrderBuf(tmlId, null, ip, port);
					}

					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				macs.remove(tmlId);
			}
		}
	}

	public String[] getAdRecord() {
		return this.adRecord;
	}

	public void setAdRecord(){
		this.adRecord = null;
	}

	public void initAdMac(List<String> tmlIds) {
		if (macs == null) macs = new ArrayList<String>();
		macs.clear();
		macs.addAll(tmlIds);
	}

	@SuppressWarnings("unchecked")
	public void addAdMac(String tmlId) {
		if (tmlId == null) return;
		if (macs == null) macs = new ArrayList<String>();
		HashSet h = new HashSet(macs);
		h.add(tmlId);
		macs.clear();
		macs.addAll(h);

		if (u_macs == null) u_macs = new ArrayList<String>();
		HashSet hu = new HashSet(u_macs);
		h.add(tmlId);
		u_macs.clear();
		u_macs.addAll(hu);
	}

	public boolean checkAdMac(String tmlId) {
		if (tmlId == null || macs == null) return false;
		boolean ret = true;

		if (!macs.contains(tmlId)) ret = false;

		return ret;
	}

	public void removeAdMac(String tmlId) {
		if (tmlId == null && macs == null) return; 

		for (Iterator ir = macs.iterator();ir.hasNext();) {
			String mac = (String) ir.next();
			if (mac.equals(tmlId)) {
				ir.remove();
			}
		}
	}

	public void cleanAdMac() {
		if (macs != null) {
			macs.clear();
		}
	}

	// UAD
	public void sendUAd() {
		log.info(">>>>>>>>>>>>>>>>>> onlines.len:"+onlines.size());
		for (Iterator ir = onlines.entrySet().iterator();ir.hasNext();) {
			Map.Entry map1 = (java.util.Map.Entry) ir.next();
			String tmlId = (String) map1.getKey();
			if (u_macs != null && u_macs.contains(tmlId)) {
				OlsDTO dto = (OlsDTO) map1.getValue();
				if (dto != null) {
					String ip = dto.getIp();
					int port = dto.getPort();
					if (ip != null && ip.trim().length() > 0 && port != 0) {
						SendOrder send = new SendOrder();
						send.createSendOrderBuf(tmlId, null, ip, port);
					}

					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				u_macs.remove(tmlId);
			}
		}
	}

	public void initUadMac(List<String> tmlIds) {
		if (u_macs == null) u_macs = new ArrayList<String>();
		u_macs.clear();
		u_macs.addAll(tmlIds);
	}

	public String getU_adRecord() {
		return u_adRecord;
	}

	public void setU_adRecord(String record) {
		u_adRecord = record;
	}

	public boolean checkUAdMac(String tmlId) {
		if (tmlId == null || u_macs == null) return false;
		boolean ret = true;

		if (!u_macs.contains(tmlId)) ret = false;

		return ret;
	}

	public void removeUAdMac(String tmlId) {
		if (tmlId == null && u_macs == null) return; 

		for (Iterator ir = u_macs.iterator();ir.hasNext();) {
			String mac = (String) ir.next();
			if (mac.equals(tmlId)) {
				ir.remove();
			}
		}
	}

	public void cleanUAdMac() {
		if (u_macs != null) {
			u_macs.clear();
		}
	}

	public static void main(String[] aa) {
		OrderMapMemory o1 = OrderMapMemory.getInstance();
		o1.init();
	}

	public void test() {
		String id = "tt";
		log.debug(orders.get(id));
	}
}
