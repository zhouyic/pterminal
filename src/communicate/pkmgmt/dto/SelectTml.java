package communicate.pkmgmt.dto;

import java.io.Serializable;

public class SelectTml implements Serializable {
	private static final long serialVersionUID = -6683989008518224093L;

	private String tmlId = "";
	private String customerType = "";
	private boolean selected = false;
	private String speIpPort = "";

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("SelectTml:[tmlId=").append(tmlId)
		.append(", customerType=").append(customerType)
		.append(", isSelected=").append(selected)
		.append(", speIpPort=").append(speIpPort)
		.append("]\r\n");
		return sb.toString();
	}

	public String getSpeIpPort() {
		return speIpPort;
	}

	public void setSpeIpPort(String speIpPort) {
		this.speIpPort = speIpPort;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getTmlId() {
		return tmlId;
	}

	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
}
