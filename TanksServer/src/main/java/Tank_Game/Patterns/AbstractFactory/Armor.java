/**
 * @(#) Armor.java
 */

package Tank_Game.Patterns.AbstractFactory;


import utils.Drop;

public abstract class Armor extends Drop
{

    public Armor(byte name, int value) {
        super(name, value);
    }
}
