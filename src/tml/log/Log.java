package tml.log;

import java.io.Serializable;
import java.util.Date;

public class Log
  implements Serializable
{
  private static final long serialVersionUID = -6683989008518224093L;
  private int id = -1;
  private String who;
  private Date time;
  private String desc;

  public String getDesc()
  {
    return this.desc;
  }

  public void setDesc(String paramString)
  {
    this.desc = paramString;
  }

  public int getId()
  {
    return this.id;
  }

  private void setId(int paramInt)
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

  public String getWho()
  {
    return this.who;
  }

  public void setWho(String paramString)
  {
    this.who = paramString;
  }

  public int hashCode()
  {
    return this.id;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof Log)))
    {
      Log localLog = (Log)paramObject;
      return localLog.id == this.id;
    }
    return false;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Log[");
    localStringBuffer.append("id = ").append(this.id);
    localStringBuffer.append(", who = ").append(this.who);
    localStringBuffer.append(", time = ").append(this.time);
    localStringBuffer.append(", desc = ").append(this.desc);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}