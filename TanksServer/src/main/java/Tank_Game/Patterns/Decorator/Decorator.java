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
		super.setShotType(shotType);
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
	public Tank setAlgorithm(MoveAlgorithm moveAlgorithm) {
		return super.setAlgorithm(moveAlgorithm);
	}

	@Override
	public byte getShotType() {
		return super.getShotType();
	}

	@Override
	public void move() {
		super.move();
	}

	@Override
	public void shoot() {
		super.shoot();
	}

	@Override
	public void setIndex(int index) {
		super.setIndex(index);
	}

	@Override
	public int getIndex() {
		return super.getIndex();
	}

	@Override
	public int getType() {
		return super.getType();
	}

	@Override
	public void setType(int type) {
		super.setType(type);
	}

	@Override
	public void setMoveAlgorithm(MoveAlgorithm moveAlgorithm) {
		super.setMoveAlgorithm(moveAlgorithm);
	}

	@Override
	public int getCord(int index) {
		return super.getCord(index);
	}

	@Override
	public void setCord(int index, int cord) {
		super.setCord(index, cord);
	}

	@Override
	public byte[] getDirection() {
		return super.getDirection();
	}

	@Override
	public void setDirection(byte direction) {
		super.setDirection(direction);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Decorator decorator = (Decorator)super.clone();
		decorator.wrapee = (Tank)this.wrapee.clone();

		decorator.setShotType(this.getShotType());
		return decorator;
	}
}
