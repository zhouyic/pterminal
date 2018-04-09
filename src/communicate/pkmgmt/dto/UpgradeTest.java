package communicate.pkmgmt.dto;

public class UpgradeTest {
	String tmlId;
	String ip;

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("UpgradeTest:[tmlId=").append(tmlId)
		.append(", ip=").append(ip)
		.append("]\r\n");
		return sb.toString();
	}

	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
