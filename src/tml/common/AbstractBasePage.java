package tml.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.tapestry.IEngine;
import org.apache.tapestry.IPage;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.event.PageDetachListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.event.PageRenderListener;
import org.apache.tapestry.html.BasePage;
import tml.common.web.Error;
import tml.common.web.Success;
import tml.common.web.Visit;
import tml.log.ILog;
import tml.log.LogImpl;

public abstract class AbstractBasePage extends BasePage
  implements PageRenderListener, PageDetachListener
{
  private static final Properties properties = new Properties();
  private ILog log = new LogImpl();

  public abstract void pageBeginRender(PageEvent paramPageEvent);

  public abstract void pageDetached(PageEvent paramPageEvent);

  protected void printStack()
  {
    try
    {
      int i = 0;
      int j = 1 / i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  protected void initialize()
  {
    super.initialize();
  }

  public void init()
  {
  }

  public void writeLog(String paramString)
  {
    this.log.addLog(getOperator(), paramString);
  }

  public String getOperator()
  {
    Visit localVisit = (Visit)getPage().getEngine().getVisit();
    if (localVisit != null)
      return localVisit.getOperator();
    return null;
  }

  public String formatTime(Date paramDate)
  {
    if (paramDate == null)
      return "";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return localSimpleDateFormat.format(paramDate);
  }

  public String getLabel(String paramString)
  {
    String str = properties.getProperty(paramString);
    return str;
  }

  public String getLabel(int paramInt)
  {
    return getLabel("" + paramInt);
  }

  public BasePage getPage(String paramString)
  {
    if (paramString == null)
      return this;
    return (BasePage)getPage().getRequestCycle().getPage(paramString);
  }

  public void activate(String paramString)
  {
    if (paramString != null)
      getPage().getRequestCycle().activate(paramString);
  }

  public void activate(BasePage paramBasePage)
  {
    if (paramBasePage != null)
    {
      getPage().getRequestCycle().activate(paramBasePage);
      if ((paramBasePage instanceof AbstractBasePage))
        ((AbstractBasePage)paramBasePage).init();
    }
  }

  public void activate(IRequestCycle paramIRequestCycle, String paramString)
  {
    if (paramIRequestCycle == null)
    {
      activate(paramString);
      return;
    }
    if (paramString != null)
      paramIRequestCycle.activate(paramString);
  }

  public void activate(IRequestCycle paramIRequestCycle, BasePage paramBasePage)
  {
    if (paramIRequestCycle == null)
    {
      activate(paramBasePage);
      return;
    }
    if (paramBasePage != null)
      paramIRequestCycle.activate(paramBasePage);
  }

  public BasePage getPage(IRequestCycle paramIRequestCycle, String paramString)
  {
    if (paramString == null)
      return this;
    if (paramIRequestCycle == null)
      return getPage(paramString);
    return (BasePage)paramIRequestCycle.getPage(paramString);
  }

  public void toSuccessPage(String paramString)
  {
    Success localSuccess = (Success)getPage("Success");
    localSuccess.setHint(paramString);
    activate(localSuccess);
  }

  public void toSuccessPage(String paramString1, String paramString2)
  {
    Success localSuccess = (Success)getPage("Success");
    localSuccess.setHint(paramString1);
    localSuccess.setBackPage(paramString2);
    activate(localSuccess);
  }

  public void toErrorPage(String paramString1, String paramString2)
  {
    Error localError = (Error)getPage("Error");
    localError.setHint(paramString1);
    localError.setBackPage(paramString2);
    activate(localError);
  }

  static
  {
    try
    {
      InputStream localInputStream = AbstractBasePage.class.getResourceAsStream("/web.properties");
      if (localInputStream != null)
        properties.load(localInputStream);
      else
        properties.load(new FileInputStream(new File("/web.native")));
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      System.out.println("cann't init resource..................");
    }
  }
}