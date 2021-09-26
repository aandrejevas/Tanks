
import utils.Utils;

public class Tank {

	protected static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;
	protected static int counter = 0;

	public final int index;
	public int x, y;
	public byte direction;

	public Tank() {
		index = counter++;
		x = Utils.random().nextInt(Main.x_tiles);
		y = Utils.random().nextInt(Main.y_tiles);
		direction = UP;
	}

	protected void sendMove(final byte message) {
		Utils.send(Main.this_server::write, message, index);
	}

	public void moveLeft() {
		if (x == 0) {
			switch (direction) {
				default:
					direction = LEFT;
					sendMove(Utils.POINT_LEFT);
				case LEFT: return;
			}
		} else {
			--x;
			switch (direction) {
				default:
					direction = LEFT;
					sendMove(Utils.TURN_LEFT);
					return;
				case LEFT:
					sendMove(Utils.MOVE_LEFT);
					return;
			}
		}
	}

	public void moveRight() {
		if (x == Main.x_tiles_S1) {
			switch (direction) {
				default:
					direction = RIGHT;
					sendMove(Utils.POINT_RIGHT);
				case RIGHT: return;
			}
		} else {
			++x;
			switch (direction) {
				default:
					direction = RIGHT;
					sendMove(Utils.TURN_RIGHT);
					return;
				case RIGHT:
					sendMove(Utils.MOVE_RIGHT);
					return;
			}
		}
	}

	public void moveUp() {
		if (y == 0) {
			switch (direction) {
				default:
					direction = UP;
					sendMove(Utils.POINT_UP);
				case UP: return;
			}
		} else {
			--y;
			switch (direction) {
				default:
					direction = UP;
					sendMove(Utils.TURN_UP);
					return;
				case UP:
					sendMove(Utils.MOVE_UP);
					return;
			}
		}
	}

	public void moveDown() {
		if (y == Main.y_tiles_S1) {
			switch (direction) {
				default:
					direction = DOWN;
					sendMove(Utils.POINT_DOWN);
				case DOWN: return;
			}
		} else {
			++y;
			switch (direction) {
				default:
					direction = DOWN;
					sendMove(Utils.TURN_DOWN);
					return;
				case DOWN:
					sendMove(Utils.MOVE_DOWN);
					return;
			}
		}
	}
}
