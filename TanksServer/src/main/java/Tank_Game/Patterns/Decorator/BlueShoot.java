/**
 * @(#) BlueShoot.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;

public class BlueShoot extends Decorator
{
	public BlueShoot(Tank wrapee) {
		super(wrapee);
	}

	public void blueShoot( )
	{

	}

	@Override
	public int getDamage() {
		return this.wrapee.getDamage()+20;
	}

	@Override
	public void shoot() {
		//blueShoot();
		super.shoot();
	}
}
