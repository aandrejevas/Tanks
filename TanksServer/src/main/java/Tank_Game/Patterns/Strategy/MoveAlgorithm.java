package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Command.BlueShootCommand;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Command.RedShootCommand;
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
			if (next_block.drop != null && tank.getType() != 1) {
				final Invoker inv = Main.clients.get(Main.available_client);

				switch (next_block.drop.getName()) {
					case Utils.DROP_SAMMO:
						switch (inv.currentDecorator().getShotType()) {
							case Utils.SHOT_NORMAL:
								break;
							default:
								inv.undoCommand();
								break;
						}
						break;
					case Utils.DROP_MAMMO:
						switch (inv.currentDecorator().getShotType()) {
							case Utils.SHOT_BLUE:
								break;
							case Utils.SHOT_NORMAL:
								inv.runCommand(new BlueShootCommand(inv.currentDecorator()));
								break;
							default:
								inv.runCommand(new BlueShootCommand(inv.undoCommand()));
								break;
						}
						break;
					case Utils.DROP_LAMMO:
						switch (inv.currentDecorator().getShotType()) {
							case Utils.SHOT_RED:
								break;
							case Utils.SHOT_NORMAL:
								inv.runCommand(new RedShootCommand(inv.currentDecorator()));
								break;
							default:
								inv.runCommand(new RedShootCommand(inv.undoCommand()));
								break;
						}
						break;
				}

				--Main.ndrops;
				Main.this_server.write(Utils.REMOVE_DROP, tank.getY(), tank.getX());
				next_block.drop = null;
			}
		}
	}

	ArenaBlock getNextBlock(final Tank tank);

	void moveBlocked(final Tank tank);

	void moveUnblocked(final Tank tank);
}
