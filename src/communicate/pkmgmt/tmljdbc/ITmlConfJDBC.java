package communicate.pkmgmt.tmljdbc;

import java.util.ArrayList;

import communicate.pkmgmt.dto.ConfigItem;

public interface ITmlConfJDBC {

	/**
	 * 根据tmlId查找groupId和配置项
	 */
	public ArrayList<ConfigItem> getAllConfigItemsBytmlId(String tmlId);
	
} 
