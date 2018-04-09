package tml.common.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import tml.sysmgmt.ISysMgmt;
import tml.sysmgmt.SysMgmtImpl;

public class Visit
  implements Serializable
{
  private String operator;
  private Collection roles;
  public static final int TIME_OUT = 7200000;
  private Date lastUsed = null;

  public String getOperator()
  {
    return this.operator;
  }

  public void setOperator(String paramString)
  {
    this.operator = paramString;
  }

  public Collection getRoles()
  {
    if (this.lastUsed == null)
      this.lastUsed = new Date();
    Date localDate = new Date();
    if (localDate.getTime() - this.lastUsed.getTime() > 7200000L)
    {
      SysMgmtImpl localSysMgmtImpl = new SysMgmtImpl();
      List localList = localSysMgmtImpl.getAllUserRoles(getOperator());
      setRoles(localList);
      localList = null;
    }
    return this.roles;
  }

  public void setRoles(Collection paramCollection)
  {
    this.lastUsed = new Date();
    this.roles = paramCollection;
  }
}