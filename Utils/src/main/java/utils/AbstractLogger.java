package utils;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class AbstractLogger {
	private Logger logger;

	protected AbstractLogger() {
		this(NullLogger.instance);
	}

	protected AbstractLogger(final Logger l) {
		logger = Objects.requireNonNull(l);
	}

	public final Logger getLogger() {
		return logger;
	}

	public final void setLogger(final Logger l) {
		logger = Objects.requireNonNull(l);
	}

	protected final void log(final Supplier<String> message) {
		logger.log(message);
	}
}
