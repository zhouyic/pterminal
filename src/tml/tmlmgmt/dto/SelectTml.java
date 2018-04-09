package tml.tmlmgmt.dto;

import java.io.Serializable;

public class SelectTml
  implements Serializable
{
  private static final long serialVersionUID = -6683989008518224093L;
  private String tmlId = "";
  private String customerType = "";
  private boolean selected = false;
  private String speIpPort = "";

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("SelectTml:[tmlId=").append(this.tmlId).append(", customerType=").append(this.customerType).append(", isSelected=").append(this.selected).append(", speIpPort=").append(this.speIpPort).append("]\r\n");
    return localStringBuffer.toString();
  }

  public String getSpeIpPort()
  {
    return this.speIpPort;
  }

  public void setSpeIpPort(String paramString)
  {
    this.speIpPort = paramString;
  }

  public boolean getSelected()
  {
    return this.selected;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.selected = paramBoolean;
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
}