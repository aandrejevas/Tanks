package Tank_Game.Patterns.Template;

import Tank_Game.Tank;

public class BlueBullet extends Bullet {

	public BlueBullet(final Tank tank) {
		super(tank);
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
