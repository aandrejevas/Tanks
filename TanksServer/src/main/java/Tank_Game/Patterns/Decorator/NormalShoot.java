package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;

public class NormalShoot extends Decorator {
	public NormalShoot(final Tank wrapee) {
		super(wrapee);
	}

	public void blueShoot() {
	}

	@Override
	public int getShotType() {
		return this.wrapee.getShotType();
	}

	@Override
	public void shoot() {
		//blueShoot();
		super.shoot();
	}
}
