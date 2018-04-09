package tml.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class DataExistException extends Exception
  implements Serializable
{
  private static final long serialVersionUID = -7508682781741827132L;
  private Throwable nestedThrowable = null;

  public DataExistException()
  {
  }

  public DataExistException(String paramString)
  {
    super(paramString);
  }

  public DataExistException(Throwable paramThrowable)
  {
    this.nestedThrowable = paramThrowable;
  }

  public DataExistException(String paramString, Throwable paramThrowable)
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