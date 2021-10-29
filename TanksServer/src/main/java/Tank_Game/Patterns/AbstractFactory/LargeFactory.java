package Tank_Game.Patterns.AbstractFactory;

public class LargeFactory implements AbstractFactory {
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
