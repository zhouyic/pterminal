package communicate.common;

import java.util.Date;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import communicate.pkmgmt.PackageConstant;
import communicate.pkmgmt.dto.Order;

public class MemCached {
	private static final Logger log = Logger.getLogger(MemCached.class);
	// create a static client as most installs only need
	// a single instance
	protected static MemCachedClient mcc = new MemCachedClient();

	protected static MemCached memCached = new MemCached();
	// set up connection pool once at class load
	static {
		GetProperty properties = new GetProperty();

		String memcache_ip = properties.MEM_IP;
		log.info("memcache_ip:"+memcache_ip);
		String memcache_server = memcache_ip+":"+PackageConstant.MEM_PORT;
		//String memcache_server = "192.168.100.29:11211";

		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();
		try {
			// server list and weights
			String[] servers =
			{
					memcache_server,
					//			  "server2.mydomain.com:1624",
					//			  "server3.mydomain.com:1624"
			};

			Integer[] weights = {3};



			// set the servers and the weights
			pool.setServers(servers);
			pool.setWeights(weights);

			// 设置初始连接数、最小和最大连接数以及最大处理时间
			pool.setInitConn(5);
			pool.setMinConn(5);
			pool.setMaxConn(250);
			pool.setMaxIdle(1000*60*60*6);

			// 设置主线程的睡眠时间
			pool.setMaintSleep(30);

			// set some TCP settings
			// disable nagle
			// set the read timeout to 3 secs
			// and don't set a connect timeout
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setSocketConnectTO(0);

			// initialize the connection pool
			pool.initialize();

			// 压缩设置，超过指定大小（单位为K）的数据都会被压缩
			mcc.setCompressEnable(true);
			mcc.setCompressThreshold(64*1024 );
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 保护型构造方法，不允许实例化！
	 *
	 */
	protected MemCached()
	{

	}

	/**
	 * 获取唯一实例.
	 * @return
	 */
	public static MemCached getInstance()
	{
		return memCached;
	}

	/**
	 * 添加一个指定的值到缓存中.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean add(String key, Object value)
	{
		return mcc.add(key, value);
	}

	public boolean add(String key, Object value, Date expiry)
	{
		return mcc.add(key, value, expiry);
	}

	public boolean replace(String key, Object value)
	{
		return mcc.replace(key, value);
	}

	public boolean replace(String key, Object value, Date expiry)
	{
		return mcc.replace(key, value, expiry);
	}

	/**
	 * 根据指定的关键字获取对象.
	 * @param key
	 * @return
	 */
	public Object get(String key)
	{
		return mcc.get(key);
	}

	public void addkey(String key, String value) {
		DBCommon_cms2 db = null;
		String sql = "insert memcached values ('"+key+"','"+value+"')";
		try {
			db = new DBCommon_cms2(sql);
			db.executeUpdate();
			db.commit();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}


	// from here on down, you can call any of the client calls
	public static void main(String[] args) {


		MemCached cache = MemCached.getInstance();
		System.out.println("----------"+cache.get("723BBA850A24E8A1EB89D02195EF2C41"));
		//System.out.println(cache.replace("08394E4E0B23F1B7D83C749FA6CA0AD3", "ttttttttttttttt"));
		Order o = new Order();
		o.setContentId("F101211350784221");
		o.setCount(20);
		System.out.println(cache.replace("723BBA850A24E8A1EB89D02195EF2C41", o.toString()));
		//System.out.println("----------"+cache.get("08394E4E0B"));
		//long start = System.currentTimeMillis();
		/*for (int i = 0; i<=100000;i++) {
			System.out.println(cache.get("E18D926541D9FD268C8E2260DA8B9FB3"));
			//cache.get("136DF848F966CBC625805F0803EC728B");
		}*/
		//long elapse = System.currentTimeMillis() - start;
		//System.out.print("elapse:"+elapse);

		/*
		DBCommon db = null;
		List<Menu> list=new ArrayList<Menu>();
		String querySQL="select hierarchyMenuId,hierarchyMenuName from PORTAL_HIERARCHY_MENU where parentName='电影' order by menuIndex";
		String key=DigestUtils.md5Hex(querySQL);
		Object ob=cache.get(key);
		if(ob==null||ob.equals("")){
			ResultSet rs = null;
			try {
				db = new DBCommon(querySQL);
				rs = db.executeQuery();
				while (rs.next()) {
					Menu dto = new Menu();
					dto.setMenuId(rs.getString("hierarchyMenuId"));
					dto.setMenuName(rs.getString("hierarchyMenuName"));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (db != null) {
					db.close();
				}
			}
			cache.add(key, list);
			//要把List<Menu>格式存入到缓存中，必须要先把Menu序列化，即public class Menu implements Serializable{...}
			rs = null;
		}else{
			list=(List<Menu>)ob;
		}
        System.out.println("get value : "+list.get(0).getMenuId());
//      清空所有缓存
//    	mcc.flushAll();
		 */}
}