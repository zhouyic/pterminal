package communicate.pkmgmt.dto;

import java.io.Serializable;

import net.sf.hibernate.cache.CacheConcurrencyStrategy;


public class TmlBase implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tmlId;
	private int tmlType;
	private String publishTime;
	private String loginTime;
	private int status;
	private String currentVersion;
	private int appStatus;
	private String contentName;
	private int serviceType;
	private String groupId;
	private String tmlSn;
	private String hardware;
	private String country;
	private String region;
	
	
	public String getHardware() {
		return hardware;
	}
	public void setHardware(String hardware) {
		this.hardware = hardware;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTmlSn() {
		return tmlSn;
	}
	public void setTmlSn(String tmlSn) {
		this.tmlSn = tmlSn;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public int getTmlType() {
		return tmlType;
	}
	public void setTmlType(int tmlType) {
		this.tmlType = tmlType;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public int getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(int appStatus) {
		this.appStatus = appStatus;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "TmlBase [tmlId=" + tmlId + ", tmlType=" + tmlType
				+ ", publishTime=" + publishTime + ", loginTime=" + loginTime
				+ ", status=" + status + ", currentVersion=" + currentVersion
				+ ", appStatus=" + appStatus + ", contentName=" + contentName
				+ ", serviceType=" + serviceType + ", groupId=" + groupId
				+ ", tmlSn=" + tmlSn + ", hardware=" + hardware + ", country="
				+ country + ", region=" + region + "]";
	}
	
}
