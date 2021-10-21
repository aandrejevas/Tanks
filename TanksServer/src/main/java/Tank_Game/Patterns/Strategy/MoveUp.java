package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.ArenaBlock;
import utils.Utils;

public class MoveUp implements MoveAlgorithm {
	private MoveUp() {
	}

	public static final MoveAlgorithm instance = new MoveUp();

	@Override
	public ArenaBlock getNextBlock(final Tank tank) {
		return Main.map.map[tank.getY() - 1][tank.getX()];
	}

	@Override
	public void moveBlocked(final Tank tank) {
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.UP);
				Main.this_server.write(Utils.POINT_UP, tank.getIndex());
			case Tank.UP: return;
		}
	}

	@Override
	public void moveUnblocked(final Tank tank) {
		tank.setY(tank.getY() - 1);
		switch (tank.getDirection()) {
			default:
				tank.setDirection(Tank.UP);
				Main.this_server.write(Utils.TURN_UP, tank.getIndex());
				break;
			case Tank.UP:
				Main.this_server.write(Utils.MOVE_UP, tank.getIndex());
				break;
		}
	}
}
