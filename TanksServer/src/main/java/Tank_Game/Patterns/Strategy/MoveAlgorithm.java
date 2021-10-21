package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.ArenaBlock;
import utils.Utils;

public interface MoveAlgorithm {
	default void move(final Tank tank) {
		final ArenaBlock next_block = getNextBlock(tank);
		if (next_block.obstacle) {
			moveBlocked(tank);
		} else {
			final ArenaBlock block = Main.map.map[tank.getY()][tank.getX()];
			next_block.value = block.value;
			block.value = block.defValue;
			block.obstacle = false;
			next_block.obstacle = true;
			moveUnblocked(tank);
			if (next_block.drop != null) {
				Main.this_server.write(Utils.REMOVE_DROP, tank.getY(), tank.getX());
				next_block.drop = null;
			}
		}
	}

	ArenaBlock getNextBlock(final Tank tank);

	void moveBlocked(final Tank tank);

	void moveUnblocked(final Tank tank);
}
