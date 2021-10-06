/**
 * @(#) MediumHealth.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class MediumHealth extends Health
{
    public MediumHealth() {
        super(Utils.DROP_MHEALTH, 40);
    }
}
