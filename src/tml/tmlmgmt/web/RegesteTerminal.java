package tml.tmlmgmt.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.form.IPropertySelectionModel;
import tml.common.AbstractBasePage;
import tml.common.DBCommon;
import tml.common.DefaultPropertySelectionModel;
import tml.common.SelectModel;
import tml.common.TimeMgmt;
import tml.sysmgmt.Dom;
import tml.tmlmgmt.jdbc.ITmlJDBC;
import tml.tmlmgmt.jdbc.TmlJDBCImpl;

public abstract class RegesteTerminal extends AbstractBasePage
{
  private static final org.apache.log4j.Logger log = tml.common.Logger.getLogger(RegesteTerminal.class);
  private IPropertySelectionModel cuTypeModel;
  private IPropertySelectionModel typeModel;
  private IPropertySelectionModel domModel;

  public IPropertySelectionModel getCuTypeModel()
  {
    return this.cuTypeModel;
  }

  public void setCuTypeModel(IPropertySelectionModel paramIPropertySelectionModel)
  {
    this.cuTypeModel = paramIPropertySelectionModel;
  }

  public IPropertySelectionModel getTypeModel()
  {
    return this.typeModel;
  }

  public void setTypeModel(IPropertySelectionModel paramIPropertySelectionModel)
  {
    this.typeModel = paramIPropertySelectionModel;
  }

  public IPropertySelectionModel getDomTypeModel()
  {
    return this.domModel;
  }

  public void setDomTypeModel(IPropertySelectionModel paramIPropertySelectionModel)
  {
    this.domModel = paramIPropertySelectionModel;
  }

  public abstract String getCustomerId();

  public abstract void setCustomerId(String paramString);

  public abstract String getPassword();

  public abstract void setPassword(String paramString);

  public abstract SelectModel getCuType();

  public abstract void setCuType(SelectModel paramSelectModel);

  public abstract SelectModel getTmlType();

  public abstract void setTmlType(SelectModel paramSelectModel);

  public abstract SelectModel getDomType();

  public abstract void setDomType(SelectModel paramSelectModel);

  public abstract String getMac();

  public abstract void setMac(String paramString);

  public abstract String getText();

  public abstract void setText(String paramString);

  public void initCuTypeModel()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SelectModel("普通", "普通"));
    localArrayList.add(new SelectModel("VIP", "VIP"));
    setCuTypeModel(new DefaultPropertySelectionModel(localArrayList, "label", "value"));
  }

  public void initDomTypeModel()
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    localArrayList1.add(new SelectModel("", ""));
    String str = "SELECT DOMID,DONAME FROM SYS_DOM";
    DBCommon localDBCommon = new DBCommon();
    ResultSet localResultSet = null;
    try
    {
      localDBCommon = new DBCommon(str);
      localResultSet = localDBCommon.executeQuery();
      while (localResultSet.next())
      {
        Dom localDom1 = new Dom();
        localDom1.setId(localResultSet.getInt("DOMID"));
        localDom1.setName(localResultSet.getString("DONAME"));
        localArrayList2.add(localDom1);
      }
    }
    catch (SQLException localSQLException)
    {
      log.error(localSQLException.getMessage());
    }
    finally
    {
      localDBCommon.close();
      localDBCommon = null;
    }
    Iterator localIterator = localArrayList2.iterator();
    while (localIterator.hasNext())
    {
      Dom localDom2 = (Dom)localIterator.next();
      localArrayList1.add(new SelectModel(localDom2.getName(), String.valueOf(localDom2.getId())));
    }
    setDomTypeModel(new DefaultPropertySelectionModel(localArrayList1, "label", "value"));
  }

  public void initTypeModel()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SelectModel("", ""));
    localArrayList.add(new SelectModel("无硬盘STB", String.valueOf(1)));
    localArrayList.add(new SelectModel("带硬盘STB", String.valueOf(2)));
    localArrayList.add(new SelectModel("NOVA Client", String.valueOf(3)));
    setTypeModel(new DefaultPropertySelectionModel(localArrayList, "label", "value"));
  }

  public void init()
  {
    initTypeModel();
    initCuTypeModel();
    initDomTypeModel();
    setMac("");
  }

  public void pageBeginRender(PageEvent paramPageEvent)
  {
  }

  public void pageDetached(PageEvent paramPageEvent)
  {
  }

  public void submit(IRequestCycle paramIRequestCycle)
  {
    int i = 0;
    String str1 = getTmlType().getValue();
    if ((str1 != null) && (!str1.trim().equals("")))
      i = Integer.valueOf(str1).intValue();
    String str2 = getCuType().getValue();
    int j;
    if ((getDomType().getValue() != null) && (getDomType().getValue().trim().length() != 0))
    {
      j = Integer.valueOf(getDomType().getValue()).intValue();
      log.info("domId=" + j);
    }
    else
    {
      j = -3;
      log.info("终端没有注册到组");
    }
    String str3 = getMac();
    String str4 = getText();
    String str5 = getCustomerId();
    String str6 = getPassword();
    TimeMgmt localTimeMgmt = new TimeMgmt();
    String str7 = localTimeMgmt.getCurDate(9);
    String str8 = getOperator();
    TmlJDBCImpl localTmlJDBCImpl = new TmlJDBCImpl();
    int k = j == -3 ? localTmlJDBCImpl.registerTml(str5, str6, str2, str3, i, "", str7, str8, str4) : localTmlJDBCImpl.registerTmlToDom(str5, str6, str2, str3, i, "", str7, str8, str4, j);
    if (k == 0)
    {
      writeLog("注册终端  MAC: " + str3 + "，描述：" + str4);
      toSuccessPage("注册成功！", "RegesteTerminal");
    }
    else
    {
      toErrorPage("注册失败！", "RegesteTerminal");
    }
  }
}