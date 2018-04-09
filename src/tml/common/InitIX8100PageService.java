package tml.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import javax.servlet.ServletException;
import org.apache.tapestry.ApplicationRuntimeException;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.engine.IEngineServiceView;
import org.apache.tapestry.engine.PageService;
import org.apache.tapestry.request.RequestContext;
import org.apache.tapestry.request.ResponseOutputStream;
import tml.common.web.Visit;
import tml.sysmgmt.Role;

public class InitIX8100PageService extends PageService
{
  private static final String DEFAULT_PAGE = "PermitError";
  public static final String INIT_SERVICE_NAME = "initIX8100Page";

  public void service(IEngineServiceView paramIEngineServiceView, IRequestCycle paramIRequestCycle, ResponseOutputStream paramResponseOutputStream)
    throws ServletException, IOException
  {
    RequestContext localRequestContext = paramIRequestCycle.getRequestContext();
    String[] arrayOfString = getServiceContext(localRequestContext);
    if (Tapestry.size(arrayOfString) != 1)
      throw new ApplicationRuntimeException(Tapestry.format("service-single-parameter", getName()));
    Visit localVisit = (Visit)paramIEngineServiceView.getVisit(paramIRequestCycle);
    String str = "PermitError";
    if (localVisit != null)
    {
      String localObject1 = localVisit.getOperator();
      Object localObject2 = localVisit.getRoles();
      if (localObject2 == null)
        localObject2 = new ArrayList();
      HashSet localHashSet = new HashSet();
      Iterator localIterator = ((Collection)localObject2).iterator();
      while (localIterator.hasNext())
      {
        Role localRole = (Role)localIterator.next();
        localHashSet.add(localRole.getValue());
      }
      if (localHashSet.contains(arrayOfString[0]))
        str = arrayOfString[0];
    }
    Object localObject1 = paramIRequestCycle.getPage(str);
    paramIRequestCycle.activate((IPage)localObject1);
    if ((localObject1 instanceof AbstractBasePage))
      ((AbstractBasePage)localObject1).init();
    paramIEngineServiceView.renderResponse(paramIRequestCycle, paramResponseOutputStream);
  }

  public String getName()
  {
    return "initIX8100Page";
  }
}