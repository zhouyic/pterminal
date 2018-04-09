package communicate.pkmgmt.stb;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.DBCommon;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVPairList;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.dto.ConfigItem;
import communicate.pkmgmt.dto.List_Server;
import communicate.pkmgmt.tmljdbc.TmlConst;

public class AppPortalURLRequest {

	private static Logger log = communicate.common.Logger.getLogger(AppPortalURLRequest.class);

	/**
	 * 解析收到的数据包
	 * @param leftBuf
	 * @param len
	 * @param offset
	 * @param sendBuf
	 * @return
	 */
	public byte[] parseData(byte[] leftBuf, int len, int offset) {
		TLVPairList tlvs = new TLVPairList();
		try {
			tlvs.loadTLVs(leftBuf, offset, len-offset);
		} catch (Exception e) {
			log.error(e, e);
		}

		String tmlId = tlvs.getStringValue(TLVTag.TML_ID);
		int tmlType = tlvs.getIntValue(TLVTag.TML_Type);
		log.info("------STB: AppPortal-URL/FTP pk!\n------tmlId="+tmlId+", tmlType="+tmlType);

		// 需要查找AppPortal URL
		List<ConfigItem> list = null;
		//list = getURL();
		list = getURLBytmlId(tmlId);

		return newCreateSendBuf(list);
	}

	public byte[] createSendBuf(List<List_Server> list) {
		int offset = 0;

		byte[] cnt_b = new byte[list.size()*64];
		int cLen = 0;
		if (list != null && list.size() != 0) {
			for (List_Server dto : list) {
				String url = dto.getIp();
				int tag = dto.getTag();

				if (url != null || url.trim().length() > 0) {
					byte[] url_b = url.getBytes();
					TLVPair urlTLV = null;
					if (tag == TmlConst.serverType.AppPortal_URL) {
						urlTLV = new TLVPair(TLVTag.AppPortal_URL, url_b);
					}
					if (tag == TmlConst.serverType.APP_FTP_URL) {
						urlTLV = new TLVPair(TLVTag.FTP_URL, url_b);
					}

					byte[] b = urlTLV.getTLV();
					System.arraycopy(b, 0, cnt_b, cLen, b.length);
					cLen += b.length;
				}
			}
		}

		int allLen = PackageConstant.MIN_PACKAGE_LEN + cLen;
		byte[] sendBuf = new byte[allLen];

		long pkId = System.currentTimeMillis()/1000;

		offset += 16;
		sendBuf[offset++] = (byte)((PackageConstant.code.AppPortalURL_request_ACK >> 8) & 0xff);
		sendBuf[offset++] = (byte)(PackageConstant.code.AppPortalURL_request_ACK & 0xff);
		sendBuf[offset++] = (byte)((pkId >> 8) & 0xff);
		sendBuf[offset++] = (byte)(pkId & 0xff);

		sendBuf[offset++] = (byte)((allLen >> 8) & 0xff);
		sendBuf[offset++] = (byte)(allLen & 0xff);

		if (cLen != 0) {
			System.arraycopy(cnt_b, 0, sendBuf, offset, cLen);
			offset += cLen;
		}
		//log.debug("offset="+offset+"; buf="+StringUtil.byteArrayToHexString(sendBuf));

		return sendBuf;
	}

	public byte[] newCreateSendBuf(List<ConfigItem> list) {
		int offset = 0;
		//log.info("发送信息为："+list.toString());
		byte[] cnt_b = new byte[list.size()*64];
		int cLen = 0;
		if (list != null && list.size() != 0) {
			for (ConfigItem dto : list) {
				String url = dto.getIp();
				int tag = dto.getTag();

				if (url != null || url.trim().length() > 0) {
					byte[] url_b = url.getBytes();
					TLVPair urlTLV = null;
					if (tag == TLVTag.AppPortal_URL) {
						urlTLV = new TLVPair(TLVTag.AppPortal_URL, url_b);
					}
					if (tag == TLVTag.FTP_URL) {
						urlTLV = new TLVPair(TLVTag.FTP_URL, url_b);
					}

					byte[] b = urlTLV.getTLV();
					System.arraycopy(b, 0, cnt_b, cLen, b.length);
					cLen += b.length;
				}
			}
		}

		int allLen = PackageConstant.MIN_PACKAGE_LEN + cLen;
		byte[] sendBuf = new byte[allLen];

		long pkId = System.currentTimeMillis()/1000;

		offset += 16;
		sendBuf[offset++] = (byte)((PackageConstant.code.AppPortalURL_request_ACK >> 8) & 0xff);
		sendBuf[offset++] = (byte)(PackageConstant.code.AppPortalURL_request_ACK & 0xff);
		sendBuf[offset++] = (byte)((pkId >> 8) & 0xff);
		sendBuf[offset++] = (byte)(pkId & 0xff);

		sendBuf[offset++] = (byte)((allLen >> 8) & 0xff);
		sendBuf[offset++] = (byte)(allLen & 0xff);

		if (cLen != 0) {
			System.arraycopy(cnt_b, 0, sendBuf, offset, cLen);
			offset += cLen;
		}
		//log.debug("offset="+offset+"; buf="+StringUtil.byteArrayToHexString(sendBuf));
		//log.info("发出字节流："+StringUtil.byteArrayToHexString(sendBuf));
		return sendBuf;
	}
	/**
	 * 得到AppPortal URL
	 * @return list
	 */
	private List<List_Server> getURL() {
		List<List_Server> list = new ArrayList<List_Server>();
		List_Server so = null;

//返回固定表
		
		
		
		DBCommon db = null;
		ResultSet rs = null;
		String querySQL = "SELECT * FROM TML_SERVER_LIST WHERE tag IN (?,?)";
		try {
			db = new DBCommon(querySQL);
			db.setInt(1, TmlConst.serverType.AppPortal_URL);
			db.setInt(2, TmlConst.serverType.APP_FTP_URL);

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				so = new List_Server();
				so.setIp(rs.getString("ip"));
				so.setPort(rs.getInt("port"));
				so.setTag(rs.getInt("tag"));
				list.add(so);
			}
		} catch (Exception e) {
			log.error(this,e);
		} finally {
			db.close();
		}

		rs = null;

		return list;
	}
	
	
	public List<ConfigItem> getURLBytmlId(String tmlId) {
		List<ConfigItem> list = new ArrayList<ConfigItem>();
		ConfigItem so = null;
		DBCommon db = null;
		ResultSet rs = null;
		String sql = "SELECT TML_BASE.groupId FROM TML_BASE,SYS_DOM WHERE TML_BASE.groupId=SYS_DOM.DOMID AND TML_BASE.tmlId='"+tmlId+"'";
		int groupId = -3;
		try{
			db = new DBCommon(sql);
			rs = db.executeQuery();
			if(rs.next())
				groupId = rs.getInt("TML_BASE.groupId");
		}catch(Exception e){
			log.error(this,e);
		}finally{
			db.close();
		}
		if(groupId == -3)
		{
			log.info("没有对应分组");
			sql = "SELECT * FROM CONF_ITEM WHERE confTag="+TLVTag.AppPortal_URL+" OR confTag="+TLVTag.FTP_URL+" AND confName='APP_FTP_URL'";
			try{
				db = new DBCommon(sql);
				rs = db.executeQuery();
				while(rs.next())
				{
					so = new ConfigItem();
					so.setName(rs.getString("confName"));
					so.setIp(rs.getString("IP"));
					so.setPort(rs.getString("Port"));
					so.setTag(rs.getInt("confTag"));
					so.setName(rs.getString("confName"));
					list.add(so);
				}
			}catch(Exception e){
				log.error(this,e);
			}finally{
				db.close();
			}
		}
		else{
			log.info("有对应分组");
		String querySQL = "SELECT * FROM DOM_CONF c,CONF_ITEM d WHERE c.confItemId=d.confItemId AND c.DOMID="+groupId+" AND ( d.confTag="+TLVTag.AppPortal_URL+" OR ( confTag="+TLVTag.FTP_URL+" AND confName='APP_FTP_URL' ))";
		try {
			db = new DBCommon(querySQL);
			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				so = new ConfigItem();
				so.setIp(rs.getString("c.IP"));
				so.setPort(rs.getString("c.Port"));
				so.setTag(rs.getInt("d.confTag"));
				so.setName(rs.getString("d.confName"));
				list.add(so);
			}
		} catch (Exception e) {
			//Subagentx.submitOID(TmsOID.oid_tms_database);
			log.error(this,e);
		} finally {
			db.close();
		}
		}

		rs = null;
		log.info("得到的URL为："+list.toString());
		return list;
	}

	public static void main(String[] a) {
		AppPortalURLRequest l = new AppPortalURLRequest();
		byte[] sss = new byte[1024];
		log.debug("list="+l.getURL());
		//l.createSendBuf(list, sss);
	}
}
