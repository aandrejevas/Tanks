/**
 * @(#) MediumAmmo.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Utils;

public class MediumAmmo extends Ammo
{
    public MediumAmmo() {
        super(Utils.DROP_MAMMO, 20);
    }
}
