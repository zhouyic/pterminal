package communicate.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Constants {
	public static Map<String, List<String>> tmlProcessMap=new HashMap<String, List<String>>();//存储终端和指令的map
	public static List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
//	public static Map<String, Integer> processMap = new IdentityHashMap<String, Integer>();
	public static Map<String, String> processMap2 = new IdentityHashMap<String, String>();
	
	
}
