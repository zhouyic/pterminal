package tml.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClient
{
  private static org.apache.log4j.Logger log = Logger.getLogger(DBClient.class);
  private static String password;
  private static String username;
  private static String driver;
  private static String url;
  private Connection conn;

  public Connection getConnection()
  {
    try
    {
      Class.forName(getDriver()).newInstance();
      this.conn = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }
    catch (Exception localException)
    {
      log.info("Can not find the data source!");
    }
    return this.conn;
  }

  public void closeConnection()
  {
    if (this.conn != null)
      try
      {
        this.conn.close();
      }
      catch (Exception localException)
      {
      }
  }

  public Statement getStatement()
  {
    Statement localStatement = null;
    if (this.conn == null)
      this.conn = getConnection();
    if (this.conn != null)
      try
      {
        localStatement = this.conn.createStatement();
      }
      catch (SQLException localSQLException)
      {
        log.info("Can not get Statement!");
        log.debug(localSQLException.getMessage(), localSQLException);
      }
    return localStatement;
  }

  private String getDriver()
  {
    return driver;
  }

  private String getPassword()
  {
    return password;
  }

  private String getUrl()
  {
    return url;
  }

  private String getUsername()
  {
    return username;
  }

  public static void main(String[] paramArrayOfString)
    throws SQLException
  {
    DBClient localDBClient = new DBClient();
  }

  static
  {
    driver = "com.mysql.jdbc.Driver";
    url = "jdbc:mysql://192.168.47.16:3306/tms?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
    username = "root";
    password = "3NMediaSiTV";
    log.info("driver = " + driver);
    log.debug("url = " + url);
    log.debug("userName = " + username);
    log.debug("password = " + password);
  }
}