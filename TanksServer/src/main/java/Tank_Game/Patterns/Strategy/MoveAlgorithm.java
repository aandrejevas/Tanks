package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import utils.Utils;

public abstract class MoveAlgorithm implements Cloneable {
	public abstract void move(int[] cord, byte[] direction, int index);

	protected static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;

	protected void sendMove(final byte message, int index) {
		Main.this_server.write(message, index);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
