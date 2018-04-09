package communicate.pkmgmt.dto;

public class StatusDTO {
	private String id = "";
	private int status = 0;
	private int status2 = 0;
	private String bakStr = "";

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("DownStatus[");
		sb.append("id=").append(id);
		sb.append(", status=").append(status);
		sb.append(", status2=").append(status2);
		sb.append(", bakStr=").append(bakStr);
		sb.append("]\r\n");
		return sb.toString();
	}

	public int getStatus2() {
		return status2;
	}

	public void setStatus2(int status2) {
		this.status2 = status2;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getBakStr() {
		return bakStr;
	}

	public void setBakStr(String bakStr) {
		this.bakStr = bakStr;
	}
}
