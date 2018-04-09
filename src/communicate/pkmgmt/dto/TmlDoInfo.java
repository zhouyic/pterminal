package communicate.pkmgmt.dto;

public class TmlDoInfo {
	private static final long serialVersionUID = 1L;
	private int id;
	private String tmlId;
	private int tmlStatus;
	private int serviceType;
	private String tmlDoing;
	private String downSource;
	private String downRate;
	private String inTime;

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("TmlDoInfo:[tmlId=").append(tmlId)
		.append(", tmlStatus=").append(tmlStatus)
		.append(", tmlDoing=").append(tmlDoing)
		.append(", downSource=").append(downSource)
		.append(", downRate=").append(downRate)
		.append("]\r\n");
		return sb.toString();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public int getTmlStatus() {
		return tmlStatus;
	}
	public void setTmlStatus(int tmlStatus) {
		this.tmlStatus = tmlStatus;
	}
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public String getTmlDoing() {
		return tmlDoing;
	}
	public void setTmlDoing(String tmlDoing) {
		this.tmlDoing = tmlDoing;
	}
	public String getDownSource() {
		return downSource;
	}
	public void setDownSource(String downSource) {
		this.downSource = downSource;
	}
	public String getDownRate() {
		return downRate;
	}
	public void setDownRate(String downRate) {
		this.downRate = downRate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((downRate == null) ? 0 : downRate.hashCode());
		result = prime * result
		+ ((downSource == null) ? 0 : downSource.hashCode());
		result = prime * result + ((inTime == null) ? 0 : inTime.hashCode());
		result = prime * result + serviceType;
		result = prime * result
		+ ((tmlDoing == null) ? 0 : tmlDoing.hashCode());
		result = prime * result + ((tmlId == null) ? 0 : tmlId.hashCode());
		result = prime * result + tmlStatus;
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
		final TmlDoInfo other = (TmlDoInfo) obj;
		if (downRate == null) {
			if (other.downRate != null)
				return false;
		} else if (!downRate.equals(other.downRate))
			return false;
		if (downSource == null) {
			if (other.downSource != null)
				return false;
		} else if (!downSource.equals(other.downSource))
			return false;
		if (inTime == null) {
			if (other.inTime != null)
				return false;
		} else if (!inTime.equals(other.inTime))
			return false;
		if (serviceType != other.serviceType)
			return false;
		if (tmlDoing == null) {
			if (other.tmlDoing != null)
				return false;
		} else if (!tmlDoing.equals(other.tmlDoing))
			return false;
		if (tmlId == null) {
			if (other.tmlId != null)
				return false;
		} else if (!tmlId.equals(other.tmlId))
			return false;
		if (tmlStatus != other.tmlStatus)
			return false;
		return true;
	}
}
