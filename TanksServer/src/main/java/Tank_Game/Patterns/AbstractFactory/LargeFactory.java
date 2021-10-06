/**
 * @(#) LargeFactory.java
 */

package Tank_Game.Patterns.AbstractFactory;

public class LargeFactory extends AbstractFactory
{

    @Override
    public Health createHealth() {
        return new LargeHealth();
    }

    @Override
    public Armor createArmor() {
        return new LargeArmor();
    }

    @Override
    public Ammo createAmmo() {
        return new LargeAmmo();
    }
}
