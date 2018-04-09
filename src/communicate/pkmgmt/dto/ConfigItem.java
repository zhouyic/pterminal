package communicate.pkmgmt.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;
/**

 * @author guotao
 */
public class ConfigItem implements Serializable{
	private static final long serialVersionUID = -6359910636941103273L;
	private static final Logger log = communicate.common.Logger.getLogger(ConfigItem.class);

	private String ip;
	private String port;
	private String name;
	private int type;//0:IP     1:IP&PORT
	private String desc;
	private int confItemId;
	private int tag;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getConfItemId() {
		return confItemId;
	}

	public void setConfItemId(int confItemId) {
		this.confItemId = confItemId;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + confItemId;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + tag;
		result = prime * result + type;
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
		ConfigItem other = (ConfigItem) obj;
		if (confItemId != other.confItemId)
			return false;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (tag != other.tag)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Config[name:").append(name);
		str.append(" ,ip:").append(ip);
		str.append(" ,port:").append(port);
		str.append(" ,desc:").append(desc);
		str.append(" ,itemid:").append(confItemId);
		str.append(" ,tag:").append(tag);
		if(type==0)
			str.append(" ,type:").append("IP");
		else if(type==1)
			str.append(" ,type:").append("IP&Port");
		else
		str.append(" ,type:").append("unknown");
		str.append("]\r\n");
		return str.toString();
	}
}
