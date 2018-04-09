package tml.sysmgmt;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role
  implements Serializable, Comparable
{
  private int id = -1;
  private String name;
  private String value;
  private String parent;
  private int priority;
  private Set users = new HashSet();

  public int getPriority()
  {
    return this.priority;
  }

  public void setPriority(int paramInt)
  {
    this.priority = paramInt;
  }

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

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }

  public String getParent()
  {
    return this.parent;
  }

  public void setParent(String paramString)
  {
    this.parent = paramString;
  }

  public int compareTo(Object paramObject)
  {
    Role localRole = (Role)paramObject;
    return this.id == localRole.id ? 0 : this.id > localRole.id ? 1 : -1;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Role))
      return false;
    Role localRole = (Role)paramObject;
    if (localRole.value == null ? this.value != null : !localRole.value.equals(this.value))
      return false;
    if (localRole.parent == null ? this.parent != null : !localRole.parent.equals(this.parent))
      return false;
    return this.id == localRole.id;
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
    localStringBuffer.append("Role[id: ");
    localStringBuffer.append(this.id).append(",name: ").append(this.name);
    localStringBuffer.append(",value: ").append(this.value);
    localStringBuffer.append(",parent: ").append(this.parent);
    localStringBuffer.append(",priority: ").append(this.priority);
    localStringBuffer.append("]\r\n");
    return localStringBuffer.toString();
  }
}