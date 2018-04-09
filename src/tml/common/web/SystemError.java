package tml.common.web;

import org.apache.tapestry.html.BasePage;

public abstract class SystemError extends BasePage
{
  public abstract void setMsg(String paramString);

  public abstract String getMsg();
}