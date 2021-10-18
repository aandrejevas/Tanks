package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;

public class PlayerCreator implements Creator {
	@Override
	public Tank factoryMethod(final int playerIndex, final boolean type) {
		if (type) {
			return new Real_Player(playerIndex);
		} else {
			return new AI_Player(playerIndex);
		}
	}
}
