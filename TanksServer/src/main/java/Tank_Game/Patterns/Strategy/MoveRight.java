package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.Utils;

public class MoveRight implements MoveAlgorithm {
	@Override
	public void move(final Tank tank) {
		if (Main.map.map[tank.y][tank.x + 1].obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.RIGHT;
					sendMove(Utils.POINT_RIGHT, tank.index);
				case Tank.RIGHT: return;
			}
		} else {
			Main.map.map[tank.y][tank.x + 1].value = Main.map.map[tank.y][tank.x].value;
			Main.map.map[tank.y][tank.x].value = Main.map.map[tank.y][tank.x].defValue;
			Main.map.map[tank.y][tank.x].obstacle = false;
			Main.map.map[tank.y][tank.x + 1].obstacle = true;
			++tank.x;
			switch (tank.direction) {
				default:
					tank.direction = Tank.RIGHT;
					sendMove(Utils.TURN_RIGHT, tank.index);
					return;
				case Tank.RIGHT:
					sendMove(Utils.MOVE_RIGHT, tank.index);
					return;
			}
		}
	}
}
