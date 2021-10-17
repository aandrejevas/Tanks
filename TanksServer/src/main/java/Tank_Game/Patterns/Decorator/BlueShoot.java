/**
 * @(#) BlueShoot.java
 */

package Tank_Game.Patterns.Decorator;

import Tank_Game.Tank;
import utils.Utils;

public class BlueShoot extends Decorator
{
	public BlueShoot(Tank wrapee) {
		super(wrapee);
	}

	public void blueShoot( )
	{

	}

	@Override
	public int getShotType() {
		return Utils.SHOT_BLUE;
	}

	@Override
	public void shoot() {
		//blueShoot();
		super.shoot();
	}
}
