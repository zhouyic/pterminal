package communicate.pkmgmt.stb.test;

import java.util.ArrayList;

import communicate.pkmgmt.dto.ConfigItem;
import communicate.pkmgmt.stb.AppPortalURLRequest;

public class TestJdbc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AppPortalURLRequest appPortal = new AppPortalURLRequest();
		ArrayList<ConfigItem> list = null;
		//list = getURL();
		list =(ArrayList<ConfigItem>)appPortal.getURLBytmlId("");
		System.out.println(list);
	}

}
