package tml.tmlmgmt.dto;

public class TmlDoInfo
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String tmlId;
  private int tmlStatus;
  private int serviceType;
  private String tmlDoing;
  private String downSource;
  private String downRate;
  private String inTime;

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("TmlDoInfo:[tmlId=").append(this.tmlId).append(", tmlStatus=").append(this.tmlStatus).append(", tmlDoing=").append(this.tmlDoing).append(", downSource=").append(this.downSource).append(", downRate=").append(this.downRate).append("]\r\n");
    return localStringBuffer.toString();
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public String getInTime()
  {
    return this.inTime;
  }

  public void setInTime(String paramString)
  {
    this.inTime = paramString;
  }

  public String getTmlId()
  {
    return this.tmlId;
  }

  public void setTmlId(String paramString)
  {
    this.tmlId = paramString;
  }

  public int getTmlStatus()
  {
    return this.tmlStatus;
  }

  public void setTmlStatus(int paramInt)
  {
    this.tmlStatus = paramInt;
  }

  public int getServiceType()
  {
    return this.serviceType;
  }

  public void setServiceType(int paramInt)
  {
    this.serviceType = paramInt;
  }

  public String getTmlDoing()
  {
    return this.tmlDoing;
  }

  public void setTmlDoing(String paramString)
  {
    this.tmlDoing = paramString;
  }

  public String getDownSource()
  {
    return this.downSource;
  }

  public void setDownSource(String paramString)
  {
    this.downSource = paramString;
  }

  public String getDownRate()
  {
    return this.downRate;
  }

  public void setDownRate(String paramString)
  {
    this.downRate = paramString;
  }

  public int hashCode()
  {
    int i = 1;
    i = 31 * i + (this.downRate == null ? 0 : this.downRate.hashCode());
    i = 31 * i + (this.downSource == null ? 0 : this.downSource.hashCode());
    i = 31 * i + (this.inTime == null ? 0 : this.inTime.hashCode());
    i = 31 * i + this.serviceType;
    i = 31 * i + (this.tmlDoing == null ? 0 : this.tmlDoing.hashCode());
    i = 31 * i + (this.tmlId == null ? 0 : this.tmlId.hashCode());
    i = 31 * i + this.tmlStatus;
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
    TmlDoInfo localTmlDoInfo = (TmlDoInfo)paramObject;
    if (this.downRate == null)
    {
      if (localTmlDoInfo.downRate != null)
        return false;
    }
    else if (!this.downRate.equals(localTmlDoInfo.downRate))
      return false;
    if (this.downSource == null)
    {
      if (localTmlDoInfo.downSource != null)
        return false;
    }
    else if (!this.downSource.equals(localTmlDoInfo.downSource))
      return false;
    if (this.inTime == null)
    {
      if (localTmlDoInfo.inTime != null)
        return false;
    }
    else if (!this.inTime.equals(localTmlDoInfo.inTime))
      return false;
    if (this.serviceType != localTmlDoInfo.serviceType)
      return false;
    if (this.tmlDoing == null)
    {
      if (localTmlDoInfo.tmlDoing != null)
        return false;
    }
    else if (!this.tmlDoing.equals(localTmlDoInfo.tmlDoing))
      return false;
    if (this.tmlId == null)
    {
      if (localTmlDoInfo.tmlId != null)
        return false;
    }
    else if (!this.tmlId.equals(localTmlDoInfo.tmlId))
      return false;
    return this.tmlStatus == localTmlDoInfo.tmlStatus;
  }
}