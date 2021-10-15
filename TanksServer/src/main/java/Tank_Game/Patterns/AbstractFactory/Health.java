/**
 * @(#) Health.java
 */

package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public abstract class Health extends Drop
{
    public Health(byte name, int value) {
        super(name, value);
    }
}
