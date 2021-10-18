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
	public void move(final Tank tank) {
		final ArenaBlock next_block = Main.map.map[tank.getY() + 1][tank.getX()];
		if (next_block.obstacle) {
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.DOWN);
					sendMove(Utils.POINT_DOWN, tank.getIndex());
				case Tank.DOWN: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.getY()][tank.getX()];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			tank.setY(tank.getY()+1);
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.DOWN);
					sendMove(Utils.TURN_DOWN, tank.getIndex());
					return;
				case Tank.DOWN:
					sendMove(Utils.MOVE_DOWN, tank.getIndex());
					return;
			}
		}
	}
}
