package communicate.pkmgmt.dto;

import java.io.Serializable;

import net.sf.hibernate.cache.CacheConcurrencyStrategy;

/**
 * 终端历史记录
 * @author 周益才
 *
 */
public class TmlHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String tmlId;
	private String nftime;
	private String type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTmlId() {
		return tmlId;
	}
	public void setTmlId(String tmlId) {
		this.tmlId = tmlId;
	}
	public String getNftime() {
		return nftime;
	}
	public void setNftime(String nftime) {
		this.nftime = nftime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "TmlHistory [id=" + id + ", tmlId=" + tmlId + ", nftime="
				+ nftime + ", type=" + type + "]";
	}
	
}
