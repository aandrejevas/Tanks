package utils;

@FunctionalInterface
public interface ToByteFunction<T> {
	byte applyAsByte(final T value);
}
