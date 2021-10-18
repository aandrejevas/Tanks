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
	public void move(final Tank tank) {
		final ArenaBlock next_block = Main.map.map[tank.getY() - 1][tank.getX()];
		if (next_block.obstacle) {
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.UP);
					sendMove(Utils.POINT_UP, tank.getIndex());
				case Tank.UP: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.getY()][tank.getX()];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			tank.setY(tank.getY()-1);
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.UP);
					sendMove(Utils.TURN_UP, tank.getIndex());
					return;
				case Tank.UP:
					sendMove(Utils.MOVE_UP, tank.getIndex());
					return;
			}
		}
	}
}
