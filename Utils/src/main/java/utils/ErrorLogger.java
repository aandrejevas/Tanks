package utils;

import java.util.function.Supplier;

public class ErrorLogger implements Logger {
	private ErrorLogger() {
	}

	public static final Logger instance = new ErrorLogger();

	@Override
	public void log(final Supplier<String> message) {
		System.err.println(message.get());
	}
}
