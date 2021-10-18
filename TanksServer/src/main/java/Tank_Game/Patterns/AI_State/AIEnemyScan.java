package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;

public class AIEnemyScan implements AIState {
	@Override
	public void perform(final AI_Player ai) {
		final int[] best_dist = { Integer.MAX_VALUE };
		final Tank best_tank = Main.clients.values().stream().reduce(null, (final Tank best, final Tank tank) -> {
			final int dist = distSq(ai, tank);
			if (best_dist[0] > dist) {
				best_dist[0] = dist;
				return tank;
			}
			return best;
		});

		if (best_dist[0] < ai.scanDist && best_tank != null) {
			ai.state.addState(new AICompState(AICompState.AI_TARGET_FOUND));
			ai.state.addState(new AICompState(AICompState.AI_ROAM_FOUND));
			ai.pursueTarget = best_tank;
		} else {
			ai.state.removeState(new AICompState(AICompState.AI_TARGET_FOUND));
		}
	}
}
