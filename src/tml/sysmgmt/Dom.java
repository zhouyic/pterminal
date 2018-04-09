package tml.sysmgmt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Dom
  implements Serializable, Comparable
{
  private static final long serialVersionUID = -6359910636941103274L;
  private int id;
  private String name;
  private String desc;
  private int parentId;
  private String parentName;
  private String creator;
  private Date createTime;
  private int type;
  private Set users = new HashSet();

  public Set getUsers()
  {
    return this.users;
  }

  public void setUsers(Set paramSet)
  {
    this.users = paramSet;
  }

  public int getId()
  {
    return this.id;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date paramDate)
  {
    this.createTime = paramDate;
  }

  public String getCreator()
  {
    return this.creator;
  }

  public void setCreator(String paramString)
  {
    this.creator = paramString;
  }

  public String getDesc()
  {
    return this.desc;
  }

  public void setDesc(String paramString)
  {
    this.desc = paramString;
  }

  public int getParentId()
  {
    return this.parentId;
  }

  public void setParentId(int paramInt)
  {
    this.parentId = paramInt;
  }

  public String getParentName()
  {
    return this.parentName;
  }

  public void setParentName(String paramString)
  {
    this.parentName = paramString;
  }

  public int getType()
  {
    return this.type;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public int compareTo(Object paramObject)
  {
    Dom localDom = (Dom)paramObject;
    return this.id == localDom.id ? 0 : this.id > localDom.id ? 1 : -1;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Dom))
      return false;
    Dom localDom = (Dom)paramObject;
    if (localDom.name == null ? this.name != null : !localDom.name.equals(this.name))
      return false;
    return this.id == localDom.id;
  }

  public int hashCode()
  {
    int i = 17;
    i = 37 * i + this.id;
    return i;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Dom[id:").append(this.id);
    localStringBuffer.append(" ,name:").append(this.name);
    localStringBuffer.append(" ,desc:").append(this.desc);
    localStringBuffer.append(" ,parentId:").append(this.parentId);
    localStringBuffer.append(" ,parentName:").append(this.parentName);
    localStringBuffer.append(" ,creator:").append(this.creator);
    localStringBuffer.append(" ,createTime:").append(this.createTime);
    localStringBuffer.append(" ,type:").append(this.type);
    localStringBuffer.append(" ,users:").append(this.users);
    localStringBuffer.append("]\r\n");
    return localStringBuffer.toString();
  }
}