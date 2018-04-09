package tml.log;

import java.util.Date;
import java.util.List;

public abstract interface ILog
{
  public abstract int addLog(String paramString1, String paramString2);

  public abstract int deleteLog(Date paramDate);

  public abstract List findLog(int paramInt, String paramString1, Date paramDate1, Date paramDate2, String paramString2, String paramString3);
}