/**
 * @(#) AbstractFactory.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public abstract class AbstractFactory
{
	public abstract Drop createHealth( );
	
	public abstract Drop createArmor( );
	
	public abstract Drop createAmmo( );
	
	
}
