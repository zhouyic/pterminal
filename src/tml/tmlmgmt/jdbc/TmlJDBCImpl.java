package tml.tmlmgmt.jdbc;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import communicate.pkmgmt.tmljdbc.TmlConst;

import tml.common.DBCommon;
import tml.common.TimeMgmt;
import tml.common.exception.DataExistException;
import tml.common.exception.SqlException;
import tml.sysmgmt.Dom;
import tml.tmlmgmt.TmlConst.netType;
import tml.tmlmgmt.TmlConst.serverType;
import tml.tmlmgmt.TmlConst.tmlStatus;
import tml.tmlmgmt.TmlConst.tmlType;
import tml.tmlmgmt.dto.Group;
import tml.tmlmgmt.dto.SelectTml;
import tml.tmlmgmt.dto.ServerList;
import tml.tmlmgmt.dto.Terminal;
import tml.tmlmgmt.dto.TmlDoInfo;
import tml.tmlmgmt.dto.TmlSys;
import tml.tmlmgmt.dto.UpgradeTest;

public class TmlJDBCImpl
  implements ITmlJDBC
{
  private static final org.apache.log4j.Logger log = tml.common.Logger.getLogger(TmlJDBCImpl.class);
  DBCommon db = null;

  public String getUserName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().equals("")))
      return null;
    String str1 = "SELECT customerId FROM TML_BASE WHERE tmlId=?";
    ResultSet localResultSet = null;
    String str2 = "";
    try
    {
      this.db = new DBCommon(str1);
      this.db.setString(1, paramString.toUpperCase());
      localResultSet = this.db.executeQuery();
      if (localResultSet.next())
        str2 = localResultSet.getString(1);
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return str2;
  }

  public int registerTml(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    int i = 0;
    try
    {
      int j = 0;
      String str1 = "SELECT COUNT(*) FROM TML_BASE WHERE customerId=? AND customerPwd=? AND tmlId=?";
      this.db = new DBCommon(str1);
      this.db.setString(1, paramString1);
      this.db.setString(2, paramString2);
      this.db.setString(3, paramString4.toUpperCase());
      ResultSet localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        j = localResultSet.getInt(1);
      localResultSet = null;
      if (j == 0)
      {
        String str2 = "INSERT INTO TML_BASE (customerId, customerPwd, tmlType, tmlId, inTime, inoperator, description, customerType, ipAddr) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        this.db = new DBCommon(str2);
        this.db.setString(1, paramString1);
        this.db.setString(2, paramString2);
        this.db.setInt(3, paramInt);
        this.db.setString(4, paramString4.toUpperCase());
        this.db.setString(5, paramString6);
        this.db.setString(6, paramString7);
        this.db.setString(7, paramString8);
        this.db.setString(8, paramString3);
        this.db.setString(9, paramString5);
        this.db.executeUpdate();
        this.db.commit();
      }
    }
    catch (SQLException localSQLException)
    {
      i = 501;
      log.error(this, localSQLException);
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return i;
  }

  public int registerTmlToDom(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt2)
  {
    int i = 0;
    try
    {
      int j = 0;
      String str1 = "SELECT COUNT(*) FROM TML_BASE WHERE customerId=? AND customerPwd=? AND tmlId=?";
      this.db = new DBCommon(str1);
      this.db.setString(1, paramString1);
      this.db.setString(2, paramString2);
      this.db.setString(3, paramString4.toUpperCase());
      ResultSet localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        j = localResultSet.getInt(1);
      localResultSet = null;
      if (j == 0)
      {
        String str2 = "INSERT INTO TML_BASE (customerId, customerPwd, tmlType, tmlId, inTime, inoperator, description, customerType, ipAddr,groupId) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        this.db = new DBCommon(str2);
        this.db.setString(1, paramString1);
        this.db.setString(2, paramString2);
        this.db.setInt(3, paramInt1);
        this.db.setString(4, paramString4.toUpperCase());
        this.db.setString(5, paramString6);
        this.db.setString(6, paramString7);
        this.db.setString(7, paramString8);
        this.db.setString(8, paramString3);
        this.db.setString(9, paramString5);
        this.db.setInt(10, paramInt2);
        this.db.executeUpdate();
        this.db.commit();
      }
    }
    catch (SQLException localSQLException)
    {
      i = 501;
      log.error(this, localSQLException);
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return i;
  }

  public boolean insertTmlSpe(List<SelectTml> paramList)
  {
    boolean bool = true;
    if ((paramList == null) || (paramList.size() == 0))
      return bool;
    DBCommon localDBCommon = new DBCommon();
    try
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        SelectTml localSelectTml = (SelectTml)localIterator.next();
        if (localSelectTml != null)
        {
          String str1 = localSelectTml.getTmlId();
          String str2 = localSelectTml.getSpeIpPort();
          String str3 = "INSERT INTO TML_SPE(tmlId, speIp) VALUES('" + str1 + "','" + str2 + "')";
          localDBCommon.addBatch(str3);
        }
      }
      localDBCommon.executeBatch();
      localDBCommon.commit();
    }
    catch (Exception localException)
    {
      bool = false;
      log.error(localException.getMessage());
    }
    finally
    {
      localDBCommon.close();
    }
    return bool;
  }

  public int findNuitByName(String paramString)
    throws SqlException
  {
    int i = 0;
    ResultSet localResultSet = null;
    String str = "SELECT DOMID FROM SYS_DOM WHERE DONAME=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      if (localResultSet.next())
        i = localResultSet.getInt(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return i;
  }

  public int findGroupIdByName(String paramString)
    throws SqlException
  {
    int i = 0;
    ResultSet localResultSet = null;
    String str = "SELECT DOMID FROM SYS_DOM WHERE DONAME=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      if (localResultSet.next())
        i = localResultSet.getInt(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return i;
  }

  public int getUserId(String paramString)
    throws SqlException
  {
    int i = 0;
    ResultSet localResultSet = null;
    String str = "SELECT USERID FROM SYS_USER WHERE NAME=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      if (localResultSet.next())
        i = localResultSet.getInt(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return i;
  }

  public void registerDom(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt3)
    throws SqlException
  {
    String str = "INSERT INTO SYS_DOM (DOMID, DONAME, PARENTID, PARENTNAME, TYPE, DDESC, CREATE_TIME, CREATOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try
    {
      this.db = new DBCommon(str);
      this.db.setInt(1, paramInt1);
      this.db.setString(2, paramString1);
      this.db.setString(3, paramString3);
      this.db.setString(4, paramString4);
      this.db.setString(5, paramString5);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public List<SelectTml> getTmls()
    throws SqlException
  {
    String str = "SELECT tmlId,customerType FROM TML_BASE WHERE isSpe=0";
    ArrayList localArrayList = new ArrayList();
    SelectTml localSelectTml = null;
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localSelectTml = new SelectTml();
        localSelectTml.setTmlId(localResultSet.getString("tmlId"));
        localSelectTml.setCustomerType(localResultSet.getString("customerType"));
        localArrayList.add(localSelectTml);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public List<Terminal> getTmls(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
    throws SqlException
  {
    int i = 0;
    String str1 = "";
    if ((paramString4 != null) && (!paramString4.trim().equals("")))
    {
      i = 1;
      str1 = str1 + " AND c.tmlDownsource='" + paramString4 + "'";
    }
    if ((paramString5 != null) && (!paramString5.trim().equals("")))
    {
      i = 1;
      str1 = str1 + " AND c.tmlDownrate>='" + paramString5 + "'";
    }
    if ((paramString6 != null) && (!paramString6.trim().equals("")))
    {
      i = 1;
      str1 = str1 + " AND c.tmlDoingCnt like '%" + paramString6 + "%'" + " AND c.tmlStatus=4";
    }
    else if ((paramString7 != null) && (!paramString7.trim().equals("")))
    {
      i = 1;
      str1 = str1 + " AND c.tmlDoingCnt like '%" + paramString7 + "%'" + "AND c.tmlStatus=2";
    }
    if ((paramInt4 == 2) || (paramInt4 == 4))
    {
      i = 1;
      str1 = str1 + " AND c.tmlStatus=" + paramInt4;
    }
    int j = 0;
    String str2 = "";
    if ((paramString1 != null) && (!paramString1.trim().equals("")))
    {
      j = 1;
      str2 = str2 + " AND b.tmlId like '%" + paramString1.toUpperCase() + "%'";
    }
    if (paramInt1 != 0)
    {
      j = 1;
      str2 = str2 + " AND b.tmlType=" + paramInt1;
    }
    if (paramBoolean == true)
    {
      j = 1;
      str2 = str2 + " AND b.tmlStatus>0";
    }
    else if (paramInt4 != -1)
    {
      j = 1;
      str2 = str2 + " AND b.tmlStatus=" + paramInt4;
    }
    if ((paramString2 != null) && (!paramString2.trim().equals("")))
    {
      j = 1;
      str2 = str2 + " AND b.inTime>='" + paramString2 + "'";
    }
    if ((paramString3 != null) && (!paramString3.trim().equals("")))
    {
      j = 1;
      str2 = str2 + " AND b.inOperator='" + paramString3 + "'";
    }
    String str3 = "";
    if (i != 0)
      str3 = "SELECT DISTINCT b.tmlId,tmlType,b.tmlStatus,b.inTime,b.telNo,b.groupId,s.DONAME,ipAddr,onTime,tmlDoingCnt,c.tmlStatus,tmlDownrate,tmlDownsource FROM TML_BASE b, TML_CURRENT_DO c,SYS_DOM s WHERE c.tmlId=b.tmlId AND b.groupId=s.DOMID";
    else if ((i == 0) && (j != 0))
      str3 = "SELECT b.tmlId,tmlType,b.tmlStatus,b.inTime,ipAddr,onTime,b.telNo,b.groupId,s.DONAME  FROM TML_BASE b,SYS_DOM s WHERE 1=1 AND b.groupId=s.DOMID";
    else
      str3 = "SELECT b.tmlId,tmlType,b.tmlStatus,b.inTime,ipAddr,onTime,b.telNo,b.groupId,s.DONAME  FROM TML_BASE b,SYS_DOM s WHERE 1=1 AND b.groupId=s.DOMID";
    if (j != 0)
      str3 = str3 + str2;
    if (i != 0)
      str3 = str3 + str1 + " AND b.tmlStatus>'" + 0 + "'";
    if (paramInt2 >= 0)
      str3 = str3 + " AND b.groupId=" + paramInt2 + " ";
    Terminal localTerminal = null;
    ResultSet localResultSet = null;
    ArrayList localArrayList = new ArrayList();
    log.info(str3);
    int k;
    try
    {
      this.db = new DBCommon(str3);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localTerminal = new Terminal();
        String str4 = localResultSet.getString("b.tmlId");
        localTerminal.setTmlId(str4);
        localTerminal.setStrTmlType(TmlConst.tmlType.getStrTmlType(localResultSet.getInt("tmlType")));
        k = localResultSet.getInt("b.tmlStatus");
        localTerminal.setStrTmlStatus(TmlConst.tmlStatus.getStrTmlStatus(k));
        localTerminal.setTmlStatus(k);
        localTerminal.setGroupId(localResultSet.getInt("b.groupId"));
        localTerminal.setGroupName(localResultSet.getString("s.DONAME"));
        if (k == 3)
          localTerminal.setShowGetSys(true);
        localTerminal.setInTime(localResultSet.getString("inTime"));
        localTerminal.setIpAddr(localResultSet.getString("ipAddr"));
        localTerminal.setOnTime(localResultSet.getString("onTime"));
        localTerminal.setTelNo(localResultSet.getString("telNo"));
        localArrayList.add(localTerminal);
      }
    }
    catch (SQLException localSQLException1)
    {
      log.error(this, localSQLException1);
      throw new SqlException(localSQLException1.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    if (paramInt2 <= 0)
    {
      if (i != 0)
        str3 = "SELECT DISTINCT b.tmlId,tmlType,b.tmlStatus,b.inTime,b.telNo,ipAddr,onTime,tmlDoingCnt,c.tmlStatus,tmlDownrate,tmlDownsource FROM TML_BASE b, TML_CURRENT_DO c WHERE c.tmlId=b.tmlId AND b.groupId=0 ";
      else if ((i == 0) && (j != 0))
        str3 = "SELECT b.tmlId,tmlType,b.tmlStatus,b.inTime,ipAddr,onTime,b.telNo,b.groupId  FROM TML_BASE b WHERE b.groupId=0 ";
      else
        str3 = "SELECT b.tmlId,tmlType,b.tmlStatus,b.inTime,ipAddr,onTime,b.telNo,b.groupId  FROM TML_BASE b WHERE b.groupId=0 ";
      if (j != 0)
        str3 = str3 + str2;
      if (i != 0)
        str3 = str3 + str1 + " AND b.tmlStatus>'" + 0 + "'";
      localTerminal = null;
      localResultSet = null;
      log.info(str3);
      try
      {
        this.db = new DBCommon(str3);
        localResultSet = this.db.executeQuery();
        while (localResultSet.next())
        {
          localTerminal = new Terminal();
          String str5 = localResultSet.getString("b.tmlId");
          localTerminal.setTmlId(str5);
          localTerminal.setStrTmlType(TmlConst.tmlType.getStrTmlType(localResultSet.getInt("tmlType")));
          k = localResultSet.getInt("b.tmlStatus");
          localTerminal.setStrTmlStatus(TmlConst.tmlStatus.getStrTmlStatus(k));
          localTerminal.setTmlStatus(k);
          localTerminal.setGroupId(0);
          localTerminal.setGroupName("无分组");
          if (k == 3)
            localTerminal.setShowGetSys(true);
          localTerminal.setInTime(localResultSet.getString("inTime"));
          localTerminal.setIpAddr(localResultSet.getString("ipAddr"));
          localTerminal.setOnTime(localResultSet.getString("onTime"));
          localTerminal.setTelNo(localResultSet.getString("telNo"));
          localArrayList.add(localTerminal);
        }
      }
      catch (SQLException localSQLException2)
      {
        log.error(this, localSQLException2);
        throw new SqlException(localSQLException2.getMessage());
      }
      finally
      {
        this.db.close();
        this.db = null;
      }
    }
    return localArrayList;
  }

  public List<SelectTml> getTmlSpe(String paramString1, String paramString2)
  {
    ArrayList localArrayList = new ArrayList();
    String str = "SELECT * FROM TML_SPE WHERE 1=1 ";
    if ((paramString1 != null) && (!paramString1.trim().equals("")))
      str = str + " AND tmlId='" + paramString1.toUpperCase() + "'";
    if ((paramString2 != null) && (paramString2.trim().length() > 0))
      str = str + " AND speIp='" + paramString2 + "'";
    SelectTml localSelectTml = null;
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localSelectTml = new SelectTml();
        localSelectTml.setTmlId(localResultSet.getString("tmlId"));
        localSelectTml.setSpeIpPort(localResultSet.getString("speIp"));
        localArrayList.add(localSelectTml);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public List<SelectTml> getTmlSpe(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String str = "SELECT * FROM TML_SPE WHERE 1=1 ";
    if ((paramString != null) && (paramString.trim().length() > 0))
      str = str + " AND speIp='" + paramString + "'";
    SelectTml localSelectTml = null;
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localSelectTml = new SelectTml();
        localSelectTml.setTmlId(localResultSet.getString("tmlId"));
        localSelectTml.setSpeIpPort(localResultSet.getString("speIp"));
        localArrayList.add(localSelectTml);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public String getSpeIpPort(String paramString)
  {
    String str1 = "";
    String str2 = "SELECT * FROM TML_SPE WHERE tmlId=? ";
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str2);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        str1 = localResultSet.getString("speIp");
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return str1;
  }

  public List<Group> getGroups()
    throws SqlException
  {
    ArrayList localArrayList = new ArrayList();
    Group localGroup = null;
    ResultSet localResultSet = null;
    String str = "SELECT * FROM TML_GROUP";
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localGroup = new Group();
        localGroup.setGroupId(localResultSet.getInt("groupId"));
        localGroup.setGroupName(localResultSet.getString("groupName"));
        localGroup.setInTime(localResultSet.getString("inTime"));
        localGroup.setInOperator(localResultSet.getString("inOperator"));
        localGroup.setDescription(localResultSet.getString("description"));
        localArrayList.add(localGroup);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public void delTml(String paramString)
    throws SqlException
  {
    String str = "DELETE FROM TML_BASE WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString.toUpperCase());
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void delTmlSpe(String paramString)
    throws SqlException
  {
    String str = "DELETE FROM TML_SPE WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString.toUpperCase());
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public List<ServerList> getServers(int paramInt, String paramString)
    throws SqlException
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = "SELECT * FROM TML_SERVER_LIST WHERE 1=1";
    if (paramInt != -1)
      str1 = str1 + " AND tag=" + paramInt;
    if ((paramString != null) && (!paramString.trim().equals("")))
      str1 = str1 + " AND inTime>='" + paramString + "'";
    ResultSet localResultSet = null;
    ServerList localServerList = null;
    try
    {
      this.db = new DBCommon(str1);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        localServerList = new ServerList();
        localServerList.setId(localResultSet.getInt("id"));
        localServerList.setInTime(localResultSet.getString("inTime"));
        localServerList.setInOperator(localResultSet.getString("inOperator"));
        localServerList.setDescription(localResultSet.getString("description"));
        int i = localResultSet.getInt("tag");
        localServerList.setTag(i);
        localServerList.setServerName(TmlConst.serverType.getStrServerType(i));
        localServerList.setStrNetType(TmlConst.netType.getStrNetType(localResultSet.getInt("netType")));
        String str2 = localResultSet.getString("ip");
        int j = localResultSet.getInt("port");
        localServerList.setServerIp(str2);
        localServerList.setServerPort(j);
        localServerList.setIpPort(str2 + ":" + j);
        localArrayList.add(localServerList);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public void delServer(int paramInt)
    throws SqlException
  {
    String str = "DELETE FROM TML_SERVER_LIST WHERE id=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setInt(1, paramInt);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void registerServer(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, String paramString4, int paramInt3, int paramInt4, int paramInt5)
    throws DataExistException, SqlException
  {
    String str1 = "SELECT COUNT(*) FROM TML_SERVER_LIST WHERE ip=? AND port=? AND tag=? AND netType=?";
    ResultSet localResultSet = null;
    int i = 0;
    try
    {
      this.db = new DBCommon(str1);
      this.db.setString(1, paramString1);
      this.db.setInt(2, paramInt2);
      this.db.setInt(3, paramInt1);
      this.db.setInt(4, paramInt5);
      localResultSet = this.db.executeQuery();
      if (localResultSet.next())
        i = localResultSet.getInt(1);
      if (i != 0)
        throw new DataExistException("This data is exist!");
      String str2 = "INSERT INTO TML_SERVER_LIST (id, ip, port, tag, inTime, inOperator, description, groupId, unitId, netType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      if (str2.trim().equals(""))
        throw new SqlException("Param Error!");
      int j = (int)(System.currentTimeMillis() / 1000L);
      this.db = new DBCommon(str2);
      this.db.setInt(1, j);
      this.db.setString(2, paramString1);
      this.db.setInt(3, paramInt2);
      this.db.setInt(4, paramInt1);
      this.db.setString(5, paramString2);
      this.db.setString(6, paramString3);
      this.db.setString(7, paramString4);
      this.db.setInt(8, paramInt3);
      this.db.setInt(9, paramInt4);
      this.db.setInt(10, paramInt5);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void addUpgradeTest(String paramString1, String paramString2)
  {
    String str = "INSERT INTO TML_UPGRADE_TEST(tmlId,upgradeIp) VALUES (?, ?)";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString1);
      this.db.setString(2, paramString2);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public String getUpgradeURL(String paramString)
  {
    String str1 = "";
    ResultSet localResultSet = null;
    String str2 = "SELECT upgradeIp FROM TML_UPGRADE_TEST WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str2);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        str1 = localResultSet.getString(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return str1;
  }

  public List<UpgradeTest> getUpgrades(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    UpgradeTest localUpgradeTest = null;
    String str;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      localUpgradeTest = new UpgradeTest();
      str = getUpgradeURL(paramString);
      localUpgradeTest.setIp(str);
      localUpgradeTest.setTmlId(paramString);
      localArrayList.add(localUpgradeTest);
    }
    else
    {
      str = "SELECT * FROM TML_UPGRADE_TEST";
      ResultSet localResultSet = null;
      try
      {
        this.db = new DBCommon(str);
        localResultSet = this.db.executeQuery();
        while (localResultSet.next())
        {
          localUpgradeTest = new UpgradeTest();
          localUpgradeTest.setIp(localResultSet.getString("upgradeIp"));
          localUpgradeTest.setTmlId(localResultSet.getString("tmlId"));
          localArrayList.add(localUpgradeTest);
        }
      }
      catch (SQLException localSQLException)
      {
        log.error(localSQLException.getMessage());
      }
      finally
      {
        this.db.close();
        this.db = null;
      }
    }
    return localArrayList;
  }

  public void delUpgradeTest(String paramString)
  {
    String str = "DELETE FROM TML_UPGRADE_TEST WHERE tmlId='" + paramString + "'";
    try
    {
      this.db = new DBCommon(str);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void updateServer(ServerList paramServerList)
    throws SqlException
  {
    int i = paramServerList.getId();
    int j = paramServerList.getTag();
    String str1 = "";
    String str2 = paramServerList.getServerIp();
    int k = paramServerList.getServerPort();
    str1 = str1 + "UPDATE TML_SERVER_LIST SET ip=?, port=?, description=? WHERE id=?";
    if (str1.trim().equals(""))
      throw new SqlException("Param Error!");
    try
    {
      this.db = new DBCommon(str1);
      this.db.setString(1, str2);
      this.db.setInt(2, k);
      this.db.setString(3, paramServerList.getDescription());
      this.db.setInt(4, i);
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void updateTmlSpe(String paramString1, String paramString2)
    throws SqlException
  {
    String str = "UPDATE TML_SPE SET speIp=? WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString2);
      this.db.setString(2, paramString1.toUpperCase());
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void updTml(String paramString, int paramInt)
    throws SqlException
  {
    String str1 = "";
    if (paramInt == 3)
      str1 = "UPDATE TML_BASE SET tmlStatus=?,onTime=? WHERE tmlId=?";
    else if (paramInt == 0)
      str1 = "UPDATE TML_BASE SET tmlStatus=? WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str1);
      this.db.setInt(1, paramInt);
      if (paramInt == 3)
      {
        TimeMgmt localTimeMgmt = new TimeMgmt();
        String str2 = localTimeMgmt.getCurDate(5);
        this.db.setString(2, str2);
        this.db.setString(3, paramString.toUpperCase());
      }
      else if (paramInt == 0)
      {
        this.db.setString(2, paramString.toUpperCase());
      }
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public void updTml(String paramString1, String paramString2, int paramInt)
    throws SqlException
  {
    String str = "";
    if (paramInt == 0)
      str = "UPDATE TML_BASE SET adId=? WHERE tmlId=?";
    else if (paramInt == 1)
      str = "UPDATE TML_BASE SET uadId=? WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString2);
      this.db.setString(2, paramString1.toUpperCase());
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }

  public boolean updateTmls(List<SelectTml> paramList, int paramInt)
  {
    boolean bool = true;
    if ((paramList == null) || (paramList.size() == 0))
      return bool;
    DBCommon localDBCommon = new DBCommon();
    try
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        SelectTml localSelectTml = (SelectTml)localIterator.next();
        if (localSelectTml != null)
        {
          String str1 = localSelectTml.getTmlId();
          String str2 = "UPDATE TML_BASE SET isSpe=" + paramInt + " WHERE tmlId='" + str1 + "'";
          localDBCommon.addBatch(str2);
        }
      }
      localDBCommon.executeBatch();
      localDBCommon.commit();
    }
    catch (Exception localException)
    {
      bool = false;
      localException.printStackTrace();
    }
    finally
    {
      localDBCommon.close();
    }
    return bool;
  }
/*
  public TmlSys getTmlSys(String paramString)
    throws SqlException
  {
    TmlSys localTmlSys = new TmlSys();
    localTmlSys.setTmlId(paramString);
    if ((paramString == null) || (paramString.trim().equals("")))
      return localTmlSys;
    String str1 = "SELECT * FROM TML_SYS_INFO WHERE tmlId=?";
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str1);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      String str2 = "";
      while (localResultSet.next())
      {
        localTmlSys.setAbleMem(localResultSet.getString("ableMem"));
        localTmlSys.setLeftDiskSize(localResultSet.getString("leftDiskSize"));
        localTmlSys.setPortalUrl(localResultSet.getString("portalUrl"));
        localTmlSys.setPortalVersion(localResultSet.getString("portalVersion"));
        localTmlSys.setTmlApps(localResultSet.getString("tmlApps"));
        str2 = localResultSet.getString("tmlSpelist");
        localTmlSys.setTmlVersion(localResultSet.getString("tmlVersion"));
        localTmlSys.setUpdateDate(localResultSet.getString("updateDate"));
      }
      if ((str2 != null) && (!str2.trim().equals("")))
      {
        localObject1 = new ArrayList();
        int i;
        if (str2.contains("，"))
        {
          localObject2 = str2.split("，");
          for (i = 0; i < localObject2.length; i++)
            ((List)localObject1).add(localObject2[i]);
        }
        else if (str2.contains(","))
        {
          localObject2 = str2.split(",");
          for (i = 0; i < localObject2.length; i++)
            ((List)localObject1).add(localObject2[i]);
        }
        else
        {
          ((List)localObject1).add(str2);
        }
        localTmlSys.setTmlSpelist((List)localObject1);
      }
      Object localObject1 = "SELECT * FROM TML_CURRENT_DO WHERE tmlId=?";
      this.db = new DBCommon((String)localObject1);
      this.db.setString(1, paramString);
      localResultSet = this.db.executeQuery();
      Object localObject2 = new ArrayList();
      TmlDoInfo localTmlDoInfo = null;
      while (localResultSet.next())
      {
        localTmlDoInfo = new TmlDoInfo();
        String str3 = localResultSet.getString("tmlDoingCnt");
        int j = localResultSet.getInt("tmlStatus");
        if (j == 2)
        {
          localTmlSys.setTmlPlayling(str3);
        }
        else if (j == 4)
        {
          localTmlDoInfo.setTmlDoing(str3);
          localTmlDoInfo.setDownRate(localResultSet.getString("tmlDownrate"));
          localTmlDoInfo.setDownSource(localResultSet.getString("tmlDownsource"));
          localTmlDoInfo.setInTime(localResultSet.getString("inTime"));
          ((List)localObject2).add(localTmlDoInfo);
        }
      }
      localTmlSys.setDoinfo((List)localObject2);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      localSQLException.printStackTrace();
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localTmlSys;
  }
*/
  public int getOrderNum(String paramString1, String paramString2)
    throws SqlException
  {
    int i = 0;
    String str = "SELECT COUNT(*) FROM TML_ORDER WHERE inTime>=? AND inTime<=?";
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      this.db.setString(1, paramString1);
      this.db.setString(2, paramString2);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        i = localResultSet.getInt(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      localSQLException.printStackTrace();
    }
    finally
    {
      this.db.close();
      this.db = null;
      localResultSet = null;
    }
    return i;
  }

  public int getTmsOnlineNum()
    throws SqlException
  {
    int i = 0;
    String str = "SELECT COUNT(*) FROM TML_BASE WHERE tmlStatus>0";
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
        i = localResultSet.getInt(1);
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      localSQLException.printStackTrace();
    }
    finally
    {
      this.db.close();
      this.db = null;
      localResultSet = null;
    }
    return i;
  }

  public static void main(String[] paramArrayOfString)
    throws DataExistException
  {
    TmlJDBCImpl localTmlJDBCImpl = new TmlJDBCImpl();
    try
    {
      List localList = localTmlJDBCImpl.getTmls("", 0, 0, 0, -1, false, "", "", "", "", "", "");
      System.out.println(localList);
    }
    catch (SqlException localSqlException)
    {
      localSqlException.printStackTrace();
    }
  }

  public List<Dom> getAllDoms()
  {
    ArrayList localArrayList = new ArrayList();
    String str = "SELECT DOMID,DONAME FROM SYS_DOM";
    ResultSet localResultSet = null;
    try
    {
      this.db = new DBCommon(str);
      localResultSet = this.db.executeQuery();
      while (localResultSet.next())
      {
        Dom localDom = new Dom();
        localDom.setId(localResultSet.getInt("DOMID"));
        localDom.setName(localResultSet.getString("DONAME"));
        localArrayList.add(localDom);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
    return localArrayList;
  }

  public void updateTmlGroup(String paramString, int paramInt)
    throws SqlException
  {
    String str = "UPDATE TML_BASE SET groupId=? WHERE tmlId=?";
    try
    {
      this.db = new DBCommon(str);
      this.db.setInt(1, paramInt);
      this.db.setString(2, paramString.toUpperCase());
      this.db.executeUpdate();
      this.db.commit();
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
      throw new SqlException(localSQLException.getMessage());
    }
    finally
    {
      this.db.close();
      this.db = null;
    }
  }
}