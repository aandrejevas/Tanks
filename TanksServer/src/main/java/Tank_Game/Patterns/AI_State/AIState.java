package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import utils.Utils;

public interface AIState {
	void perform(final AI_Player ai);

	default int distSq(final Tank from, final Tank to) {
		return Utils.sq(from.x - to.x) + Math.abs(from.y - to.y);
	}
}
