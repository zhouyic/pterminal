package tml.tmlmgmt.jdbc;

import java.util.List;
import tml.common.exception.DataExistException;
import tml.common.exception.SqlException;
import tml.sysmgmt.Dom;
import tml.tmlmgmt.dto.Group;
import tml.tmlmgmt.dto.SelectTml;
import tml.tmlmgmt.dto.ServerList;
import tml.tmlmgmt.dto.Terminal;
import tml.tmlmgmt.dto.TmlSys;
import tml.tmlmgmt.dto.UpgradeTest;

public abstract interface ITmlJDBC
{
  public abstract String getUserName(String paramString);

  public abstract int registerTml(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, String paramString5, String paramString6, String paramString7, String paramString8);

  public abstract int registerTmlToDom(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt2);

  public abstract void delTml(String paramString)
    throws SqlException;

  public abstract int findNuitByName(String paramString)
    throws SqlException;

  public abstract int findGroupIdByName(String paramString)
    throws SqlException;

  public abstract List<Group> getGroups()
    throws SqlException;

  public abstract int getUserId(String paramString)
    throws SqlException;

  public abstract void registerDom(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt3)
    throws SqlException;

  public abstract List<Terminal> getTmls(String paramString1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
    throws SqlException;

  public abstract List<SelectTml> getTmls()
    throws SqlException;

  public abstract List<ServerList> getServers(int paramInt, String paramString)
    throws SqlException;

  public abstract void delServer(int paramInt)
    throws SqlException;

  public abstract void registerServer(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3, String paramString4, int paramInt3, int paramInt4, int paramInt5)
    throws DataExistException, SqlException;

  public abstract void updateServer(ServerList paramServerList)
    throws SqlException;

  public abstract boolean updateTmls(List<SelectTml> paramList, int paramInt);

  public abstract List<SelectTml> getTmlSpe(String paramString1, String paramString2);

  public abstract List<SelectTml> getTmlSpe(String paramString);

  public abstract String getSpeIpPort(String paramString);

  public abstract boolean insertTmlSpe(List<SelectTml> paramList);

  public abstract void updateTmlSpe(String paramString1, String paramString2)
    throws SqlException;

  public abstract void updateTmlGroup(String paramString, int paramInt)
    throws SqlException;

  public abstract void delTmlSpe(String paramString)
    throws SqlException;

  public abstract String getUpgradeURL(String paramString);

  public abstract List<UpgradeTest> getUpgrades(String paramString);

  public abstract void addUpgradeTest(String paramString1, String paramString2);

  public abstract void delUpgradeTest(String paramString);
/*
  public abstract TmlSys getTmlSys(String paramString)
    throws SqlException;
*/
  public abstract int getOrderNum(String paramString1, String paramString2)
    throws SqlException;

  public abstract int getTmsOnlineNum()
    throws SqlException;

  public abstract List<Dom> getAllDoms();
}