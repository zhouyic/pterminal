package tml.common;

import java.io.PrintStream;
import java.net.URL;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.cfg.Configuration;

public class HBSessionFactory
{
  private static org.apache.log4j.Logger log = Logger.getLogger(HBSessionFactory.class);
  private static HBSessionFactory instance = new HBSessionFactory();
  private SessionFactory factory;
  public long start = 0L;

  private HBSessionFactory()
  {
    try
    {
      Configuration localConfiguration = new Configuration();
      URL localURL = getClass().getResource("/hibernate.cfg.xml");
      if (localURL != null)
        localConfiguration.configure(localURL);
      String str1 = "localhost";
      if ((str1 != null) && (!str1.trim().equals("")))
      {
        String str2 = "jdbc:mysql://dp.cug8angi2aje.eu-central-1.rds.amazonaws.com:3306/tms?useUnicode=true&amp;characterEncoding=utf8&amp;autoReconnect=true";
        String str3 = "dp";
        String str4 = "dspcmpdpp";
        localConfiguration.setProperty("hibernate.connection.url", str2);
        localConfiguration.setProperty("hibernate.connection.username", str3);
        localConfiguration.setProperty("hibernate.connection.password", str4);
      }
      else
      {
        log.error("HBSessionFactory: tml_db_ip is null!");
      }
      this.factory = localConfiguration.buildSessionFactory();
      this.start = System.currentTimeMillis();
    }
    catch (HibernateException localHibernateException1)
    {
      log.error(localHibernateException1, localHibernateException1);
      try
      {
        instance.factory.close();
      }
      catch (HibernateException localHibernateException2)
      {
        log.error(localHibernateException2, localHibernateException2);
      }
      throw new RuntimeException("hibernate configure exception! maybe can find hibernate.cfg.xml." + localHibernateException1.getMessage());
    }
  }

  public static Session openSession()
    throws HibernateException
  {
    long l = System.currentTimeMillis();
    synchronized (instance)
    {
      if (l - instance.start > 3600000L)
        reload();
    }
    return instance.factory.openSession();
  }

  public static void reload()
  {
    try
    {
      instance.factory.close();
    }
    catch (HibernateException localHibernateException)
    {
      log.error(localHibernateException, localHibernateException);
    }
    instance = new HBSessionFactory();
  }

  public static Session getCurrentSession()
  {
    long l = System.currentTimeMillis();
    synchronized (instance)
    {
      if (l - instance.start > 3600000L)
        reload();
    }
   // ((HBSessionFactory)instance.factory);
    return getCurrentSession();
  }

  public static SessionFactory getSessionFactory()
  {
    return instance.factory;
  }

  public static void main(String[] paramArrayOfString)
  {
    try
    {
      Session localSession = openSession();
      System.out.println(localSession);
    }
    catch (HibernateException localHibernateException)
    {
      localHibernateException.printStackTrace();
    }
  }
}