package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;
import utils.Utils;

public class NormalShoot extends Decorator {
	public NormalShoot(final Tank wrapee) {
		super(wrapee);
	}

	public void blueShoot() {
	}

	@Override
	public byte getShotType() {
		return Utils.SHOT_NORMAL;
	}

	@Override
	public void shoot() {
		//blueShoot();
		super.shoot();
	}
}
