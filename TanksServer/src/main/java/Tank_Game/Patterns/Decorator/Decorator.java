package Tank_Game.Patterns.Decorator;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import Tank_Game.Tank;

public abstract class Decorator extends Tank {
	protected Tank wrapee;

	public Decorator(final Tank wrapee) {
		super();
		this.wrapee = wrapee;
	}

	@Override
	public void setShotType(final byte shotType) {
		wrapee.setShotType(shotType);
	}

	@Override
	public MoveAlgorithm getMoveAlgorithm() {
		return super.getMoveAlgorithm();
	}

	/*@Override
	public Tank thisTank() {
		return super.thisTank();
	}*/
	@Override
	public Tank setAlgorithm(final MoveAlgorithm moveAlgorithm) {
		return wrapee.setAlgorithm(moveAlgorithm);
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
	public Object clone() throws CloneNotSupportedException {
		Decorator decorator = (Decorator)super.clone();
		//decorator.wrapee = (Tank)this.wrapee.clone();

		decorator.setShotType(this.getShotType());
		return decorator;
	}

	public Tank getTank() {
		return wrapee;
	}
}
