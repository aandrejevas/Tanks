package utils;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;
import processing.net.Client;

public abstract class Utils {
	protected Utils() {
		throw new AssertionError();
	}

	public static final byte INITIALIZE = 0, INITIALIZE_GRID = 1,
		ADD_TANK = 2, REMOVE_TANK = 3,
		MOVE_LEFT = 4, MOVE_RIGHT = 5, MOVE_UP = 6, MOVE_DOWN = 7,
		S_MOVE_LEFT = 0, S_MOVE_RIGHT = 1, S_MOVE_UP = 2, S_MOVE_DOWN = 3;

	public static final ByteBuffer buffer4 = ByteBuffer.allocate(4),
		buffer5 = ByteBuffer.allocate(5),
		buffer8 = ByteBuffer.allocate(8),
		buffer9 = ByteBuffer.allocate(9),
		buffer12 = ByteBuffer.allocate(12),
		buffer13 = ByteBuffer.allocate(13);

	public static int i1, i2, i3;

	public static byte[] bytes(final byte _0, final int _1) {
		return buffer5.put(0, _0).putInt(1, _1).array();
	}

	public static byte[] bytes(final byte _0, final int _1, final int _2) {
		return buffer9.put(0, _0).putInt(1, _1).putInt(5, _2).array();
	}

	public static byte[] bytes(final byte _0, final int _1, final int _2, final int _3) {
		return buffer13.put(0, _0).putInt(1, _1).putInt(5, _2).putInt(9, _3).array();
	}

	public static int readInt(final Client client) {
		client.readBytes(buffer4.array());
		return buffer4.getInt(0);
	}

	public static void readI(final Client client) {
		client.readBytes(buffer4.array());
		i1 = buffer4.getInt(0);
	}

	public static void readII(final Client client) {
		client.readBytes(buffer8.array());
		i1 = buffer8.getInt(0);
		i2 = buffer8.getInt(4);
	}

	public static void readIII(final Client client) {
		client.readBytes(buffer12.array());
		i1 = buffer12.getInt(0);
		i2 = buffer12.getInt(4);
		i3 = buffer12.getInt(8);
	}

	public static ThreadLocalRandom random() {
		return ThreadLocalRandom.current();
	}
}
