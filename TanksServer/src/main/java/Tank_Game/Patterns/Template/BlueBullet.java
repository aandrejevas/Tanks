package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import processing.net.Client;

public class BlueBullet extends Bullet {

	public BlueBullet(final Tank tank, final Client c) {
		super(tank, c);
	}

	@Override
	protected int doDamage() {
		return 10;
	}

	@Override
	protected int doDamageArmor() {
		return 15;
	}
}
