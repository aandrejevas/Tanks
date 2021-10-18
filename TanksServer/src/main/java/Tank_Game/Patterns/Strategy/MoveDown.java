package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.Utils;

public class MoveDown implements MoveAlgorithm {
	@Override
	public void move(final Tank tank) {
		if (Main.map.map[tank.y + 1][tank.x].obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.DOWN;
					sendMove(Utils.POINT_DOWN, tank.index);
				case Tank.DOWN: return;
			}
		} else {
			Main.map.map[tank.y + 1][tank.x].value = Main.map.map[tank.y][tank.x].value;
			Main.map.map[tank.y][tank.x].value = Main.map.map[tank.y][tank.x].defValue;
			Main.map.map[tank.y][tank.x].obstacle = false;
			Main.map.map[tank.y + 1][tank.x].obstacle = true;
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
