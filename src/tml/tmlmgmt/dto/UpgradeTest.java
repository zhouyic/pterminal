package tml.tmlmgmt.dto;

public class UpgradeTest
{
  String tmlId;
  String ip;

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("UpgradeTest:[tmlId=").append(this.tmlId).append(", ip=").append(this.ip).append("]\r\n");
    return localStringBuffer.toString();
  }

  public String getTmlId()
  {
    return this.tmlId;
  }

  public void setTmlId(String paramString)
  {
    this.tmlId = paramString;
  }

  public String getIp()
  {
    return this.ip;
  }

  public void setIp(String paramString)
  {
    this.ip = paramString;
  }
}