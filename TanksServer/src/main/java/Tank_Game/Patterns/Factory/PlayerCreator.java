package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;
import mediator.Mediator;

public class PlayerCreator implements Creator {
	@Override
	public Tank factoryMethod(final int playerIndex, final boolean type, final Mediator m) {
		if (type) {
			return new Real_Player(playerIndex, m);
		} else {
			return new AI_Player(playerIndex, m);
		}
	}
}
