package utils;

public class NullLogger implements Logger {
	private NullLogger() {
	}

	public static final Logger instance = new NullLogger();

	@Override
	public void log(final String message) {
	}
}
