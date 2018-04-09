package communicate.common;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheCommon {
	protected static CacheManager cacheManager = CacheManager.getInstance();

	public static Cache getCommonCache(){
		Cache cache = cacheManager.getCache("common");
		return cache;
	}
}
