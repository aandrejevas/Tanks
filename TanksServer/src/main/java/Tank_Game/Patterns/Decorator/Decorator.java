package Tank_Game.Patterns.Decorator;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import Tank_Game.Tank;

public abstract class Decorator extends Tank implements Cloneable {
	protected final Tank wrapee;

	public Decorator(final Tank tank) {
		wrapee = tank;
	}

	@Override
	public void setShotType(final byte shotType) {
		wrapee.setShotType(shotType);
	}

	@Override
	public MoveAlgorithm getMoveAlgorithm() {
		return wrapee.getMoveAlgorithm();
	}

	@Override
	public int getX() {
		return wrapee.getX();
	}

	@Override
	public void setX(final int x) {
		wrapee.setX(x);
	}

	@Override
	public int getY() {
		return wrapee.getY();
	}

	@Override
	public void setY(final int y) {
		wrapee.setY(y);
	}

	@Override
	public byte getShotType() {
		return wrapee.getShotType();
	}

	@Override
	public void move() {
		wrapee.move();
	}

	@Override
	public int getIndex() {
		return wrapee.getIndex();
	}

	@Override
	public int getType() {
		return wrapee.getType();
	}

	@Override
	public void setMoveAlgorithm(final MoveAlgorithm moveAlgorithm) {
		wrapee.setMoveAlgorithm(moveAlgorithm);
	}

	@Override
	public byte getDirection() {
		return wrapee.getDirection();
	}

	@Override
	public void setDirection(final byte direction) {
		wrapee.setDirection(direction);
	}

	@Override
	public int getHealth() {
		return wrapee.getHealth();
	}

	@Override
	public void setHealth(int health) {
		wrapee.setHealth(health);
	}

	@Override
	public int getArmor() {
		return wrapee.getArmor();
	}

	@Override
	public void setArmor(int armor) {
		wrapee.setArmor(armor);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		final Decorator decorator = (Decorator)super.clone();
		//decorator.wrapee = (Tank)this.wrapee.clone();

		decorator.setShotType(this.getShotType());
		return decorator;
	}

	public Tank getTank() {
		return wrapee;
	}

	@Override
	public int[] getCord() {
		return wrapee.getCord();
	}

	@Override
	public void sendMessage(final byte[] data, final int offset, final int length) {
		wrapee.sendMessage(data, offset, length);
	}
}
