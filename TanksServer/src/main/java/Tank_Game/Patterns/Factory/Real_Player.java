package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;
import mediator.Mediator;
import utils.Utils;

public class Real_Player extends Tank {
	public Real_Player(final int player_index, final Mediator m) {
		super(player_index, Utils.MAP_T34, m);
	}
}
