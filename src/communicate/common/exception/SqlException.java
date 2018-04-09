package communicate.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class SqlException  extends Exception implements Serializable {
	private static final long serialVersionUID = 310642907249203645L;

	private Throwable nestedThrowable = null;

	public SqlException() {
		super("");
	}


	public SqlException(String msg) {
		super(msg);
	}

	public SqlException(java.lang.Throwable nestedThrowable) {
		super(nestedThrowable);
	}

	public SqlException(String msg,Throwable nestedThrowable) {
		super(msg);
		this.nestedThrowable = nestedThrowable;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		if (nestedThrowable != null) {
			nestedThrowable.printStackTrace();
		}
	}

	@Override
	public void printStackTrace(PrintStream ps) {
		super.printStackTrace(ps);
		if (nestedThrowable != null) {
			nestedThrowable.printStackTrace(ps);
		}
	}

	@Override
	public void printStackTrace(PrintWriter pw) {
		super.printStackTrace(pw);
		if (nestedThrowable != null) {
			nestedThrowable.printStackTrace(pw);
		}
	}
}
