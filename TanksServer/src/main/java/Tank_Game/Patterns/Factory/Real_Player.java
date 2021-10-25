package Tank_Game.Patterns.Factory;

import Tank_Game.Tank;
import utils.Utils;

public class Real_Player extends Tank {
	public Real_Player(final int player_index) {
		super(player_index, Utils.MAP_T34);
	}
}
