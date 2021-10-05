/**
 * @(#) Creator.java
 */

package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;

public abstract class Creator
{
	public abstract Tank factoryMethod(int playerType, boolean type);
}
