package communicate.pkmgmt.dto;

import java.io.Serializable;

public class STBLogDTO implements Serializable {

	private static final long serialVersionUID = -4185984208940351520L;

	private String username;

	private int tmlType;

	private int serviceType;

	private String cntName;

	private String channelIPAddress;

	private int channelStartTime;

	private int channelStopTime;

	private int doStartTime;

	private int doStopTime;

	private int doType;

	private int doResult;

	private String indbTime;

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("STBLogDTO[usernaem=").append(username)
		.append(", tmlType=").append(tmlType)
		.append(", serviceType=").append(serviceType)
		.append(", cntName=").append(cntName)
		.append(", channelIPAddress=").append(channelIPAddress)
		.append(", channelStartTime=").append(channelStartTime)
		.append(", channelStopTime=").append(channelStopTime)
		.append(", doStartTime=").append(doStartTime)
		.append(", doStopTime=").append(doStopTime)
		.append(", doType=").append(doType)
		.append(", doResult=").append(doResult)
		.append("]\r\n");
		return sb.toString();
	}

	public String getChannelIPAddress() {
		return channelIPAddress;
	}

	public void setChannelIPAddress(String channelIPAddress) {
		this.channelIPAddress = channelIPAddress;
	}

	public int getChannelStartTime() {
		return channelStartTime;
	}

	public void setChannelStartTime(int channelStartTime) {
		this.channelStartTime = channelStartTime;
	}

	public int getChannelStopTime() {
		return channelStopTime;
	}

	public void setChannelStopTime(int channelStopTime) {
		this.channelStopTime = channelStopTime;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
	}

	public int getDoResult() {
		return doResult;
	}

	public void setDoResult(int doResult) {
		this.doResult = doResult;
	}

	public int getDoStartTime() {
		return doStartTime;
	}

	public void setDoStartTime(int doStartTime) {
		this.doStartTime = doStartTime;
	}

	public int getDoStopTime() {
		return doStopTime;
	}

	public void setDoStopTime(int doStopTime) {
		this.doStopTime = doStopTime;
	}

	public int getDoType() {
		return doType;
	}

	public void setDoType(int doType) {
		this.doType = doType;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getTmlType() {
		return tmlType;
	}

	public void setTmlType(int tmlType) {
		this.tmlType = tmlType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIndbTime() {
		return indbTime;
	}

	public void setIndbTime(String indbTime) {
		this.indbTime = indbTime;
	}
}
