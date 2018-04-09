package tml.common.web;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.AbstractService;
import org.apache.tapestry.engine.IEngineServiceView;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.request.ResponseOutputStream;
import org.jCharts.Chart;
import org.jCharts.encoders.JPEGEncoder13;

public class ChartService extends AbstractService
{
  public static final String SERVICE_NAME = "chart";

  public ILink getLink(IRequestCycle paramIRequestCycle, IComponent paramIComponent, Object[] paramArrayOfObject)
  {
    String str1 = paramIComponent.getPage().getPageName();
    String str2 = paramIComponent.getIdPath();
    String[] arrayOfString;
    if (str2 != null)
    {
      arrayOfString = new String[2];
      arrayOfString[1] = str2;
    }
    else
    {
      arrayOfString = new String[1];
    }
    arrayOfString[0] = str1;
    return constructLink(paramIRequestCycle, "chart", arrayOfString, null, true);
  }

  public void service(IEngineServiceView paramIEngineServiceView, IRequestCycle paramIRequestCycle, ResponseOutputStream paramResponseOutputStream)
    throws ServletException, IOException
  {
    String[] arrayOfString = getServiceContext(paramIRequestCycle.getRequestContext());
    String str1 = arrayOfString[0];
    String str2 = arrayOfString.length == 1 ? null : arrayOfString[1];
    IPage localIPage = paramIRequestCycle.getPage(str1);
    IComponent localIComponent = str2 == null ? localIPage : localIPage.getNestedComponent(str2);
    try
    {
      IChartProvider localIChartProvider = (IChartProvider)localIComponent;
      Chart localChart = localIChartProvider.getChart();
      paramResponseOutputStream.setContentType("image/jpeg");
      synchronized (this)
      {
        JPEGEncoder13.encode(localChart, 1.0F, paramResponseOutputStream);
      }
    }
    catch (ClassCastException localClassCastException)
    {
      paramIEngineServiceView.reportException("Component " + localIComponent.getExtendedId() + " does not implement IChartProvider.", localClassCastException);
      return;
    }
    catch (Throwable localThrowable)
    {
      paramIEngineServiceView.reportException("Error creating JPEG stream.", localThrowable);
      return;
    }
  }

  public String getName()
  {
    return "chart";
  }
}