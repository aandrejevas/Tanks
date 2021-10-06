/**
 * @(#) SmallFactory.java
 */

package Tank_Game.Patterns.AbstractFactory;

public class SmallFactory extends AbstractFactory
{

    @Override
    public Health createHealth() {
        return new SmallHealth();
    }

    @Override
    public Armor createArmor() {
        return new SmallArmor();
    }

    @Override
    public Ammo createAmmo() {
        return new SmallAmmo();
    }
}
