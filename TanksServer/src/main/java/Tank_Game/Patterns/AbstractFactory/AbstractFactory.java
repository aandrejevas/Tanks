package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public interface AbstractFactory {
	Drop createHealth();

	Drop createArmor();

	Drop createAmmo();
}
