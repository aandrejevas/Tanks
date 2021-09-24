
import utils.Utils;

public class Tank {

	private static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	private static int counter = 0;

	public final int index;
	public int x, y;

	private int direction;

	public Tank() {
		index = counter++;
		x = Utils.random().nextInt(Main.x_tiles);
		y = Utils.random().nextInt(Main.y_tiles);
		direction = UP;
	}

	public byte getAddMessage() {
		switch (direction) {
			case LEFT: return Utils.ADD_LEFT_TANK;
			case RIGHT: return Utils.ADD_RIGHT_TANK;
			case UP: return Utils.ADD_UP_TANK;
			case DOWN: return Utils.ADD_DOWN_TANK;
			default: throw new AssertionError();
		}
	}

	public byte moveLeft() {
		--x;
		switch (direction) {
			case RIGHT:
			case UP:
			case DOWN:
				direction = LEFT;
				return Utils.POINT_LEFT;
			case LEFT: return Utils.MOVE_LEFT;
			default: throw new AssertionError();
		}
	}

	public byte moveRight() {
		++x;
		switch (direction) {
			case LEFT:
			case UP:
			case DOWN:
				direction = RIGHT;
				return Utils.POINT_RIGHT;
			case RIGHT: return Utils.MOVE_RIGHT;
			default: throw new AssertionError();
		}
	}

	public byte moveUp() {
		--y;
		switch (direction) {
			case LEFT:
			case RIGHT:
			case DOWN:
				direction = UP;
				return Utils.POINT_UP;
			case UP: return Utils.MOVE_UP;
			default: throw new AssertionError();
		}
	}

	public byte moveDown() {
		++y;
		switch (direction) {
			case LEFT:
			case RIGHT:
			case UP:
				direction = DOWN;
				return Utils.POINT_DOWN;
			case DOWN: return Utils.MOVE_DOWN;
			default: throw new AssertionError();
		}
	}
}
