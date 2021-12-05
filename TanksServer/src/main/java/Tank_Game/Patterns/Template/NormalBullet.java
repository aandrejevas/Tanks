package Tank_Game.Patterns.Template;

import Tank_Game.Tank;

public class NormalBullet extends Bullet {

	public NormalBullet(final Tank tank) {
		super(tank);
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
