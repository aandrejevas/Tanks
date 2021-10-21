package Tank_Game;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import utils.ArenaBlock;
import utils.Utils;

public class Tank implements Cloneable {

	public static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;

	private int index;

	private int type;
	private byte shotType = Utils.SHOT_NORMAL;

	private MoveAlgorithm moveAlgorithm;
	private int x, y;
	private byte direction;

	public Tank(final int index, final int ally_or_enemy) {
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

	public byte getShotType() {
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

	public int getIndex() {
		return index;
	}

	public int getType() {
		return type;
	}

	public void setMoveAlgorithm(final MoveAlgorithm moveAlgorithm) {
		this.moveAlgorithm = moveAlgorithm;
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public byte getDirection() {
		return direction;
	}

	public void setDirection(final byte direction) {
		this.direction = direction;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final Tank tank = (Tank)super.clone();
		/*tank.index = this.index;
		tank.type = this.type;
		tank.setShotType(this.shotType);
		if (this.moveAlgorithm != null)
			tank.setAlgorithm(this.moveAlgorithm);

		tank.x = x;
		tank.y = y;
		tank.direction = direction;*/
		return tank;
	}
}
