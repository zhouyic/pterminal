package communicate.pkmgmt.tmljdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import communicate.common.DBCommon;
import communicate.pkmgmt.dto.ConfigItem;

public class TmlConfJDBCImpl implements ITmlConfJDBC {
	private static final Logger log = communicate.common.Logger.getLogger(ITmlConfJDBC.class);
	public ArrayList<ConfigItem> getAllConfigItemsBytmlId(String tmlId) {
		
		DBCommon db = null;
		String sql = "SELECT TML_BASE.groupId FROM TML_BASE,SYS_DOM where tmlId='"+tmlId+"' AND TML_BASE.groupId=SYS_DOM.DOMID";
		ConfigItem temp;
		ResultSet rs = null;
		ArrayList<ConfigItem> res= new ArrayList<ConfigItem>();
	
		int groupId = -3;

		try {
			db = new DBCommon(sql);
			rs = db.executeQuery();
			if (rs.next()) {
				groupId = rs.getInt("groupId");
			}
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			db.close();
			db = null;
		}
		if (groupId == -3)
			{
			log.info("没有找到分组，将分配默认配置");
			sql = "SELECT * FROM CONF_ITEM";
			rs = null;
			try{
				db = new DBCommon(sql);
				rs = db.executeQuery();
				while(rs.next())
				{
					temp = new ConfigItem();
					temp.setConfItemId(rs.getInt("confItemId"));
					temp.setDesc(rs.getString("confDesc"));
					temp.setIp(rs.getString("IP"));
					temp.setName(rs.getString("confName"));
					temp.setPort(rs.getString("Port"));
					temp.setTag(rs.getInt("confTag"));
					temp.setType(rs.getInt("confType"));
					if(!(temp.getIp() == null || temp.getIp().length() == 0))
						res.add(temp);
				}
			}catch (SQLException e) {
				log.error(e.getMessage());
			} finally {
				db.close();
				db = null;
			}
			}
		else
			{
			log.info("找到分组，将分配配置");
			sql = "SELECT b.IP,b.Port,c.confName,c.confType,c.confDesc,c.confItemId,c.confTag FROM TML_BASE a,DOM_CONF b,CONF_ITEM c WHERE a.tmlId='"+tmlId+"' AND a.groupId=b.DOMID AND b.confItemId=c.confItemId";
			rs = null;
			try{
				db = new DBCommon(sql);
				rs = db.executeQuery();
				while(rs.next())
				{
					temp = new ConfigItem();
					temp.setConfItemId(rs.getInt("c.confItemId"));
					temp.setDesc(rs.getString("c.confDesc"));
					temp.setIp(rs.getString("b.IP"));
					temp.setName(rs.getString("c.confName"));
					temp.setPort(rs.getString("b.Port"));
					temp.setTag(rs.getInt("c.confTag"));
					temp.setType(rs.getInt("c.confType"));
					//log.info("查找出来一项："+temp.toString());
					if(temp.getIp()== null||temp.getIp().trim().equals(""))
						;//log.info("查找出来空项："+temp.toString());
					else
						res.add(temp);
					
				}
			}catch (SQLException e) {
				log.error(e.getMessage());
			} finally {
				db.close();
				db = null;
			}
			}
		return res;
	}
	

}
