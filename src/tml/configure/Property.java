package tml.configure;

import java.io.Serializable;

public class Property
  implements Serializable
{
  private int id = -1;
  private String name;
  private String value;
  private String desc;

  public int getId()
  {
    return this.id;
  }

  private void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public String getDesc()
  {
    return this.desc;
  }

  public void setDesc(String paramString)
  {
    this.desc = paramString;
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

  public Property()
  {
  }

  public Property(String paramString1, String paramString2, String paramString3)
  {
    this.name = paramString1;
    this.value = paramString2;
    this.desc = paramString3;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("PropertyDTO[");
    localStringBuffer.append("id = ").append(this.id);
    localStringBuffer.append(", name = ").append(this.name);
    localStringBuffer.append(", value = ").append(this.value);
    localStringBuffer.append(", desc = ").append(this.desc);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}