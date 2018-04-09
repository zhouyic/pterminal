package communicate.pkmgmt.dto;

import java.io.Serializable;

import communicate.pkmgmt.tmljdbc.TmlConst;

public class ServerList implements Serializable {
	private static final long serialVersionUID = -6683989008518224093L;

	private int id = 0;
	private int tag = TmlConst.serverType.all;
	private String serverName = "";
	private String serverIp = "";
	private int serverPort = 0;
	private String inTime = "";
	private String inOperator = "";
	private String description = "";
	private int groupId = 0;
	private int unitId = 0;
	private String groupName = "";
	private String unitName = "";
	private String strNetType = "";
	private String area = "上海";
	private boolean selected = false;

	private String ipPort = "";

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("ServerList:[id=").append(id)
		.append(", tag=").append(tag)
		.append(", serverName=").append(serverName)
		.append(", serverIp=").append(serverIp)
		.append(", serverPort=").append(serverPort)
		.append(", strNetType=").append(strNetType)
		.append(", area=").append(area)
		.append(", isSelected=").append(selected)
		.append(", ipPort=").append(ipPort)
		.append("]\r\n");
		return sb.toString();
	}

	public String getIpPort() {
		return ipPort;
	}

	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getStrNetType() {
		return strNetType;
	}

	public void setStrNetType(String strNetType) {
		this.strNetType = strNetType;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUnitId() {
		return unitId;
	}

	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getTag() {
		return tag;
	}


	public void setTag(int tag) {
		this.tag = tag;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getInOperator() {
		return inOperator;
	}

	public void setInOperator(String inOperator) {
		this.inOperator = inOperator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
