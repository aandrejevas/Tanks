/**
 * @(#) RedShoot.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;

public class RedShoot extends Decorator
{
	public RedShoot(Tank wrapee) {
		super(wrapee);
	}

	public void redShoot( )
	{

	}

	@Override
	public int getDamage() {
		return this.wrapee.getDamage()+10;
	}

	@Override
	public void shoot() {
		//redShoot();
		super.shoot();
	}
}
