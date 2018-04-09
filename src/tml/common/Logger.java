package tml.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;

public class Logger extends org.apache.log4j.Logger
{
  protected Logger(String paramString)
  {
    super(paramString);
  }

  public static org.apache.log4j.Logger getLogger(Class paramClass)
  {
    return org.apache.log4j.Logger.getLogger(paramClass);
  }

  static
  {
    int i = 1;
    Properties localProperties = new Properties();
    try
    {
      localProperties.load(new FileInputStream(new File("/usr/local/myconfig/tmllog4j_page.properties")));
      PropertyConfigurator.configure(localProperties);
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
      try
      {
        localProperties.load(new FileInputStream(new File("/home/myconfig/tmllog4j_page.properties")));
        PropertyConfigurator.configure(localProperties);
      }
      catch (Exception localException3)
      {
        i = 0;
        localException3.printStackTrace();
      }
    }
    if (i == 0)
      try
      {
        InputStream localInputStream = DBCommon.class.getResourceAsStream("/tmllog4j_page.properties");
        if (localInputStream != null)
          localProperties.load(localInputStream);
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        System.out.println("cann't init Log4j!..................");
      }
  }
}