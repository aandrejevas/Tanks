package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.ArenaBlock;
import utils.Utils;

public class MoveLeft implements MoveAlgorithm {
	private MoveLeft() {
	}

	public static final MoveAlgorithm instance = new MoveLeft();

	@Override
	public ArenaBlock getNextBlock(final Tank tank) {
		return Main.map.map[tank.getY()][tank.getX() - 1];
	}

	@Override
	public void moveBlocked(final Tank tank) {
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.LEFT);
				Main.this_server.write(Utils.POINT_LEFT, tank.getIndex());
			case Tank.LEFT: return;
		}
	}

	@Override
	public void moveUnblocked(final Tank tank) {
		tank.setX(tank.getX() - 1);
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.LEFT);
				Main.this_server.write(Utils.TURN_LEFT, tank.getIndex());
				return;
			case Tank.LEFT:
				Main.this_server.write(Utils.MOVE_LEFT, tank.getIndex());
				return;
		}
	}
}
