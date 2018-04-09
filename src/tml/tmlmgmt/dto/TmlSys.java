package tml.tmlmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TmlSys
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String tmlId;
  private String tmlVersion;
  private String leftDiskSize;
  private String ableMem;
  private String portalVersion;
  private String portalUrl;
  private String tmlPlayling;
  private String tmlApps;
  private List tmlSpelist;
  private String updateDate;
  private List<TmlDoInfo> doinfo = new ArrayList();

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("TmlSys:[tmlId=").append(this.tmlId).append(", tmlVersion=").append(this.tmlVersion).append(", leftDiskSize=").append(this.leftDiskSize).append(", ableMem=").append(this.ableMem).append(", portalVersion=").append(this.portalVersion).append(", portalUrl=").append(this.portalUrl).append(", tmlPlayling=").append(this.tmlPlayling).append(", doinfo=").append(this.doinfo).append(", tmlApps=").append(this.tmlApps).append(", tmlSpelist=").append(this.tmlSpelist).append(", updateDate=").append(this.updateDate).append("]\r\n");
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

  public String getTmlVersion()
  {
    return this.tmlVersion;
  }

  public void setTmlVersion(String paramString)
  {
    this.tmlVersion = paramString;
  }

  public String getLeftDiskSize()
  {
    return this.leftDiskSize;
  }

  public void setLeftDiskSize(String paramString)
  {
    this.leftDiskSize = paramString;
  }

  public String getAbleMem()
  {
    return this.ableMem;
  }

  public void setAbleMem(String paramString)
  {
    this.ableMem = paramString;
  }

  public String getPortalVersion()
  {
    return this.portalVersion;
  }

  public void setPortalVersion(String paramString)
  {
    this.portalVersion = paramString;
  }

  public String getPortalUrl()
  {
    return this.portalUrl;
  }

  public void setPortalUrl(String paramString)
  {
    this.portalUrl = paramString;
  }

  public String getTmlPlayling()
  {
    return this.tmlPlayling;
  }

  public void setTmlPlayling(String paramString)
  {
    this.tmlPlayling = paramString;
  }

  public List<TmlDoInfo> getDoinfo()
  {
    return this.doinfo;
  }

  public void setDoinfo(List<TmlDoInfo> paramList)
  {
    this.doinfo = paramList;
  }

  public String getTmlApps()
  {
    return this.tmlApps;
  }

  public void setTmlApps(String paramString)
  {
    this.tmlApps = paramString;
  }

  public List getTmlSpelist()
  {
    return this.tmlSpelist;
  }

  public void setTmlSpelist(List paramList)
  {
    this.tmlSpelist = paramList;
  }

  public String getUpdateDate()
  {
    return this.updateDate;
  }

  public void setUpdateDate(String paramString)
  {
    this.updateDate = paramString;
  }
}