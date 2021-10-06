package utils;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadLocalRandom;
import processing.net.Client;

public abstract class Utils {
	protected Utils() {
		throw new AssertionError();
	}

	public static final byte INITIALIZE = 0, INITIALIZE_GRID = 1,
		ADD_LEFT_TANK = 2, ADD_RIGHT_TANK = 12, ADD_UP_TANK = 13, ADD_DOWN_TANK = 14, REMOVE_TANK = 3,
		MOVE_LEFT = 4, MOVE_RIGHT = 5, MOVE_UP = 6, MOVE_DOWN = 7, POINT_LEFT = 8, POINT_RIGHT = 9, POINT_UP = 10, POINT_DOWN = 11,
		TURN_LEFT = 15, TURN_RIGHT = 16, TURN_UP = 17, TURN_DOWN = 18,
		S_MOVE_LEFT = 0, S_MOVE_RIGHT = 1, S_MOVE_UP = 2, S_MOVE_DOWN = 3,
		S_INIT_CLIENT = 4, S_SPAWN_TANK = 5;

	public static final ByteBuffer buffer4 = ByteBuffer.allocate(4),
		buffer5 = ByteBuffer.allocate(5),
		buffer8 = ByteBuffer.allocate(8),
		buffer9 = ByteBuffer.allocate(9),
		buffer12 = ByteBuffer.allocate(12),
		buffer13 = ByteBuffer.allocate(13),
		buffer = ByteBuffer.allocate(1000);

	public static int i1, i2, i3;

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
