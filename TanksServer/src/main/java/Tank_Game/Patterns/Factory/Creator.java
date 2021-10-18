package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;

public interface Creator {
	Tank factoryMethod(final int playerType, final boolean type);
}
