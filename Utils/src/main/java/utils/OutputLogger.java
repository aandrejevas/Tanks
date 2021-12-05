package utils;

import java.util.function.Supplier;

public class OutputLogger implements Logger {
	private OutputLogger() {
	}

	public static final Logger instance = new OutputLogger();

	@Override
	public void log(final Supplier<String> message) {
		System.out.println(message.get());
	}
}
