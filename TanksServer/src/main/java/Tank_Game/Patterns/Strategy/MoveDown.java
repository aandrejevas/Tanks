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
		final ArenaBlock next_block = Main.map.map[tank.y + 1][tank.x];
		if (next_block.obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.DOWN;
					sendMove(Utils.POINT_DOWN, tank.index);
				case Tank.DOWN: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.y][tank.x];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			++tank.y;
			switch (tank.direction) {
				default:
					tank.direction = Tank.DOWN;
					sendMove(Utils.TURN_DOWN, tank.index);
					return;
				case Tank.DOWN:
					sendMove(Utils.MOVE_DOWN, tank.index);
					return;
			}
		}
	}
}
