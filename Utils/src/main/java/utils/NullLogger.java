package utils;

import java.util.function.Supplier;

public class NullLogger implements Logger {
	private NullLogger() {
	}

	public static final Logger instance = new NullLogger();

	@Override
	public void log(final Supplier<String> message) {
	}
}
