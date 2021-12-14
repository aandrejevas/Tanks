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

			if (tank.getType() != Utils.MAP_TIGER) {
				if (next_block.defValue == Utils.MAP_LAVA) {
					tank.setHealth(tank.getHealth() - 10);
					if (tank.getHealth() <= 0) {
						Main.client_os.write(Utils.GAME_END);
						next_block.value = next_block.defValue;
						next_block.obstacle = false;
						Main.this_server.write(Utils.REMOVE_TANK, tank.getIndex());
						Main.clients.remove(Main.available_client);
					} else {
						Main.client_os.write(Utils.SET_HEALTH, /*tank.getIndex(), */ tank.getHealth());
					}
				} else if (next_block.defValue == Utils.MAP_WATER && tank.getArmor() != 0) {
					tank.setArmor(Math.max(tank.getArmor() - 10, 0));
					Main.client_os.write(Utils.SET_ARMOR, /*tank.getIndex(), */ tank.getArmor());
				}
			}

			if (next_block.drop != null && tank.getType() != Utils.MAP_TIGER) {
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
					case Utils.DROP_LHEALTH:
					case Utils.DROP_MHEALTH:
					case Utils.DROP_SHEALTH:
						tank.setHealth(Math.min(tank.getHealth() + next_block.drop.getValue(), 100));
						break;
					case Utils.DROP_LARMOR:
					case Utils.DROP_MARMOR:
					case Utils.DROP_SARMOR:
						tank.setArmor(Math.min(tank.getArmor() + next_block.drop.getValue(), 100));
						break;
				}

				--Main.ndrops;
				Main.this_server.write(Utils.REMOVE_DROP, tank.getY(), tank.getX(), tank.getIndex());
				next_block.drop = null;
			}
		}
	}

	ArenaBlock getNextBlock(final Tank tank);

	void moveBlocked(final Tank tank);

	void moveUnblocked(final Tank tank);
}
