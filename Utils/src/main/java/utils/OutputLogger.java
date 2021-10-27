package utils;

public class OutputLogger implements Logger {
	private OutputLogger() {
	}

	public static final Logger instance = new OutputLogger();

	@Override
	public void log(final String message) {
		System.out.println(message);
	}
}
