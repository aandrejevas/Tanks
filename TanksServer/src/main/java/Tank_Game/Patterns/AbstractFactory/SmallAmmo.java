/**
 * @(#) SmallAmmo.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class SmallAmmo extends Ammo
{
    public SmallAmmo() {
        super(Utils.DROP_SAMMO, 10);
    }
}
