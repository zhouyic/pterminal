package communicate.pkmgmt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;




















import util.DateUtils;
import communicate.common.CacheCommon;
import communicate.common.DBCommon;
import communicate.pkmgmt.dto.TmlBase;
import communicate.pkmgmt.dto.TmlConfDto;
import communicate.pkmgmt.dto.TmlDto;
import communicate.pkmgmt.dto.TmlHistory;
import communicate.pkmgmt.stb.HeartBeat;

public class ThaiServiceImpl implements IThaiService {
//	private Cache cache = CacheCommon.getCommonCache();
	private static final int RECORD_TYPE_DAY = 0;
	private static final int RECORD_TYPE_MONTH = 1;
	private static final int RETAILER_TYPE_TOTAL = 1;
	private static final int RETAILER_TYPE_SINGLE = 0;
	
	private static final Logger log = communicate.common.Logger
			.getLogger(ThaiServiceImpl.class);
	private DBCommon db = null;
	public String getCndurlDByGroupId(String groupId){
		log.info("getCndurlDByGroupId " + groupId );
		String cdn_url="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE group_id = '"+groupId+"'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdn_url=rs.getString("cdn_url");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}		
	
		return cdn_url;
		
	}
	public String getpreUrlDByDefault(String groupId) {
		log.info("getpreUrlDByDefault");
		String preurl="";
		ResultSet rs = null;
		db = new DBCommon();
			try {
				String sql = "SELECT upgradeUrl FROM tml_conf WHERE groupId='"+groupId+"'";
				log.info("——————select upgradeUrl——"+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					preurl=rs.getString("upgradeUrl");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}		
		return preurl;
	}
	public String getCdnUrlDBySerial(String usercodeId,String promodelId,String probatchId,String groupId) {
		log.info("getCdnUrlDBySerial " + usercodeId );
		String cdnurl="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE  usercode_id='"+usercodeId+"' AND promodel_id = '"+promodelId+"' AND probatch_id='"+probatchId+"' AND group_id='"+groupId+"'";
				log.debug("——————select cndurl——"+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}		
		return cdnurl;
	}
	public String getCdnUrlDByDefault(String groupId) {
		log.info("getCdnUrlDByDefault " + groupId);
		String cdnurl="";
		ResultSet rs = null;
		db = new DBCommon();
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE usercode_id='default' AND promodel_id = 'default' AND probatch_id='default' AND group_id='"+groupId+"'";
				log.info("——————select cndurl——"+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}		
		return cdnurl;
	}
	public String getSerialIdDByTmlId(String tmlId){
		log.info("getSerialIdDByTmlId = "+tmlId);
		String serialnum="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT tml_sn FROM tml_base WHERE  tml_id='"+tmlId+"'";
				log.info("判断的  sql =" + sql);
				log.debug(sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					serialnum=rs.getString("tml_sn");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return serialnum;
	}
	public String selectFirstLogin(String tmlId){
		log.info("selectFirstLogin " + tmlId);
		String firstlogin="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT first_login FROM tml_base WHERE tml_id = '"+tmlId+"'";
				log.info("查询中文的首次登陆时间："+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					firstlogin=rs.getString("first_login");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
	
		return firstlogin;
		
	}
	public String selectFirstLoginEn(String tmlId){
		log.info("selectFirstLoginEn " + tmlId);
		String firstlogin="";
		ResultSet rs = null;
		db = new DBCommon();
			try {
				String sql = "SELECT first_login FROM tml_base_en WHERE tml_id = '"+tmlId+"'";
				log.info("查询英文的首次登陆时间："+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					firstlogin=rs.getString("first_login");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
	
		return firstlogin;
		
	}
	public String gethardwareDByhardwareId(String hardwareId) {
		log.info("gethardwareDByhardwareId " + hardwareId);
		String hardware="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT hardware FROM tml_hardware WHERE hardware_id = '"+hardwareId+"'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					hardwareId=rs.getString("hardware");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return hardware;
	}
	public String getcountryDBycountryId(String countryId) {
		log.info("getcountryDBycountryId " + countryId);
		String country="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT country FROM tml_country WHERE country_id = '"+countryId+"'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					country=rs.getString("country");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return country;
	}
	public int addTmlBase(String tmlId, String hardwareId,
			String countryId,String regionId,String currentversion,String TimeZone,String Language,int tmlType ,String tmlSN,String country,String region,String hardware,String time){
		log.info("——————addTmlBase——tmlId="+tmlId);
		
		int result = 0;
		String sql = "INSERT INTO `tml_base`(tml_id, hardware_id, country_id, region_id,current_version, time_zone, language, tml_type ,tml_sn,country, region, hardware,first_login) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		log.debug("——————addTmlBase-----sql——"+sql);
		PreparedStatement stmt = null;
		Connection conn= null;
		db = new DBCommon();
		ResultSet rs = null;
		try {
			conn = db.getConnectionJdbc();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,tmlId);
			stmt.setString(2,hardwareId);
			stmt.setString(3,countryId);
			stmt.setString(4, regionId);
			stmt.setString(5, currentversion);
			stmt.setString(6, TimeZone);
			stmt.setString(7, Language);
			stmt.setInt(8, tmlType);
			stmt.setString(9, tmlSN);
			stmt.setString(10, country); 
			stmt.setString(11, region);
			stmt.setString(12, hardware);
			stmt.setString(13, time);
			result = stmt.executeUpdate();
			
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}
	public int addTmlHistory(String tmlId,String time,String type){
		int result = 0;
		log.info("----addTmlHistory-----"+"——————tmlId="+tmlId+"——————time="+time+"——————type="+type);

		String sql = "insert into tml_history (tml_id,nftime,type) values (?,?,?)";
		log.debug("——————插入上线时间中文-----sql——"+sql);
		PreparedStatement stmt = null;
		Connection conn= null;
		db = new DBCommon();
		ResultSet rs = null;
		try {
			conn = db.getConnectionJdbc(); 
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,tmlId);
			stmt.setString(2,time);
			stmt.setString(3,type);
			result = stmt.executeUpdate();		
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}
	
	public int batchAddTmlHistory(List<String> tmlIds,String time,String type){
		int result = 0;
		try {
			log.info("batchAddTmlHistory : " + tmlIds.size());
			db = new DBCommon();
			if(tmlIds != null && tmlIds.size()>0) {

				for(String tmlId : tmlIds) {
					db.addBatch("insert into tml_history (tml_id,nftime,type) values ( '" + tmlId	+ "', '" + time + "', '" +type+ "');");
					log.debug("insert into tml_history (tml_id,nftime,type) values ( '" + tmlId	+ "', '" + time + "', '" +type+ "');");
				}
				result = db.executeBatch();
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
		
		return result;
	}
	
	
	
	public int addTmlBaseEn(String tmlId, String hardwareId,
			String countryId,String regionId,String currentversion,String TimeZone,String Language,int tmlType ,String tmlSN,String country,String region,String hardware,String time){
		int result = 0;
		log.info("addTmlBaseEn " + tmlId);
		String sql = "INSERT INTO `tml_base_en`(tml_id, hardware_id, country_id, region_id,current_version, time_zone, language, tml_type , tml_sn,country, region, hardware,login_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		log.debug("——————addTmlBase-----sql——"+sql);
		PreparedStatement stmt = null;
		Connection conn= null;
		ResultSet rs = null;
		db = new DBCommon();
		try {
			conn = db.getConnectionJdbc();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,tmlId);
			stmt.setString(2,hardwareId);
			stmt.setString(3,countryId);
			stmt.setString(4, regionId);
			stmt.setString(5, currentversion);
			stmt.setString(6, TimeZone);
			stmt.setString(7, Language);
			stmt.setInt(8, tmlType);
			stmt.setString(9, tmlSN);
			stmt.setString(10, country); 
			stmt.setString(11, region);
			stmt.setString(12, hardware);
			stmt.setString(13, time);
			result = stmt.executeUpdate();
			
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}
	public String getregionDByregionId(String regionId) {
		log.info("getregionDByregionId " + regionId);
		String region="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT region FROM tml_region WHERE region_id = '"+regionId+"'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					regionId=rs.getString("region");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return region;
	}
	public String getCdnUrlDByHardwareonly(String hardwareId){
		log.info("getCdnUrlDByHardwareonly " + hardwareId);
		String cdnurl="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE  country_id='default' AND region_id='default' AND hardware_id = '"+hardwareId+"' AND group_id ='def'";
				log.info(sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return cdnurl;
	}
	public String getCdnUrlDByHardware(String hardwareId,String groupId){
		String cdnurl="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE  country_id='default' AND region_id='default' AND hardware_id = '"+hardwareId+"' AND group_id = '"+groupId+"'";
				log.debug(sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return cdnurl;
	}
	public String getCdnUrlDByCountry(String hardwareId,String countryId,String groupId) {
		String cdnurl="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE  region_id='default' AND hardware_id = '"+hardwareId+"' AND country_id='"+countryId+"' AND group_id = '"+groupId+"'";
				log.debug("——————select cndurl——"+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return cdnurl;
	}
	public String getCdnUrlDByRegion(String hardware,String country,String region,String groupId) {
		String cdnurl="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT cdn_url FROM update_file WHERE region_id='"+region+"' AND hardware_id = '"+hardware+"' AND country_id='"+country+"' AND group_id = '"+groupId+"'";
				 rs = db.executeQuery(sql);
				while (rs.next()) {
					cdnurl=rs.getString("cdn_url");
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return cdnurl;
	}
	
	public TmlBase getTmlbaseDtoByTmlId(String tmlId) {
		TmlBase tmlbase = new TmlBase();
		log.debug("no info in cache , search from db");
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT hardware_id,region_id,country_id,app_status FROM tml_base WHERE tml_id = '"
						+ tmlId + "'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					tmlbase.setHardware(rs.getString("hardware_id"));
					tmlbase.setCountry(rs.getString("country_id"));
					tmlbase.setRegion(rs.getString("region_id"));
					tmlbase.setAppStatus(Integer.valueOf(rs.getString("app_status")));
					tmlbase.setTmlId(tmlId);
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return tmlbase;
	}
	public boolean updateAppStatus(String tmlId, int status) {
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET app_status = " + status
					+ " WHERE tml_id = '" + tmlId + "'";
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}
	public boolean updateFirstLoginTime(String tmlId, String time) {
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET first_login = '" + time
					+ "' WHERE tml_id = '" + tmlId + "'";
			log.info("更新中文标首次登陆时间："+sql);
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			
			db.close();
		}
		return flag;
	}
	
	public boolean updateCurrentVersion(String tmlId, String version) {
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET current_version = '" + version
					+ "' WHERE tml_id = '" + tmlId + "'";
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}
	public void updateFirstFlag(String tmlId) {
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET first_flag = '1' WHERE tml_id = '" + tmlId + "'";
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
	}

//	public boolean isTmlExist(String tmlId) {
//		log.info("isTmlExist tmlId list tmlId=" +tmlId); 
//		List<String> list = getAllTerminal();
//		if(list != null && list.contains(tmlId)) {
//			return true;
//		} else if(list == null){
//			log.info("tmlId list is null"); 
//			return false;
//		} else {
//			log.info("tmlId doesn't exist in db");
//			return false;
//		}
//	}
	
	public boolean isTmlExist(String tmlId) {
		log.info("isTmlExist tmlId list tmlId=" +tmlId); 
		db = new DBCommon();
		boolean exist = false;
		ResultSet rs = null;
		try {
			String sql = "SELECT tml_id FROM tml_base WHERE tml_id = '"+tmlId+"'";
			log.debug(sql);
			rs = db.executeQuery(sql);
			while (rs.next()) {
				exist = true;
			}
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}		
		return exist;
	}
	
	public String getGroupIdDByTmId(String tmlId){
		log.info("getGroupIdDByTmId tmlId="+tmlId);
		String groupId="";	
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT group_id FROM tml_base WHERE tml_id = '"+tmlId+"'";
				log.debug(sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					groupId=rs.getString("group_id");		
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return groupId;
		
	}

	public TmlConfDto getTmlConfDtoByTmlId(String tmlId) {
		TmlConfDto tmlConfDto = null;
//		String key = "getTmlConfDtoByTmlId"+tmlId;
//		Element element = cache.get(key);
//		if (element != null) {
//			tmlConfDto = (TmlConfDto) element.getObjectValue();
//		}
		ResultSet rs = null;
		if(tmlConfDto == null || tmlConfDto.getPortalUrl()==null || tmlConfDto.getNtpUrl()==null || tmlConfDto.getLogUrl()==null) {
			log.debug("no info in cache , search from db");
			db = new DBCommon();
			tmlConfDto = new TmlConfDto();
			tmlConfDto.setTmlId(tmlId);
			String groupId = null;
			try {
				String sql = "SELECT group_id FROM tml_base WHERE tml_id = '"
						+ tmlId + "'";
				log.info("sql="+sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					groupId = rs.getString("group_id");
				}
				if (groupId == null) {
					tmlConfDto.setPortalUrl("");
					tmlConfDto.setLogUrl("");
					tmlConfDto.setNtpUrl("");
					tmlConfDto.setHeartbeatRate(0);
					tmlConfDto.setUpgradeUrl("");
					tmlConfDto.setUpgradeInfoUrl("");
					tmlConfDto.setAppInfoUrl("");
					log.debug("未分配配置信息分组");
				} else {
					tmlConfDto = getTmlConfDtoByGroupId(groupId);
					tmlConfDto.setTmlId(tmlId);
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}
//			cache.put(new Element(key, tmlConfDto));
		}
		return tmlConfDto;
	}
	public boolean updateTmlbaseByTmlId(String tmlId, String hardwareId,
			String countryId,String currentversion,String TimeZone,String Language,int tmlType ,String country,String hardware) {
		db = new DBCommon();
		log.info("---updateTmlbaseByTmlId---");
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET hardware_id = '"+ hardwareId +"',country_id = '" + countryId
					+ "',current_version='"+currentversion+"',time_zone='"+TimeZone+"',Language='"+Language+"',tml_type="+tmlType+",hardware='"+hardware+"',country='"+country+
					"' WHERE tml_id = '"
					+ tmlId + "'";
			log.info("盒子上报的时候信息更新数据库："+sql);
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}
	public boolean updateTmlPlayMsg(String tmlId, String contentName,String remoteIP,String country, int serviceType) {
		log.info("----updateTmlPlayMsg-----"+"——————tmlId="+tmlId+"----------country="+country+"---------tml_ip_address="+remoteIP+"——————contentName="+contentName);

		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET status = 1 ,country=\""+country+"\",tml_ip_address=\""+remoteIP+"\",content_name = \"" + contentName
					+ "\" ,service_type = " + serviceType + " WHERE tml_id = '"
					+ tmlId + "'";
			log.info("sql="+sql);
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}
	
	public boolean updateLoginTime(String tmlId, String loginTime) {
		log.info("---updateLoginTime---tmlId="+tmlId+ "   loginTime="+loginTime);
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET login_time = '" + loginTime
					+ "' WHERE tml_id = '" + tmlId + "'";
			log.info("sql="+sql);
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}

	public boolean login(String tmlId, String tmlSN) {
		log.info("---login---tmlId="+tmlId+" tmlSN="+tmlSN);
		db = new DBCommon();
		boolean flag = true;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(new Date());
			String sql = "UPDATE tml_base SET login_time = '" + time
					+ "', status = " + 1 
					+ " WHERE tml_id = '" + tmlId + "'";
			log.info("盒子存在的情况下更新登陆状态"+sql);
			db.executeUpdate(sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}

	public boolean logout(String tmlId) {
		log.info("---logout--in--tmlId="+tmlId);
		db = new DBCommon();
		boolean flag = true;
		try {
			String sql = "UPDATE tml_base SET status =" +0
					+", content_name='' WHERE tml_id = '" + tmlId + "'";			
			db.executeUpdate(sql);
			log.info("!!!!!!!!!!!!!!!!!!!!:"+sql);
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
		} finally {
			db.close();
		}
		return flag;
	}

	public TmlConfDto getTmlConfDtoByGroupId(String groupId) {
		log.info("---getTmlConfDtoByGroupId--in--groupId="+groupId);
		TmlConfDto tmlConfDto = null;
//		String key = "getTmlConfDtoByGroupId"+groupId;
//		Element element = cache.get(key);
//		if (element != null) {
//			tmlConfDto = (TmlConfDto) element.getObjectValue();
//		}
		ResultSet rs = null;
		if(tmlConfDto == null || tmlConfDto.getPortalUrl()==null || tmlConfDto.getNtpUrl()==null || tmlConfDto.getLogUrl()==null || tmlConfDto.getUpgradeInfoUrl()==null || tmlConfDto.getAppInfoUrl()==null) {
//			log.debug("no info in cache , search from db");
			db = new DBCommon();
//			log.debug("db---------------" + db);
			tmlConfDto = new TmlConfDto();
			try {
				String sql = "SELECT portalUrl, logServerUrl, ntpServerUrl,heartbeatRate ,groupName ,upgradeUrl, registerUrl,upgradeInfoUrl, appInfoUrl FROM tml_conf WHERE groupId = '"
						+ groupId + "'";
				
				rs = db.executeQuery(sql);
//				String groupname="";
				if (rs.next()) {	
//					String groupName=rs.getString(5);
//					if(groupName.equals("正式组")){
//						groupname="FORMAL";
//					}else{
//						if(groupName.equals("测试组")){
//						groupname="TEST";	
//						}else{
//							groupname=groupName;
//						}
//					}
//					tmlConfDto.setGroupName(groupname);
					tmlConfDto.setPortalUrl(rs.getString(1));
					tmlConfDto.setLogUrl(rs.getString(2));
					tmlConfDto.setNtpUrl(rs.getString(3));
					tmlConfDto.setHeartbeatRate(rs.getInt(4));
					tmlConfDto.setGroupName(rs.getString(5));
					tmlConfDto.setUpgradeUrl(rs.getString(6));
					tmlConfDto.setRegisterUrl(rs.getString(7));
					tmlConfDto.setUpgradeInfoUrl(rs.getString(8));
					tmlConfDto.setAppInfoUrl(rs.getString(9));
				} else {
					tmlConfDto.setPortalUrl("");
					tmlConfDto.setLogUrl("");
					tmlConfDto.setNtpUrl("");
					tmlConfDto.setHeartbeatRate(0);
					tmlConfDto.setGroupName("");
					tmlConfDto.setUpgradeUrl("");
					tmlConfDto.setRegisterUrl("");
					tmlConfDto.setUpgradeInfoUrl("");
					tmlConfDto.setAppInfoUrl("");
					log.debug("未查询到配置信息");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}
//			cache.put(new Element(key, tmlConfDto));
		}
		return tmlConfDto;
	}

	public boolean updateSysInfo(String tmlId, String version, String ableMem,
			String portalUrl, String logServerUrl, String ntpUrl,
			int heartBeatRate,String upgradeServerUrl,String upgradeInfoUrl,String appInfoUrl ) {
		db = new DBCommon();
		boolean flag = true;
		log.debug("upgradeInfoUrl:"+upgradeInfoUrl);
		log.debug("appInfoUrl:"+appInfoUrl);
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE tml_base SET current_version = '")
					.append(version).append("', able_mem = '").append(ableMem)
					.append("', portal_url = '").append(portalUrl)
					.append("', log_server_url = '").append(logServerUrl)
					.append("', ntp_url = '").append(ntpUrl)
					.append("', heart_beat_rate = ").append(heartBeatRate)
					.append(", upgrade_server_url = '").append(upgradeServerUrl)
					.append("', upgradeInfoUrl = '").append(upgradeInfoUrl)
					.append("', appInfoUrl = '").append(appInfoUrl)
					.append("'  WHERE tml_id = '").append(tmlId).append("'");		
			db.executeUpdate(sb.toString());	
		} catch (Exception e) {
			flag = false;
			log.error(this, e);
			log.debug("cuowu");
		} finally {
			db.close();
		}
		log.debug("ok");
		return flag;
	}
	public boolean updateOperateHistory(int cmdNo, String updateTime, String result) {
		log.info("updateOperateHistory cmdNo=" + cmdNo +" updateTime="+updateTime+" result="+result);
		db = new DBCommon();
		boolean flag = true;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE operate_history SET update_time = '")
					.append(updateTime).append("', result = '").append(result)
					.append("'  WHERE id = ").append(cmdNo);		
			db.executeUpdate(sb.toString());	
		} catch (Exception e) {
			flag = false;
			log.error(this, e);

		} finally {
			db.close();
		}
		log.debug("ok");
		return flag;
	}
	public void getAllOnTerminal()  {
		log.info("get online terminals");		
		Map<String,Long> map = HeartBeat.heartbeatMap;
		long time = System.currentTimeMillis();
		db = new DBCommon();
		ResultSet rs = null;
		try {
			String sql = "SELECT tml_id FROM tml_base WHERE status = 1";
			rs = db.executeQuery(sql);
			while (rs.next()) {
				 map.put(rs.getString("tml_id"), time);
			}
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		} finally {
			
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			
			db.close();
		}
		log.info("在线终端的map: " + HeartBeat.heartbeatMap);
	}
	public String selectFirstFlag(String tmlid) {
		log.info("判断是否是第一次登陆");
		db = new DBCommon();
         String first_flag="";
         ResultSet rs = null;
		try {
			String sql = "SELECT first_flag FROM tml_base WHERE tml_id = '"+tmlid+"'";
			log.info("sql="+sql);
			rs = db.executeQuery(sql);
			while (rs.next()) {
		    first_flag=rs.getString("first_flag");
			}
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		} finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return first_flag;
	}
	public List<String> getAllTerminal() {
		log.info("--getAllTerminal--");
		List<String> list = null;
//		String key = "getAllTerminal";
//		Element element = null;
//		try {
//			element = cache.get(key);
//		}catch(Exception e){
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
//		
//		if (element != null) {
//			list = (List<String>) element.getObjectValue();
//		}
		ResultSet rs = null;
		if(list == null || list.size()<=0) {
//			log.info("no info from cache, search from db");
			db = new DBCommon();
			list = new ArrayList<String>();
			try {
				String sql = "SELECT tml_id FROM tml_base";
				log.info(sql);
				rs = db.executeQuery(sql);
				while (rs.next()) {
					 list.add(rs.getString("tml_id"));
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}
//			cache.put(new Element(key, list));
		}
		return list;
	}
	

	public void batchUpdateLoginTime(Collection<TmlDto> tmls) {
		if(tmls==null){
			log.error("tmls is null ");
			return;
		}
		try {
			log.info("batchUpdateLoginTime : " + tmls.size());
			db = new DBCommon();
			if(tmls != null && tmls.size()>0) {
				log.debug("here");
				for(TmlDto dto : tmls) {
					db.addBatch("UPDATE tml_base SET login_time = '" + dto.getOnlineTime()	+ "', status = " + 1 + ", tml_sn = '" + dto.getTmlSN() + "' WHERE tml_id = '" + dto.getTmlId() + "'");
				}
				db.executeBatch();
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public List<String> getRetailerList() {
		List<String> res = new ArrayList<String>();
		ResultSet rs = null;
		try {
			log.info("getRetailerList : " );
			db = new DBCommon();
			rs = db.executeQuery("select distinct NNAME from RRETAILER");
			if(rs == null)
				res = null;
			else {
				while(rs.next()) {
					res.add(rs.getString(1));
				}
			}
			
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}

	public List<String> getRetailerListInLoginTbl(String time) {
		List<String> res = new ArrayList<String>();
		ResultSet rs = null;
		try {
			log.info("getRetailerListInLoginTbl : " );
			db = new DBCommon();
			rs = db.executeQuery("select distinct retailer_name from stb_login_analysis_tbl where record_time='"+time+"'");
			if(rs == null)
				res = null;
			else {
				while(rs.next()) {
					res.add(rs.getString(1));
				}
			}
			
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}
	public List<String> getRetailerListInActivationTbl(String time) {
		List<String> res = new ArrayList<String>();
		ResultSet rs = null;
		try {
			log.info("getRetailerListInLoginTbl : " );
			db = new DBCommon();
			rs = db.executeQuery("select distinct retailer_name from stb_activation_analysis_tbl where record_time='"+time+"'");
			if(rs == null)
				res = null;
			else {
				while(rs.next()) {
					res.add(rs.getString(1));
				}
			}
			
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}
	public int countLoginTotal(String retailerName, String date) {
		int res = 0;
		ResultSet rs = null;
		try {
			log.info("countLoginTotal : " );
			db = new DBCommon();
			String sql = "select count(*) from  tml_history where tml_id in ( select tml_id from tml_base where retailer_name = '" + retailerName + "'  ) and nftime like '%" + date +"%' and type='online'";
			log.info(sql);
			rs = db.executeQuery(sql);
			if(rs != null) {
				if(rs.next()) {
					res =rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}
	public int countLoginTotalDay(String date) {
		ResultSet rs = null;
		int res = 0;
		try {
			log.info("countLoginTotal : " );
			db = new DBCommon();
			String sql = "select count(*) from  tml_history where  nftime like '%" + date +"%' and type='online'";
			log.info(sql);
			rs = db.executeQuery(sql);
			if(rs != null) {
				if(rs.next()) {
					res =rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}

	public int countRegisterTotal(String retailerName, String date) {
		int res = 0;
		ResultSet rs=null;
		try {
			log.info("countRegisterTotal : " );
			db = new DBCommon();
			String sql = "select count(*) from  tml_base where retailer_name = '" + retailerName + "' and first_login like '%" + date +"%'";
			log.debug(sql);
			rs = db.executeQuery(sql);
			if(rs != null) {
				if(rs.next()) {
					res =rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}
	public int countRegisterTotalDay(String date) {
		int res = 0;
		ResultSet rs = null;
		try {
			log.info("countRegisterTotal : " );
			db = new DBCommon();
			String sql = "select count(*) from  tml_base where first_login like '%" + date +"%'";
			log.debug(sql);
			rs = db.executeQuery(sql);
			if(rs != null) {
				if(rs.next()) {
					res =rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
		return res;
	}
	public void batchInsertStbLoginAnalysisTblDay(Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbLoginAnalysisTblDay : " + map.size());
			db = new DBCommon();
				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {
					String sql = "INSERT INTO stb_login_analysis_tbl(retailer_name,num,record_type,retailer_type,record_time) values('" + retailerName + "'," + map.get(retailerName) + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_SINGLE +",'"+ recordTime +"')";
					log.debug(sql);
					db.addBatch(sql);
						if(i>50) {
						db.executeBatch();
						i = 0;
						log.info("execute once");
					} else {
						i++;
					}
				}
				int r = db.executeBatch();
				db.commit();
				log.info("batchInsertStbLoginAnalysisTblDay : r = " + r);
		
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void insertStbLoginAnalysisTblDayTotal(int num, String recordTime) {

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("insertStbLoginAnalysisTblDayTotal : " + num);
			db = new DBCommon();
			//log.debug("here: " + "INSERT INTO stb_login_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')");
			db.executeUpdate("INSERT INTO stb_login_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')");
			db.commit();
			//				
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void batchInsertStbLoginAnalysisTblMonth(Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbLoginAnalysisTblMonth : " + map.size());
			db = new DBCommon();
//				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {
					db.addBatch("INSERT INTO stb_login_analysis_tbl(retailer_name,num,record_type,retailer_type,record_time) values('" + retailerName + "'," + map.get(retailerName) + ", "+ RECORD_TYPE_MONTH  +","+ RETAILER_TYPE_SINGLE +",'"+ recordTime +"')");
					if(i>50) {
						db.executeBatch();
						i = 0;
//						log.info("execute once");
					} else {
						i++;
					}
				}
				db.executeBatch();
				db.commit();
		
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void insertOrUpdateStbLoginAnalysisTblMonthTotal(int num, String recordTime) {

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		ResultSet rs = null;
		try {
			log.info("insertStbLoginAnalysisTblMonthTotal : " + num);
			db = new DBCommon();
			log.debug("here");
			rs = db.executeQuery("SELECT * FROM stb_login_analysis_tbl WHERE record_type="+ RECORD_TYPE_MONTH  +" AND retailer_type= "+ RETAILER_TYPE_TOTAL +" AND record_time='"+ recordTime +"'");
			if(rs.next()) {
				db.executeUpdate("UPDATE stb_login_analysis_tbl SET num=" + num + " WHERE record_type="+ RECORD_TYPE_MONTH  +" AND retailer_type="+ RETAILER_TYPE_TOTAL +" AND record_time = '"+ recordTime +"'");
			} else {
				db.executeUpdate("INSERT INTO stb_login_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_MONTH  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')");
			}
			db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
	}
	public void batchInsertStbActivationAnalysisTblDay(Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbActivationAnalysisTbl : " + map.size());
			db = new DBCommon();
				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {
					db.addBatch("INSERT INTO stb_activation_analysis_tbl(retailer_name,num,record_type,retailer_type,record_time) values('" + retailerName + "'," + map.get(retailerName) + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_SINGLE +",'"+ recordTime +"')");
					if(i>50) {
						db.executeBatch();
						i = 0;
						log.info("execute once");
					} else {
						i++;
					}
				}
				db.executeBatch();
				db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void insertStbActivationAnalysisTblDayTotal(int num, String recordTime) {

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbActivationAnalysisTblDayTotal : " + num);
			db = new DBCommon();
			log.debug("here");
			String sql = "INSERT INTO stb_activation_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')";
			log.debug(sql);
			db.executeUpdate("INSERT INTO stb_activation_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_DAY  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')");
			db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void batchInsertStbActivationAnalysisTblMonth(Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbActivationAnalysisTbl : " + map.size());
			db = new DBCommon();
				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {
					db.addBatch("INSERT INTO stb_activation_analysis_tbl(retailer_name,num,record_type,retailer_type,record_time) values('" + retailerName + "'," + map.get(retailerName) + ", "+ RECORD_TYPE_MONTH  +","+ RETAILER_TYPE_SINGLE +",'"+ recordTime +"')");
					if(i>50) {
						db.executeBatch();
						i = 0;
						log.info("execute once");
					} else {
						i++;
					}
				}
				db.executeBatch();
				db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	public void insertOrUpdateStbActivationAnalysisTblMonthTotal(int num, String recordTime) {

//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		ResultSet rs = null;
		try {
			log.info("batchInsertStbActivationAnalysisTblDayTotal : " + num);
			db = new DBCommon();
			log.debug("here");
			rs = db.executeQuery("SELECT * FROM stb_activation_analysis_tbl WHERE record_type = "+ RECORD_TYPE_MONTH  +" AND retailer_type = "+ RETAILER_TYPE_TOTAL +" AND record_time ='"+ recordTime +"'"); 
			if(rs.next())
				db.executeUpdate("UPDATE stb_activation_analysis_tbl SET num=" + num + " WHERE record_type= "+ RECORD_TYPE_MONTH  +" AND retailer_type="+ RETAILER_TYPE_TOTAL +" AND record_time='"+ recordTime +"'");
			else
				db.executeUpdate("INSERT INTO stb_activation_analysis_tbl(num,record_type,retailer_type,record_time) values(" + num + ", "+ RECORD_TYPE_MONTH  +","+ RETAILER_TYPE_TOTAL +",'"+ recordTime +"')");
				
			db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			try {
				if (rs !=null) {
				rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			db.close();
		}
	}
	public static void main(String[] args) {
		ThaiServiceImpl thaiServiceImpl=new ThaiServiceImpl();
		List<String> tmlIds=new ArrayList<String>();
		tmlIds.add("8810361FF48A");
		tmlIds.add("8810361FF601");
//		thaiServiceImpl.batchUpdateAppInfosLastesMonths(tmlIds, "run", "exit");
//		thaiServiceImpl.batchLogout(tmlIds);
		List<TmlHistory> findTmlHistory = thaiServiceImpl.findTmlHistory("8810361FEC63", "online", "", "2016-11-22 09:05:30", "desc", 1);
		if(findTmlHistory!=null&&findTmlHistory.size()>0){
			for(TmlHistory tmlHistory:findTmlHistory){
				System.out.println(tmlHistory);
			}
		}
	}

	public void batchUpdateStbLoginAnalysisTblMonth(Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchUpdateStbLoginAnalysisTblMonth : " + map.size());
			db = new DBCommon();
				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {
					db.addBatch("UPDATE stb_login_analysis_tbl SET num=" + map.get(retailerName) + " WHERE retailer_name = '" + retailerName + "' AND record_type = " + RECORD_TYPE_MONTH + " AND retailer_type = " + RETAILER_TYPE_SINGLE + " AND record_time='"+ recordTime +"'");
//						db.addBatch("UPDATE tml_base_en SET login_time = '" + dto.getOnlineTime()	+ "', status = " + 1 + ", tml_sn = '" + dto.getTmlSN() + "' WHERE tml_id = '" + dto.getTmlId() + "'");
//						db.addBatch("UPDATE tml_base_thai SET login_time = '" + dto.getOnlineTime()	+ "', status = " + 1 + ", tml_sn = '" + dto.getTmlSN() + "' WHERE tml_id = '" + dto.getTmlId() + "'");
					if(i>50) {
						db.executeBatch();
						i = 0;
						log.info("execute once");
					} else {
						i++;
					}
				}
				db.executeBatch();
				db.commit();
		
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
	}
	
	public void batchUpdateStbActivationAnalysisTblMonth(
			Map<String, Integer> map, String recordTime) {
		if(map==null || map.size()==0){
			log.error("map is null ");
			return;
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String time = df.format(new Date());
		try {
			log.info("batchInsertStbActivationAnalysisTbl : " + map.size());
			db = new DBCommon();
				log.debug("here");
				int i = 0;
				for(String retailerName : map.keySet()) {


					db.addBatch("UPDATE stb_activation_analysis_tbl SET num= " + map.get(retailerName) + "  WHERE retailer_name = '" + retailerName + "' AND record_type="+ RECORD_TYPE_MONTH  +" AND retailer_type = "+ RETAILER_TYPE_SINGLE +" AND record_time= '"+ recordTime +"'");
//					db.addBatch("INSERT INTO stb_activation_analysis_tbl(retailer_name,num,record_type,retailer_type,record_time) values('" + retailerName + "'," + map.get(retailerName) + ", "+ RECORD_TYPE_MONTH  +","+ RETAILER_TYPE_SINGLE +",'"+ recordTime +"')");
//						db.addBatch("UPDATE tml_base_en SET login_time = '" + dto.getOnlineTime()	+ "', status = " + 1 + ", tml_sn = '" + dto.getTmlSN() + "' WHERE tml_id = '" + dto.getTmlId() + "'");
//						db.addBatch("UPDATE tml_base_thai SET login_time = '" + dto.getOnlineTime()	+ "', status = " + 1 + ", tml_sn = '" + dto.getTmlSN() + "' WHERE tml_id = '" + dto.getTmlId() + "'");
					if(i>50) {
						db.executeBatch();
						i = 0;
						log.info("execute once");
					} else {
						i++;
					}
				}
				db.executeBatch();
				db.commit();
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}finally {
			db.close();
		}
		
	}
	public String getHeartBeatRateByTmlId(String tmlId) {
		// TODO Auto-generated method stub
		log.info("getHeartBeatRateByTmlId:" + tmlId );
		String heartbeatRate="";
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT * FROM tml_conf f LEFT JOIN tml_base b ON f.groupId=b.group_id WHERE b.tml_id= '"+tmlId+"'";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					heartbeatRate=rs.getString("heartbeatRate");
				}
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if (rs !=null) {
					rs.close();
					}
				} catch (SQLException e) {
					log.error(e, e);
				}
				db.close();
			}		
	
		return heartbeatRate;
	}

//	@Override

	public TmlHistory getTmlHistoryByConf(String tmlId, String type) {
		TmlHistory tmlHistory = new TmlHistory();
		log.debug("************根据终端Id tmlId:"+tmlId+"和终端上线离线类型type:"+type+"查询最新的终端上线历史数据*************");
		db = new DBCommon();
		ResultSet rs = null;
			try {
				String sql = "SELECT * FROM tml_history WHERE tml_id='"+tmlId+"'";
				if(!StringUtils.isEmpty(type)){
					sql+=" AND type='"+type+"'";
				}
				sql+=" ORDER BY nftime DESC LIMIT 1";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					tmlHistory.setId(Integer.valueOf(rs.getString("id")));
					tmlHistory.setNftime(rs.getString("nftime"));
					tmlHistory.setType(rs.getString("type"));
					tmlHistory.setTmlId(rs.getString("tml_id"));
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return tmlHistory;
	}
	public List<TmlHistory> findTmlHistory(String tmlId, String type) {
		
		log.debug("************根据终端Id tmlId:"+tmlId+"和终端上线离线类型type:"+type+"查询最新的终端上线历史数据*************");
		db = new DBCommon();
		List<TmlHistory> tmlHistoryList=new ArrayList<TmlHistory>();
		ResultSet rs = null;
			try {
				String sql = "SELECT * FROM tml_history WHERE tml_id='"+tmlId+"'";
				if(!StringUtils.isEmpty(type)){
					sql+=" AND type='"+type+"'";
				}
				sql+=" ORDER BY nftime DESC LIMIT 2";
				rs = db.executeQuery(sql);
				while (rs.next()) {
					TmlHistory tmlHistory = new TmlHistory();
					tmlHistory.setId(Integer.valueOf(rs.getString("id")));
					tmlHistory.setNftime(rs.getString("nftime"));
					tmlHistory.setType(rs.getString("type"));
					tmlHistory.setTmlId(rs.getString("tml_id"));
					tmlHistoryList.add(tmlHistory);
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return tmlHistoryList;
	}
	

	//批量更新终端id的业务日志数据
	public boolean batchUpdateAppInfosLastesMonths(List<String> tmlIds, String selectType,String updateType) {

		log.debug("********批量更新终端id的业务日志数据****************8");
		String sql = "UPDATE APP_INFOS_LASTEST_MONTH SET SSTATUS =?,END_TIME=?,DDURATION=ABS(TIMESTAMPDIFF(SECOND,?,START_TIME)*1000) WHERE STB_ID = ? AND SSTATUS=?";
		PreparedStatement stmt = null;
		Connection conn= null;
		db = new DBCommon();
		ResultSet rs = null;
		boolean batchSuccessFlag=false;
		String currentTime=DateUtils.getCurrentTime();
		try {
			if(tmlIds!=null&&tmlIds.size()>0){
				conn = db.getConnectionJdbc(); 
				stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				for(int x = 0; x < tmlIds.size();x++){  
					stmt.setString(1,updateType);
					stmt.setString(2,currentTime);
					stmt.setString(3,currentTime);
					stmt.setString(4,tmlIds.get(x));
					stmt.setString(5,selectType);
					stmt.addBatch();   
				}
				int[] executeBatch = stmt.executeBatch(); 
				conn.commit();
				System.out.println(executeBatch.length);
				batchSuccessFlag=true;
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			batchSuccessFlag=false;
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	
		return batchSuccessFlag;
	
	}
	
	
//	@Override

	public List<String> getAllTmlIds() {
		log.info("************getAllTmlIds查询所有的终端Id*************");
		db = new DBCommon();
		List<String> tmlIds=new ArrayList<String>();
		ResultSet rs = null;
		try {
			String sql = "SELECT DISTINCT tml_id FROM tml_base";
			rs = db.executeQuery(sql);
			while (rs.next()) {
				tmlIds.add(rs.getString("tml_id"));
			}
			
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			db.close();
		}		
		return tmlIds;
	}

//	@Override

	public List<String> getAllOnlineTerminal() {
		log.info("*******************get online terminals****************");		
		List<String> onlineTmlIds = new ArrayList<String>();
		db = new DBCommon();
		ResultSet rs = null;
		try {
			String sql = "SELECT DISTINCT tml_id FROM tml_base WHERE status = 1";
			rs = db.executeQuery(sql);
			while (rs.next()) {
				onlineTmlIds.add(rs.getString("tml_id"));
			}
		} catch (Exception e) {
			log.error(e, e);
			e.printStackTrace();
		} finally {
			
			try {
				if (rs !=null) {
					rs.close();
				}
			} catch (SQLException e) {
				log.error(e, e);
			}
			
			db.close();
		}
		log.info("在线终端的onlineTmlIds: " + onlineTmlIds);
		return onlineTmlIds;
	}
	public boolean batchLogout(List<String> tmlIds) {
		log.debug("********批量更新终端状态****************8");
		String sql = "UPDATE tml_base SET status =?, content_name='' WHERE tml_id = ?";
		PreparedStatement stmt = null;
		Connection conn= null;
		db = new DBCommon();
		ResultSet rs = null;
		boolean batchSuccessFlag=false;
		try {
			if(tmlIds!=null&&tmlIds.size()>0){
				conn = db.getConnectionJdbc(); 
				stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				for(int x = 0; x < tmlIds.size();x++){  
					stmt.setInt(1, 0);
					stmt.setString(2,tmlIds.get(x));
					stmt.addBatch();   
				}
				int[] executeBatch = stmt.executeBatch(); 
				conn.commit();
				batchSuccessFlag=true;
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			batchSuccessFlag=false;
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	
		return batchSuccessFlag;
	}
	public List<TmlHistory> findTmlHistory(String tmlId, String type,
			String startTime,String endTime, String order, int count) {

		
//		log.debug("************根据终端Id tmlId:"+tmlId+"和终端上线离线类型type:"+type+"查询最新的终端上线历史数据*************");
		db = new DBCommon();
		List<TmlHistory> tmlHistoryList=new ArrayList<TmlHistory>();
		ResultSet rs = null;
			try {
				StringBuffer sql=new StringBuffer("SELECT * FROM tml_history WHERE 1=1");
//				String sql = "SELECT * FROM tml_history WHERE tml_id='"+tmlId+"'";
				if(!StringUtils.isEmpty(tmlId)){
					sql.append(" AND tml_id='");
					sql.append(tmlId);
					sql.append("'");
				}
				if(!StringUtils.isEmpty(type)){
					sql.append(" AND type='");
					sql.append(type);
					sql.append("'");
				}
				if(!StringUtils.isEmpty(startTime)){
					sql.append(" AND nftime>'");
					sql.append(startTime);
					sql.append("'");
				}
				if(!StringUtils.isEmpty(endTime)){
					sql.append(" AND nftime<'");
					sql.append(endTime);
					sql.append("'");
				}
				if(!StringUtils.isEmpty(order)){
					sql.append(" ORDER BY nftime ");
					sql.append(order);
				}
				if(count!=0){
					sql.append(" LIMIT ");
					sql.append(count);
				}
				rs = db.executeQuery(sql.toString());
				while (rs.next()) {
					TmlHistory tmlHistory = new TmlHistory();
					tmlHistory.setId(Integer.valueOf(rs.getString("id")));
					tmlHistory.setNftime(rs.getString("nftime"));
					tmlHistory.setType(rs.getString("type"));
					tmlHistory.setTmlId(rs.getString("tml_id"));
					tmlHistoryList.add(tmlHistory);
				}
				
			} catch (Exception e) {
				log.error(e, e);
			} finally {
				try {
					if(rs != null)
						rs.close();
				} catch (SQLException ex) {
					log.error(ex.getMessage(), ex);
				}
				db.close();
			}		
		return tmlHistoryList;
	}
	public Boolean batchSaveTmlHisoty(List<String> tmlIds,
			String nfTime, String Type) {
		log.debug("——————批量插入终端历史数据--------");

		String sql = "insert into tml_history (tml_id,nftime,type) values (?,?,?)";
		PreparedStatement stmt = null;
		Connection conn= null;
		db = new DBCommon();
		ResultSet rs = null;
		boolean batchSuccessFlag=false;
		try {
			if(tmlIds!=null&&tmlIds.size()>0){
				conn = db.getConnectionJdbc(); 
				stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				for(int x = 0; x < tmlIds.size();x++){  
					stmt.setString(1,tmlIds.get(x));
					stmt.setString(2,nfTime);
					stmt.setString(3,Type);
					stmt.addBatch();   
				}
				int[] executeBatch = stmt.executeBatch(); 
				System.out.println(executeBatch.length);
				batchSuccessFlag=true;
			}
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			batchSuccessFlag=false;
		}finally {
			try {
				if(rs != null)
					rs.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	
		return batchSuccessFlag;
	}
}
