package tml.sysmgmt;

import java.io.PrintStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import net.sf.hibernate.Criteria;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.expression.Expression;
//import tml.common.DbcpDataSource;
import tml.common.HBSessionFactory;

public class SysMgmtImpl
  implements ISysMgmt
{
  private static org.apache.log4j.Logger log = tml.common.Logger.getLogger(SysMgmtImpl.class);
  private static final char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  private static final SimpleDateFormat sdf0 = new SimpleDateFormat("yyyy-MM-dd");
  private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public boolean isAdmin(String paramString)
  {
    if (paramString == null)
      return false;
    Session localSession = null;
    Transaction localTransaction = null;
    boolean res = false;// localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
      {
        res = false;
      }
      else
      {
    	  User localUser2 = (User)localList.get(0);
        if (localUser2.getType() == User.TYPE_SUPER)
          res = true;
        else
          res = false;
      }
      localTransaction.commit();
      localSession.close();
      return res;
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
      boolean bool = false;
      return bool;
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

  public List<Role> getAvailableRoles()
  {
	  List<Role> localObject1 = new ArrayList<Role>();
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      localObject1 = localSession.find("from Role as r where r.priority=?", new Integer(User.TYPE_NORMAL), Hibernate.INTEGER);
      localTransaction.commit();
      localSession.close();
      List<Role> localObject2 = localObject1;
      return localObject2;
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
      ((List)localObject1).clear();
      List<Role> localObject3 = localObject1;
      return localObject3;
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

 public List<Role> getAllUserRoles(String paramString)
  {
	 List<User> localList = new ArrayList<User>();
    ArrayList<Role> localArrayList1 = new ArrayList<Role>();
    if (paramString == null)
    {
      localArrayList1.clear();
      return localArrayList1;
    }
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      if (isAdmin(paramString))
      {
        localArrayList1.addAll(localSession.find("from Role"));
      }
      else
      {
        localList = (List<User>)(localSession.find("from User as u where u.name=?", paramString, Hibernate.STRING));
        if (localList.size() == 0)
        {
          localArrayList1.clear();
        }
        else
        {
          User localUser = (User)localList.get(0);
          localArrayList1.addAll(localUser.getRoles());
        }
      }
      localTransaction.commit();
      localSession.close();
      List localList1 = orderCollection(localArrayList1);
      return localList1;
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
      localArrayList1.clear();
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
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
 /*
  public int addUserRoles(String paramString, Collection paramCollection)
  {
    if (paramString == null)
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 2;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        localUser2.getRoles().addAll(paramCollection);
        localSession.update(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public int updateUserRoles(String paramString, Collection paramCollection)
  {
    log.debug(" updateUserRoles ...");
    log.debug(" user=" + paramString);
    log.debug(" roles=" + paramCollection);
    if (paramString == null)
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 31;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        localUser2.getRoles().clear();
        localUser2.getRoles().addAll(paramCollection);
        localSession.update(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public boolean isRegNameUsable(String paramString)
  {
    if (paramString == null)
      return false;
    Session localSession = null;
    Transaction localTransaction = null;
    boolean bool1 = false;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
        bool1 = true;
      else
        bool1 = false;
      localTransaction.commit();
      localSession.close();
      boolean bool2 = bool1;
      return bool2;
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
      boolean bool3 = false;
      return bool3;
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
*/
  private static String encryptMD5(String paramString)
  {
    String str = new String();
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      for (int i = 0; i < arrayOfByte.length; i++)
      {
        char[] arrayOfChar = new char[2];
        arrayOfChar[0] = digit[(arrayOfByte[i] >>> 4 & 0xF)];
        arrayOfChar[1] = digit[(arrayOfByte[i] & 0xF)];
        str = str + new String(arrayOfChar);
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      str = null;
    }
    return str;
  }
/*
  public int addUser(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 == null) || (paramString1.trim().length() == 0) || (paramString2 == null) || (paramString2.trim().length() == 0))
      return 1;
    paramString2 = encryptMD5(paramString2);
    if (paramString2 == null)
      return 5;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
      if (localList.size() != 0)
      {
        localUser1 = 21;
      }
      else
      {
        localUser2 = new User();
        localUser2.setName(paramString1);
        localUser2.setPwd(paramString2);
        localUser2.setType(User.TYPE_NORMAL);
        localUser2.setEmail(paramString3);
        localUser2.setPhone(paramString4);
        localUser2.setMobile(paramString5);
        localSession.save(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public int updateUser(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 31;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        localUser2.setEmail(paramString2);
        localUser2.setPhone(paramString3);
        localUser2.setMobile(paramString4);
        localSession.update(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public int updateUserPwd(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString3 == null))
      return 1;
    paramString2 = encryptMD5(paramString2);
    paramString3 = encryptMD5(paramString3);
    if ((paramString2 == null) || (paramString3 == null))
      return 5;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 1;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        if (!localUser2.getPwd().equals(paramString2))
        {
          localUser1 = 1;
        }
        else
        {
          localUser1 = 0;
          localUser2.setPwd(paramString3);
          localSession.update(localUser2);
        }
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public int updateUserPwdForce(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return 1;
    paramString2 = encryptMD5(paramString2);
    if (paramString2 == null)
      return 5;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 31;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        localUser2.setPwd(paramString2);
        localSession.update(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public int delUser(String paramString)
  {
    if (paramString == null)
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    User localUser1 = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = 41;
      }
      else
      {
        localUser2 = (User)localList.get(0);
        localSession.delete(localUser2);
        localUser1 = 0;
      }
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      int i = 5;
      return i;
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

  public List getAllUsers()
  {
    ArrayList localArrayList1 = new ArrayList();
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      Object localObject1 = localSession.find("from User as u where u.type <> ?", new Integer(User.TYPE_SUPER), Hibernate.INTEGER);
      localTransaction.commit();
      localSession.close();
      if (localObject1 == null)
        localObject1 = new ArrayList();
      User localUser = null;
      localArrayList1 = new ArrayList();
      for (int i = 0; i < ((List)localObject1).size(); i++)
      {
        localUser = (User)((List)localObject1).get(i);
        localUser.setRoles(null);
        localArrayList1.add(localUser);
      }
      ((List)localObject1).clear();
      ArrayList localArrayList3 = localArrayList1;
      return localArrayList3;
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
      localArrayList1.clear();
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
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

  public Role getRoleByID(int paramInt)
  {
    Role localRole1 = null;
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      localRole1 = (Role)localSession.get(Role.class, new Integer(paramInt));
      localTransaction.commit();
      localSession.close();
      Role localRole2 = localRole1;
      return localRole2;
    }
    catch (HibernateException localHibernateException)
    {
      log.error(" HibernateException " + localHibernateException.getMessage());
      try
      {
        if (localTransaction != null)
          localTransaction.rollback();
      }
      catch (Exception localException1)
      {
      }
      Role localRole3 = localRole1;
      return localRole3;
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

  public User getUserByName(String paramString)
  {
    if (paramString == null)
      return null;
    User localUser1 = null;
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
        localUser1 = null;
      else
        localUser1 = (User)localList.get(0);
      localTransaction.commit();
      localSession.close();
      User localUser2 = localUser1;
      return localUser2;
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
      User localUser3 = null;
      return localUser3;
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
*/
  public int login(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 == null) || (paramString2 == null))
      return 1;
    paramString2 = encryptMD5(paramString2);
    if (paramString2 == null)
      return 5;
    Session localSession = null;
    Transaction localTransaction = null;
    boolean localUser1 = false;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
      if (localList.size() == 0)
      {
        localUser1 = false;
      }
      else
      {
    	  User localUser2 = (User)localList.get(0);
        System.out.println("input pwd=" + paramString2);
        System.out.println("db pwd=" + localUser2.getPwd());
        if (!localUser2.getPwd().equals(paramString2))
        {
          System.out.println("mimabudui");
          localUser1 = false;
        }
        else
        {
          History localHistory = new History();
          localHistory.setLogType(1);
          localHistory.setTime(new Date());
          localHistory.setUserId(localUser2.getId());
          localHistory.setUserName(localUser2.getName());
          localHistory.setClientIp(paramString3);
          localSession.save(localHistory);
          localUser1 = true;
          System.out.println("已经");
        }
      }
      localTransaction.commit();
      localSession.close();
      if(localUser1)
    	  return 0;
      else 
    	  return 1;
      //return localUser2;
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
      int i = 5;
      return i;
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

  public void logout(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      Session localSession = null;
      Transaction localTransaction = null;
      try
      {
        localSession = HBSessionFactory.openSession();
        localTransaction = localSession.beginTransaction();
        List localList = localSession.find("select u from User as u where u.name=?", paramString1, Hibernate.STRING);
        if (localList.size() != 0)
        {
          User localUser = (User)localList.get(0);
          History localHistory = new History();
          localHistory.setLogType(2);
          localHistory.setTime(new Date());
          localHistory.setUserId(localUser.getId());
          localHistory.setUserName(localUser.getName());
          localHistory.setClientIp(paramString2);
          localSession.save(localHistory);
        }
        localTransaction.commit();
        localSession.close();
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
          System.out.println(localException1.toString());
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
    }
  }
/*
  public List findHistory(String paramString1, Date paramDate1, Date paramDate2, String paramString2)
  {
    log.debug("user = " + paramString1 + "operator = " + paramString2);
    ArrayList localArrayList1 = new ArrayList();
    Session localSession = null;
    Transaction localTransaction = null;
    paramDate2 = getFormatDate(paramDate2);
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      Criteria localCriteria = localSession.createCriteria(History.class);
      if ((paramString1 != null) && (paramString1.trim().length() > 0))
        localCriteria.add(Expression.like("userName", "%" + paramString1 + "%"));
      if ((paramDate1 != null) && (paramDate2 != null))
        localCriteria.add(Expression.between("time", paramDate1, paramDate2));
      else if ((paramDate1 != null) && (paramDate2 == null))
        localCriteria.add(Expression.gt("time", paramDate1));
      else if ((paramDate1 == null) && (paramDate2 != null))
        localCriteria.add(Expression.lt("time", paramDate2));
      localArrayList1.addAll(localCriteria.list());
      localTransaction.commit();
      localSession.close();
      ArrayList localArrayList2 = localArrayList1;
      return localArrayList2;
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
      localArrayList1.clear();
      ArrayList localArrayList3 = localArrayList1;
      return localArrayList3;
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

  public int deleteLoginLog(Date paramDate)
  {
    log.debug("deleteLoginLog before=" + paramDate);
    if (paramDate == null)
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      localSession.delete("from tml.sysmgmt.History as h where h.time <= ?", paramDate, Hibernate.TIMESTAMP);
      localTransaction.commit();
      localSession.close();
      log.debug("successfully deleteLoginLog!");
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
      log.debug("fail to deleteLoginLog!");
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
*/
  public List<Role> orderCollection(Collection<Role> paramCollection)
  {
    ArrayList<Role> localArrayList = new ArrayList<Role>();
    if ((paramCollection == null) || (paramCollection.size() < 1))
      return localArrayList;
    Object[] arrayOfObject = paramCollection.toArray();
    Arrays.sort(arrayOfObject);
    for (int i = 0; i < arrayOfObject.length; i++)
      localArrayList.add((Role)arrayOfObject[i]);
    return localArrayList;
  }
/*
  public static void main(String[] paramArrayOfString)
  {
    SysMgmtImpl localSysMgmtImpl = new SysMgmtImpl();
    System.out.println(encryptMD5("3ntv3ntvpwd"));
    System.out.println(encryptMD5("123456"));
    System.out.println("0EBCA856BB4AA12447A5098D8ED583A1");
  }

  public int getUserType(String paramString)
  {
    log.debug("[SysMgmtImpl()].getUserType param: userName=" + paramString);
    if ((paramString == null) || (paramString.equals("")))
      return 1;
    int i = -1;
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() != 1)
      {
        int j = -1;
        return j;
      }
      User localUser = (User)localList.get(0);
      i = localUser.getType();
      localTransaction.commit();
      localSession.close();
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
        jsr 14;
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
    return i;
  }

  public List getUsersByType(int paramInt)
  {
    log.debug("[SysMgmtImpl()].getUsersByType param: type=" + paramInt);
    Object localObject1 = new ArrayList();
    Session localSession = null;
    Transaction localTransaction = null;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      localObject1 = localSession.find("select u from User as u where u.type=?", Integer.valueOf(paramInt), Hibernate.INTEGER);
      localTransaction.commit();
      localSession.close();
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
        jsr 14;
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
    return localObject1;
  }

  public int addUserDom(int paramInt1, int paramInt2)
  {
    log.debug("[SysMgmtImpl()].addUserDom param: uerId=" + paramInt1 + ", domId=" + paramInt2);
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return 1;
    int i = 0;
    Connection localConnection = null;
    ResultSet localResultSet = null;
    PreparedStatement localPreparedStatement = null;
    try
    {
      String str1 = "SELECT * FROM SYS_USER_DOM WHERE USERID=" + paramInt1 + "  AND DOMID=" + paramInt2;
      localConnection = DbcpDataSource.getConnection();
      localPreparedStatement = localConnection.prepareStatement(str1);
      localResultSet = localPreparedStatement.executeQuery();
      if (!localResultSet.next())
      {
        String str2 = "INSERT INTO SYS_USER_DOM(USERID, DOMID) VALUES (?, ?)";
        localConnection = DbcpDataSource.getConnection();
        localPreparedStatement = localConnection.prepareStatement(str2);
        localPreparedStatement.setInt(1, paramInt1);
        localPreparedStatement.setInt(2, paramInt2);
        localPreparedStatement.executeUpdate();
      }
      localConnection.commit();
    }
    catch (SQLException localSQLException)
    {
      System.out.println("[InstallCheck].logout has a SQLException: msg=" + localSQLException.getMessage());
    }
    finally
    {
      releaseDBResource(localResultSet, localPreparedStatement, localConnection);
    }
    return i;
  }

  private void releaseDBResource(ResultSet paramResultSet, PreparedStatement paramPreparedStatement, Connection paramConnection)
  {
    try
    {
      if (paramResultSet != null)
      {
        paramResultSet.close();
        paramResultSet = null;
      }
      if (paramPreparedStatement != null)
      {
        paramPreparedStatement.close();
        paramPreparedStatement = null;
      }
    }
    catch (SQLException localSQLException)
    {
      System.out.println(" error when close dataBaseResource" + localSQLException.getMessage());
    }
    DbcpDataSource.freeConnection(paramConnection);
  }

  public int getUserId(String paramString)
  {
    log.debug("[SysMgmtImpl()].getUserId param: userName=" + paramString);
    if ((paramString == null) || (paramString.equals("")))
      return 1;
    Session localSession = null;
    Transaction localTransaction = null;
    int i = 0;
    try
    {
      localSession = HBSessionFactory.openSession();
      localTransaction = localSession.beginTransaction();
      List localList = localSession.find("select u from User as u where u.name=?", paramString, Hibernate.STRING);
      if (localList.size() == 0)
      {
        i = -2;
      }
      else
      {
        User localUser = (User)localList.get(0);
        i = localUser.getId();
      }
      localTransaction.commit();
      localSession.close();
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
        jsr 14;
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
    return i;
  }*/
}