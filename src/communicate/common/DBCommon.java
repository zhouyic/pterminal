package communicate.common;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

//import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;



/**
 * 数据库连接
 * @author jinj
 */
public class DBCommon {
	private static Logger log = communicate.common.Logger.getLogger(DBCommon.class);

	private Statement stmt = null;

	private PreparedStatement prepstmt = null;
	private ResultSet rs = null;
	
	//定义c3p0 数据源
	private static ComboPooledDataSource datasource ;
	
	//连接的容器
	public  ThreadLocal<Connection> container = new ThreadLocal<Connection>();
	
	private static String userName = null;
	private static String password = null;
	private static String url = null;
	private static String driverClass = null;
	
	public static void init() {
		userName = GetProperty.userName;
		password = GetProperty.password;
		url = GetProperty.url;
		driverClass = GetProperty.driverClass;
	}
	
    public static void initDataSource() {
    	log.info("-------------initDataSource------------");
		ComboPooledDataSource cpds = new ComboPooledDataSource(); 
		
		cpds = new ComboPooledDataSource();  
		cpds.setUser(GetProperty.userName);  
		cpds.setPassword(GetProperty.password);  
		cpds.setJdbcUrl(GetProperty.url);  
        try {
        	cpds.setDriverClass(GetProperty.driverClass);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        cpds.setInitialPoolSize(GetProperty.initialPoolSize); //初始化时创建的连接数，应在minPoolSize与maxPoolSize之间取值。默认为3； 
        cpds.setMinPoolSize(GetProperty.minPoolSize);  
        cpds.setMaxPoolSize(GetProperty.maxPoolSize);  //连接池中保留的最大连接数。默认为15；  
        cpds.setMaxStatements(GetProperty.maxStatements);  //JDBC的标准参数，用以控制数据源内加载的PreparedStatement数量。但由于预缓存的Statement属 于单个Connection而不是整个连接池。所以设置这个参数需要考虑到多方面的因素，如果maxStatements与 maxStatementsPerConnection均为0，则缓存被关闭。默认为0； 
        cpds.setMaxIdleTime(GetProperty.maxIdleTime);  //最大空闲时间
        cpds.setCheckoutTimeout(GetProperty.checkoutTimeout);//当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出SQLException,如设为0则无限期等待。单位毫秒
        cpds.setMaxIdleTime(GetProperty.maxIdleTime);//最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 
        //cpds.setUnreturnedConnectionTimeout(5);
        cpds.setMaxIdleTimeExcessConnections(20);
        cpds.setMaxConnectionAge(20);
       
        datasource = cpds;
	}
	
	public DBCommon() {

		try {
			log.info("---DBCommon----");
			Connection conn =  getConnectionJdbc();
			this.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (Exception e) {
			log.error(DBCommon.class, e);
		}
	}

	public DBCommon(String sql) {
		try {

//			Connection conn =  getConnection();
//			this.setAutoCommit(false);
			this.prepareStatement(sql);
		} catch (Exception e) {
			log.error(DBCommon.class, e);
		}
	}

	/**
	 * @function prepareStatement
	 * @param sql
	 *            String
	 * @throws SQLException
	 */
	public void prepareStatement(String sql) throws SQLException {
		Connection conn = this.getConnectionJdbc();
		
		prepstmt = conn.prepareStatement(sql);
	}

	/**
	 * @function executeQuery
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		if (stmt != null) {
			return stmt.executeQuery(sql);
		} else {
			Connection conn = this.getConnectionJdbc();
			stmt = conn.createStatement();
			return stmt.executeQuery(sql);
			
		}
	}

	/**
	 * @function executeQuery
	 * @throws SQLException
	 * @return ResultSet
	 */
	public ResultSet executeQuery() throws SQLException {
		if (prepstmt != null) {
			return prepstmt.executeQuery();
		} else {
			throw new SQLException();
		}
	}

	/**
	 * @function executeUpdate
	 * @param sql
	 *            String
	 * @throws SQLException
	 */
	public void executeUpdate(String sql) throws SQLException {
		log.debug("executeUpdate sql=" + sql);
		if (stmt != null){
			stmt.executeUpdate(sql);
		}else {
			Connection conn = this.getConnectionJdbc();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}
	}

	/**
	 * @function executeUpdate
	 * @throws SQLException
	 */
	public void executeUpdate() throws SQLException {
		if (prepstmt != null){
			prepstmt.executeUpdate();
		}else {
			throw new SQLException();
		}
	}

	/**
	 * @function executeUpdate
	 * @throws SQLException
	 */
	public int executeBatch() throws SQLException {
		int res = 0;
		Connection conn = this.getConnectionJdbc();
		if (stmt == null){
			stmt = conn.createStatement();
		}
		int[] tmp = stmt.executeBatch();
		conn.commit();
		for(int i : tmp){
			res += i;
		}
		return res;
	}

	/**
	 * @function addBatch
	 * @param value
	 *            String
	 * @throws SQLException
	 */
	public void addBatch(String value) throws SQLException {
		if (stmt == null){
			Connection conn = this.getConnectionJdbc();
			stmt = conn.createStatement();
		}
		stmt.addBatch(value);
	}

	/**
	 * @function setString
	 * @param index
	 *            int
	 * @param value
	 *            String
	 * @throws SQLException
	 */
	public void setString(int index, String value) throws SQLException {
		
		prepstmt.setString(index, value);
	}

	/**
	 * @function setInt
	 * @param index
	 *            int
	 * @param value
	 *            int
	 * @throws SQLException
	 */
	public void setInt(int index, int value) throws SQLException {
		prepstmt.setInt(index, value);
	}

	/**
	 * @function setBoolean
	 * @param index
	 *            int
	 * @param value
	 *            boolean
	 * @throws SQLException
	 */
	public void setBoolean(int index, boolean value) throws SQLException {
		prepstmt.setBoolean(index, value);
	}

	/**
	 * @function setDate
	 * @param index
	 *            int
	 * @param value
	 *            Date
	 * @throws SQLException
	 */
	public void setDate(int index, Date value) throws SQLException {
		prepstmt.setDate(index, value);
	}

	/**
	 * @function setLong
	 * @param index
	 *            int
	 * @param value
	 *            long
	 * @throws SQLException
	 */
	public void setLong(int index, long value) throws SQLException {
		prepstmt.setLong(index, value);
	}

	/**
	 * @function setFloat
	 * @param index
	 *            int
	 * @param value
	 *            float
	 * @throws SQLException
	 */
	public void setFloat(int index, float value) throws SQLException {
		prepstmt.setFloat(index, value);
	}

	/**
	 * @function setBytes
	 * @param index
	 *            int
	 * @param value
	 *            byte[]
	 * @throws SQLException
	 */
	public void setBytes(int index, byte[] value) throws SQLException {
		prepstmt.setBytes(index, value);
	}

	/**
	 * @function setBinaryStream
	 * @param index
	 *            int
	 * @param value
	 *            InputStream
	 * @param len
	 *            int
	 * @throws SQLException
	 */
	public void setBinaryStream(int index, InputStream value, int len)
	throws SQLException {
		prepstmt.setBinaryStream(index, value, len);
	}

	/**
	 * @function setTimestamp
	 * @param index
	 *            int
	 * @param timestamp
	 *            Timestamp
	 * @throws SQLException
	 */
	public void setTimestamp(int index, Timestamp timestamp)
	throws SQLException {
		prepstmt.setTimestamp(index, timestamp);
	}

	/**
	 * @function setAutoCommit
	 * @param value
	 *            boolean
	 * @throws SQLException
	 */
	public void setAutoCommit(boolean value) throws SQLException {
		Connection conn = this.getConnectionJdbc();
		if (conn != null){
			conn.setAutoCommit(value);
		}
	}

	/**
	 * @function commit
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		Connection conn = this.getConnectionJdbc();
		conn.commit();
	}

	/**
	 * @function rollback
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		Connection conn = this.getConnectionJdbc();
		conn.rollback();
	}

	/**
	 * @function close
	 * @throws Exception
	 * proguard4.6对判断空有个bug???
	 */
	public void close(){
		log.debug("-----db.close();------");
//		try {
//			log.debug("close() NumConnections="+datasource.getNumConnections()+", BusyConnections="
//					+ datasource.getNumBusyConnections()+",  getNumIdleConnections="
//					+ datasource.getNumIdleConnections());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		 if(stmt != null){
	            try {
	            	log.debug("-----stmt.close();------");
	            	stmt.close();
	            } catch (SQLException e) {
	                throw new RuntimeException(e);
	             }finally{
	            	 stmt = null;
	             }
	        }   
        if(prepstmt != null){
            try {
            	log.debug("-----prepstmt.close();------");
            	prepstmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
             }finally{
            	 prepstmt = null;
             }
        }
        
        if(rs != null){
            try {
            	log.debug("-----rs.close();------");
            	rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
             }finally{
            	 rs = null;
             }
        }
	        
	        
        Connection conn =  container.get();
		if(conn != null){
            try {
            	log.debug("-----conn.close();------");
                conn.close();
                container.remove();
            } catch (SQLException e) {
                throw new RuntimeException(e);
             }finally{
                 conn = null;
             }
        }        
//		     try {
//					log.debug("close() NumConnections="+datasource.getNumConnections()+", BusyConnections="
//							+ datasource.getNumBusyConnections()+",  getNumIdleConnections="
//							+ datasource.getNumIdleConnections());
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		
		
	}
	
	public synchronized  Connection getConnectionJdbc() throws SQLException {
		log.debug("--------getConnectionJdbc-------");
		Connection conn = null;
		if (userName == null) {
			init();
		}
		
		
		while(conn==null || conn.isClosed()) {
			log.debug("--------getConnectionJdbc while-------");
			conn = container.get();
			if(conn==null || conn.isClosed()){
				try {
					Class.forName(driverClass).newInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				DriverManager.setLoginTimeout(6);
				conn = DriverManager.getConnection(url, userName, password);
				
				conn.setAutoCommit(false);
	            container.remove();
	            container.set(conn); 	
				
			}
			 
		}
		
		return conn;   
	}

	/*private  synchronized  Connection getConnection() throws SQLException {
		log.debug("--------getConnection-------");
		Connection conn = null;
		while(conn==null || conn.isClosed()){
			log.debug("--------getConnection while-------");
			conn = container.get();
		
			if(conn==null){
				
		         if (datasource == null) {   
		             initDataSource();
		         }   
		         try {   
		        	 log.debug("---datasource--");
		             conn = datasource.getConnection(); 
		             //将Connection设置到ThreadLocal线程变量中
		             conn.setAutoCommit(false);
		             container.remove();
		             container.set(conn); 
		         } catch (SQLException e) {   
		             // TODO Auto-generated catch block   
		             e.printStackTrace();   
		         } 
		    }else if(conn.isClosed()){
		    	log.debug("--------conn.isClosed()-------");
		    	
		    	conn = datasource.getConnection(); 
	             //将Connection设置到ThreadLocal线程变量中
	             conn.setAutoCommit(false);
	             container.remove();
	             container.set(conn); 
		    	
			}
		}
		return conn;   
	}*/

//	public static void freeConnection(Connection conn) {
//		if (conn != null) {
//			try {
//				conn.close();
//			} catch (Exception e) {
//				log.error("Close connection Error");
//				log.error(e);
//			}
//		}
//	}

	public static void main(String[] args) throws SQLException {
//		Connection conn = null;
//        PreparedStatement prepareStatement =null;
//        try {
//        	//获取连接
//        	conn=DBCommon.getConnection();
//			conn.setAutoCommit(false);
//			String sql = "INSERT INTO error_log(id,tmlId,sn,emergency,errorSources,errorType,doTime,DDESC)VALUES(null,?,?,?,?,?,?,?)";
//			
//			//获取预编译的statement
//			prepareStatement = conn.prepareStatement(sql);
//			//设置参数（sql语句的参数设置索引从1开始）
//			//执行sql
//			prepareStatement.setString(1,"12345678");   
//			prepareStatement.setString(2,"snStr2");   
//			prepareStatement.setString(3,"henji2");
//			prepareStatement.setString(4,"app2");   
//			prepareStatement.setString(5,"type2");  
//			prepareStatement.setString(6,"2017-02-28 12:12:11");
//			prepareStatement.setString(7,"desc2");
//			int executeUpdate = prepareStatement.executeUpdate();
//			System.out.println("插入行数：   "+executeUpdate);
//			conn.commit();  // 提交
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			C3P0Util.release(conn, prepareStatement, null);
//		}
	}
}

