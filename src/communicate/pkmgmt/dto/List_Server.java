package communicate.pkmgmt.dto;

public class List_Server {
	private String ip = "";
	private int port = 0;
	private int tag = 0;
	private int netType = 1;
	//private int groupId = 0;

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("List_Server[");
		sb.append("tag=").append(tag);
		sb.append(", ip=").append(ip);
		sb.append(", port=").append(port);
		sb.append(", netType=").append(netType);
		//sb.append(", groupId=").append(groupId);
		sb.append("]\r\n");
		return sb.toString();
	}


	/*	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}*/

	public int getNetType() {
		return netType;
	}


	public void setNetType(int netType) {
		this.netType = netType;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
