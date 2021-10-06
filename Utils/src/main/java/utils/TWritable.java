package utils;

import java.util.stream.IntStream;

public interface TWritable {
	void write(final int data);

	default void write(final byte[] data) {
		write(data, data.length);
	}

	default void write(final byte[] data, final int len) {
		write(data, 0, len);
	}

	default void write(final byte[] data, final int off, final int len) {
		IntStream.range(off, off + len).forEach((final int index) -> write(data[index]));
	}

	default void write(final String data) {
		write(data.getBytes());
	}

	default void write(final byte _0) {
		write((int)_0);
	}

	default void write(final byte _0, final int _1) {
		write(Utils.buffer.put(0, _0).putInt(1, _1).array(), 5);
	}

	default void write(final byte _0, final int _1, final int _2) {
		write(Utils.buffer.put(0, _0).putInt(1, _1).putInt(5, _2).array(), 9);
	}

	default void write(final byte _0, final int _1, final int _2, final int _3) {
		write(Utils.buffer.put(0, _0).putInt(1, _1).putInt(5, _2).putInt(9, _3).array(), 13);
	}
}
