package tml.tmlmgmt.domain;

import java.io.Serializable;

public class TerminalEntity
  implements Serializable
{
  private static final long serialVersionUID = -1985948970289444949L;
  private String macAddr;
  private int groupId;
  private int nuitId;
  private String inOperator;
  private String inTime;
  private int status;
  private String onTime;
  private String outTime;
  private String ipAddr;
  private String description;

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("BaseEntity[");
    localStringBuffer.append(" macAddr=").append(this.macAddr);
    localStringBuffer.append(", groupId=").append(this.groupId);
    localStringBuffer.append(", nuitId=").append(this.nuitId);
    localStringBuffer.append(", inOperator=").append(this.inOperator);
    localStringBuffer.append(", inTime=").append(this.inTime);
    localStringBuffer.append(", status=").append(this.status);
    localStringBuffer.append(", onTime=").append(this.onTime);
    localStringBuffer.append(", outTime=").append(this.outTime);
    localStringBuffer.append(", ipAddr=").append(this.ipAddr);
    localStringBuffer.append(", description=").append(this.description);
    localStringBuffer.append("]\r\n");
    return localStringBuffer.toString();
  }

  public String getMacAddr()
  {
    return this.macAddr;
  }

  public void setMacAddr(String paramString)
  {
    this.macAddr = paramString;
  }

  public int getGroupId()
  {
    return this.groupId;
  }

  public void setGroupId(int paramInt)
  {
    this.groupId = paramInt;
  }

  public int getNuitId()
  {
    return this.nuitId;
  }

  public void setNuitId(int paramInt)
  {
    this.nuitId = paramInt;
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

  public int getStatus()
  {
    return this.status;
  }

  public void setStatus(int paramInt)
  {
    this.status = paramInt;
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

  public String getIpAddr()
  {
    return this.ipAddr;
  }

  public void setIpAddr(String paramString)
  {
    this.ipAddr = paramString;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }

  public int hashCode()
  {
    int i = 1;
    i = 31 * i + (this.macAddr == null ? 0 : this.macAddr.hashCode());
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
    TerminalEntity localTerminalEntity = (TerminalEntity)paramObject;
    if (this.macAddr == null)
    {
      if (localTerminalEntity.macAddr != null)
        return false;
    }
    else if (!this.macAddr.equals(localTerminalEntity.macAddr))
      return false;
    return true;
  }
}