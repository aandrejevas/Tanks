package Tank_Game;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import mediator.Mediator;
import utils.ArenaBlock;
import utils.Utils;

public class Tank implements Cloneable {

	public static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;

	protected final Mediator mediator;

	protected final int index;

	protected final int type;
	protected byte shotType = Utils.SHOT_NORMAL;

	protected MoveAlgorithm moveAlgorithm;
	protected int x, y;
	protected byte direction;
	protected int health = 100;
	protected int armor = 100;

	public Tank(final int i, final int ally_or_enemy, final Mediator m) {
		index = i;
		type = ally_or_enemy;
		mediator = m;
		do {
			x = Utils.random().nextInt(Main.edge);
			y = Utils.random().nextInt(Main.edge);
		} while (Main.map.map[y][x].value < Utils.MAP_NON_OBSTACLE);
		final ArenaBlock block = Main.map.map[y][x];
		block.value = Utils.MAP_PLAYER;
		block.obstacle = true;
		direction = UP;
	}

	protected Tank() {
		index = -1;
		type = -1;
		mediator = null;
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

	public int[] getCord() {
		return new int[] { x, y };
	}

	public byte getDirection() {
		return direction;
	}

	public void setDirection(final byte direction) {
		this.direction = direction;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public Mediator getMediator() {
		return mediator;
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

	public void sendMessage(final byte[] data, final int offset, final int length) {
		mediator.sendMessage(data, offset, length);
	}
}
