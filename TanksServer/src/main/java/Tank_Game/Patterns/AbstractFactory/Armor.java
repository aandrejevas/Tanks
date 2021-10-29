package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public abstract class Armor extends Drop {
	public Armor(final byte name, final int value) {
		super(name, value);
	}
}
