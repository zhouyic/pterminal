package tml.sysmgmt;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User
  implements Serializable
{
  public static int TYPE_NORMAL = 1;
  public static int TYPE_SUPER = 900;
  private int id = -1;
  private int type;
  private String name;
  private String pwd;
  private String email;
  private String phone;
  private String mobile;
  private volatile int hashCode = 0;
  private Set<Role> roles = new HashSet<Role>();
  private Set<Dom> dom = new HashSet<Dom>();

  public Set<Dom> getDom()
  {
    return this.dom;
  }

  public void setDom(Set<Dom> paramSet)
  {
    this.dom = paramSet;
  }

  public Set<Role> getRoles()
  {
    return this.roles;
  }

  public void setRoles(Set<Role> paramSet)
  {
    this.roles = paramSet;
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

  public int getType()
  {
    return this.type;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public String getPwd()
  {
    return this.pwd;
  }

  public void setPwd(String paramString)
  {
    this.pwd = paramString;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String paramString)
  {
    this.mobile = paramString;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject != null) && (getClass() == paramObject.getClass()))
    {
      User localUser = (User)paramObject;
      if (this.id == localUser.id)
        return true;
    }
    return false;
  }

  public int hashCode()
  {
    if (this.hashCode == 0)
    {
      int i = 19;
      i = 31 * i + this.id;
      this.hashCode = i;
    }
    return this.hashCode;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("User[ID: ").append(this.id);
    localStringBuffer.append(",username: ").append(this.name);
    localStringBuffer.append(",type: ").append(this.type);
    localStringBuffer.append(",email address: ").append(this.email);
    localStringBuffer.append(",phone: ").append(this.phone);
    localStringBuffer.append(",mobile: ").append(this.mobile);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }

  public static void main(String[] paramArrayOfString)
  {
  }
}