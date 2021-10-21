package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;
import utils.Utils;

public class BlueShoot extends Decorator {
	public BlueShoot(final Tank wrapee) {
		super(wrapee);
	}

	@Override
	public byte getShotType() {
		return Utils.SHOT_BLUE;
	}
}
