package tml.sysmgmt;

import java.io.Serializable;
import java.util.Date;

public class History
  implements Serializable
{
  public static final int LOGIN = 1;
  public static final int LOGOUT = 2;
  private int id = -1;
  private Date time;
  private int userId;
  private String userName;
  private int logType;
  private String clientIp;

  public String getClientIp()
  {
    return this.clientIp;
  }

  public void setClientIp(String paramString)
  {
    this.clientIp = paramString;
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public Date getTime()
  {
    return this.time;
  }

  public void setTime(Date paramDate)
  {
    this.time = paramDate;
  }

  public int getUserId()
  {
    return this.userId;
  }

  public void setUserId(int paramInt)
  {
    this.userId = paramInt;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }

  public int getLogType()
  {
    return this.logType;
  }

  public void setLogType(int paramInt)
  {
    this.logType = paramInt;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("History[Id: ").append(this.id);
    localStringBuffer.append(",userId: ").append(this.userId);
    localStringBuffer.append(",userName: ").append(this.userName);
    localStringBuffer.append(",HistoryTime:").append(this.time);
    localStringBuffer.append(",logType:").append(this.logType);
    localStringBuffer.append(", clientIp = ").append(this.clientIp);
    return localStringBuffer.toString();
  }
}