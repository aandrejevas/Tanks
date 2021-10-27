package utils;

public class ErrorLogger implements Logger {
	private ErrorLogger() {
	}

	public static final Logger instance = new ErrorLogger();

	@Override
	public void log(final String message) {
		System.err.println(message);
	}
}
