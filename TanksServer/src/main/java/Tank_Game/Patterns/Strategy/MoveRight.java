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
	public void move(final Tank tank) {
		final ArenaBlock next_block = Main.map.map[tank.getY()][tank.getX() + 1];
		if (next_block.obstacle) {
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.RIGHT);
					sendMove(Utils.POINT_RIGHT, tank.getIndex());
				case Tank.RIGHT: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.getY()][tank.getX()];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			tank.setX(tank.getX()+1);
			switch (tank.getDirection()) {
				default:
					tank.setDirection(Tank.RIGHT);
					sendMove(Utils.TURN_RIGHT, tank.getIndex());
					return;
				case Tank.RIGHT:
					sendMove(Utils.MOVE_RIGHT, tank.getIndex());
					return;
			}
		}
	}
}
