package Tank_Game.Patterns.Template;

import Tank_Game.Tank;

public class RedBullet extends Bullet {

	public RedBullet(final Tank tank) {
		super(tank);
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
