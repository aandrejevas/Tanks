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
				System.out.println("drop:" + next_block.drop.getName());
				Main.clients.get(Main.available_client); //todo
				switch (next_block.drop.getName()) {
					case Utils.DROP_SAMMO:
						tank.setShotType(Utils.SHOT_NORMAL);
						break;
					case Utils.DROP_MAMMO:
						tank.setShotType(Utils.SHOT_BLUE);
						break;
					case Utils.DROP_LAMMO:
						tank.setShotType(Utils.SHOT_RED);
						break;
				}
				Main.this_server.write(Utils.REMOVE_DROP, tank.getY(), tank.getX());
				next_block.drop = null;
			}
		}
	}

	ArenaBlock getNextBlock(final Tank tank);

	void moveBlocked(final Tank tank);

	void moveUnblocked(final Tank tank);
}
