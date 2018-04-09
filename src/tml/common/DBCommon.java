package tml.common;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DBCommon
{
  private static org.apache.log4j.Logger log = Logger.getLogger(DBCommon.class);
  private static String password = null;
  private static String username = "";
  private static String driver = "";
  private static String url = "";
  private Connection conn = null;
  private Statement stmt = null;
  private PreparedStatement prepstmt = null;

  public DBCommon()
  {
    try
    {
      Class.forName(driver).newInstance();
      this.conn = DriverManager.getConnection(url, username, password);
      setAutoCommit(false);
      this.stmt = this.conn.createStatement();
    }
    catch (Exception localException)
    {
      log.error(DBCommon.class, localException);
    }
  }

  public DBCommon(String paramString)
  {
    try
    {
      Class.forName(driver).newInstance();
      this.conn = DriverManager.getConnection(url, username, password);
      setAutoCommit(false);
      prepareStatement(paramString);
    }
    catch (Exception localException)
    {
      log.error(DBCommon.class, localException);
    }
  }

  public void prepareStatement(String paramString)
    throws SQLException
  {
    this.prepstmt = this.conn.prepareStatement(paramString);
  }

  public ResultSet executeQuery(String paramString)
    throws SQLException
  {
    if (this.stmt != null)
      return this.stmt.executeQuery(paramString);
    return null;
  }

  public ResultSet executeQuery()
    throws SQLException
  {
    if (this.prepstmt != null)
      return this.prepstmt.executeQuery();
    return null;
  }

  public void executeUpdate(String paramString)
    throws SQLException
  {
    if (this.stmt != null)
      this.stmt.executeUpdate(paramString);
  }

  public void executeUpdate()
    throws SQLException
  {
    if (this.prepstmt != null)
      this.prepstmt.executeUpdate();
  }

  public void executeBatch()
    throws SQLException
  {
    if (this.stmt != null)
      this.stmt.executeBatch();
  }

  public void addBatch(String paramString)
    throws SQLException
  {
    this.stmt.addBatch(paramString);
  }

  public void setString(int paramInt, String paramString)
    throws SQLException
  {
    this.prepstmt.setString(paramInt, paramString);
  }

  public void setInt(int paramInt1, int paramInt2)
    throws SQLException
  {
    this.prepstmt.setInt(paramInt1, paramInt2);
  }

  public void setBoolean(int paramInt, boolean paramBoolean)
    throws SQLException
  {
    this.prepstmt.setBoolean(paramInt, paramBoolean);
  }

  public void setDate(int paramInt, Date paramDate)
    throws SQLException
  {
    this.prepstmt.setDate(paramInt, paramDate);
  }

  public void setLong(int paramInt, long paramLong)
    throws SQLException
  {
    this.prepstmt.setLong(paramInt, paramLong);
  }

  public void setFloat(int paramInt, float paramFloat)
    throws SQLException
  {
    this.prepstmt.setFloat(paramInt, paramFloat);
  }

  public void setBytes(int paramInt, byte[] paramArrayOfByte)
    throws SQLException
  {
    this.prepstmt.setBytes(paramInt, paramArrayOfByte);
  }

  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2)
    throws SQLException
  {
    this.prepstmt.setBinaryStream(paramInt1, paramInputStream, paramInt2);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp)
    throws SQLException
  {
    this.prepstmt.setTimestamp(paramInt, paramTimestamp);
  }

  public void setAutoCommit(boolean paramBoolean)
    throws SQLException
  {
    if (this.conn != null)
      this.conn.setAutoCommit(paramBoolean);
  }

  public void commit()
    throws SQLException
  {
    this.conn.commit();
  }

  public void rollback()
    throws SQLException
  {
    this.conn.rollback();
  }

  public void close()
  {
    try
    {
      if (this.stmt != null)
      {
        this.stmt.close();
        this.stmt = null;
      }
    }
    catch (Exception localException1)
    {
      log.error("dbBean close stmt error!");
    }
    finally
    {
    }
    return;
  }

  public static Connection getConnection()
    throws SQLException
  {
    Connection localConnection = null;
    try
    {
      Class.forName(driver).newInstance();
      localConnection = DriverManager.getConnection(url, username, password);
      localConnection.setAutoCommit(false);
    }
    catch (Exception localException)
    {
      log.error("Cannot get connection!");
    }
    return localConnection;
  }

  public static void freeConnection(Connection paramConnection)
  {
    if (paramConnection != null)
      try
      {
        paramConnection.close();
      }
      catch (Exception localException)
      {
        log.error("Close connection Error");
        log.error(localException);
      }
  }

  public static void main(String[] paramArrayOfString)
  {
    DBCommon localDBCommon = new DBCommon();
    localDBCommon.close();
  }

  static
  {
//    driver = "com.mysql.jdbc.Driver";
//    url = "jdbc:mysql://dp.cug8angi2aje.eu-central-1.rds.amazonaws.com:3306/tms_ems?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
//    username = "dp";
//    password = "dspcmpdpp";
//	  
    driver = "com.mysql.jdbc.Driver";
    url = "jdbc:mysql://192.168.101.39:3306/totaltms_yixl?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
    username = "root";
    password = "3NMediaSiTV";
//    
    log.debug(driver);
    log.debug(url);
    log.debug(username);
    log.debug(password);
  }
}