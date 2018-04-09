package communicate.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;


/**
 * 数据库连接
 * @author jinj
 *
 */
public class DBCommon_dcms {
	private static Logger log = communicate.common.Logger.getLogger(DBCommon_dcms.class);

	private static String password = null;

	private static String username = "";

	private static String driver = "";

	private static String url = "";

	private Connection conn = null;

	private Statement stmt = null;

	private PreparedStatement prepstmt = null;

	static {
		GetProperty properties = new GetProperty();
		driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://"+properties.SLIVER_CMS_DB_IP+":3306/cms2?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
		username = "dsp";
		password = "3NMediaSiTVcms";

		/*driver = "com.mysql.jdbc.Driver";
		url = "jdbc:mysql://127.0.0.1:3306/ols?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
		username = "ols";
		password = "ols";*/

		log.debug(driver);
		log.debug(url);
		log.debug(username);
		log.debug(password);
	}

	public DBCommon_dcms() {
		try {
			Class.forName(driver).newInstance();
			DriverManager.setLoginTimeout(6);
			conn = DriverManager.getConnection(url, username, password);
			this.setAutoCommit(false);
			stmt = conn.createStatement();
		} catch (Exception e) {
			log.error(DBCommon_dcms.class, e);
		}
	}

	public DBCommon_dcms(String sql) {
		try {
			Class.forName(driver).newInstance();
			DriverManager.setLoginTimeout(6);
			conn = DriverManager.getConnection(url, username, password);
			this.setAutoCommit(false);
			this.prepareStatement(sql);
		} catch (Exception e) {
			log.error(DBCommon_dcms.class, e);
		}
	}

	/**
	 * @function prepareStatement
	 * @param sql
	 *            String
	 * @throws SQLException
	 */
	public void prepareStatement(String sql) throws SQLException {
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
			return null;
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
			return null;
		}
	}

	/**
	 * @function executeUpdate
	 * @param sql
	 *            String
	 * @throws SQLException
	 */
	public void executeUpdate(String sql) throws SQLException {
		if (stmt != null){
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
		}
	}

	/**
	 * @function executeUpdate
	 * @throws SQLException
	 */
	public void executeBatch() throws SQLException {
		if (stmt != null){
			stmt.executeBatch();
		}
	}

	/**
	 * @function addBatch
	 * @param value
	 *            String
	 * @throws SQLException
	 */
	public void addBatch(String value) throws SQLException {
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
		if (this.conn != null){
			this.conn.setAutoCommit(value);
		}
	}

	/**
	 * @function commit
	 * @throws SQLException
	 */
	public void commit() throws SQLException {
		this.conn.commit();
	}

	/**
	 * @function rollback
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {
		this.conn.rollback();
	}

	/**
	 * @function close
	 * @throws Exception
	 */
	public void close() {
		try {
			//if (stmt != null) {
			stmt.close();
			stmt = null;
			//}
		} catch (Exception e) {
			//log.error("dbBean close stmt error!");
		} finally {
			try {
				//if (prepstmt != null) {
				prepstmt.close();
				prepstmt = null;
				//	}
			} catch (Exception e) {
				log.error("dbBean close prepstmt error!");
			} finally {
				try {
					//	if (conn != null) {
					conn.close();
					conn = null;
					//	}
				} catch (Exception e) {
					log.error("dbBean close conn error!");
				}
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(driver).newInstance();
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			log.error("Cannot get connection!");
		}
		return conn;
	}

	public static void freeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				log.error("Close connection Error");
				log.error(e);
			}
		}
	}

	public static void main(String[] args) {
		DBCommon_dcms client = new DBCommon_dcms();
	}
}