package communicate.common.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class DataExistException extends Exception implements Serializable {

	private static final long serialVersionUID = -7508682781741827132L;


	private Throwable nestedThrowable = null;

	public DataExistException(){
		super();
	}

	public DataExistException(String msg){
		super(msg);
	}

	public DataExistException(Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
	}

	public DataExistException(String msg,Throwable nestedThrowable) {
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
