package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import utils.Utils;

public class AILockTarget implements AIState {
	@Override
	public void perform(AI_Player ai) {
		final int[] best_dist = { Integer.MAX_VALUE };
		final Tank best_tank = Main.clients.values().stream().reduce(null, (final Tank best, final Tank tank) -> {
			final int dist = distSq(ai, tank);
			if (best_dist[0] > dist) {
				best_dist[0] = dist;
				return tank;
			}
			return best;
		});

		if (best_dist[0] < ai.sightDist && best_tank != null) {
			ai.state.addState(AICompState.AI_TARGET_LOCKED);
			ai.fireTarget = best_tank;
			ai.fireTargetDist = best_dist[0];
			if (isAimed(ai, ai.fireTarget)) {
				ai.state.addState(AICompState.AI_AIMED);
			}
		}
	}

	private boolean isClearSight(int[] from, int[] to) {
		int f = 0;
		int t = 0;
		if (from[0] == to[0]) {
			if (from[1] > to[1]) {
				f = to[1] + 1;
				t = from[1];
			} else {
				f = from[1] + 1;
				t = to[1];
			}
			for (int i = f; i < t; i++) {
				if (Main.map.isObstacle(from[0], i)) {
					return false;
				}
			}
			return true;
		} else if (from[1] == to[1]) {
			if (from[0] > to[0]) {
				f = to[0] + 1;
				t = from[0];
			} else {
				f = from[0] + 1;
				t = to[0];
			}
			for (int i = f; i < t; i++) {
				if (Main.map.isObstacle(i, from[1])) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}

	}

	private boolean isAimed(final Tank ai, final Tank to) {
		switch (ai.direction) {
			case Utils.ADD_UP_TANK:
				return (to.y - ai.y) < 0;
			case Utils.ADD_DOWN_TANK:
				return (to.y - ai.y) > 0;
			case Utils.ADD_LEFT_TANK:
				return (to.x - ai.x) < 0;
			case Utils.ADD_RIGHT_TANK:
				return (to.x - ai.x) > 0;
			default: return false;
		}
	}
}
