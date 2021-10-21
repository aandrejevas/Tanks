package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.ArenaBlock;
import utils.Utils;

public class MoveRight implements MoveAlgorithm {
	private MoveRight() {
	}

	public static final MoveAlgorithm instance = new MoveRight();

	@Override
	public ArenaBlock getNextBlock(final Tank tank) {
		return Main.map.map[tank.getY()][tank.getX() + 1];
	}

	@Override
	public void moveBlocked(final Tank tank) {
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.RIGHT);
				Main.this_server.write(Utils.POINT_RIGHT, tank.getIndex());
			case Tank.RIGHT: return;
		}
	}

	@Override
	public void moveUnblocked(final Tank tank) {
		tank.setX(tank.getX() + 1);
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.RIGHT);
				Main.this_server.write(Utils.TURN_RIGHT, tank.getIndex());
				break;
			case Tank.RIGHT:
				Main.this_server.write(Utils.MOVE_RIGHT, tank.getIndex());
				break;
		}
	}
}
