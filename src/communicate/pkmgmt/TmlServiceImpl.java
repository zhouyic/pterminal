package communicate.pkmgmt;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import communicate.common.DBCommon;
import communicate.common.DBCommon_cms2;
import communicate.common.MD5;
import communicate.common.MemCached;
import communicate.common.tlv.TLVPair;
import communicate.common.tlv.TLVTag;
import communicate.pkmgmt.dto.List_Server;
import communicate.pkmgmt.dto.Order;
import communicate.pkmgmt.dto.StatusDTO;
import communicate.pkmgmt.tmljdbc.TmlConst;

public class TmlServiceImpl implements ITmlService {
	private static final Logger log = communicate.common.Logger.getLogger(TmlServiceImpl.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	//private int authenticator = 0;
	private int header_len = 22;
	private DBCommon_cms2 dbcms = null;
	private MemCached memc = MemCached.getInstance();
	private MD5 MD5 = new MD5();

	/**
	 * @description 数据包头
	 * @param code
	 * @param len
	 * @return headerByte
	 */
	public byte[] createByte(int code, int len) {
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
	}

	private Order getOrder(String orderId) {
		Order o = null;

		DBCommon db = null;
		ResultSet rs = null;
		String querySQL = "SELECT * FROM TML_ORDER WHERE orderId=?";
		try {
			db = new DBCommon(querySQL);
			db.setString(1, orderId);

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				o = new Order();

				o.setServiceType(rs.getInt("serviceType"));
				o.setEpisodeIndex(rs.getInt("episodeIndex"));
				o.setEpisodes(rs.getInt("episodes"));
				o.setParentName(rs.getString("parentName"));
				o.setContentName(rs.getString("contentName"));
				o.setMovieName(rs.getString("movieName"));
				o.setShowTime(rs.getString("showTime"));
				o.setListName(rs.getString("listName"));
				o.setCXMLName(rs.getString("CXMLName"));
				o.setLicenseName(rs.getString("licenseName"));
				o.setSnap(rs.getString("snap"));
				o.setMovieSize(rs.getLong("movieSize"));
				o.setTarFileName(rs.getString("tarFileName"));
				o.setIdxFileName(rs.getString("idxFileName"));
				o.setRunTime(rs.getInt("runTime"));
				o.setActors(rs.getString("actors"));
				o.setLauguage(rs.getString("pLauguage"));
				o.setScreenFormat(rs.getString("screenFormat"));
				o.setDirector(rs.getString("directors"));
				o.setCountry(rs.getString("country"));
				o.setDesc(rs.getString("description"));
				o.setProgramId(rs.getString("programId"));
				o.setContentId(rs.getString("contentId"));
				
				o.setMovieCategory(rs.getString("movieCategory"));
				o.setArtistPinyin(rs.getString("artistPinyin"));
				o.setCaption(rs.getString("caption"));
				o.setSingleSnap(rs.getString("singleSnap"));
				o.setSingleDesc(rs.getString("singleDesc"));
				o.setCaptionFileName(rs.getString("captionFileName"));
				o.setTvHoriSnap(rs.getString("tvHoriSnap"));
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			rs = null;
		}

		return o;
	}

	/**
	 * @description 向DRM请求许可证文件
	 */
	public byte[] createLRBufToDRM(String tmlId, String contendId, int count, String startTime, String endTime) {

		// tmlId TLV
		byte[] tId = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tId);
		byte[] tmlId_b = tmlIdTLV.getTLV();

		// contendId TLV
		byte[] cId = contendId.getBytes();
		TLVPair cIdTLV = new TLVPair(TLVTag.Content_ID, cId);
		byte[] cId_b = cIdTLV.getTLV();

		// count TLV
		byte[] abytes = new byte[4];
		abytes[0] = (byte)((count >> 24) & 0xff);
		abytes[1] = (byte)((count >> 16) & 0xff);
		abytes[2] = (byte)((count >> 8) & 0xff);
		abytes[3] = (byte)(count & 0xff);
		TLVPair countTLV = new TLVPair(TLVTag.Count, abytes);
		byte[] count_b = countTLV.getTLV();

		// startTime TLV
		byte[] start = startTime.getBytes();
		TLVPair startTLV = new TLVPair(TLVTag.StartTime, start);
		byte[] start_b = startTLV.getTLV();

		// endTime TLV
		byte[] end = endTime.getBytes();
		TLVPair endTLV = new TLVPair(TLVTag.endTime, end);
		byte[] end_b = endTLV.getTLV();

		int offset = 0;
		int allLen = header_len 
		+ tmlId_b.length 
		+ cId_b.length 
		+ count_b.length 
		+ start_b.length 
		+ end_b.length;
		byte[] toDRM = createByte(PackageConstant.code.tml_drm_LR, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toDRM, offset, tmlId_b.length);
		offset = offset + tmlId_b.length;

		System.arraycopy(cId_b, 0, toDRM, offset, cId_b.length);
		offset = offset + cId_b.length;

		System.arraycopy(count_b, 0, toDRM, offset, count_b.length);
		offset = offset + count_b.length;

		System.arraycopy(start_b, 0, toDRM, offset, start_b.length);
		offset = offset + start_b.length;

		System.arraycopy(end_b, 0, toDRM, offset, end_b.length);
		offset = offset + end_b.length;

		return toDRM;
	}

	/**
	 * @description 回复STBID，验证码，Result-Code=0，成功；其他，失败。
	 */
	public byte[] createResultCodeBuf(int code, String tmlId, int resultCode) {
		// tmlId TLV
		byte[] tmlIdbyte = tmlId.getBytes();
		TLVPair tmlIdTLV = new TLVPair(TLVTag.TML_ID, tmlIdbyte);
		byte[] tmlId_b = tmlIdTLV.getTLV();
		int tmlId_b_len = tmlId_b.length;

		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();
		int result_b_len = result_b.length;

		int offset = 0;
		int allLen = header_len + tmlId_b_len + result_b_len;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(tmlId_b, 0, toBuf, offset, tmlId_b_len);
		offset = offset + tmlId_b_len;

		System.arraycopy(result_b, 0, toBuf, offset, result_b_len);
		offset = offset + result_b_len;
		return toBuf;
	}

	/**
	 * @description 回复验证码，Result-Code=0，成功；其他，失败。
	 */
	public byte[] createResultCodeBuf(int code, int resultCode) {
		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();

		int offset = 0;
		int allLen = header_len + result_b.length;

		byte[] toBuf = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(result_b, 0, toBuf, offset, result_b.length);
		offset = offset + result_b.length;
		return toBuf;
	}

	/**
	 * @description 回复SPE许可证文件，Result-Code=0，请求成功
	 */
	public byte[] createLCBufToSPE(int resultCode, byte[] license) {
		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();

		// license TLV
		TLVPair licenseTLV = new TLVPair(TLVTag.License, license);
		byte[] license_b = licenseTLV.getTLV();

		int offset = 0;
		int allLen = header_len + result_b.length + license_b.length;

		byte[] toSPE = createByte(PackageConstant.code.spe_tml_LR_Ack, allLen);

		offset += header_len;

		System.arraycopy(result_b, 0, toSPE, offset, result_b.length);
		offset = offset + result_b.length;

		System.arraycopy(license_b, 0, toSPE, offset, license_b.length);
		offset = offset + license_b.length;
		return toSPE;
	}

	/**
	 * @description 回复SPE验证终端合法性请求，Result-Code=0，合法终端；Result-Code=其它，非法终端
	 */
	public byte[] creatCertifyACKToSPE(int resultCode) {
		// result-code TLV
		byte[] result = new byte[4];
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();

		int offset = 0;
		int allLen = header_len + result_b.length;

		byte[] toSPE = createByte(PackageConstant.code.spe_tml_certify_Ack, allLen);

		offset += header_len;

		System.arraycopy(result_b, 0, toSPE, offset, result_b.length);
		offset = offset + result_b.length;

		return toSPE;
	}

	/**
	 * @description 回复SPE订购单请求
	 */
	public byte[] createOrderBufToSPE(String orderId, int code) {
		OrderMapMemory map = OrderMapMemory.getInstance();
		Order order = map.getReqOrder(orderId);
		log.debug("sendOrderToSPE: order = "+order);

		if (order == null) {
			order = getOrder(orderId);
			log.debug("sendOrderToSPE from DB: order = "+order);
		}

		int offset = 0;
		int allLen = 0;

		// orderId TLV
		byte[] orderIdbyte = orderId.getBytes();
		TLVPair orderIdTLV = new TLVPair(TLVTag.OrderId, orderIdbyte);
		byte[] orderId_b = orderIdTLV.getTLV();
		int orderId_b_len = orderId_b.length;

		int resultCode = PackageConstant.result_code.ok;
		byte[] result = new byte[4];

		if (order == null) {
			log.debug("sendOrderToSPE: order null!");
			resultCode = PackageConstant.result_code.ubknown_Order;
			result[0] = (byte)((resultCode >> 24) & 0xff);
			result[1] = (byte)((resultCode >> 16) & 0xff);
			result[2] = (byte)((resultCode >> 8) & 0xff);
			result[3] = (byte)(resultCode & 0xff);
			TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
			byte[] result_b = resultTLV.getTLV();

			allLen = header_len + result_b.length+orderId_b_len;

			byte[] toSPE = createByte(PackageConstant.code.spe_tml_order_Ack, allLen);

			offset += header_len;

			System.arraycopy(orderId_b, 0, toSPE, offset, orderId_b_len);
			offset = offset + orderId_b_len;

			System.arraycopy(result_b, 0, toSPE, offset, result_b.length);
			offset = offset + result_b.length;

			return toSPE;
		}

		String contentName = order.getContentName();
		String movieName = order.getMovieName();
		String listName = order.getListName();
		String CXMLName = order.getCXMLName();
		String lisenceName = order.getLicenseName();
		String captionFileName = order.getCaptionFileName();
		String snap = order.getSnap();
		long movieSize = order.getMovieSize();

		int serviceType = order.getServiceType();
		String parentName = order.getParentName();

		String programId = order.getProgramId();

		String showTime = order.getShowTime();
		String tarFileName = order.getTarFileName();
		String idxFileName = order.getIdxFileName();
		int episodeIndex = order.getEpisodeIndex();
		int episodes = order.getEpisodes();
		int runTime = order.getRunTime();

		if (contentName == null) contentName = "";
		if (movieName == null) movieName = "";
		if (listName == null) listName = "";
		if (CXMLName == null) CXMLName = "";
		if (lisenceName == null) lisenceName = "";
		if (snap == null) snap = "";
		if (parentName == null) parentName = "";
		if (showTime == null) showTime = "";
		if (tarFileName == null) tarFileName = "";
		if (idxFileName == null) idxFileName = "";
		if (captionFileName == null) captionFileName = "";

		List<String> movieCategory = new ArrayList<String>();
		if(serviceType == PackageConstant.service_Type.music_single 
				|| serviceType == PackageConstant.service_Type.music_album) {
			movieCategory = getMusicCategory(order.getProgramId());
		} else {
			movieCategory = getMovieCategory(order.getContentId());
		}

		// Result-Code
		result[0] = (byte)((resultCode >> 24) & 0xff);
		result[1] = (byte)((resultCode >> 16) & 0xff);
		result[2] = (byte)((resultCode >> 8) & 0xff);
		result[3] = (byte)(resultCode & 0xff);
		TLVPair resultTLV = new TLVPair(TLVTag.Result_Code, result);
		byte[] result_b = resultTLV.getTLV();
		int result_b_len = result_b.length;

		// serviceType TLV
		byte[] sbytes = new byte[4];
		sbytes[0] = (byte)((serviceType >> 24) & 0xff);
		sbytes[1] = (byte)((serviceType >> 16) & 0xff);
		sbytes[2] = (byte)((serviceType >> 8) & 0xff);
		sbytes[3] = (byte)(serviceType & 0xff);
		TLVPair serviceTypeTLV = new TLVPair(TLVTag.Service_Type, sbytes);
		byte[] serviceType_b = serviceTypeTLV.getTLV();
		int serviceType_b_len = serviceType_b.length;

		// episodeIndex TLV
		byte[] indexbytes = new byte[4];
		indexbytes[0] = (byte)((episodeIndex >> 24) & 0xff);
		indexbytes[1] = (byte)((episodeIndex >> 16) & 0xff);
		indexbytes[2] = (byte)((episodeIndex >> 8) & 0xff);
		indexbytes[3] = (byte)(episodeIndex & 0xff);
		TLVPair episodeIndexTLV = new TLVPair(TLVTag.episodeIndex, indexbytes);
		byte[] episodeIndex_b = episodeIndexTLV.getTLV();
		int episodeIndex_b_len = episodeIndex_b.length;

		// episodes TLV
		byte[] ebytes = new byte[4];
		ebytes[0] = (byte)((episodes >> 24) & 0xff);
		ebytes[1] = (byte)((episodes >> 16) & 0xff);
		ebytes[2] = (byte)((episodes >> 8) & 0xff);
		ebytes[3] = (byte)(episodes & 0xff);
		TLVPair episodesTLV = new TLVPair(TLVTag.episodes, ebytes);
		byte[] episodes_b = episodesTLV.getTLV();
		int episodes_b_len = episodes_b.length;

		// parentName TLV
		byte[] pName = null;
		try {
			pName = parentName.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		int pName_b_len = 0;
		byte[] pName_b = null;
		if (pName != null) {
			TLVPair pNameTLV = new TLVPair(TLVTag.Parent_Name, pName);
			pName_b = pNameTLV.getTLV();
			pName_b_len = pName_b.length;
		}

		// contentName TLV
		byte[] cName = contentName.getBytes();
		TLVPair cNameTLV = new TLVPair(TLVTag.Content_Name, cName);
		byte[] cName_b = cNameTLV.getTLV();
		int cName_b_len = cName_b.length;

		// movieName TLV
		byte[] mName = null;
		try {
			mName = movieName.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		int mName_b_len = 0;
		byte[] mName_b = null;
		if (mName != null) {
			TLVPair mNameTLV = new TLVPair(TLVTag.movieName, mName);
			mName_b = mNameTLV.getTLV();
			mName_b_len = mName_b.length;
		}

		// showTime TLV
		byte[] show_Time = showTime.getBytes();
		TLVPair showTimeTLV = new TLVPair(TLVTag.showTime, show_Time);
		byte[] showTime_b = showTimeTLV.getTLV();
		int showTime_b_len = showTime_b.length;

		byte[] listName_b = null;
		int listName_b_len = 0;

		byte[] cxmlName_b = null;
		int cxml_b_len = 0;

		int license_b_len = 0;
		byte[] licenseName_b = null;

		int captionFile_b_len = 0;
		byte[] captionFile_b = null;

		if (serviceType == PackageConstant.service_Type.movie
				|| serviceType == PackageConstant.service_Type.teleplay
				|| serviceType == PackageConstant.service_Type.SPECIAL_MULTI
				|| serviceType == PackageConstant.service_Type.SPECIAL_SINGLE) {
			// listName TLV
			byte[] list_Name = listName.getBytes();
			TLVPair listNameTLV = new TLVPair(TLVTag.listName, list_Name);
			listName_b = listNameTLV.getTLV();
			listName_b_len = listName_b.length;

			// CXMLName TLV
			byte[] cxml_Name = CXMLName.getBytes();
			TLVPair cxmlNameTLV = new TLVPair(TLVTag.CXMLName, cxml_Name);
			cxmlName_b = cxmlNameTLV.getTLV();
			cxml_b_len = cxmlName_b.length;

			// lisenceName TLV
			byte[] license_Name = lisenceName.getBytes();
			TLVPair licenseNameTLV = new TLVPair(TLVTag.licenseName, license_Name);
			licenseName_b = licenseNameTLV.getTLV();
			license_b_len = licenseName_b.length;

			// captionFileName
			byte[] captionFile_Name = captionFileName.getBytes();
			TLVPair captionFileNameTLV = new TLVPair(TLVTag.captionFileName, captionFile_Name);
			captionFile_b = captionFileNameTLV.getTLV();
			captionFile_b_len = captionFile_b.length;
		}

		// tarFileName TLV
		byte[] tarFile = tarFileName.getBytes();
		TLVPair tarFileNameTLV = new TLVPair(TLVTag.tarFileName, tarFile);
		byte[] tarFileName_b = tarFileNameTLV.getTLV();
		int tarFileName_b_len = tarFileName_b.length;

		// snap TLV
		byte[] snap_ = snap.getBytes();
		TLVPair snapTLV = new TLVPair(TLVTag.snap, snap_);
		byte[] snap_b = snapTLV.getTLV();
		int snap_b_len = snap_b.length;

		// movieSize TLV
		byte[] cbytes = new byte[4];
		cbytes[0] = (byte)((movieSize >> 24) & 0xff);
		cbytes[1] = (byte)((movieSize >> 16) & 0xff);
		cbytes[2] = (byte)((movieSize >> 8) & 0xff);
		cbytes[3] = (byte)(movieSize & 0xff);
		TLVPair movieSizeTLV = new TLVPair(TLVTag.movieSize, cbytes);
		byte[] movieSize_b = movieSizeTLV.getTLV();
		int movieSize_b_len = movieSize_b.length;

		// idxFileName TLV
		byte[] idxFile = idxFileName.getBytes();
		TLVPair idxFileNameTLV = new TLVPair(TLVTag.idxFileName, idxFile);
		byte[] idxFileName_b = idxFileNameTLV.getTLV();
		int idxFileName_b_len = idxFileName_b.length;

		// runTime TLV
		byte[] rbytes = new byte[4];
		rbytes[0] = (byte)((runTime >> 24) & 0xff);
		rbytes[1] = (byte)((runTime >> 16) & 0xff);
		rbytes[2] = (byte)((runTime >> 8) & 0xff);
		rbytes[3] = (byte)(runTime & 0xff);
		TLVPair runTimeTLV = new TLVPair(TLVTag.runTime, rbytes);
		byte[] runTime_b = runTimeTLV.getTLV();
		int runTime_b_len = runTime_b.length;

		// programId TLV
		byte[] programIdb = programId.getBytes();
		TLVPair programIdTLV = new TLVPair(TLVTag.programId, programIdb);
		byte[] programId_b = programIdTLV.getTLV();
		int programId_b_len = programId_b.length;

		// movieCategory TLV
		int movieCategory_b_len = 0;
		byte[] movieCategory_b = new byte[1024];
		if (movieCategory != null && movieCategory.size() != 0) {
			for (int i = 0; i <  movieCategory.size(); i++) {
				try {
					byte[] ca = movieCategory.get(i).getBytes("UTF-8");
					TLVPair caTLV = new TLVPair(TLVTag.movieCategory, ca);
					byte[] ca_b = caTLV.getTLV();
					System.arraycopy(ca_b, 0, movieCategory_b, movieCategory_b_len, ca_b.length);
					movieCategory_b_len += ca_b.length;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		String director = order.getDirector();
		String country = order.getCountry();
		String desc = order.getDesc();
		String caption = order.getCaption();
		if (director == null) director="";
		if (country == null) country="";
		if (desc == null) desc="";
		if (caption == null) caption = "";

		// 字幕
		byte[] captioinb = null;
		try {
			captioinb = caption.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		TLVPair caption_bTLV = new TLVPair(TLVTag.Caption, captioinb);
		byte[] caption_b = caption_bTLV.getTLV();
		int caption_b_len = caption_b.length;

		// 导演
		byte[] directorb = null;
		try {
			directorb = director.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		TLVPair director_bTLV = new TLVPair(TLVTag.director, directorb);
		byte[] director_b = director_bTLV.getTLV();
		int director_b_len = director_b.length;

		// 国家/地区
		byte[] countryb = null;
		try {
			countryb = country.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		TLVPair countrybTLV = new TLVPair(TLVTag.country, countryb);
		byte[] country_b = countrybTLV.getTLV();
		int country_b_len = country_b.length;

		// 剧情简介
		byte[] descb = null;
		try {
			descb = desc.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		TLVPair descbTLV = new TLVPair(TLVTag.description, descb);
		byte[] desc_b = descbTLV.getTLV();
		int desc_b_len = desc_b.length;

		String searchName = order.getSearchName();
		String artistPy = order.getArtistPinyin();
		if (searchName == null) searchName="";
		if (artistPy == null) artistPy="";

		// 拼音
		byte[] artistPyb = artistPy.getBytes();
		TLVPair artistPybTLV = new TLVPair(TLVTag.artistPinyin, artistPyb);
		byte[] artistPy_b = artistPybTLV.getTLV();
		int artistPy_b_len = artistPy_b.length;

		// 搜索名
		byte[] searchb = searchName.getBytes();
		TLVPair searchbTLV = new TLVPair(TLVTag.searchName, searchb);
		byte[] search_b = searchbTLV.getTLV();
		int search_b_len = search_b.length;

		allLen = 
			header_len +runTime_b_len+ orderId_b_len+serviceType_b_len + pName_b_len + cName_b_len + mName_b_len
			+result_b_len+ listName_b_len + cxml_b_len + license_b_len + snap_b_len + movieSize_b_len + caption_b_len + captionFile_b_len 
			+ showTime_b_len + tarFileName_b_len + episodes_b_len + movieCategory_b_len + idxFileName_b_len
			+ episodeIndex_b_len + programId_b_len + director_b_len + country_b_len + desc_b_len + artistPy_b_len + search_b_len;

		byte[] add = new byte[256];
		int addOffset = 0;
		// 歌手名，演员
		try {
			String actors = order.getActors();
			if (actors == null) actors = "";
			byte[] actorsb = actors.getBytes("UTF-8");
			TLVPair actorsTLV = new TLVPair(TLVTag.actors, actorsb);
			byte[] actors_b = actorsTLV.getTLV();
			int actors_b_len = actors_b.length;
			System.arraycopy(actors_b, 0, add, addOffset, actors_b_len);
			addOffset += actors_b_len;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 语言种类
		try {
			String laug = order.getLauguage();
			if (laug == null) laug = "";
			byte[] laugb = laug.getBytes("UTF-8");
			TLVPair laugTLV = new TLVPair(TLVTag.lauguage, laugb);
			byte[] laug_b = laugTLV.getTLV();
			int laug_b_len = laug_b.length;
			System.arraycopy(laug_b, 0, add, addOffset, laug_b_len);
			addOffset += laug_b_len;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 歌手分类
		try {
			String scrFor = order.getScreenFormat();
			if (scrFor == null) scrFor = "";
			byte[] scrForb = scrFor.getBytes("UTF-8");
			TLVPair scrForTLV = new TLVPair(TLVTag.screenFormat, scrForb);
			byte[] scrFor_b = scrForTLV.getTLV();
			int scrFor_b_len = scrFor_b.length;
			System.arraycopy(scrFor_b, 0, add, addOffset, scrFor_b_len);
			addOffset += scrFor_b_len;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 单集快照
		String singleSnap = order.getSingleSnap();
		int singleSnap_TLV_len = 0;
		byte[] singleSnap_TLV = null;
		if (singleSnap != null && singleSnap.trim().length() > 0) {
			byte[] ssnapb = singleSnap.getBytes();
			TLVPair ssnapbTLV = new TLVPair(TLVTag.singleSnap, ssnapb);
			singleSnap_TLV = ssnapbTLV.getTLV();
			singleSnap_TLV_len = singleSnap_TLV.length;
		}

		// 单集详情
		String singleDesc = order.getSingleDesc();
		int singleDesc_TLV_len = 0;
		byte[] singleDesc_TLV = null;
		if (singleDesc != null && singleDesc.trim().length() > 0) {
			try {
				byte[] sdescb = singleDesc.getBytes("UTF-8");
				TLVPair sdescbTLV = new TLVPair(TLVTag.singleDesc, sdescb);
				singleDesc_TLV = sdescbTLV.getTLV();
				singleDesc_TLV_len = singleDesc_TLV.length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// HoriSnap
		String horiSnap = order.getTvHoriSnap();
		int horiSnap_TLV_len = 0;
		byte[] horiSnap_TLV = null;
		if (horiSnap != null && horiSnap.trim().length() > 0) {
			byte[] horiSnapb = horiSnap.getBytes();
			TLVPair horiSnapbTLV = new TLVPair(TLVTag.HoriSnap, horiSnapb);
			horiSnap_TLV = horiSnapbTLV.getTLV();
			horiSnap_TLV_len = horiSnap_TLV.length;
		}

		allLen += addOffset;
		allLen += singleSnap_TLV_len;
		allLen += singleDesc_TLV_len;
		allLen += horiSnap_TLV_len;

		byte[] toSPE = createByte(code, allLen);

		offset += header_len;

		System.arraycopy(orderId_b, 0, toSPE, offset, orderId_b_len);
		offset = offset + orderId_b_len;

		System.arraycopy(result_b, 0, toSPE, offset, result_b.length);
		offset = offset + result_b.length;

		System.arraycopy(serviceType_b, 0, toSPE, offset, serviceType_b_len);
		offset = offset + serviceType_b_len;

		System.arraycopy(episodeIndex_b, 0, toSPE, offset, episodeIndex_b_len);
		offset = offset + episodeIndex_b_len;

		System.arraycopy(episodes_b, 0, toSPE, offset, episodes_b_len);
		offset = offset + episodes_b_len;

		if (pName_b_len != 0) {
			System.arraycopy(pName_b, 0, toSPE, offset, pName_b_len);
			offset = offset + pName_b_len;
		}

		System.arraycopy(cName_b, 0, toSPE, offset, cName_b_len);
		offset = offset + cName_b_len;

		if (mName_b_len != 0) {
			System.arraycopy(mName_b, 0, toSPE, offset, mName_b_len);
			offset = offset + mName_b_len;
		}

		System.arraycopy(showTime_b, 0, toSPE, offset, showTime_b_len);
		offset = offset + showTime_b_len;

		if (serviceType == PackageConstant.service_Type.movie
				|| serviceType == PackageConstant.service_Type.teleplay 
				|| serviceType == PackageConstant.service_Type.SPECIAL_MULTI
				|| serviceType == PackageConstant.service_Type.SPECIAL_SINGLE) {
			if (listName_b_len != 0) {
				System.arraycopy(listName_b, 0, toSPE, offset, listName_b_len);
				offset = offset + listName_b_len;
			}

			if (cxml_b_len != 0) {
				System.arraycopy(cxmlName_b, 0, toSPE, offset, cxml_b_len);
				offset = offset + cxml_b_len;
			}

			if (license_b_len != 0) {
				System.arraycopy(licenseName_b, 0, toSPE, offset, license_b_len);
				offset = offset + license_b_len;
			}

			if (captionFile_b_len != 0) {
				System.arraycopy(captionFile_b, 0, toSPE, offset, captionFile_b_len);
				offset = offset + captionFile_b_len;
			}
		}

		System.arraycopy(snap_b, 0, toSPE, offset, snap_b_len);
		offset = offset + snap_b_len;

		System.arraycopy(movieSize_b, 0, toSPE, offset, movieSize_b_len);
		offset = offset + movieSize_b_len;

		if (tarFileName_b_len != 0) {
			System.arraycopy(tarFileName_b, 0, toSPE, offset, tarFileName_b_len);
			offset = offset + tarFileName_b_len;
		}

		System.arraycopy(idxFileName_b, 0, toSPE, offset, idxFileName_b_len);
		offset = offset + idxFileName_b_len;

		System.arraycopy(runTime_b, 0, toSPE, offset, runTime_b_len);
		offset = offset + runTime_b_len;

		System.arraycopy(programId_b, 0, toSPE, offset, programId_b_len);
		offset = offset + programId_b_len;

		if (movieCategory_b_len != 0) {
			System.arraycopy(movieCategory_b, 0, toSPE, offset, movieCategory_b_len);
			offset = offset + movieCategory_b_len;
		}

		if (addOffset != 0) {
			System.arraycopy(add, 0, toSPE, offset, addOffset);
			offset = offset + addOffset;
		}

		System.arraycopy(director_b, 0, toSPE, offset, director_b_len);
		offset = offset + director_b_len;

		System.arraycopy(country_b, 0, toSPE, offset, country_b_len);
		offset = offset + country_b_len;

		System.arraycopy(desc_b, 0, toSPE, offset, desc_b_len);
		offset = offset + desc_b_len;

		System.arraycopy(artistPy_b, 0, toSPE, offset, artistPy_b_len);
		offset = offset + artistPy_b_len;

		System.arraycopy(search_b, 0, toSPE, offset, search_b_len);
		offset = offset + search_b_len;

		System.arraycopy(caption_b, 0, toSPE, offset, caption_b_len);
		offset = offset + caption_b_len;

		if (singleSnap_TLV_len != 0) {
			System.arraycopy(singleSnap_TLV, 0, toSPE, offset, singleSnap_TLV_len);
			offset = offset + singleSnap_TLV_len;
		}

		if (singleDesc_TLV_len != 0) {
			System.arraycopy(singleDesc_TLV, 0, toSPE, offset, singleDesc_TLV_len);
			offset = offset + singleDesc_TLV_len;
		}

		if (horiSnap_TLV_len != 0) {
			System.arraycopy(horiSnap_TLV, 0, toSPE, offset, horiSnap_TLV_len);
			offset = offset + horiSnap_TLV_len;
		}

		map.updOrderStatus(orderId, TmlConst.orderStatus.hav_send);

		return toSPE;
	}

	public List<Order> getOnlineContents(String programId, int programType) {
		ResultSet rs = null;
		List<Order> temp = new ArrayList<Order>();
		String querySQL = "";
		String value = "";

		if (programType == MediaConst.ProgramType.ALBUM) {
			querySQL = 
				"SELECT c.contentId,c.fileSize,p.releaseDate,c.contentName,p.programName,p.episodes," +
				"c.episodeIndex,c.fileURL,p.posterVertSmall,p.posterVertBig,c.contentPrice," +
				"p.country,p.director,p.artist,p.summary,p.searchName,p.artistPinyin,c.extraFileUrl," +
				"p.language,p.artistType,p.tarFileUrl,c.runTime" +
				" FROM CNT_MUSIC_PROGRAM p, CNT_MUSIC_CONTENT c " +
				" WHERE p.programId=c.programId AND c.programId='"+programId+"'";
			value = "CNT_MUSIC_PROGRAM,CNT_MUSIC_CONTENT";
		} else if (programType == MediaConst.ProgramType.PIC) {

		} else {
			querySQL = 
				"SELECT c.contentId,c.fileSize,p.releaseDate,c.contentName,p.programName,p.episodes," +
				"c.episodeIndex,c.fileURL,p.posterVertSmall,p.posterVertBig,p.posterHoriBig,p.posterHoriSmall," +
				"c.posterHoriBig,c.posterVertBig,c.maxPlayTimes," +
				"c.contentPrice,c.extraFileUrl,p.country,p.director,p.actors,p.caption,p.summary,c.summary,c.captionFileUrl," +
				"p.searchName,p.language,p.tarFileUrl,c.runTime,t.listFileName,t.playCtrlFileName " +
				" FROM CNT_VIDEO_PROGRAM p, CNT_VIDEO_CONTENT c, AD_TEMPLATE t " +
				" WHERE p.adTmplId=t.templateId AND p.programId=c.programId AND c.programId ='"+programId+"'";
			value = "CNT_VIDEO_PROGRAM,CNT_VIDEO_CONTENT,AD_TEMPLATE";
		}

		String key = MD5.encryptMD5(querySQL);
		Object ob = memc.get(key);
		Order needSave = null;
		if(ob == null || ob.equals("")){
			needSave = new Order();
			dbcms = new DBCommon_cms2(querySQL);
			try {
				rs = dbcms.executeQuery();

				// 取出播放列表中所有的数据
				while (rs.next()) {
					long movieSize = rs.getLong("fileSize");
					needSave.setMovieSize(movieSize/1024);
					needSave.setServiceType(programType);
					needSave.setProgramId(programId);

					String parentName = rs.getString("programName");
					needSave.setParentName(parentName);

					String movieName = rs.getString("contentName");
					needSave.setMovieName(movieName);

					if (programType == MediaConst.ProgramType.ALBUM 
							|| programType == MediaConst.ProgramType.TVSERIES
							|| programType == MediaConst.ProgramType.SPECIAL_MULTI
							|| programType == MediaConst.ProgramType.SPECIAL_SINGLE) {
						int episodeIndex = rs.getInt("episodeIndex");
						needSave.setEpisodeIndex(episodeIndex);
					}

					String fileUrl = rs.getString("fileURL");
					int index = fileUrl.lastIndexOf("/");
					if(index > 0) {
						String cntName = fileUrl.substring(index+1);
						needSave.setContentName(cntName);
					}

					needSave.setShowTime(rs.getString("releaseDate"));


					String tarFileURL = rs.getString("tarFileURL");
					if (tarFileURL != null) {
						int index2 = tarFileURL.lastIndexOf("/");
						if(index2 > 0) {
							String tarFileName = tarFileURL.substring(index2+1);
							needSave.setTarFileName(tarFileName);
						}
					}

					String idxFileUrl = rs.getString("extraFileUrl");
					if (idxFileUrl != null) {
						int index3 = idxFileUrl.lastIndexOf("/");
						if(index3 > 0) {
							String idxFileName = idxFileUrl.substring(index3+1);
							needSave.setIdxFileName(idxFileName);
						}
					}

					String rTime = rs.getString("runTime");
					if (rTime != null && rTime.trim().length() > 0 ) {
						String[] time = rTime.split(":");

						int runTime = Integer.valueOf(time[0]) * 3600 
						+ Integer.valueOf(time[1]) * 60
						+ Integer.valueOf(time[2]);

						needSave.setRunTime(runTime);
					}

					needSave.setContentId(rs.getString("contentId"));
					needSave.setLauguage(rs.getString("language"));
					needSave.setCountry(rs.getString("country"));

					String actors = "";
					String pdesc = rs.getString("p.summary");
					needSave.setDesc(pdesc);

					// p
					String postUrl = "";

					if (programType == MediaConst.ProgramType.ALBUM) {
						String artistType = rs.getString("artistType");
						needSave.setScreenFormat(PackageConstant.service_Type.getString(artistType));
						needSave.setArtistPinyin(rs.getString("artistPinyin"));
						actors = rs.getString("artist");

						postUrl = rs.getString("p.posterVertBig");
						if (postUrl == null || postUrl.trim().equals("")) {
							postUrl = rs.getString("p.posterVertSmall");
						}
					} else if (programType == MediaConst.ProgramType.PIC) {

					} else {
						needSave.setCount(rs.getInt("maxPlayTimes"));
						needSave.setCaption(rs.getString("caption"));
						needSave.setListName(rs.getString("listFileName"));
						needSave.setCXMLName(rs.getString("playCtrlFileName"));
						actors = rs.getString("actors");
						String cdesc = rs.getString("c.summary");
						if (cdesc != null && cdesc.trim().length()>80) {
							cdesc = cdesc.substring(0, 80);
						}
						needSave.setSingleDesc(cdesc);

						postUrl = rs.getString("p.posterVertSmall");
						if (postUrl == null || postUrl.trim().equals("")) {
							postUrl = rs.getString("p.posterVertBig");
						}

						// c
						String cpostUrl = rs.getString("c.posterHoriBig");
						if (cpostUrl == null || cpostUrl.trim().equals("")) {
							cpostUrl = rs.getString("c.posterVertBig");
						}

						if (cpostUrl != null) {
							int indexc = cpostUrl.lastIndexOf("/");
							if(indexc > 0) {
								String csnap = cpostUrl.substring(indexc+1);
								needSave.setSingleSnap(csnap);
							}
						}

						// captionFileUrl
						String captionFileUrl = rs.getString("captionFileUrl");
						if (captionFileUrl != null && !captionFileUrl.trim().equals("")) {
							int indexcap = captionFileUrl.lastIndexOf("/");
							if(indexcap > 0) {
								String captionFileName = captionFileUrl.substring(indexcap+1);
								needSave.setCaptionFileName(captionFileName);
							}
						}
					}

					// TV Horizontal Snap
					if (programType == MediaConst.ProgramType.TVSERIES) {
						String horiSnapUrl = rs.getString("posterHoriBig");
						if (horiSnapUrl == null || horiSnapUrl.trim().equals("")) {
							horiSnapUrl = rs.getString("posterHoriSmall");
						}

						if (horiSnapUrl != null && !horiSnapUrl.trim().equals("")) {
							int snapindex = horiSnapUrl.lastIndexOf("/");
							if (snapindex > 0) {
								String horiSnap = horiSnapUrl.substring(snapindex+1);
								needSave.setTvHoriSnap(horiSnap);
							}
						}
					}

					int index1 = postUrl.lastIndexOf("/");
					if(index1 > 0) {
						String snap = postUrl.substring(index1+1);
						needSave.setSnap(snap);
					}

					if (actors != null && actors.trim().length() > 0) {
						if (actors.contains("，")) {
							actors = splitStr(actors, "，");
						} else if (actors.contains(",")) {
							actors = splitStr(actors, ",");
						}
					}
					needSave.setActors(actors);

					String director = rs.getString("director");
					if (director != null && director.trim().length() > 0) {
						if (director.contains("，")) {
							director = splitStr(director, "，");
						} else if (director.contains(",")) {
							director = splitStr(director, ",");
						}
					}
					needSave.setDirector(director);

					String price = rs.getString("contentPrice");
					int amount = 0;
					if (price != null && price.trim().length()>0) {
						Float s = Float.parseFloat(price);
						amount = (int)(s * 100);
					}
					needSave.setAmount(amount);

					needSave.setSearchName(rs.getString("searchName"));
					needSave.setEpisodes(rs.getInt("episodes"));

					temp.add(needSave);
				}
				// save cache
				memc.add(key, temp);
				memc.addkey(key, value);
			} catch (Exception e) {
				log.error(this,e);
			} finally {
				dbcms.close();
			}
		} else {
			temp = (List<Order>) ob;
		}

		return temp;
	}

	public List<Order> getContentsByPid(String programId, int programType) {
		List<Order> cnts = new ArrayList<Order>();
		ResultSet rs = null;
		String querySQL = "";
		String value = "";

		if (programType == MediaConst.ProgramType.ALBUM) {
			querySQL = 
				"SELECT contentId FROM  CNT_MUSIC_CONTENT WHERE programId='"+programId+"'";
			value = "CNT_MUSIC_CONTENT";
		} else if (programType == MediaConst.ProgramType.PIC) {

		} else {
			querySQL = 
				"SELECT contentId,maxPlayTimes FROM CNT_VIDEO_CONTENT WHERE programId ='"+programId+"'";
			value = "CNT_VIDEO_CONTENT";
		}

		String key = MD5.encryptMD5(querySQL);
		Object ob = memc.get(key);
		if(ob == null || ob.equals("")){
			dbcms = new DBCommon_cms2(querySQL);
			try {
				rs = dbcms.executeQuery();

				// 取出播放列表中所有的数据
				while (rs.next()) {
					Order order = new Order();
					order.setContentId(rs.getString("contentId"));
					if (programType != MediaConst.ProgramType.ALBUM && programType != MediaConst.ProgramType.PIC) {
						order.setCount(rs.getInt("maxPlayTimes"));
					}
					cnts.add(order);
				}
				// save cache
				memc.add(key, cnts);
				memc.addkey(key, value);
			} catch (Exception e) {
				log.error(this,e);
			} finally {
				dbcms.close();
			}
		} else {
			cnts = (List<Order>) ob;
		}

		return cnts;
	}

	public Order getContentsByCid(String contentId, int programType) {
		Order order = new Order();
		ResultSet rs = null;
		String querySQL = "";
		String value = "";

		if (programType == MediaConst.ProgramType.ALBUM) {
			return order;
		} else if (programType == MediaConst.ProgramType.PIC) {

		} else {
			querySQL = 
				"SELECT maxPlayTimes FROM CNT_VIDEO_CONTENT WHERE contentId ='"+contentId+"'";
			value = "CNT_VIDEO_CONTENT";
		}

		String key = MD5.encryptMD5(querySQL);
		Object ob = memc.get(key);
		if(ob == null || ob.equals("")){
			dbcms = new DBCommon_cms2(querySQL);
			try {
				rs = dbcms.executeQuery();

				// 取出播放列表中所有的数据
				while (rs.next()) {
					order.setContentId(contentId);
					if (programType != MediaConst.ProgramType.ALBUM && programType != MediaConst.ProgramType.PIC) {
						order.setCount(rs.getInt("maxPlayTimes"));
					}
				}

				// save cache
				memc.add(key, order);
				memc.addkey(key, value);
			} catch (Exception e) {
				log.error(this,e);
			} finally {
				dbcms.close();
			}
		} else {
			order = (Order) ob;
		}
		return order;
	}

	public List<Order> getDetails(List<Order> list) {
		ResultSet rs = null;
		List<Order> temp = new ArrayList<Order>();
		String querySQL = "";
		String value = "";

		for (Order order : list) {
			String cntId = order.getContentId();
			int type = order.getServiceType();

			if (type == MediaConst.ProgramType.ALBUM) {
				querySQL = 
					"SELECT c.fileSize,c.programId,p.releaseDate,c.contentName,p.programName,p.episodes," +
					"c.episodeIndex,c.fileURL,p.posterVertSmall,p.posterVertBig,c.contentPrice," +
					"p.country,p.director,p.artist,p.summary,p.searchName,p.artistPinyin,c.extraFileUrl," +
					"p.language,p.artistType,p.tarFileUrl,c.runTime" +
					" FROM CNT_MUSIC_PROGRAM p, CNT_MUSIC_CONTENT c " +
					" WHERE p.programId=c.programId AND c.contentId='"+cntId+"'";
				value = "CNT_MUSIC_PROGRAM,CNT_MUSIC_CONTENT";
			} else if (type == MediaConst.ProgramType.PIC) {

			} else {
				querySQL = 
					"SELECT c.fileSize,c.programId,p.releaseDate,c.contentName,p.programName,p.episodes," +
					"c.episodeIndex,c.fileURL,p.posterVertSmall,p.posterVertBig,p.posterHoriBig,p.posterHoriSmall," +
					"c.posterHoriBig,c.posterVertBig,c.maxPlayTimes," +
					"c.contentPrice,c.extraFileUrl,p.country,p.director,p.actors,p.caption,p.summary,c.summary,c.captionFileUrl," +
					"p.searchName,p.language,p.tarFileUrl,c.runTime,t.listFileName,t.playCtrlFileName " +
					" FROM CNT_VIDEO_PROGRAM p, CNT_VIDEO_CONTENT c, AD_TEMPLATE t " +
					" WHERE p.adTmplId=t.templateId AND p.programId=c.programId AND c.contentId ='"+cntId+"'";
				value = "CNT_VIDEO_PROGRAM,CNT_VIDEO_CONTENT,AD_TEMPLATE";
			}

			String key = MD5.encryptMD5(querySQL);
			Object ob = memc.get(key);
			Order needSave = null;
			if(ob == null || ob.equals("")){
				needSave = new Order();
				needSave.setContentId(cntId);
				needSave.setServiceType(type);
				dbcms = new DBCommon_cms2(querySQL);
				try {
					rs = dbcms.executeQuery();

					// 取出播放列表中所有的数据
					while (rs.next()) {
						long movieSize = rs.getLong("fileSize");
						needSave.setMovieSize(movieSize/1024);
						needSave.setServiceType(type);
						
						String parentName = rs.getString("programName");
						needSave.setParentName(parentName);

						String movieName = rs.getString("contentName");
						needSave.setMovieName(movieName);

						if (type == MediaConst.ProgramType.ALBUM 
								|| type == MediaConst.ProgramType.TVSERIES
								|| type == MediaConst.ProgramType.SPECIAL_MULTI
								|| type == MediaConst.ProgramType.SPECIAL_SINGLE) {
							int episodeIndex = rs.getInt("episodeIndex");
							needSave.setEpisodeIndex(episodeIndex);
						}

						String fileUrl = rs.getString("fileURL");
						int index = fileUrl.lastIndexOf("/");
						if(index > 0) {
							String cntName = fileUrl.substring(index+1);
							needSave.setContentName(cntName);
						}

						needSave.setShowTime(rs.getString("releaseDate"));


						String tarFileURL = rs.getString("tarFileURL");
						if (tarFileURL != null) {
							int index2 = tarFileURL.lastIndexOf("/");
							if(index2 > 0) {
								String tarFileName = tarFileURL.substring(index2+1);
								needSave.setTarFileName(tarFileName);
							}
						}

						String idxFileUrl = rs.getString("extraFileUrl");
						if (idxFileUrl != null) {
							int index3 = idxFileUrl.lastIndexOf("/");
							if(index3 > 0) {
								String idxFileName = idxFileUrl.substring(index3+1);
								needSave.setIdxFileName(idxFileName);
							}
						}

						String rTime = rs.getString("runTime");
						if (rTime != null && rTime.trim().length() > 0 ) {
							String[] time = rTime.split(":");

							int runTime = Integer.valueOf(time[0]) * 3600 
							+ Integer.valueOf(time[1]) * 60
							+ Integer.valueOf(time[2]);

							needSave.setRunTime(runTime);
						}

						needSave.setProgramId(rs.getString("programId"));
						needSave.setLauguage(rs.getString("language"));
						needSave.setCountry(rs.getString("country"));

						String actors = "";
						String pdesc = rs.getString("p.summary");
						needSave.setDesc(pdesc);

						// p
						String postUrl = "";

						if (type == MediaConst.ProgramType.ALBUM) {
							String artistType = rs.getString("artistType");
							needSave.setScreenFormat(PackageConstant.service_Type.getString(artistType));
							needSave.setArtistPinyin(rs.getString("artistPinyin"));
							actors = rs.getString("artist");

							postUrl = rs.getString("p.posterVertBig");
							if (postUrl == null || postUrl.trim().equals("")) {
								postUrl = rs.getString("p.posterVertSmall");
							}
						} else if (type == MediaConst.ProgramType.PIC) {

						} else {
							needSave.setCount(rs.getInt("maxPlayTimes"));
							needSave.setCaption(rs.getString("caption"));
							needSave.setListName(rs.getString("listFileName"));
							needSave.setCXMLName(rs.getString("playCtrlFileName"));
							actors = rs.getString("actors");
							String cdesc = rs.getString("c.summary");
							if (cdesc != null && cdesc.trim().length()>80) {
								cdesc = cdesc.substring(0, 80);
							}
							needSave.setSingleDesc(cdesc);

							postUrl = rs.getString("p.posterVertSmall");
							if (postUrl == null || postUrl.trim().equals("")) {
								postUrl = rs.getString("p.posterVertBig");
							}

							// c
							String cpostUrl = rs.getString("c.posterHoriBig");
							if (cpostUrl == null || cpostUrl.trim().equals("")) {
								cpostUrl = rs.getString("c.posterVertBig");
							}

							if (cpostUrl != null) {
								int indexc = cpostUrl.lastIndexOf("/");
								if(indexc > 0) {
									String csnap = cpostUrl.substring(indexc+1);
									needSave.setSingleSnap(csnap);
								}
							}

							// captionFileUrl
							String captionFileUrl = rs.getString("captionFileUrl");
							if (captionFileUrl != null && !captionFileUrl.trim().equals("")) {
								int indexcap = captionFileUrl.lastIndexOf("/");
								if(indexcap > 0) {
									String captionFileName = captionFileUrl.substring(indexcap+1);
									needSave.setCaptionFileName(captionFileName);
								}
							}
						}

						// TV Horizontal Snap
						if (type == MediaConst.ProgramType.TVSERIES) {
							String horiSnapUrl = rs.getString("posterHoriBig");
							if (horiSnapUrl == null || horiSnapUrl.trim().equals("")) {
								horiSnapUrl = rs.getString("posterHoriSmall");
							}

							if (horiSnapUrl != null && !horiSnapUrl.trim().equals("")) {
								int snapindex = horiSnapUrl.lastIndexOf("/");
								if (snapindex > 0) {
									String horiSnap = horiSnapUrl.substring(snapindex+1);
									needSave.setTvHoriSnap(horiSnap);
								}
							}
						}

						int index1 = postUrl.lastIndexOf("/");
						if(index1 > 0) {
							String snap = postUrl.substring(index1+1);
							needSave.setSnap(snap);
						}

						if (actors != null && actors.trim().length() > 0) {
							if (actors.contains("，")) {
								actors = splitStr(actors, "，");
							} else if (actors.contains(",")) {
								actors = splitStr(actors, ",");
							}
						}
						needSave.setActors(actors);

						String director = rs.getString("director");
						if (director != null && director.trim().length() > 0) {
							if (director.contains("，")) {
								director = splitStr(director, "，");
							} else if (director.contains(",")) {
								director = splitStr(director, ",");
							}
						}
						needSave.setDirector(director);

						String price = rs.getString("contentPrice");
						int amount = 0;
						if (price != null && price.trim().length()>0) {
							Float s = Float.parseFloat(price);
							amount = (int)(s * 100);
						}
						needSave.setAmount(amount);

						needSave.setSearchName(rs.getString("searchName"));
						needSave.setEpisodes(rs.getInt("episodes"));
					}

					// save cache
					memc.add(key, needSave);
					memc.addkey(key, value);
				} catch (Exception e) {
					log.error(this,e);
				} finally {
					dbcms.close();
				}
			} else {
				needSave = (Order) ob;
			}

			if (needSave != null) {
				order.setMovieSize(needSave.getMovieSize());
				order.setServiceType(type);
				order.setParentName(needSave.getParentName());
				order.setMovieName(needSave.getMovieName());
				order.setEpisodeIndex(needSave.getEpisodeIndex());
				order.setContentName(needSave.getContentName());
				order.setShowTime(needSave.getShowTime());
				order.setTarFileName(needSave.getTarFileName());
				order.setIdxFileName(needSave.getIdxFileName());
				order.setRunTime(needSave.getRunTime());
				order.setProgramId(needSave.getProgramId());
				order.setLauguage(needSave.getLauguage());
				order.setCountry(needSave.getCountry());
				order.setCount(needSave.getCount());
				order.setDesc(needSave.getDesc());
				order.setScreenFormat(needSave.getScreenFormat());
				order.setArtistPinyin(needSave.getArtistPinyin());
				order.setCaption(needSave.getCaption());
				order.setListName(needSave.getListName());
				order.setCXMLName(needSave.getCXMLName());
				order.setSingleDesc(needSave.getSingleDesc());
				order.setSingleSnap(needSave.getSingleSnap());
				order.setCaptionFileName(needSave.getCaptionFileName());
				order.setTvHoriSnap(needSave.getTvHoriSnap());
				order.setSnap(needSave.getSnap());
				order.setActors(needSave.getActors());
				order.setDirector(needSave.getDirector());
				order.setAmount(needSave.getAmount());
				order.setSearchName(needSave.getSearchName());
				order.setEpisodes(needSave.getEpisodes());
				temp.add(order);
			}

			// 加入unDownOkOrders-map
			OrderMapMemory map = OrderMapMemory.getInstance();
			map.insUnDownOkOrder(order.getOrderId(), order);
			if (order.getPortalType() == TmlConst.portalType.pcPortal) {
				List<Order> pclist = new ArrayList<Order>();
				pclist.add(order);
				map.addOrder(order.getTmlId(), pclist);
			}

			if (type == MediaConst.ProgramType.ALBUM) {
				map.insMusicDownTimes(cntId);
			} else if (type == MediaConst.ProgramType.PIC) {

			} else {
				map.insVideoDownTimes(cntId);
			}
		}
		rs = null;
		return temp;
	}

	private String splitStr(String name, String reg) {
		String ret = "";
		String[] strs = name.split(reg);
		ret = strs[0];
		if (strs.length > 1) {
			for (int i=1; i<strs.length; i++) {
				ret += " / " + strs[i];
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<String> getMovieCategory(String contentId) {
		List<String> ret = new ArrayList<String>();
		if (contentId == null || contentId.trim().equals("")) {
			log.info("getMovieCategory contentId is null!");
			return ret;
		}
		ResultSet rs = null;

		String sql =
			"SELECT HIERARCHYMENUNAME FROM CNT_VIDEO_CONTENT c, PORTAL_HIERARCHY_MENU p, " +
			"PORTAL_HIERARCHY_MENU_PROGRAM m WHERE c.programId=m.programId AND " +
			"p.HIERARCHYMENUID=m.HIERARCHYMENUID AND p.HIERARCHYMENUNAME != '人气排行'" +
			" AND p.HIERARCHYMENUNAME != '最新添加' AND p.HIERARCHYMENUNAME != '电影' " +
			" AND p.HIERARCHYMENUNAME != '最新上线' AND p.parentId != -1 AND c.contentId='"+contentId+"'";

		MemCached cache = MemCached.getInstance();
		String key = MD5.encryptMD5(sql);
		Object ob = cache.get(key);
		if(ob == null || ob.equals("")){
			try {
				dbcms = new DBCommon_cms2(sql);
				rs = dbcms.executeQuery();

				while (rs.next()) {
					ret.add(rs.getString(1));
				}

				cache.add(key, ret);
				cache.addkey(key, "CNT_VIDEO_CONTENT,PORTAL_HIERARCHY_MENU,PORTAL_HIERARCHY_MENU_PROGRAM");
			} catch (Exception e) {
				log.error(this,e);
			} finally {
				dbcms.close();
			}
		} else {
			ret = (List<String>) ob;
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public List<String> getMusicCategory(String programId) {
		MemCached cache = MemCached.getInstance();
		List<String> ret = new ArrayList<String>();
		if (programId == null || programId.trim().equals("")) {
			log.info("getMusicCategory programId is null!");
			return ret;
		}
		ResultSet rs = null;

		String sql =
			"SELECT c.categoryName FROM CNT_MUSIC_PROGRAM p, CNT_CATEGORY c" +
			" WHERE p.categoryId=c.categoryId AND p.programId='"+programId+"'";

		String key = MD5.encryptMD5(sql);
		Object ob = cache.get(key);
		if(ob == null || ob.equals("")){
			try {
				dbcms = new DBCommon_cms2(sql);
				rs = dbcms.executeQuery();

				while (rs.next()) {
					ret.add(rs.getString(1));
				}
			} catch (Exception e) {
				log.error(this,e);
			} finally {
				dbcms.close();
			}
			cache.add(key, ret);
			cache.addkey(key, "CNT_MUSIC_PROGRAM,CNT_CATEGORY");
		} else {
			ret = (List<String>) ob;
		}


		return ret;
	}

	public boolean insertOrder(List<Order> list) {
		DBCommon db = null;
		boolean flg = true;

		String sql = 
			"INSERT INTO TML_ORDER(orderId, tmlId, programId, contentId, contentName, " +
			"movieName, CXMLName, listName, chargeMode, count, portalType, " +
			"status, licenseName, snap, movieSize, serviceType, parentName, " +
			"inTime, showTime, tarFileName, idxFileName, runTime, episodes, " +
			"actors, pLauguage, screenFormat ,episodeIndex, startTime, endTime,directors, country, description) " +
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		for (Order order : list) {
			try {
				db = new DBCommon(sql);
				if (order != null) {
					String orderId = order.getOrderId();
					String tmlId = order.getTmlId();
					String contentId = order.getContentId();
					String cntName = order.getContentName();
					String mName = order.getMovieName();
					String CXMLName = order.getCXMLName();
					String listName = order.getListName();
					int charge = order.getChargeMode();
					int count = order.getCount();
					String licenseName = order.getLicenseName();
					String snap = order.getSnap();
					long movieSize = order.getMovieSize();
					int serviceType = order.getServiceType();
					String parentName = order.getParentName();
					int episodeIndex = order.getEpisodeIndex();

					String showTime = order.getShowTime();
					String tarFileName = order.getTarFileName();
					String idxFileName = order.getIdxFileName();
					int episodes = order.getEpisodes();
					int runTime = order.getRunTime();
					String inTime = sdf.format(new Date());

					String lcStart = order.getStartTime();
					String lcEndTime = order.getEndTime();
					String programId = order.getProgramId();

					int portalType = order.getPortalType();
					int status = 0;

					String actors = order.getActors();
					String lauguage = order.getLauguage();
					String screenFormat = order.getScreenFormat();

					String director = order.getDirector();
					String country = order.getCountry();
					String desc = order.getDesc();

					/*String sql = 
						"INSERT INTO TML_ORDER(orderId, tmlId, programId, contentId, contentName, " +
						"movieName, CXMLName, listName, chargeMode, count, portalType, " +
						"status, licenseName, snap, movieSize, serviceType, parentName, " +
						"inTime, showTime, tarFileName, idxFileName, runTime, episodes, " +
						"actors, pLauguage, screenFormat ,episodeIndex, startTime, endTime," +
						"directors, country, description) " +
						"VALUES('" + orderId + "','" + tmlId + "','" + programId
						+ "','" + contentId + "','" + cntName 
						+ "','" + mName + "','" + CXMLName + "','" + listName
						+ "','" + charge + "','" + count + "','" + portalType 
						+ "','" + status + "','" + licenseName+ "','" + snap 
						+ "','" + movieSize + "','" + serviceType + "','"+ parentName 
						+ "','" + inTime + "','" + showTime+ "','" + tarFileName 
						+  "','" + idxFileName + "','" + runTime+"','" + episodes
						+"','" + actors + "','" + lauguage +"','" + screenFormat
						+"','" + episodeIndex+ "','" + lcStart+"','" + lcEndTime+
						"','" + director+"','" + country+"','" + desc+"')";*/

					db.setString(1, orderId);
					db.setString(2, tmlId);
					db.setString(3, programId);
					db.setString(4, contentId);
					db.setString(5, cntName);
					db.setString(6, mName);
					db.setString(7, CXMLName);
					db.setString(8, listName);
					db.setInt(9, charge);
					db.setInt(10, count);
					db.setInt(11, portalType);
					db.setInt(12, status);
					db.setString(13, licenseName);
					db.setString(14, snap);
					db.setLong(15, movieSize);
					db.setInt(16, serviceType);
					db.setString(17, parentName);
					db.setString(18, inTime);
					db.setString(19, showTime);
					db.setString(20, tarFileName);
					db.setString(21, idxFileName);
					db.setInt(22, runTime);
					db.setInt(23, episodes);
					db.setString(24, actors);
					db.setString(25, lauguage);
					db.setString(26, screenFormat);
					db.setInt(27, episodeIndex);
					db.setString(28, lcStart);
					db.setString(29, lcEndTime);
					db.setString(30, director);
					db.setString(31, country);
					db.setString(32, desc);

					db.executeUpdate();
					db.commit();
				}
			} catch (Exception e) {
				log.error(this,e);
				flg = false;
				 
			} finally {
				db.close();
			}
		}
		return flg;
	}

	public boolean updateOrder(List<StatusDTO> list) {
		if (list == null || list.size() == 0) return false;
		DBCommon db = new DBCommon();
		boolean flg = true;
		try {
			for (StatusDTO order : list) {
				if (order != null) {
					String orderId = order.getId();
					int status = order.getStatus();

					String sql = 
						"UPDATE TML_ORDER SET status="+status+" WHERE orderId='"+orderId+"'";
					db.addBatch(sql);
				}
			}

			db.executeBatch();
			db.commit();
		} catch (Exception e) {
			flg = false;
			 
			log.error(this,e);
		} finally {
			db.close();
		}
		return flg;
	}

	public boolean updateDownTimes(List<StatusDTO> list) {
		if (list == null || list.size() == 0) return false;
		DBCommon_cms2 db = new DBCommon_cms2();
		boolean flg = true;
		try {
			for (StatusDTO dto : list) {
				if (dto != null) {
					String contentId = dto.getId();
					int times = dto.getStatus();
					int type = dto.getStatus2();

					String sql = "";
					if (type == MediaConst.ProgramType.ALBUM) {
						sql = 
							"UPDATE CNT_MUSIC_PROGRAM p,CNT_MUSIC_CONTENT c SET p.downloadTimes=p.downloadTimes+"+times
							+",c.downloadTimes=c.downloadTimes+"+times+"  WHERE p.programId=c.programId AND contentId='"+contentId+"'";
					} else if (type == MediaConst.ProgramType.PIC) {

					} else {
						sql = 
							"UPDATE CNT_VIDEO_PROGRAM p,CNT_VIDEO_CONTENT c SET p.downloadTimes=p.downloadTimes+"+times
							+",c.downloadTimes=c.downloadTimes+"+times+"  WHERE p.programId=c.programId AND contentId='"+contentId+"'";
					}

					db.addBatch(sql);
				}
			}

			db.executeBatch();
			db.commit();
		} catch (Exception e) {
			flg = false;
			log.error(this,e);
		} finally {
			db.close();
		}
		return flg;
	}

	/**
	 * 得到SPE/OLS及STBPortal URL列表
	 * @return list
	 */
	public List<List_Server> getList(int netType, boolean conSpe) {
		List<List_Server> list = new ArrayList<List_Server>();
		List_Server so = null;

		DBCommon db = null;
		ResultSet rs = null;
		String querySQL = "";

		if (conSpe) {
			querySQL = "SELECT * FROM TML_SERVER_LIST WHERE netType=? AND tag NOT IN (?,?,?,?)";
		} else {
			querySQL = "SELECT * FROM TML_SERVER_LIST WHERE netType=? AND tag NOT IN (?,?,?,?,?)";
		}

		try {
			db = new DBCommon(querySQL);
			db.setInt(1, netType);
			db.setInt(2, TmlConst.serverType.AppPortal_URL);
			db.setInt(3, TmlConst.serverType.APP_FTP_URL);
			db.setInt(4, TmlConst.serverType.Upgrade_FTP_URL);
			db.setInt(5, TmlConst.serverType.Detail_URL);
			if (!conSpe) {
				db.setInt(6, TmlConst.serverType.spe);
			}

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				so = new List_Server();
				so.setIp(rs.getString("ip"));
				so.setPort(rs.getInt("port"));
				so.setTag(rs.getInt("tag"));
				so.setNetType(rs.getInt("netType"));
				//so.setGroupId(rs.getInt("groupId"));
				list.add(so);
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
		}

		rs = null;
		log.debug("select from db:"+list);

		return list;
	}

	public List_Server getServerURL(int tag, int netType) {
		List_Server so = null;

		DBCommon db = null;
		ResultSet rs = null;
		String querySQL = "SELECT * FROM TML_SERVER_LIST WHERE tag IN (?) AND netType=?";
		try {
			db = new DBCommon(querySQL);
			db.setInt(1, tag);
			db.setInt(2, netType);

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				so = new List_Server();
				so.setIp(rs.getString("ip"));
				so.setPort(rs.getInt("port"));
				so.setTag(rs.getInt("tag"));
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
			rs = null;
		}
		return so;
	}

	public boolean certifySTB(String tmlId, String userName, String encryptedPassword) {
		DBCommon db = null;
		ResultSet rs = null;
		boolean flg = false;
		String customerId = "";
		String customerPwd = "";

		String querySQL = "SELECT customerId,customerPwd FROM TML_BASE WHERE tmlId = ?";
		log.info("来认证");
		try {
			db = new DBCommon(querySQL);

			db.setString(1, tmlId);

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				customerId = rs.getString(1);
				log.info("用户名"+customerId);
				customerPwd = rs.getString(2);
				log.info("密码"+customerPwd);
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
		}
		if (customerId != null && (!customerId.trim().equals(""))
				&& customerPwd != null && (!customerPwd.trim().equals(""))) {
			MD5 m = new MD5();
			String md5Pwd = m.encryptMD5(customerPwd);
			//log.info("用户名为："+userName);
			//log.info("数据库用户名为"+customerId);
			//if(!customerId.trim().equals(userName)) log.info("用户名不对");
			//if(!md5Pwd.equals(encryptedPassword.toUpperCase())) log.info("密码不对");
			log.info("密码为："+encryptedPassword);
			log.info("数据库密码为:加密后"+md5Pwd);
			if (customerId.trim().equals(userName.trim()) 
					&& md5Pwd.equals(encryptedPassword.toUpperCase())) 
				{flg = true;log.info("认证通过了");}
			else log.info("用户名密码不对");
		}

		return flg;
	}

	public String getContentName(String contentFile) {
		String contentName = "";
		DBCommon_cms2 db = null;
		ResultSet rs = null;
		String querySQL = "SELECT CONTENTTYPE,CONTENTNAME,PROGRAMNAME FROM CONTENT_NAMES WHERE fileURL like ?";

		MemCached cache = MemCached.getInstance();
		String key = MD5.encryptMD5(querySQL);
		Object ob = cache.get(key);
		if(ob == null || ob.equals("")){
			try {
				db = new DBCommon_cms2(querySQL);

				db.setString(1, "%"+contentFile);

				rs = db.executeQuery();

				// 取出播放列表中所有的数据
				while (rs.next()) {
					if(rs.getString("CONTENTTYPE").equals("4") 
							|| rs.getString("CONTENTTYPE").equals("12") 
							|| rs.getString("CONTENTTYPE").equals("9")) {
						contentName = rs.getString("CONTENTNAME");
					} else {
						String name1="";
						if(rs.getString("CONTENTTYPE").equals("7"))name1="，电视剧：";
						if(rs.getString("CONTENTTYPE").equals("8"))name1="，音乐专辑：";
						if(rs.getString("CONTENTTYPE").equals("11"))name1="，相册：";
						if(rs.getString("CONTENTTYPE").equals("13"))name1="，专题：";
						contentName = rs.getString("CONTENTNAME")+name1+rs.getString("PROGRAMNAME");
					}
				}
				rs = null;
			} catch (Exception e) {
				log.error(this,e);
				contentName = contentFile;
			} finally {
				db.close();
			}

			cache.add(key, contentName);
			cache.addkey(key, "CONTENT_NAMES");
		} else {
			contentName = (String) ob;
		}

		return contentName;
	}

	public boolean checkLastOrder(String tmlId, String contentId,String time) {
		boolean flg = false;
		String sql = "SELECT COUNT(*) FROM TML_ORDER WHERE tmlId=? AND contentId=? AND inTime>=?";
		DBCommon db = null;
		ResultSet rs = null;
		int count = 0;
		try {
			db = new DBCommon(sql);
			db.setString(1, tmlId);
			db.setString(2, contentId);
			db.setString(3, time);
			rs = db.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			 
			log.error(this,e);
		} finally {
			db.close();
		}
		if (count > 0)flg = true;
		return flg;
	}

	public String getConfigureValue(String name) {
		String ret = "";
		DBCommon db = null;
		ResultSet rs = null;
		String sql = "SELECT VALUE FROM SYS_CONFIGURE WHERE NAME = ?";
		try {
			db = new DBCommon(sql);

			db.setString(1, name);

			rs = db.executeQuery();

			// 取出播放列表中所有的数据
			while (rs.next()) {
				ret = rs.getString(1);
			}
			rs = null;
		} catch (Exception e) {
			 
			log.error(e,e);
		} finally {
			db.close();
		}
		return ret;
	}

	public static void main(String[] a) {
		TmlServiceImpl t = new TmlServiceImpl();
		//orderId=001240712865F01173913406651820110302170938, actors=闃挎柉钂傝姮'A, lauguage=鏃ヨ, screenFormat=, amount=0, director=鎾掑湴鏂?A, country=棣欐腐, desc=闃垮嚒杈炬柉'A, searchName=TEST8, artistPinyin=, caption=鏃ユ枃, singleSnap=, singleDesc=null, captionFileName=, tvHoriSnap=]
		/*List<Order> list = new ArrayList<Order>();
		Order o = new Order();
		o.setOrderId("");
		o.setServiceType(7);
		o.setContentId("F111107188904549");
		list.add(o);
		System.out.println(t.getDetails(list));*/
		System.out.println(t.getContentName("F1100233489348934.ts"));
		/*Calendar c = Calendar.getInstance();
		c.add(c.MINUTE, -10);
		String time = PackageConstant.sdf0.format(c.getTimeInMillis());
		System.out.println(time);
		System.out.println(t.checkLastOrder("0012408E1B53", "F031014296712627", time));*/
		/*String name = null;
		String retu = "";
		if (name.contains("，")) {
			retu = t.splitStr(name, "，");
		}else if (name.contains(",")) {
			retu = t.splitStr(name, ",");
		}*/
		/*
		String tmlId = "001240712865";
		String contentId = "F011739134066518";
		String cntName = "E_F011739134066518.ts";
		String mName = "TEST8";
		String CXMLName = "C_000000000000_00000.XML";
		String listName = "L_000000000000_00000.XML";
		int charge = 0;
		int count = 3;
		String licenseName = "001240712865F01173913406651820110302170938.license";
		String snap = "P011739328753736.jpg";
		long movieSize = 136342;
		int serviceType = 4;
		String parentName = "TEST8";
		int episodeIndex = 0;

		String showTime = "2010-6-2";
		String tarFileName = "G100603095207262.tar.gz";
		String idxFileName = "";
		int episodes = 0;
		int runTime = 712;
		String inTime = sdf.format(new Date());

		String lcStart = "2010-06-07 16:52:05";
		String lcEndTime = "2010-06-14 16:52:05";
		String programId = "F011739312810124";

		int portalType = 0;
		int status = 0;

		String actors = "";
		String lauguage = "";
		String screenFormat = "";


		List<Order> list = new ArrayList<Order>();
		Order o = new Order();
		o.setOrderId("001240712865F01173913406651820110302170938");
		o.setActors(actors);
		o.setChargeMode(charge);
		o.setContentId(contentId);
		o.setContentName(cntName);
		o.setCount(count);
		o.setCXMLName(CXMLName);
		o.setEndTime(lcEndTime);
		o.setEpisodeIndex(episodeIndex);
		o.setEpisodes(episodes);
		o.setIdxFileName(idxFileName);
		o.setLauguage(lauguage);
		o.setLicenseName(licenseName);
		o.setListName(listName);
		o.setMovieName(mName);
		o.setMovieSize(movieSize);
		o.setMovieSize(movieSize);
		o.setParentName(parentName);
		o.setPortalType(portalType);
		o.setProgramId(programId);
		o.setRunTime(runTime);
		o.setScreenFormat(screenFormat);
		o.setServiceType(serviceType);
		o.setShowTime(showTime);
		o.setSnap(snap);
		o.setStartTime(lcStart);
		o.setStatus(status);
		o.setTarFileName(tarFileName);
		o.setTmlId(tmlId);
		o.setActors("ASSDD'DDD");
		o.setDirector("阿对外大槐树'地前往");
		list.add(o);

		t.insertOrder(list);*/

	}
}