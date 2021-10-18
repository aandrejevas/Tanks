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
	public void move(final Tank tank) {
		final ArenaBlock next_block = Main.map.map[tank.y][tank.x - 1];
		if (next_block.obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.LEFT;
					sendMove(Utils.POINT_LEFT, tank.index);
				case Tank.LEFT: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.y][tank.x];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			--tank.x;
			switch (tank.direction) {
				default:
					tank.direction = Tank.LEFT;
					sendMove(Utils.TURN_LEFT, tank.index);
					return;
				case Tank.LEFT:
					sendMove(Utils.MOVE_LEFT, tank.index);
					return;
			}
		}
	}
}
