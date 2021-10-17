/**
 * @(#) RedShoot.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;
import utils.Utils;

public class RedShoot extends Decorator
{
	public RedShoot(Tank wrapee) {
		super(wrapee);
	}

	public void redShoot( )
	{

	}

	@Override
	public int getShotType() {
		return Utils.SHOT_RED;
	}

	@Override
	public void shoot() {
		//redShoot();
		super.shoot();
	}
}