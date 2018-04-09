package tml.common.web;

import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;

public class Control extends BasePage
{
  public String getTopPageName()
  {
    return "Top";
  }

  public String getLeftPageName()
  {
    return "Menu";
  }

  public String getMainPageName()
  {
    return "Welcome";
  }

  public void init()
  {
  }

  public void pageDetached(PageEvent paramPageEvent)
  {
  }

  public void pageBeginRender(PageEvent paramPageEvent)
  {
  }
}