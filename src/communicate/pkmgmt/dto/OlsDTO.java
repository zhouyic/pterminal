package communicate.pkmgmt.dto;

public class OlsDTO {
	private String ip = "";
	private int port = 0;

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("OlsDTO:[");
		sb.append(" ip=").append(ip);
		sb.append(", port=").append(port);
		sb.append("]\r\n");
		return sb.toString();
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
}