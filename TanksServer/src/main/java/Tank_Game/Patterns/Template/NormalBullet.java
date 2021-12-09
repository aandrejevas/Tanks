package Tank_Game.Patterns.Template;

import Tank_Game.Tank;
import processing.net.Client;

public class NormalBullet extends Bullet {

	public NormalBullet(final Tank tank, final Client c) {
		super(tank, c);
	}

	@Override
	protected int doDamage() {
		return 5;
	}

	@Override
	protected int doDamageArmor() {
		return 5;
	}
}
