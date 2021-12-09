package utils;

import java.nio.ByteBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Utils {
	protected Utils() {
		throw new AssertionError();
	}

	public static final byte INITIALIZE_GRID = 1,
		ADD_LEFT_TANK = 2, ADD_RIGHT_TANK = 12, ADD_UP_TANK = 13, ADD_DOWN_TANK = 14, ADD_NEW_TANK = 36, REMOVE_TANK = 3, REMOVE_AI_TANK = 42,
		MOVE_LEFT = 4, MOVE_RIGHT = 5, MOVE_UP = 6, MOVE_DOWN = 7, POINT_LEFT = 8, POINT_RIGHT = 9, POINT_UP = 10, POINT_DOWN = 11,
		TURN_LEFT = 15, TURN_RIGHT = 16, TURN_UP = 17, TURN_DOWN = 18,
		ADD_DROP = 28, REMOVE_DROP = 29,
		BULLET_LEFT = 30, BULLET_RIGHT = 31, BULLET_UP = 32, BULLET_DOWN = 33,
		ADD_BULLET = 34, REMOVE_BULLET = 35,
		SET_HEALTH = 37, SET_ARMOR = 38,
		MESSAGE = 39,
		GAME_END = 40, GAME_START = 41;

	public static final byte S_MOVE_LEFT = 0, S_MOVE_RIGHT = 1, S_MOVE_UP = 2, S_MOVE_DOWN = 3,
		S_INIT_CLIENT = 4, S_SHOOT_NORMAL = 5, S_SHOOT_BLUE = 6, S_SHOOT_RED = 7,
		S_MESSAGE = MESSAGE;

	public static final byte MAP_WALL = 1, MAP_BORDER = 2, MAP_PLAYER = 4, MAP_T34 = 5, MAP_T34H = 3, MAP_SHERMAN = 6, MAP_TIGER = 7,
		MAP_NON_OBSTACLE = 8, MAP_EMPTY = 9, MAP_WATER = 10, MAP_LAVA = 11,
		MAP_COLLECTIBLE = 16, MAP_DROP = 17,
		DROP_SHEALTH = 18, DROP_MHEALTH = 19, DROP_LHEALTH = 20,
		DROP_SARMOR = 21, DROP_MARMOR = 22, DROP_LARMOR = 23,
		DROP_SAMMO = 24, DROP_MAMMO = 25, DROP_LAMMO = 26,
		SHOT_NORMAL = 27, SHOT_RED = 28, SHOT_BLUE = 29,
		BIG_SHOT_NORMAL = 30, BIG_SHOT_RED = 31, BIG_SHOT_BLUE = 32,
		HEALTH_ICON = 33, SHIELD_ICON = 37,
		SELECTED_SHOT_NORMAL = 34, SELECTED_SHOT_RED = 35, SELECTED_SHOT_BLUE = 36;

	public static final Random random = new Random();

	public static final ByteBuffer wbuf = ByteBuffer.allocate(1000),
		rbuf = ByteBuffer.allocate(1000);
	public static final CharsetEncoder ascii_encoder = StandardCharsets.US_ASCII.newEncoder()
		.onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);

	public static ThreadLocalRandom random() {
		return ThreadLocalRandom.current();
	}

	public static <T> T random(final T[] array) {
		return array[random().nextInt(array.length)];
	}

	public static int sq(final int x) {
		return x * x;
	}

	public static int manhattanDist(final int[] from, final int[] to) {
		return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
	}
}
