package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.ArenaBlock;
import utils.Utils;

public class MoveDown implements MoveAlgorithm {
	private MoveDown() {
	}

	public static final MoveAlgorithm instance = new MoveDown();

	@Override
	public ArenaBlock getNextBlock(final Tank tank) {
		return Main.map.map[tank.getY() + 1][tank.getX()];
	}

	@Override
	public void moveBlocked(final Tank tank) {
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.DOWN);
				Main.this_server.write(Utils.POINT_DOWN, tank.getIndex());
			case Tank.DOWN: return;
		}
	}

	@Override
	public void moveUnblocked(final Tank tank) {
		tank.setY(tank.getY() + 1);
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.DOWN);
				Main.this_server.write(Utils.TURN_DOWN, tank.getIndex());
				return;
			case Tank.DOWN:
				Main.this_server.write(Utils.MOVE_DOWN, tank.getIndex());
				return;
		}
	}
}
