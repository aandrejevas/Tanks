package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Command.BlueShootCommand;
import Tank_Game.Patterns.Command.Command;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Command.RedShootCommand;
import Tank_Game.Patterns.Decorator.Decorator;
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
				Command command = null;

				Invoker inv = Main.clients.get(Main.available_client);
				Decorator decorator = inv.currentDecorator();
				byte st = inv.currentDecorator().getShotType();

				switch (next_block.drop.getName()) {
					case Utils.DROP_SAMMO:
						if (st == Utils.SHOT_NORMAL){
							break;
						} else {
							inv.undoCommand();
						}
						break;
					case Utils.DROP_MAMMO:
						if (st == Utils.SHOT_BLUE) {
							break;
						} else if (st == Utils.SHOT_NORMAL) {
							command = new BlueShootCommand(decorator);
							inv.runCommand(command);
						} else {
							decorator = inv.undoCommand();
							command = new BlueShootCommand(decorator);
							inv.runCommand(command);
						}
						break;
					case Utils.DROP_LAMMO:
						if (st == Utils.SHOT_RED) {
							break;
						} else if (st == Utils.SHOT_NORMAL) {
							command = new RedShootCommand(decorator);
							inv.runCommand(command);
						} else {
							decorator = inv.undoCommand();
							command = new RedShootCommand(decorator);
							inv.runCommand(command);
						}
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
