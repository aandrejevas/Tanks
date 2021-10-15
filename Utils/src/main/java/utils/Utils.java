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
		S_INIT_CLIENT = 4,
		ADD_DROP = 28, REMOVE_DROP = 29;

	public static final byte MAP_WALL = 1, MAP_BORDER = 2, MAP_PLAYER = 4, MAP_T34 = 5, MAP_SHERMAN = 6, MAP_TIGER = 7,
		MAP_NON_OBSTACLE = 8, MAP_EMPTY = 9, MAP_WATER = 10, MAP_LAVA = 11,
		MAP_COLLECTIBLE = 16, MAP_DROP = 17;

	public static final byte DROP_SHEALTH = 18, DROP_MHEALTH = 19, DROP_LHEALTH = 20,
		DROP_SARMOR = 21, DROP_MARMOR = 22, DROP_LARMOR = 23,
		DROP_SAMMO = 24, DROP_MAMMO = 25, DROP_LAMMO = 26;

	public static final ByteBuffer buffer4 = ByteBuffer.allocate(4),
		buffer5 = ByteBuffer.allocate(5),
		buffer8 = ByteBuffer.allocate(8),
		buffer9 = ByteBuffer.allocate(9),
		buffer12 = ByteBuffer.allocate(12),
		buffer13 = ByteBuffer.allocate(13),
		buffer16 = ByteBuffer.allocate(16),
		buffer17 = ByteBuffer.allocate(17),
		buffer20 = ByteBuffer.allocate(20),
		buffer21 = ByteBuffer.allocate(21),
		buffer = ByteBuffer.allocate(1000);

	public static int i1, i2, i3, i4, i5;

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

	public static void readIV(final Client client) {
		client.readBytes(buffer16.array());
		i1 = buffer16.getInt(0);
		i2 = buffer16.getInt(4);
		i3 = buffer16.getInt(8);
		i4 = buffer16.getInt(12);
	}

	public static void readV(final Client client) {
		client.readBytes(buffer20.array());
		i1 = buffer20.getInt(0);
		i2 = buffer20.getInt(4);
		i3 = buffer20.getInt(8);
		i4 = buffer20.getInt(12);
		i5 = buffer20.getInt(16);
	}

	public static ThreadLocalRandom random() {
		return ThreadLocalRandom.current();
	}

	public static int getRand(final ArenaMap map) {
		map.seed = ((map.seed * 1103515245) + 12345) & 0x7fffffff;
		return map.seed;
	}
}
