package tml.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class SqlException extends Exception
  implements Serializable
{
  private static final long serialVersionUID = 310642907249203645L;
  private Throwable nestedThrowable = null;

  public SqlException()
  {
    super("");
  }

  public SqlException(String paramString)
  {
    super(paramString);
  }

  public SqlException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }

  public SqlException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.nestedThrowable = paramThrowable;
  }

  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.nestedThrowable != null)
      this.nestedThrowable.printStackTrace();
  }

  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.nestedThrowable != null)
      this.nestedThrowable.printStackTrace(paramPrintStream);
  }

  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.nestedThrowable != null)
      this.nestedThrowable.printStackTrace(paramPrintWriter);
  }
}