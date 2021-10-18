package Tank_Game;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import utils.ArenaBlock;
import utils.Utils;

public class Tank implements Cloneable {

	public static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;

	public int index;

	public int type;
	private byte shotType = Utils.SHOT_NORMAL;

	private MoveAlgorithm moveAlgorithm;
	public int x, y;
	public byte direction;

	public Tank(int index, int ally_or_enemy) {
		this.index = index;
		this.type = ally_or_enemy;
		do {
			x = Utils.random().nextInt(Main.edge);
			y = Utils.random().nextInt(Main.edge);
		} while (Main.map.map[y][x].value < Utils.MAP_NON_OBSTACLE);
		final ArenaBlock block = Main.map.map[y][x];
		block.value = Utils.MAP_PLAYER;
		block.obstacle = true;
		direction = UP;
	}

	public Tank() {

	}

	public Tank setAlgorithm(final MoveAlgorithm moveAlgorithm) {
		this.moveAlgorithm = moveAlgorithm;
		return this;
	}

	public MoveAlgorithm getMoveAlgorithm() {
		return moveAlgorithm;
	}

	public int getShotType() {
		return shotType;
	}

	public void setShotType(final byte shotType) {
		this.shotType = shotType;
	}

	public void move() {
		moveAlgorithm.move(this);
	}

	public void shoot() {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final Tank tank = (Tank)super.clone();
		tank.index = this.index;
		tank.type = this.type;
		tank.setShotType(this.shotType);
		if (this.moveAlgorithm != null)
			tank.setAlgorithm(this.moveAlgorithm);

		tank.x = x;
		tank.y = y;
		tank.direction = direction;
		return tank;
	}
}
