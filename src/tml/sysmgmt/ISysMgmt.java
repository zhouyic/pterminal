package tml.sysmgmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public abstract interface ISysMgmt
{
  public abstract boolean isAdmin(String paramString);

  public abstract List getAvailableRoles();

  public abstract List getAllUserRoles(String paramString);
  /*
  public abstract int addUserRoles(String paramString, Collection paramCollection);

  public abstract int updateUserRoles(String paramString, Collection paramCollection);

  public abstract boolean isRegNameUsable(String paramString);

  public abstract int addUser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);

  public abstract int updateUser(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract int updateUserPwd(String paramString1, String paramString2, String paramString3);

  public abstract int updateUserPwdForce(String paramString1, String paramString2);

  public abstract int delUser(String paramString);

  public abstract List getAllUsers();

  public abstract User getUserByName(String paramString);
*/
  public abstract int login(String paramString1, String paramString2, String paramString3);

  public abstract void logout(String paramString1, String paramString2);
/*
  public abstract List findHistory(String paramString1, Date paramDate1, Date paramDate2, String paramString2);

  public abstract int deleteLoginLog(Date paramDate);

  public abstract Role getRoleByID(int paramInt);

  public abstract int getUserType(String paramString);

  public abstract List getUsersByType(int paramInt);

  public abstract int addUserDom(int paramInt1, int paramInt2);

  public abstract int getUserId(String paramString);*/
}