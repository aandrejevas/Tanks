/**
 * @(#) LargeAmmo.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class LargeAmmo extends Ammo
{
    public LargeAmmo() {
        super(Utils.DROP_LAMMO, 30);
    }
}
