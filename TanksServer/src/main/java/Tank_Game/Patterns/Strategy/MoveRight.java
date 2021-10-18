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
		final ArenaBlock next_block = Main.map.map[tank.y][tank.x + 1];
		if (next_block.obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.RIGHT;
					sendMove(Utils.POINT_RIGHT, tank.index);
				case Tank.RIGHT: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.y][tank.x];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			++tank.x;
			switch (tank.direction) {
				default:
					tank.direction = Tank.RIGHT;
					sendMove(Utils.TURN_RIGHT, tank.index);
					return;
				case Tank.RIGHT:
					sendMove(Utils.MOVE_RIGHT, tank.index);
					return;
			}
		}
	}
}
