package tml.tmlmgmt.dto;

import java.io.Serializable;

public class Group
  implements Serializable
{
  private static final long serialVersionUID = -6683989008518224093L;
  private int groupId = 0;
  private String groupName = "";
  private String inTime = "";
  private String inOperator = "";
  private String description = "";

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Group:[groupId=").append(this.groupId).append(", groupName=").append(this.groupName).append(", inTime=").append(this.inTime).append(", inOperator=").append(this.inOperator).append(", description=").append(this.description).append("]\r\n");
    return localStringBuffer.toString();
  }

  public int getGroupId()
  {
    return this.groupId;
  }

  public void setGroupId(int paramInt)
  {
    this.groupId = paramInt;
  }

  public String getGroupName()
  {
    return this.groupName;
  }

  public void setGroupName(String paramString)
  {
    this.groupName = paramString;
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