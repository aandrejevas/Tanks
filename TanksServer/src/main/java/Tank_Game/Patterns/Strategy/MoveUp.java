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
		final ArenaBlock next_block = Main.map.map[tank.y - 1][tank.x];
		if (next_block.obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.UP;
					sendMove(Utils.POINT_UP, tank.index);
				case Tank.UP: return;
			}
		} else {
			final ArenaBlock block = Main.map.map[tank.y][tank.x];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			--tank.y;
			switch (tank.direction) {
				default:
					tank.direction = Tank.UP;
					sendMove(Utils.TURN_UP, tank.index);
					return;
				case Tank.UP:
					sendMove(Utils.MOVE_UP, tank.index);
					return;
			}
		}
	}
}
