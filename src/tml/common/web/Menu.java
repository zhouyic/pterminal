package tml.common.web;

import com.uptech.web.tapestry.components.menu.MenuItem;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.tapestry.IEngine;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.request.RequestContext;
import tml.common.AbstractBasePage;
import tml.sysmgmt.Role;
import tml.sysmgmt.SysMgmtImpl;

public class Menu extends BasePage
{
  public void init()
  {
    System.out.println(" ----------- init ");
  }

  public List<MenuItem> getMenuItems()
  {
    List<MenuItem> localArrayList = new ArrayList<MenuItem>();
    Visit localVisit = (Visit)getPage().getEngine().getVisit(getRequestCycle());
    if (localVisit == null)
      return Collections.EMPTY_LIST;
    MenuItem localMenuItem = null;
    Object localObject = localVisit.getRoles();
    if (localObject == null)
      localObject = new ArrayList();
    HashSet localHashSet = new HashSet();
    int i = 0;
    Iterator localIterator = ((Collection)localObject).iterator();
    while (localIterator.hasNext())
    {
      Role localRole = (Role)localIterator.next();
      String str = localRole.getParent();
      if (!localHashSet.contains(str))
      {
        localHashSet.add(str);
        localMenuItem = new MenuItem();
        localMenuItem.setDisplay(true);
        localMenuItem.setLevel(0);
        localMenuItem.setFlag(0);
        localMenuItem.setPermission("" + localRole.getId() / 100);
        localMenuItem.setName(str);
        localMenuItem.setMenuid(localRole.getId() / 100);
        localMenuItem.setParentid(0);
        localMenuItem.setSequence(++i);
        localArrayList.add(localMenuItem);
      }
      localMenuItem = new MenuItem();
      localMenuItem.setDisplay(true);
      localMenuItem.setLevel(1);
      localMenuItem.setFlag(1);
      localMenuItem.setPermission("" + localRole.getId());
      localMenuItem.setName(localRole.getName());
      localMenuItem.setMenuid(localRole.getId());
      localMenuItem.setParentid(localRole.getId() / 100);
      localMenuItem.setSequence(++i);
      localMenuItem.setUrl("/terminal/app?service=initIX8100Page/" + localRole.getValue());
      localArrayList.add(localMenuItem);
    }
    return localArrayList;
  }

  public void logout(IRequestCycle paramIRequestCycle)
  {
    Visit localVisit = (Visit)getPage().getEngine().getVisit(paramIRequestCycle);
    if (localVisit != null)
    {
      String str = paramIRequestCycle.getRequestContext().getRequest().getRemoteHost();
      new SysMgmtImpl().logout(localVisit.getOperator(), str);
    }
    paramIRequestCycle.activate("Home");
  }

  public void go(IRequestCycle paramIRequestCycle, String paramString)
  {
    IPage localIPage = paramIRequestCycle.getPage(paramString);
    paramIRequestCycle.activate(localIPage);
    if ((localIPage instanceof AbstractBasePage))
      ((AbstractBasePage)localIPage).init();
  }
}