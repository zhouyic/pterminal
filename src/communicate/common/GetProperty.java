package communicate.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class GetProperty {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = communicate.common.Logger.getLogger(GetProperty.class);

	public static String TML_DB_IP;
	public static String CMS_DB_IP;

	public static String SLIVER_CMS_DB_IP="localhost";
	public static String MASTER_CMS_DB_IP="localhost";
	public static String MEM_IP="localhost";
	
	public static String udpmaxthread;
	public static String tcpmaxthread;
	public static String tcpSSLMaxthread;
	public static String driverClass; //数据库驱动
	public static String userName; //用户名
	public static String password; //密码
	public static String url; //数据连接路劲
//	c3p0.minPoolSize=3  
//			c3p0.maxPoolSize=25
	public static int minPoolSize;
	public static int maxPoolSize;
	public static int maxIdleTime;
	public static int initialPoolSize;
	public static int maxStatements;
	public static int checkoutTimeout;
	
	static {
		
			Properties properties = new Properties();
			try {

				properties.load(new FileInputStream(new File("/home/myconfig/tmlconfig.properties")));
//				properties.load(new FileInputStream(new File("D:/workspace/eclipse/pterminalV2/conf/tmlconfig.properties")));

			} catch (Exception e) {
				log.error(e, e);
				
				try {

					properties.load(new FileInputStream(new File("/home/myconfig/tmlconfig.properties")));
//					properties.load(new FileInputStream(new File("D:/workspace/eclipse/pterminalV2/conf/tmlconfig.properties")));

//					properties.load(new FileInputStream(new File("D:/workspace/eclipse/pterminal/conf/tmlconfig.properties")));
				} catch (FileNotFoundException e1) {
					log.error(e1, e1);
				} catch (IOException e1) {
					log.error(e1, e1);
				}
			}

			TML_DB_IP = properties.getProperty("TML_DB_IP");
			CMS_DB_IP = properties.getProperty("CMS_DB_IP");
			

			
			udpmaxthread = properties.getProperty("udpmaxthread");
			tcpmaxthread = properties.getProperty("tcpmaxthread");
			tcpSSLMaxthread = properties.getProperty("tcpSSLMaxthread");
		    
			driverClass=properties.getProperty("driverClass");
			userName=properties.getProperty("userName");
			password=properties.getProperty("password");
			url=properties.getProperty("url");
			minPoolSize=Integer.parseInt(properties.getProperty("c3p0.minPoolSize"));
			maxPoolSize=Integer.parseInt(properties.getProperty("c3p0.maxPoolSize"));
			maxIdleTime=Integer.parseInt(properties.getProperty("c3p0.maxIdleTime"));
			initialPoolSize=Integer.parseInt(properties.getProperty("c3p0.initialPoolSize"));
			maxIdleTime=Integer.parseInt(properties.getProperty("c3p0.maxStatements"));
			checkoutTimeout = Integer.parseInt(properties.getProperty("c3p0.checkoutTimeout"));
		
			
			
			log.info("\n TML_DB_IP:"+TML_DB_IP+"\n udpmaxthread:"+udpmaxthread
				+"\n tcpmaxthread:"+tcpmaxthread+"\n tcpSSLMaxthread:"+tcpSSLMaxthread);
	}
}
