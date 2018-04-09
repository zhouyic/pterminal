package tml.common.web;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

public abstract class Error extends BasePage
{
  private String backPage;

  public abstract String getHint();

  public abstract void setHint(String paramString);

  public void setBackPage(String paramString)
  {
    this.backPage = paramString;
  }

  public void back(IRequestCycle paramIRequestCycle)
  {
    if (this.backPage != null)
      paramIRequestCycle.activate(this.backPage);
  }
}