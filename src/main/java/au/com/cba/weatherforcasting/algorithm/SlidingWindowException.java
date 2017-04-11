package au.com.cba.weatherforcasting.algorithm;

public class SlidingWindowException extends Exception {

	private static final long serialVersionUID = -5845886002241247432L;

	public SlidingWindowException() {

		super();
	}

	public SlidingWindowException(String msg) {

		super(msg);
	}

	public SlidingWindowException(String message, Throwable cause) {

		super(message, cause);
	}

	public SlidingWindowException(Throwable cause) {

		super(cause);
	}
}