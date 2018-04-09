package tml.common;

import java.lang.reflect.Method;
import java.util.List;
import org.apache.tapestry.form.IPropertySelectionModel;

public class DefaultPropertySelectionModel
  implements IPropertySelectionModel
{
  private List list;
  private String label;
  private String value;

  public DefaultPropertySelectionModel(List paramList, String paramString1, String paramString2)
  {
    this.list = paramList;
    this.label = paramString1;
    this.value = paramString2;
  }

  public int getOptionCount()
  {
    return this.list.size();
  }

  public Object getOption(int paramInt)
  {
    return this.list.get(paramInt);
  }

  public Object translateValue(String paramString)
  {
    Object localObject = null;
    for (int i = 0; i < this.list.size(); i++)
      if (getValue(i).equals(paramString))
      {
        localObject = this.list.get(i);
        break;
      }
    return localObject;
  }

  public String getLabel(int paramInt)
  {
    Object localObject1 = this.list.get(paramInt);
    Class localClass = localObject1.getClass();
    String str1 = this.label.toUpperCase().substring(0, 1);
    String str2 = "";
    try
    {
      Method localMethod = localClass.getMethod("get" + str1 + this.label.substring(1, this.label.length()), new Class[0]);
      Object localObject2 = localMethod.invoke(localObject1, new Object[0]);
      if (localObject2 != null)
        if ((localObject2 instanceof String))
          str2 = (String)localObject2;
        else if ((localObject2 instanceof Integer))
          str2 = ((Integer)localObject2).toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str2;
  }

  public String getValue(int paramInt)
  {
    Object localObject1 = this.list.get(paramInt);
    Class localClass = localObject1.getClass();
    String str1 = this.value.toUpperCase().substring(0, 1);
    String str2 = "";
    try
    {
      Method localMethod = localClass.getMethod("get" + str1 + this.value.substring(1, this.value.length()), new Class[0]);
      Object localObject2 = localMethod.invoke(localObject1, new Object[0]);
      if ((localObject2 != null) || ((localObject2 instanceof String)))
        str2 = (String)localObject2;
      else if ((localObject2 instanceof Integer))
        str2 = ((Integer)localObject2).toString();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str2;
  }
}