package utils;

import java.nio.ByteBuffer;
import java.util.function.IntConsumer;
import processing.net.Client;

public class Utils {
	private Utils() {
	}

	public static final ByteBuffer buffer1 = ByteBuffer.allocate(1),
		buffer4 = ByteBuffer.allocate(4),
		buffer5 = ByteBuffer.allocate(5),
		buffer8 = ByteBuffer.allocate(8),
		buffer9 = ByteBuffer.allocate(9),
		buffer12 = ByteBuffer.allocate(12),
		buffer13 = ByteBuffer.allocate(13);

	public static byte[] bytes(final int _0) {
		return buffer1.put(0, (byte)_0).array();
	}

	public static byte[] bytes(final int _0, final int _1) {
		return buffer5.put(0, (byte)_0).putInt(1, _1).array();
	}

	public static byte[] bytes(final int _0, final int _1, final int _2) {
		return buffer9.put(0, (byte)_0).putInt(1, _1).putInt(5, _2).array();
	}

	public static byte[] bytes(final int _0, final int _1, final int _2, final int _3) {
		return buffer13.put(0, (byte)_0).putInt(1, _1).putInt(5, _2).putInt(9, _3).array();
	}

	public static void read(final Client client, final IntConsumer action) {
		client.readBytes(buffer4.array());
		action.accept(buffer4.getInt(0));
	}

	public static void read(final Client client, final IntBiConsumer action) {
		client.readBytes(buffer8.array());
		action.accept(buffer8.getInt(0), buffer8.getInt(4));
	}

	public static void read(final Client client, final IntTriConsumer action) {
		client.readBytes(buffer12.array());
		action.accept(buffer12.getInt(0), buffer12.getInt(4), buffer12.getInt(8));
	}
}
