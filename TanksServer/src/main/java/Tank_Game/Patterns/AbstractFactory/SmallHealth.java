/**
 * @(#) SmallHealth.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class SmallHealth extends Health
{
    public SmallHealth() {
        super(Utils.DROP_SHEALTH, 20);
    }
}
