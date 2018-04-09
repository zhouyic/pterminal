package communicate.pkmgmt.dto;

public class TmlConfDto {
	private String tmlId;
	private int heartbeatRate;
	private String portalUrl;
	private String logUrl;
	private String ntpUrl;
	private String groupName;
	private String upgradeUrl;
	private String  registerUrl;
	private String upgradeInfoUrl;
	private String appInfoUrl;
	public String getUpgradeInfoUrl() {
		return upgradeInfoUrl;
	}
	public void setUpgradeInfoUrl(String upgradeInfoUrl) {
		this.upgradeInfoUrl = upgradeInfoUrl;
	}
	public String getAppInfoUrl() {
		return appInfoUrl;
	}
	public void setAppInfoUrl(String appInfoUrl) {
		this.appInfoUrl = appInfoUrl;
	}
	public String getRegisterUrl() {
		return registerUrl;
	}
	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUpgradeUrl() {
		return upgradeUrl;
	}
	public void setUpgradeUrl(String upgradeUrl) {
		this.upgradeUrl = upgradeUrl;
	}

	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public int getHeartbeatRate() {
		return heartbeatRate;
	}
	public void setHeartbeatRate(int heartbeatRate) {
		this.heartbeatRate = heartbeatRate;
	}
	public String getPortalUrl() {
		return portalUrl;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public String getLogUrl() {
		return logUrl;
	}
	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}
	public String getNtpUrl() {
		return ntpUrl;
	}
	public void setNtpUrl(String ntpUrl) {
		this.ntpUrl = ntpUrl;
	}
	@Override
	public String toString() {
		return "TmlConfDto [tmlId=" + tmlId + ", heartbeatRate="
				+ heartbeatRate + ", portalUrl=" + portalUrl + ", logUrl="
				+ logUrl + ", ntpUrl=" + ntpUrl + ", groupName=" + groupName
				+ ", upgradeUrl=" + upgradeUrl + ", registerUrl=" + registerUrl
				+ ", upgradeInfoUrl=" + upgradeInfoUrl + ", appInfoUrl="
				+ appInfoUrl + "]";
	}

	
}
