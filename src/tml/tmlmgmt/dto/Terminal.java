package tml.tmlmgmt.dto;

import java.io.PrintStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tml.common.DBCommon;

public class Terminal
  implements Serializable
{
  private static final long serialVersionUID = -6683989008518224093L;
  private String tmlId = "";
  private int tmlType = 0;
  private String strTmlType = "";
  private int groupId = 0;
  private int unitId = 0;
  private String groupName = "";
  private String unitName = "";
  private String customerId = "";
  private String customerPwd = "";
  private String inOperator = "";
  private String inTime = "";
  private int tmlStatus = 0;
  private String strTmlStatus = "";
  private String ipAddr = "";
  private String onTime = "";
  private String outTime = "";
  private String description = "";
  private String customerType = "";
  private List<TmlDoInfo> downling = new ArrayList();
  private List<TmlDoInfo> playling = new ArrayList();
  private boolean showGetSys;
  private String telNo = "";

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Terminal:[tmlId=").append(this.tmlId).append(", strTmlType=").append(this.strTmlType).append(", inOperator=").append(this.inOperator).append(", inTime=").append(this.inTime).append(", tmlStatus=").append(this.tmlStatus).append(", strTmlStatus=").append(this.strTmlStatus).append(", ipAddr=").append(this.ipAddr).append(", onTime=").append(this.onTime).append(", outTime=").append(this.outTime).append(", description=").append(this.description).append(", downling=").append(this.downling).append(", playling=").append(this.playling).append(",telNo=").append(this.telNo).append("]\r\n");
    return localStringBuffer.toString();
  }

  public List<TmlDoInfo> getDownling()
  {
    return getDoing(4);
  }

  public void setDownling(List<TmlDoInfo> paramList)
  {
    this.downling = paramList;
  }

  public List<TmlDoInfo> getPlayling()
  {
    return getDoing(2);
  }

  public void setPlayling(List<TmlDoInfo> paramList)
  {
    this.playling = paramList;
  }

  public boolean isShowGetSys()
  {
    return this.showGetSys;
  }

  public void setShowGetSys(boolean paramBoolean)
  {
    this.showGetSys = paramBoolean;
  }

  public String getCustomerType()
  {
    return this.customerType;
  }

  public void setCustomerType(String paramString)
  {
    this.customerType = paramString;
  }

  public String getTmlId()
  {
    return this.tmlId;
  }

  public void setTmlId(String paramString)
  {
    this.tmlId = paramString;
  }

  public int getTmlType()
  {
    return this.tmlType;
  }

  public void setTmlType(int paramInt)
  {
    this.tmlType = paramInt;
  }

  public String getCustomerId()
  {
    return this.customerId;
  }

  public void setCustomerId(String paramString)
  {
    this.customerId = paramString;
  }

  public String getCustomerPwd()
  {
    return this.customerPwd;
  }

  public void setCustomerPwd(String paramString)
  {
    this.customerPwd = paramString;
  }

  public String getInOperator()
  {
    return this.inOperator;
  }

  public void setInOperator(String paramString)
  {
    this.inOperator = paramString;
  }

  public String getInTime()
  {
    return this.inTime;
  }

  public void setInTime(String paramString)
  {
    this.inTime = paramString;
  }

  public int getTmlStatus()
  {
    return this.tmlStatus;
  }

  public void setTmlStatus(int paramInt)
  {
    this.tmlStatus = paramInt;
  }

  public String getIpAddr()
  {
    return this.ipAddr;
  }

  public void setIpAddr(String paramString)
  {
    this.ipAddr = paramString;
  }

  public String getOnTime()
  {
    return this.onTime;
  }

  public void setOnTime(String paramString)
  {
    this.onTime = paramString;
  }

  public String getOutTime()
  {
    return this.outTime;
  }

  public void setOutTime(String paramString)
  {
    this.outTime = paramString;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public int getGroupId()
  {
    return this.groupId;
  }

  public void setGroupId(int paramInt)
  {
    this.groupId = paramInt;
  }

  public int getUnitId()
  {
    return this.unitId;
  }

  public void setUnitId(int paramInt)
  {
    this.unitId = paramInt;
  }

  public String getGroupName()
  {
    return this.groupName;
  }

  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
  }

  public String getUnitName()
  {
    return this.unitName;
  }

  public void setUnitName(String paramString)
  {
    this.unitName = paramString;
  }

  public String getStrTmlType()
  {
    return this.strTmlType;
  }

  public void setStrTmlType(String paramString)
  {
    this.strTmlType = paramString;
  }

  public String getStrTmlStatus()
  {
    return this.strTmlStatus;
  }

  public void setStrTmlStatus(String paramString)
  {
    this.strTmlStatus = paramString;
  }

  public String getTelNo()
  {
    return this.telNo;
  }

  public void setTelNo(String paramString)
  {
    this.telNo = paramString;
  }

  public int hashCode()
  {
    int i = 1;
    i = 31 * i + (this.tmlId == null ? 0 : this.tmlId.hashCode());
    return i;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (paramObject == null)
      return false;
    if (getClass() != paramObject.getClass())
      return false;
    Terminal localTerminal = (Terminal)paramObject;
    if (this.tmlId == null)
    {
      if (localTerminal.tmlId != null)
        return false;
    }
    else if (!this.tmlId.equals(localTerminal.tmlId))
      return false;
    return true;
  }

  public List<TmlDoInfo> getDoing(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    DBCommon localDBCommon = null;
    ResultSet localResultSet = null;
    String str1 = "SELECT * FROM TML_CURRENT_DO WHERE tmlStatus=" + paramInt + " AND tmlId='" + this.tmlId + "'";
    try
    {
      localDBCommon = new DBCommon(str1);
      localResultSet = localDBCommon.executeQuery();
      while (localResultSet.next())
      {
        String str2 = localResultSet.getString("tmlDoingCnt");
        int i = localResultSet.getInt("tmlStatus");
        if ((str2 != null) && (!str2.equals("")))
        {
          TmlDoInfo localTmlDoInfo = new TmlDoInfo();
          localTmlDoInfo.setTmlDoing(str2);
          localTmlDoInfo.setTmlStatus(i);
          if (i == 4)
          {
            localTmlDoInfo.setDownRate(localResultSet.getString("tmlDownrate"));
            localTmlDoInfo.setDownSource(localResultSet.getString("tmlDownsource"));
            localArrayList.add(localTmlDoInfo);
          }
          else if (i == 2)
          {
            localArrayList.add(localTmlDoInfo);
          }
        }
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
    finally
    {
      localDBCommon.close();
      localDBCommon = null;
    }
    return localArrayList;
  }

  public static void main(String[] paramArrayOfString)
  {
    Terminal localTerminal = new Terminal();
    localTerminal.tmlId = "0016E8176834";
    System.out.println(localTerminal.getDoing(4));
  }
}