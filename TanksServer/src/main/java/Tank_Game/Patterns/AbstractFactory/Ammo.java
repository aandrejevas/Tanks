/**
 * @(#) Ammo.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public abstract class Ammo extends Drop
{
    public Ammo(byte name, int value) {
        super(name, value);
    }
}
