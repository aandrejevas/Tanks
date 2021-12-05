package utils;

import java.util.function.Supplier;

public interface Logger {
	void log(final Supplier<String> message);
}
