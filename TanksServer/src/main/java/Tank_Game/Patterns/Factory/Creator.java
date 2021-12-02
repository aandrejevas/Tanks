package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;
import mediator.Mediator;

public interface Creator {
	Tank factoryMethod(final int playerType, final boolean type, final Mediator m);
}
