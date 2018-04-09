package tml.log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
import tml.common.HBSessionFactory;

public class LogImpl
  implements ILog
{
  private static org.apache.log4j.Logger log = tml.common.Logger.getLogger(LogImpl.class);
  private static final SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public int addLog(String paramString1, String paramString2)
  {
    log.debug("addLog who=" + paramString1 + "  desc=" + paramString2);
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      Log localLog = new Log();
      localLog.setWho(paramString1);
      localLog.setDesc(paramString2);
      localLog.setTime(new Date());
      localSession.save(localLog);
      localTransaction.commit();
      localSession.close();
      log.debug("successfully addLog!");
      int i = 0;
      return i;
    }
    catch (HibernateException localHibernateException)
    {
      try
      {
        if (localTransaction != null)
          localTransaction.rollback();
      }
      catch (Exception localException1)
      {
      }
      log.debug("fail to addLog!");
      int j = 2;
      return j;
    }
    finally
    {
      try
      {
        if (localSession != null)
          localSession.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public int deleteLog(Date paramDate)
  {
    log.debug("deleteLog before=" + paramDate);
    if (paramDate == null)
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      localSession.delete("from tml.log.Log as l where l.time <= ?", paramDate, Hibernate.TIMESTAMP);
      localTransaction.commit();
      localSession.close();
      log.debug("successfully deleteLog!");
      int i = 0;
      return i;
    }
    catch (HibernateException localHibernateException)
    {
      try
      {
        if (localTransaction != null)
          localTransaction.rollback();
      }
      catch (Exception localException1)
      {
      }
      log.debug("fail to deleteLog!" + localHibernateException.getMessage());
      int j = 4;
      return j;
    }
    finally
    {
      try
      {
        if (localSession != null)
          localSession.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }

  public List findLog(int paramInt, String paramString1, Date paramDate1, Date paramDate2, String paramString2, String paramString3)
  {
    log.debug("findLog id=" + paramInt + " who=" + paramString1 + " afterTime=" + paramDate1 + " beforeTime=" + paramDate2 + " desc=" + paramString2);
    Object localObject1 = new ArrayList();
    Session localSession = null;
    Transaction localTransaction = null;
    paramDate2 = getFormatDate(paramDate2);
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      Criteria localCriteria = localSession.createCriteria(Log.class);
      if (paramInt > 0)
        localCriteria.add(Expression.eq("id", new Integer(paramInt)));
      if ((paramString1 != null) && (paramString1.length() > 0))
        localCriteria.add(Expression.like("who", "%" + paramString1 + "%"));
      if (paramDate1 != null)
        localCriteria.add(Expression.gt("time", paramDate1));
      if (paramDate2 != null)
        localCriteria.add(Expression.lt("time", paramDate2));
      if ((paramString2 != null) && (paramString2.length() > 0))
        localCriteria.add(Expression.like("desc", "%" + paramString2 + "%"));
      ((List)localObject1).addAll(localCriteria.list());
      localTransaction.commit();
      localSession.close();
      ArrayList localArrayList = new ArrayList();
      if (!paramString3.equals("zkzw"))
      {
        for (int i = 0; i < ((List)localObject1).size(); i++)
        {
          Log localLog = (Log)((List)localObject1).get(i);
          String str = localLog.getWho();
          if (!"zkzw".equals(str))
            localArrayList.add(localLog);
        }
        ((List)localObject1).clear();
        localObject1 = localArrayList;
      }
      Object localObject2 = localObject1;
      return (List)localObject2;
    }
    catch (HibernateException localHibernateException)
    {
      try
      {
        if (localTransaction != null)
          localTransaction.rollback();
      }
      catch (Exception localException1)
      {
    	  System.out.println(localException1.getMessage());
      }
    }
    finally
    {
      try
      {
        if (localSession != null)
          localSession.close();
      }
      catch (Exception localException2)
      {
      }
    }
    log.debug("findLog result=" + localObject1);
    return (List)localObject1;
  }

  private Date getFormatDate(Date paramDate)
  {
    Date localDate = paramDate;
    if (paramDate == null)
      return localDate;
    String str = sdf0.format(paramDate);
    str = str + " 23:59:59";
    try
    {
      localDate = sdf1.parse(str);
    }
    catch (ParseException localParseException)
    {
      localDate = paramDate;
    }
    return localDate;
  }
}