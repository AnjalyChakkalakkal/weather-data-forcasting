package au.com.cba.weatherforcasting;

public class WeatherDataBuilderException extends Exception {

	private static final long serialVersionUID = -5845886002241247432L;

	public WeatherDataBuilderException() {

		super();
	}

	public WeatherDataBuilderException(String msg) {

		super(msg);
	}

	public WeatherDataBuilderException(String message, Throwable cause) {

		super(message, cause);
	}

	public WeatherDataBuilderException(Throwable cause) {

		super(cause);
	}
}