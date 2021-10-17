/**
 * @(#) BlueShoot.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;

public class NormalShoot extends Decorator
{
	public NormalShoot(Tank wrapee) {
		super(wrapee);
	}

	public void blueShoot( )
	{

	}

	@Override
	public int getDamage() {
		return this.wrapee.getDamage();
	}

	@Override
	public void shoot() {
		//blueShoot();
		super.shoot();
	}
}
