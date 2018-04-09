package communicate.pkmgmt.dto;

public class TmlDto {

	private String onlineTime;
	private String tmlId;
	private String tmlSN;
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public String getTmlSN() {
		return tmlSN;
	}
	public void setTmlSN(String tmlSN) {
		this.tmlSN = tmlSN;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((onlineTime == null) ? 0 : onlineTime.hashCode());
		result = prime * result + ((tmlId == null) ? 0 : tmlId.hashCode());
		result = prime * result + ((tmlSN == null) ? 0 : tmlSN.hashCode());
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
		TmlDto other = (TmlDto) obj;
		if (onlineTime == null) {
			if (other.onlineTime != null)
				return false;
		} else if (!onlineTime.equals(other.onlineTime))
			return false;
		if (tmlId == null) {
			if (other.tmlId != null)
				return false;
		} else if (!tmlId.equals(other.tmlId))
			return false;
		if (tmlSN == null) {
			if (other.tmlSN != null)
				return false;
		} else if (!tmlSN.equals(other.tmlSN))
			return false;
		return true;
	}
}
