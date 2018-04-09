package communicate.pkmgmt.dto;

public class BillingDTO {
	private String orderId;
	private String tmlId;
	private String bossId;
	private String contentId;
	private String contentName;
	private String releaseId;
	private int amount;
	private String syncServiceId;
	private int status;

	@Override
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("BillingDTO:[");
		buffer.append("TmlId = ").append(getTmlId());
		buffer.append(", orderId = ").append(getOrderId());
		buffer.append(", BossId = ").append(getBossId());
		buffer.append(", ContentId = ").append(getContentId());
		buffer.append(", contentName = ").append(getContentName());
		buffer.append(", ReleaseId = ").append(getReleaseId());
		buffer.append(", Amount = ").append(getAmount());
		buffer.append(", SyncServiceId = ").append(getSyncServiceId());
		buffer.append(", Status = ").append(getStatus());
		buffer.append("]\r\n");
		return buffer.toString();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public String getBossId() {
		return bossId;
	}
	public void setBossId(String bossId) {
		this.bossId = bossId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getReleaseId() {
		return releaseId;
	}
	public void setReleaseId(String releaseId) {
		this.releaseId = releaseId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getSyncServiceId() {
		return syncServiceId;
	}
	public void setSyncServiceId(String syncServiceId) {
		this.syncServiceId = syncServiceId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
