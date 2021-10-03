
import utils.Utils;

public class Tank {

	protected static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;
	protected static int counter = 0;

	public final int index;
	public int x, y;
	public byte direction;

	public Tank() {
		index = counter++;
		do {
			x = Utils.random().nextInt(Main.edge);
			y = Utils.random().nextInt(Main.edge);
		} while (Main.map.map[y][x].value < Utils.MAP_NON_OBSTACLE);
		Main.map.map[y][x].value = Utils.MAP_PLAYER;
		direction = UP;
	}

	protected void sendMove(final byte message) {
		Utils.send(Main.this_server::write, message, index);
	}

	public void moveLeft() {
		if (Main.map.map[y][x - 1].value < Utils.MAP_NON_OBSTACLE) {
			switch (direction) {
				default:
					direction = LEFT;
					sendMove(Utils.POINT_LEFT);
				case LEFT: return;
			}
		} else {
			Main.map.map[y][x].value = Main.map.defMap[y][x].value;
			Main.map.map[y][--x].value = Utils.MAP_PLAYER;
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
		if (Main.map.map[y][x + 1].value < Utils.MAP_NON_OBSTACLE) {
			switch (direction) {
				default:
					direction = RIGHT;
					sendMove(Utils.POINT_RIGHT);
				case RIGHT: return;
			}
		} else {
			Main.map.map[y][x].value = Main.map.defMap[y][x].value;
			Main.map.map[y][++x].value = Utils.MAP_PLAYER;
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
		if (Main.map.map[y - 1][x].value < Utils.MAP_NON_OBSTACLE) {
			switch (direction) {
				default:
					direction = UP;
					sendMove(Utils.POINT_UP);
				case UP: return;
			}
		} else {
			Main.map.map[y][x].value = Main.map.defMap[y][x].value;
			Main.map.map[--y][x].value = Utils.MAP_PLAYER;
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
		if (Main.map.map[y + 1][x].value < Utils.MAP_NON_OBSTACLE) {
			switch (direction) {
				default:
					direction = DOWN;
					sendMove(Utils.POINT_DOWN);
				case DOWN: return;
			}
		} else {
			Main.map.map[y][x].value = Main.map.defMap[y][x].value;
			Main.map.map[++y][x].value = Utils.MAP_PLAYER;
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
