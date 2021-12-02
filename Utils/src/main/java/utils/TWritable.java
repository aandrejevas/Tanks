package utils;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
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
		IntStream.range(off, off + len).forEach((final int index) -> write((int)data[index]));
	}

	default void write(final String data) {
		write(data.getBytes(StandardCharsets.US_ASCII));
	}

	default void write(final byte _0, final CharBuffer in) {
		Utils.ascii_encoder.encode(in, Utils.wbuf.put(_0).putInt(in.remaining()), true);
		write(Utils.wbuf.array(), Utils.wbuf.position());
		Utils.wbuf.rewind();
	}

	default void write(final byte _0, final byte[] _1, final int offset, final int length) {
		write(Utils.wbuf.put(0, _0).put(1, _1, offset, length).array(), length + 1);
	}

	default void write(final byte _0) {
		write((int)_0);
	}

	default void write(final byte _0, final int _1) {
		write(Utils.wbuf.put(0, _0).putInt(1, _1).array(), 5);
	}

	default void write(final byte _0, final int _1, final int _2) {
		write(Utils.wbuf.put(0, _0).putInt(1, _1).putInt(5, _2).array(), 9);
	}

	default void write(final byte _0, final int _1, final int _2, final int _3) {
		write(Utils.wbuf.put(0, _0).putInt(1, _1).putInt(5, _2).putInt(9, _3).array(), 13);
	}

	default void write(final byte _0, final int _1, final int _2, final int _3, final int _4) {
		write(Utils.wbuf.put(0, _0).putInt(1, _1).putInt(5, _2).putInt(9, _3).putInt(13, _4).array(), 17);
	}

	default void write(final byte _0, final int _1, final int _2, final int _3, final int _4, final int _5) {
		write(Utils.wbuf.put(0, _0).putInt(1, _1).putInt(5, _2).putInt(9, _3).putInt(13, _4).putInt(17, _5).array(), 21);
	}
}
