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
		return wrapee.setAlgorithm(moveAlgorithm);
	}

	@Override
	public byte getShotType() {
		return super.getShotType();
	}

	@Override
	public void move() {
		wrapee.move();
	}

	@Override
	public void shoot() {
		super.shoot();
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
	public void setMoveAlgorithm(MoveAlgorithm moveAlgorithm) {
		wrapee.setMoveAlgorithm(moveAlgorithm);
	}

	@Override
	public int[] getCord() {
		return wrapee.getCord();
	}

	@Override
	public int getCordByIndex(int index) {
		return wrapee.getCordByIndex(index);
	}

	@Override
	public void setCord(int index, int cord) {
		wrapee.setCord(index, cord);
	}

	@Override
	public byte getDirection() {
		return wrapee.getDirection();
	}

	@Override
	public void setDirection(byte direction) {
		super.setDirection(direction);
	}

	@Override
	public byte[] getDir() {
		return wrapee.getDir();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Decorator decorator = (Decorator)super.clone();
		//decorator.wrapee = (Tank)this.wrapee.clone();

		decorator.setShotType(this.getShotType());
		return decorator;
	}
}
