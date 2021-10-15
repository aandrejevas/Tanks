/**
 * @(#) LargeHealth.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class LargeHealth extends Health
{
    public LargeHealth() {
        super(Utils.DROP_LHEALTH, 80);
    }
}
