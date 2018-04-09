package communicate.pkmgmt.tmljdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.DBCommon;
import communicate.common.TimeMgmt;
import communicate.common.exception.DataExistException;
import communicate.common.exception.SqlException;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.dto.SelectTml;
import communicate.pkmgmt.dto.ServerList;
import communicate.pkmgmt.dto.TmlDoInfo;
import communicate.pkmgmt.dto.UpgradeTest;

public class TmlJDBCImpl implements ITmlJDBC {

	private static final Logger log = communicate.common.Logger.getLogger(TmlJDBCImpl.class);
	private DBCommon db = null;

	public String getUserName(String tmlId) {
		if (tmlId == null || tmlId.trim().equals("")) return null;
		String sql = "SELECT customerId FROM TML_BASE WHERE tmlId=?";

		ResultSet rs = null;
		String userName = "";
		try {
			db = new DBCommon(sql);
			db.setString(1, tmlId.toUpperCase());
			rs = db.executeQuery();
			if (rs.next()) {
				userName = rs.getString(1);
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return userName;
	}

	/*public int checkTml(String customerId, String password, String mac) {
		String ckcSql = "SELECT COUNT(*) FROM TML_BASE WHERE tmlId=? AND customerId=? AND customerPwd=?";
		ResultSet rsCount = null;
		int count = 0;
		int ret = PackageConstant.result_code.certify_error;

		try {
			db = new DBCommon(ckcSql);
			db.setString(1, mac.toUpperCase());
			db.setString(2, customerId);
			db.setString(3, password);
			rsCount = db.executeQuery();
			if (rsCount.next()) {
				count = rsCount.getInt(1);
			}
			if (count != 0) {
				ret = PackageConstant.result_code.stb_exist;
			}
		} catch (SQLException e) {
			ret = PackageConstant.result_code.db_error;
			 
			log.error(this, e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}*/

	public int registerTml(String customerId, String password, String customerType,
			String mac, int tmlType, String ip, String inTime, String inOperator, String dec) {
		int ret = PackageConstant.result_code.ok;

		try {
			int count = 0;
			String sql0 = "SELECT COUNT(*) FROM TML_BASE WHERE customerId=? AND customerPwd=? AND tmlId=?";
			db = new DBCommon(sql0);
			db.setString(1, customerId);
			db.setString(2, password);
			db.setString(3, mac.toUpperCase());
			ResultSet rs = db.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs = null;

			if (count == 0) {
				String sql = 
					"INSERT INTO TML_BASE " +
					"(customerId, customerPwd, tmlType, tmlId, inTime, inoperator, description, customerType, ipAddr)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
				db = new DBCommon(sql);
				db.setString(1, customerId);
				db.setString(2, password);
				db.setInt(3, tmlType);
				db.setString(4, mac.toUpperCase());
				db.setString(5, inTime);
				db.setString(6, inOperator);
				db.setString(7, dec);
				db.setString(8, customerType);
				db.setString(9, ip);
				db.executeUpdate();
				db.commit();
			}

		} catch (SQLException e) {
			ret = PackageConstant.result_code.db_error;
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}

	public int addADRecord(String adId, int type, String listName, String CXMLName, String outDate) {
		int ret = PackageConstant.result_code.ok;

		try {
			String sql = 
				"INSERT INTO TML_AD(adId, adType, listName, CXMLName, inTime,outDate) VALUES (?, ?, ?, ?, ?,?)";
			db = new DBCommon(sql);
			db.setString(1, adId);
			db.setInt(2, type);
			db.setString(3, listName);
			db.setString(4, CXMLName);
			db.setString(5, PackageConstant.sdf.format(new Date()));
			db.setString(6, outDate);
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			ret = PackageConstant.result_code.error;
			 
			log.error(this, e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}

	public int addUAD(String uadId, String listName) {
		int ret = PackageConstant.result_code.ok;

		try {
			String sql = 
				"INSERT INTO TML_UAD(uadId, listName, inTime) VALUES (?, ?, ?)";
			db = new DBCommon(sql);
			db.setString(1, uadId);
			db.setString(2, listName);
			db.setString(3, PackageConstant.sdf.format(new Date()));
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			ret = PackageConstant.result_code.error;
			 
			log.error(this, e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}

	public List<SelectTml> getTmls() throws SqlException {
		String sql = "SELECT tmlId,customerType FROM TML_BASE WHERE isSpe="+TmlConst.isSpe_false;

		List<SelectTml> ret = new ArrayList<SelectTml>();
		SelectTml tml = null;
		ResultSet rs = null;

		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();

			while (rs.next()) {
				tml = new SelectTml();
				tml.setTmlId(rs.getString("tmlId"));
				tml.setCustomerType(rs.getString("customerType"));
				ret.add(tml);
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
			throw new SqlException(e.getMessage());
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}


	public List<SelectTml> getTmlSpe(String mac, String speipPort) {
		List<SelectTml> ret = new ArrayList<SelectTml>();

		String sql = "SELECT * FROM TML_SPE WHERE 1=1 ";

		if (mac != null && !mac.trim().equals("")) {
			sql += " AND tmlId='" + mac.toUpperCase() + "'";
		}

		if (speipPort != null && speipPort.trim().length() > 0) {
			sql += " AND speIp='" + speipPort+"'";
		}

		SelectTml dto = null;
		ResultSet rs = null;

		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();

			while (rs.next()) {
				dto = new SelectTml();
				dto.setTmlId(rs.getString("tmlId"));
				dto.setSpeIpPort(rs.getString("speIp"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}

	public List<SelectTml> getTmlSpe(String speipPort) {
		List<SelectTml> ret = new ArrayList<SelectTml>();

		String sql = "SELECT * FROM TML_SPE WHERE 1=1 ";

		if (speipPort != null && speipPort.trim().length() > 0) {
			sql += " AND speIp='" + speipPort+"'";
		}

		SelectTml dto = null;
		ResultSet rs = null;

		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();

			while (rs.next()) {
				dto = new SelectTml();
				dto.setTmlId(rs.getString("tmlId"));
				dto.setSpeIpPort(rs.getString("speIp"));
				ret.add(dto);
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}



	public List<ServerList> getServers(int tag, String inTime) throws SqlException {
		List<ServerList> ret = new ArrayList<ServerList>();

		// 构造sql语句
		String sql = "SELECT * FROM TML_SERVER_LIST WHERE 1=1";

		if (tag != TmlConst.serverType.all) {
			sql += " AND tag=" + tag;
		}

		if (inTime != null && !inTime.trim().equals("")) {
			sql += " AND inTime>='" + inTime + "'";
		}

		ResultSet rs = null;
		ServerList dto = null;

		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();

			while (rs.next()) {
				dto = new ServerList();
				dto.setId(rs.getInt("id"));
				dto.setInTime(rs.getString("inTime"));
				dto.setInOperator(rs.getString("inOperator"));
				dto.setDescription(rs.getString("description"));
				//dto.setGroupId(rs.getInt("groupId"));
				//dto.setUnitId(rs.getInt("unitId"));

				int tag0 = rs.getInt("tag");
				dto.setTag(tag0);
				dto.setServerName(TmlConst.serverType.getStrServerType(tag0));

				dto.setStrNetType(TmlConst.netType.getStrNetType(rs.getInt("netType")));

				String ip = rs.getString("ip");
				int port = rs.getInt("port");
				dto.setServerIp(ip);
				dto.setServerPort(port);
				dto.setIpPort(ip+":"+port);

				ret.add(dto);
			}

			/*String sql0 = "SELECT DONAME,PARENTNAME FROM SYS_DOM WHERE DOMID=?";
			rs = null;
			for (ServerList ser : ret) {
				if (ser.getTag() == TmlConst.serverType.spe) {
					db = new DBCommon(sql0);
					int uId = ser.getUnitId();
					if (uId != 0) {
						db.setInt(1, uId);
					} else {
						db.setInt(1, ser.getGroupId());
					}
					rs = db.executeQuery();

					while (rs.next()) {
						ser.setUnitName(rs.getString(1));
						ser.setGroupName(rs.getString(2));
					}
				}
			}*/

		} catch (SQLException e) {
			 
			log.error(this,e);
			throw new SqlException(e.getMessage());
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}

	public String getUpgradeURL(String mac) {
		String url = "";
		ResultSet rs = null;

		String sql = "SELECT upgradeIp FROM TML_UPGRADE_TEST WHERE tmlId=?";
		try {
			db = new DBCommon(sql);

			db.setString(1, mac);
			rs = db.executeQuery();

			while (rs.next()) {
				url = rs.getString(1);
			}

		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return url;
	}

	public List<UpgradeTest> getUpgrades(String tmlId){
		List<UpgradeTest> list = new ArrayList<UpgradeTest>();
		UpgradeTest test = null;

		if (tmlId != null && tmlId.trim().length() > 0) {
			test = new UpgradeTest();
			String ip = getUpgradeURL(tmlId);
			test.setIp(ip);
			test.setTmlId(tmlId);
			list.add(test);
		} else {
			String sql = "SELECT * FROM TML_UPGRADE_TEST";
			ResultSet rs = null;
			try {
				db = new DBCommon(sql);
				rs = db.executeQuery();

				while (rs.next()) {
					test = new UpgradeTest();
					test.setIp(rs.getString("upgradeIp"));
					test.setTmlId(rs.getString("tmlId"));
					list.add(test);
				}
			} catch (SQLException e) {
				 
				log.error(this,e);
			} finally {
				db.close();
				db = null;
			}
		}

		return list;
	}

	public void updateTmlSpe(String tmlId, String speIp) throws SqlException {
		String sql = "UPDATE TML_SPE SET speIp=? WHERE tmlId=?";
		try {
			db = new DBCommon(sql);

			db.setString(1, speIp);
			db.setString(2, tmlId.toUpperCase());
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			 
			log.error(this,e);
			throw new SqlException(e.getMessage());
		} finally {
			db.close();
			db = null;
		}
	}

	public void updTml(String tmlId, int status) throws SqlException {
		String sql = "";
		if (status == TmlConst.tmlStatus.online) {
			sql = "UPDATE TML_BASE SET tmlStatus=?,onTime=? WHERE tmlId=?";
		} else if (status == TmlConst.tmlStatus.outline) {
			sql = "UPDATE TML_BASE SET tmlStatus=? WHERE tmlId=?";
		}
		try {
			db = new DBCommon(sql);

			db.setInt(1, status);
			if (status == TmlConst.tmlStatus.online) {
				TimeMgmt t = new TimeMgmt();
				String onTime = t.getCurDate(5);
				db.setString(2, onTime);
				db.setString(3, tmlId.toUpperCase());
			} else if (status == TmlConst.tmlStatus.outline) {
				db.setString(2, tmlId.toUpperCase());
			}

			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			 
			log.error(this,e);
			throw new SqlException(e.getMessage());
		} finally {
			db.close();
			db = null;
		}
	}

	public void updTml(String tmlId, String adId, int adType) throws SqlException {
		String sql = "";
		if (adType == TmlConst.adType.ad) {
			sql = "UPDATE TML_BASE SET adId=? WHERE tmlId=?";
		} else if (adType == TmlConst.adType.uad){
			sql = "UPDATE TML_BASE SET uadId=? WHERE tmlId=?";
		}

		try {
			db = new DBCommon(sql);
			db.setString(1, adId);
			db.setString(2, tmlId.toUpperCase());
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new SqlException(e.getMessage());
		} finally {
			db.close();
			db = null;
		}
	}

	public boolean updateTmls(List<SelectTml> list, int status) {
		boolean ret = true;
		if (list == null || list.size() == 0) return ret;
		DBCommon db = new DBCommon();
		try {
			for (SelectTml dto : list) {
				if (dto != null) {
					String tmlId = dto.getTmlId();

					String sql = 
						"UPDATE TML_BASE SET isSpe="+status+" WHERE tmlId='"+tmlId+"'";
					db.addBatch(sql);
				}
			}

			db.executeBatch();
			db.commit();
		} catch (Exception e) {
			ret = false;
			 
			log.error(this, e);
		} finally {
			db.close();
		}

		return ret;
	}

	public int updTml(String tmlId, String customerId, String password, String oldUser, String oldPwd) {
		int ret = PackageConstant.result_code.ok;
		String sql = "UPDATE TML_BASE SET " +
		"customerId=?,customerPwd=?,oldCustomerId=?,oldCustomerPwd=? WHERE tmlId=?";
		try {
			db = new DBCommon(sql);

			db.setString(1, customerId);
			db.setString(2, password);
			db.setString(3, oldUser);
			db.setString(4, oldPwd);
			db.setString(5, tmlId.toUpperCase());
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			ret = PackageConstant.result_code.db_error;
			log.error(e.getMessage());
		} finally {
			db.close();
			db = null;
		}
		return ret;
	}

	public void addOrUpdateTmlSysInfo(String tmlId, String tmlVersion, String leftDiskSize, String ableMem,
			String portalVersion, String portalUrl, String tmlPlayling, String tmlDownling, String tmlApps,
			String tmlSpelist) {
		String ckcSql = "SELECT COUNT(*) FROM TML_SYS_INFO WHERE tmlId=?";

		ResultSet rsCount = null;
		int count = 0;
		String sql = "";

		String date = PackageConstant.sdf.format(new Date());

		try {
			db = new DBCommon(ckcSql);
			db.setString(1, tmlId);
			rsCount = db.executeQuery();
			if (rsCount.next()) {
				count = rsCount.getInt(1);
			}

			if (count != 0) {
				sql = "UPDATE TML_SYS_INFO SET tmlVersion=?,leftDiskSize=?,ableMem=?,portalVersion=?," +
				"portalUrl=?,tmlPlayling=?,tmlDownling=?,tmlApps=?,tmlSpelist=?,updateDate=? WHERE " +
				"tmlId=?";
			}  else {
				sql = 
					"INSERT INTO " +
					"TML_SYS_INFO (tmlVersion,leftDiskSize,ableMem,portalVersion," +
					"portalUrl,tmlPlayling,tmlDownling,tmlApps,tmlSpelist,updateDate,tmlId)" +
					" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			}
			db = new DBCommon(sql);
			db.setString(1, tmlVersion);
			db.setString(2, leftDiskSize);
			db.setString(3, ableMem);
			db.setString(4, portalVersion);
			db.setString(5, portalUrl);
			db.setString(6, tmlPlayling);
			db.setString(7, tmlDownling);
			db.setString(8, tmlApps);
			db.setString(9, tmlSpelist);
			db.setString(10, date);
			db.setString(11, tmlId);
			db.executeUpdate();
			db.commit();
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}
	}

	public int getTmsOnlineNum() throws SqlException {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM TML_BASE WHERE tmlStatus>"+TmlConst.tmlStatus.outline;
		ResultSet rs = null;
		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
			rs = null;
		}
		return count;
	}

	public void updateTmlDoCnt(String tmlId, List<TmlDoInfo> list) throws SqlException {
		DBCommon db = null;
		try {
			db = new DBCommon();
			String delSql = "DELETE FROM TML_CURRENT_DO WHERE tmlId='"+tmlId+"'";
			db.addBatch(delSql);
			db.executeBatch();
			db.commit();
			if (list != null && list.size() != 0) {
				for (TmlDoInfo info : list) {
					if (info != null) {
						String sql = 
							"INSERT TML_CURRENT_DO (id,tmlId,tmlStatus,cntType,tmlDoingCnt,tmlDownrate,tmlDownsource,inTime) VALUES (?,?,?,?,?,?,?,?)" ;
						db = new DBCommon(sql);
						db.setInt(1, info.getId());
						db.setString(2, info.getTmlId());
						db.setInt(3, info.getTmlStatus());
						db.setInt(4, info.getServiceType());
						db.setString(5, info.getTmlDoing());
						db.setString(6, info.getDownRate());
						db.setString(7, info.getDownSource());
						db.setString(8, info.getInTime());
						db.executeUpdate();
						db.commit();
					}
				}
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] a) throws DataExistException {
		TmlJDBCImpl t = new TmlJDBCImpl();

		/*try {
			List<Terminal> aa = t.getTmls("", 0, 0, 0, 
					-1, false, "", "", "", "", "天使", "");
			//TmlSys aa =  t.getTmlSys("0016E8176834");
			System.out.println(aa);
		} catch (SqlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String spes = "211.144.118.25:9007，211.144.118.20:9002，180.168.93.166:9003";
		if (spes != null && !spes.trim().equals("")) {
			List list = new ArrayList();
			if (spes.contains("，")) {
				String[] ss = spes.split("，");
				for (int i = 0; i < ss.length; i++) {
					list.add(ss[i]);
				}
			} else {
				list.add(spes);
			}
			System.out.println(list);
		}


		/*List<TmlDoInfo> list = new ArrayList<TmlDoInfo>();
		TmlDoInfo info = new TmlDoInfo();
		info.setTmlId("0016E85E312C");
		info.setTmlStatus(TmlConst.tmlStatus.download);
		info.setTmlDoing("美人心计（4）");
		info.setDownSource("211.144.118.240:9000");
		info.setDownRate("10KB/S");
		info.setInTime(PackageConstant.sdf.format(new Date()));
		info.setId(info.hashCode());
		list.add(info);

		TmlDoInfo info1 = new TmlDoInfo();
		info1.setTmlId("0016E85E312C");
		info1.setTmlStatus(TmlConst.tmlStatus.play);
		info1.setTmlDoing("阿凡达");
		info1.setServiceType(4);
		info1.setInTime(PackageConstant.sdf.format(new Date()));
		info1.setId(info1.hashCode());
		list.add(info1);
		try {
			t.updateTmlDoCnt("0016E85E312C",list);
		} catch (SqlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public String getSpeIpPort(String mac) {
		String speIpPort = "";

		String sql = "SELECT * FROM TML_SPE WHERE tmlId=? ";
		ResultSet rs = null;

		try {
			db = new DBCommon(sql);
			db.setString(1, mac);
			rs = db.executeQuery();

			while (rs.next()) {
				speIpPort = rs.getString("speIp");
			}
		} catch (SQLException e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return speIpPort;
	}
	
	public int getOrderNum(String startTime, String endTime) throws SqlException {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM TML_ORDER WHERE inTime>=? AND inTime<=?";
		ResultSet rs = null;
		try {
			db = new DBCommon(sql);
			db.setString(1, startTime);
			db.setString(2, endTime);
			rs = db.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			db.close();
			db = null;
			rs = null;
		}
		return count;
	}
	
	public String getConfigValue(String name) {
		String value = "";
		String sql = "SELECT VALUE FROM SYS_CONFIGURE WHERE NAME=?";
		ResultSet rs = null;
		try {
			db = new DBCommon(sql);
			db.setString(1, name);
			rs = db.executeQuery();
			while(rs.next()) {
				value = rs.getString(1);
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			db.close();
			db = null;
			rs = null;
		}
		return value;
	}
	public int bindTelNo(String customerId, 
			String password, 
			String tmlId,
			String telNo) {
		int ret = PackageConstant.result_code.ok;

		try {
			int count = 0;
			String sql0 = "SELECT COUNT(*) FROM TML_BASE WHERE customerId=? AND customerPwd=? AND tmlId=?";
			db = new DBCommon(sql0);
			db.setString(1, customerId);
			db.setString(2, password);
			tmlId =  tmlId.toUpperCase();
			db.setString(3, tmlId);
			ResultSet rs = db.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			rs = null;
			if (count == 1) {
				String sql = "UPDATE TML_BASE SET telNo=? WHERE tmlId=?";
				db = new DBCommon(sql);
				db.setString(1, telNo);
				db.setString(2, tmlId);
				db.executeUpdate();
				db.commit();
			}

		} catch (SQLException e) {
			ret = PackageConstant.result_code.db_error;
			 
			log.error(this,e);
		} finally {
			db.close();
			db = null;
		}

		return ret;
	}
}

class MidiDTO {
	private List<TmlDoInfo> str1 = new ArrayList<TmlDoInfo>();
	private List<TmlDoInfo> str2 = new ArrayList<TmlDoInfo>();
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("MidiDTO:[str1=").append(str1)
		.append(", str2=").append(str2)
		.append("]\r\n");
		return sb.toString();
	}
	public List<TmlDoInfo> getStr1() {
		return str1;
	}
	public void setStr1(List<TmlDoInfo> str1) {
		this.str1 = str1;
	}
	public List<TmlDoInfo> getStr2() {
		return str2;
	}
	public void setStr2(List<TmlDoInfo> str2) {
		this.str2 = str2;
	}
}