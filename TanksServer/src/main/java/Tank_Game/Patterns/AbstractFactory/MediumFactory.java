/**
 * @(#) MediumFactory.java
 */

package Tank_Game.Patterns.AbstractFactory;

public class MediumFactory extends AbstractFactory
{

    @Override
    public Health createHealth() {
        return new MediumHealth();
    }

    @Override
    public Armor createArmor() {
        return new MediumArmor();
    }

    @Override
    public Ammo createAmmo() {
        return new MediumAmmo();
    }
}
