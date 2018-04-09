package tml.common.web;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.RedirectException;
import org.apache.tapestry.StaleLinkException;
import org.apache.tapestry.StaleSessionException;
import org.apache.tapestry.engine.BaseEngine;
import org.apache.tapestry.request.ResponseOutputStream;

public class WebEngine extends BaseEngine
{
  protected void handlePageRedirectException(PageRedirectException paramPageRedirectException, IRequestCycle paramIRequestCycle, ResponseOutputStream paramResponseOutputStream)
    throws IOException, ServletException
  {
    super.handlePageRedirectException(paramPageRedirectException, paramIRequestCycle, paramResponseOutputStream);
  }

  protected void handleRedirectException(IRequestCycle paramIRequestCycle, RedirectException paramRedirectException)
  {
    super.handleRedirectException(paramIRequestCycle, paramRedirectException);
  }

  protected void handleStaleLinkException(StaleLinkException paramStaleLinkException, IRequestCycle paramIRequestCycle, ResponseOutputStream paramResponseOutputStream)
    throws IOException, ServletException
  {
    super.handleStaleLinkException(paramStaleLinkException, paramIRequestCycle, paramResponseOutputStream);
  }

  protected void handleStaleSessionException(StaleSessionException paramStaleSessionException, IRequestCycle paramIRequestCycle, ResponseOutputStream paramResponseOutputStream)
    throws IOException, ServletException
  {
    redirect("Home", paramIRequestCycle, paramResponseOutputStream, paramStaleSessionException);
  }
}