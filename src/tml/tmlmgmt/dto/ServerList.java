package tml.tmlmgmt.dto;

import java.io.Serializable;

public class ServerList
  implements Serializable
{
  private static final long serialVersionUID = -6683989008518224093L;
  private int id = 0;
  private int tag = -1;
  private String serverName = "";
  private String serverIp = "";
  private int serverPort = 0;
  private String inTime = "";
  private String inOperator = "";
  private String description = "";
  private int groupId = 0;
  private int unitId = 0;
  private String groupName = "";
  private String unitName = "";
  private String strNetType = "";
  private String area = "上海";
  private boolean selected = false;
  private String ipPort = "";

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("ServerList:[id=").append(this.id).append(", tag=").append(this.tag).append(", serverName=").append(this.serverName).append(", serverIp=").append(this.serverIp).append(", serverPort=").append(this.serverPort).append(", strNetType=").append(this.strNetType).append(", area=").append(this.area).append(", isSelected=").append(this.selected).append(", ipPort=").append(this.ipPort).append("]\r\n");
    return localStringBuffer.toString();
  }

  public String getIpPort()
  {
    return this.ipPort;
  }

  public void setIpPort(String paramString)
  {
    this.ipPort = paramString;
  }

  public String getArea()
  {
    return this.area;
  }

  public void setArea(String paramString)
  {
    this.area = paramString;
  }

  public boolean getSelected()
  {
    return this.selected;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.selected = paramBoolean;
  }

  public String getStrNetType()
  {
    return this.strNetType;
  }

  public void setStrNetType(String paramString)
  {
    this.strNetType = paramString;
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

  public int getTag()
  {
    return this.tag;
  }

  public void setTag(int paramInt)
  {
    this.tag = paramInt;
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public String getServerName()
  {
    return this.serverName;
  }

  public void setServerName(String paramString)
  {
    this.serverName = paramString;
  }

  public String getServerIp()
  {
    return this.serverIp;
  }

  public void setServerIp(String paramString)
  {
    this.serverIp = paramString;
  }

  public int getServerPort()
  {
    return this.serverPort;
  }

  public void setServerPort(int paramInt)
  {
    this.serverPort = paramInt;
  }

  public String getInTime()
  {
    return this.inTime;
  }

  public void setInTime(String paramString)
  {
    this.inTime = paramString;
  }

  public String getInOperator()
  {
    return this.inOperator;
  }

  public void setInOperator(String paramString)
  {
    this.inOperator = paramString;
  }

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
}