package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;
import utils.Utils;

public class MoveUp implements MoveAlgorithm {
	private MoveUp() {
	}

	public static final MoveAlgorithm instance = new MoveUp();

	@Override
	public void move(final Tank tank) {
		if (Main.map.map[tank.y - 1][tank.x].obstacle) {
			switch (tank.direction) {
				default:
					tank.direction = Tank.UP;
					sendMove(Utils.POINT_UP, tank.index);
				case Tank.UP: return;
			}
		} else {
			Main.map.map[tank.y - 1][tank.x].value = Main.map.map[tank.y][tank.x].value;
			Main.map.map[tank.y][tank.x].value = Main.map.map[tank.y][tank.x].defValue;
			Main.map.map[tank.y][tank.x].obstacle = false;
			Main.map.map[tank.y - 1][tank.x].obstacle = true;
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
