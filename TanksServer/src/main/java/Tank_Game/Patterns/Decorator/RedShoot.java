package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;
import utils.Utils;

public class RedShoot extends Decorator {
	public RedShoot(final Tank wrapee) {
		super(wrapee);
	}

	@Override
	public byte getShotType() {
		return Utils.SHOT_RED;
	}

}
