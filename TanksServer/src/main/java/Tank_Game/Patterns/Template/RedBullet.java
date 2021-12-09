package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import processing.net.Client;

public class RedBullet extends Bullet {

	public RedBullet(final Tank tank, final Client c) {
		super(tank, c);
	}

	@Override
	protected int doDamage() {
		return 15;
	}

	@Override
	protected int doDamageArmor() {
		return 10;
	}
}
