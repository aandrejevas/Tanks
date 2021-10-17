/**
 * @(#) Decorator.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import Tank_Game.Tank;

public abstract class Decorator extends Tank
{
	protected Tank wrapee;

	public Decorator(Tank wrapee) {
		super();
		this.wrapee = wrapee;
	}

	@Override
	public void setShotType(byte shotType) {
		super.setShotType(shotType);
	}

	@Override
	public MoveAlgorithm getMoveAlgorithm() {
		return super.getMoveAlgorithm();
	}

	@Override
	public Tank thisTank() {
		return super.thisTank();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Decorator decorator = (Decorator) super.clone();
		decorator.wrapee = (Tank) this.wrapee.thisTank().clone();
		//decorator.wrapee = (Tank) super.thisTank().clone();
		return decorator;
	}
}
