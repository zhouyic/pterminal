package tml.common.web;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import org.apache.tapestry.IEngine;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.request.RequestContext;
import tml.common.DBClient;
import tml.sysmgmt.ISysMgmt;
import tml.sysmgmt.SysMgmtImpl;

public abstract class Home extends BasePage
{
  public abstract String getOperator();

  public abstract void setOperator(String paramString);

  public abstract String getPassword();

  public abstract void setPassword(String paramString);

  public void pageBeginRender(PageEvent paramPageEvent)
  {
    setOperator("");
    setPassword("");
  }

  public void pageDetached(PageEvent paramPageEvent)
  {
    setOperator("");
    setPassword("");
  }

  public void submit(IRequestCycle paramIRequestCycle)
  {
    Visit localVisit = (Visit)getPage().getEngine().getVisit(paramIRequestCycle);
    if (localVisit != null)
      localVisit.setOperator(getOperator());
    String str = paramIRequestCycle.getRequestContext().getRequest().getRemoteHost();
    SysMgmtImpl localSysMgmtImpl = new SysMgmtImpl();
    int i = localSysMgmtImpl.login(getOperator(), getPassword(), str);
    Object localObject;
    if (i == 0)
    {
      localVisit.setRoles(localSysMgmtImpl.getAllUserRoles(getOperator()));
      localObject = (Control)paramIRequestCycle.getPage("Control");
      ((Control)localObject).init();
      paramIRequestCycle.activate((IPage)localObject);
    }
    else if (i == 1)
    {
      localObject = (SystemError)paramIRequestCycle.getPage("SystemError");
      ((SystemError)localObject).setMsg("The username or password that you import has a mistake, import again please!");
      paramIRequestCycle.activate((IPage)localObject);
    }
    else if ((i == 5) && (!checkDB()))
    {
      localObject = (SystemError)paramIRequestCycle.getPage("SystemError");
      ((SystemError)localObject).setMsg("Database error!");
      paramIRequestCycle.activate((IPage)localObject);
    }
    else
    {
      localObject = (SystemError)paramIRequestCycle.getPage("SystemError");
      ((SystemError)localObject).setMsg("System error!");
      paramIRequestCycle.activate((IPage)localObject);
    }
  }

  private boolean checkDB()
  {
    DBClient localDBClient = new DBClient();
    Connection localConnection = localDBClient.getConnection();
    if (localConnection != null)
    {
      localDBClient.closeConnection();
      return true;
    }
    return false;
  }
}