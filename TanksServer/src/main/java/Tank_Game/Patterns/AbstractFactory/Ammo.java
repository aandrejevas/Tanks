package Tank_Game.Patterns.AbstractFactory;

import utils.Drop;

public abstract class Ammo extends Drop {
	public Ammo(final byte name, final int value) {
		super(name, value);
	}
}
