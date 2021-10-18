package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.Utils;

public class MoveLeft implements MoveAlgorithm {
	private MoveLeft() {
	}

	public static final MoveAlgorithm instance = new MoveLeft();

	@Override
	public void move(final Tank tank) {
		if (Main.map.map[tank.y][tank.x - 1].obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.LEFT;
					sendMove(Utils.POINT_LEFT, tank.index);
				case Tank.LEFT: return;
			}
		} else {
			Main.map.map[tank.y][tank.x - 1].value = Main.map.map[tank.y][tank.x].value;
			Main.map.map[tank.y][tank.x].value = Main.map.map[tank.y][tank.x].defValue;
			Main.map.map[tank.y][tank.x].obstacle = false;
			Main.map.map[tank.y][tank.x - 1].obstacle = true;
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
