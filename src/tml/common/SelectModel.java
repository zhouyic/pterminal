package tml.common;

import java.io.Serializable;

public class SelectModel
  implements Serializable
{
  private String value;
  private String label;

  public SelectModel()
  {
  }

  public SelectModel(String paramString1, String paramString2)
  {
    this.label = paramString1;
    this.value = paramString2;
  }

  public String getLabel()
  {
    return this.label;
  }

  public void setLabel(String paramString)
  {
    this.label = paramString;
  }

  public String getValue()
  {
    return this.value;
  }

  public int getIntValue()
  {
    return Integer.parseInt(this.value);
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }

  public void setValue(int paramInt)
  {
    this.value = ("" + paramInt);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof SelectModel))
      return false;
    SelectModel localSelectModel = (SelectModel)paramObject;
    if (localSelectModel.label == null ? this.label != null : !localSelectModel.label.equals(this.label))
      return false;
    return localSelectModel.value == null ? this.value == null : localSelectModel.value.equals(this.value);
  }

  public int hashCode()
  {
    int i = 17;
    i = 37 * i + this.label.hashCode();
    i = 37 * i + this.value.hashCode();
    return i;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("SelectModel [ ");
    localStringBuffer.append("VALUE = ").append(this.value).append(" ; ");
    localStringBuffer.append("LABEL = ").append(this.label).append(" ]");
    return localStringBuffer.toString();
  }
}