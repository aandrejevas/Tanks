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

	@Override
	public Object clone() throws CloneNotSupportedException {
		Decorator decorator = (Decorator)super.clone();
		decorator.wrapee = (Tank)this.wrapee.clone();
		//decorator.wrapee = (Tank) super.thisTank().clone();
		return decorator;
	}
}
