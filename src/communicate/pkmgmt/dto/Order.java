package communicate.pkmgmt.dto;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = -1985948970289444949L;

	private String orderId = "";
	private String tmlId = "";
	private String contentId = "";
	private String programId = "";
	private String movieName = "";
	private String contentName = "";
	private int episodeIndex = 0;
	private String listName = "";
	private String CXMLName = "";
	private String licenseName = "";
	private String snap = "";
	private int chargeMode = 0;
	private int count = 0;
	private String startTime = "0000-00-00 00:00:00";
	private String endTime = "0000-00-00 00:00:00";
	private boolean tag = false; /*true:数据库中已存在; false:数据库中不存在 */
	private int portalType = 0;
	private int status = 0;
	private long movieSize = 0;
	private int serviceType = 0;
	private String parentName = "";
	private String tarFileName = "";
	private String movieCategory = "";
	private int episodes = 0;
	private String showTime = "";
	private String idxFileName = "";
	private int runTime = 0;
	private String director = "";
	private String actors = "";
	private String country = "";

	private String lauguage = "";
	private String caption = "";

	private String screenFormat = "";
	private String productId = "";
	private int amount = 0;
	private String desc = "";

	private String searchName="";
	private String artistPinyin="";

	private String singleSnap="";
	private String singleDesc="";

	private String captionFileName = "";

	private String tvHoriSnap = "";

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("OrderEntity[");
		sb.append(" orderId=").append(orderId);
		sb.append(", tmlId=").append(tmlId);
		sb.append(", contentId=").append(contentId);
		sb.append(", programId=").append(programId);
		sb.append(", contentName=").append(contentName);
		sb.append(", movieName=").append(movieName);
		sb.append(", episodes=").append(episodes);
		sb.append(", episodeIndex=").append(episodeIndex);
		//sb.append(", listName=").append(listName);
		//sb.append(", CXMLName=").append(CXMLName);
		sb.append(", licenseName=").append(licenseName);
		sb.append(", snap=").append(snap);
		//sb.append(", chargeMode=").append(chargeMode);
		sb.append(", count=").append(count);
		//sb.append(", startTime=").append(startTime);
		//sb.append(", endTime=").append(endTime);
		sb.append(", tag=").append(tag);
		sb.append(", portalType=").append(portalType);
		sb.append(", status=").append(status);
		sb.append(", movieSize=").append(movieSize);
		sb.append(", serviceType=").append(serviceType);
		sb.append(", parentName=").append(parentName);
		sb.append(", tarFileName=").append(tarFileName);
		sb.append(", movieCategory=").append(movieCategory);
		sb.append(", showTime=").append(showTime);
		sb.append(", idxFileName=").append(idxFileName);
		sb.append(", runTime=").append(runTime);
		sb.append(", actors=").append(actors);
		sb.append(", lauguage=").append(lauguage);
		sb.append(", screenFormat=").append(screenFormat);
		//sb.append(", productId=").append(productId);
		sb.append(", amount=").append(amount);
		sb.append(", director=").append(director);
		sb.append(", country=").append(country);
		sb.append(", desc=").append(desc);
		sb.append(", searchName=").append(searchName);
		sb.append(", artistPinyin=").append(artistPinyin);
		sb.append(", caption=").append(caption);
		sb.append(", singleSnap=").append(singleSnap);
		sb.append(", singleDesc=").append(singleDesc);
		sb.append(", captionFileName=").append(captionFileName);
		sb.append(", tvHoriSnap=").append(tvHoriSnap);
		sb.append("]\r\n");
		return sb.toString();
	}

	public String getTvHoriSnap() {
		return tvHoriSnap;
	}


	public void setTvHoriSnap(String tvHoriSnap) {
		this.tvHoriSnap = tvHoriSnap;
	}


	public String getCaptionFileName() {
		return captionFileName;
	}

	public void setCaptionFileName(String captionFileName) {
		this.captionFileName = captionFileName;
	}

	public String getSingleSnap() {
		return singleSnap;
	}

	public void setSingleSnap(String singleSnap) {
		this.singleSnap = singleSnap;
	}

	public String getSingleDesc() {
		return singleDesc;
	}

	public void setSingleDesc(String singleDesc) {
		this.singleDesc = singleDesc;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getArtistPinyin() {
		return artistPinyin;
	}

	public void setArtistPinyin(String artistPinyin) {
		this.artistPinyin = artistPinyin;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getLauguage() {
		return lauguage;
	}

	public void setLauguage(String lauguage) {
		this.lauguage = lauguage;
	}

	public String getScreenFormat() {
		return screenFormat;
	}

	public void setScreenFormat(String screenFormat) {
		this.screenFormat = screenFormat;
	}

	public int getEpisodeIndex() {
		return episodeIndex;
	}

	public void setEpisodeIndex(int episodeIndex) {
		this.episodeIndex = episodeIndex;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public String getIdxFileName() {
		return idxFileName;
	}

	public void setIdxFileName(String idxFileName) {
		this.idxFileName = idxFileName;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public int getEpisodes() {
		return episodes;
	}

	public void setEpisodes(int episodes) {
		this.episodes = episodes;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getTarFileName() {
		return tarFileName;
	}

	public void setTarFileName(String tarFileName) {
		this.tarFileName = tarFileName;
	}

	public String getMovieCategory() {
		return movieCategory;
	}

	public void setMovieCategory(String movieCategory) {
		this.movieCategory = movieCategory;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}

	public long getMovieSize() {
		return movieSize;
	}

	public void setMovieSize(long movieSize) {
		this.movieSize = movieSize;
	}

	public String getSnap() {
		return snap;
	}

	public void setSnap(String snap) {
		this.snap = snap;
	}

	public int getPortalType() {
		return portalType;
	}

	public void setPortalType(int portalType) {
		this.portalType = portalType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isTag() {
		return tag;
	}

	public void setTag(boolean tag) {
		this.tag = tag;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTmlId() {
		return tmlId;
	}

	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getCXMLName() {
		return CXMLName;
	}

	public void setCXMLName(String name) {
		CXMLName = name;
	}

	public int getChargeMode() {
		return chargeMode;
	}

	public void setChargeMode(int chargeMode) {
		this.chargeMode = chargeMode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		return true;
	}
}
