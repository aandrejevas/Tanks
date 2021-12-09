package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Patterns.Factory.AI_Player;
import java.util.Iterator;
import utils.Utils;

public class AIEnemyScan implements AIState {
	@Override
	public void perform(AI_Player ai) {
//        println("AI enemy scan");
		final Iterator<Invoker> players = Main.clients.values().iterator();

		Decorator best_tank = null;
		int bestDist = Integer.MAX_VALUE;

		while (players.hasNext()) {
			final Invoker player = players.next();
			final int dist = Utils.manhattanDist(ai.getCord(), player.currentDecorator().getCord());
			if (dist < bestDist) {
				bestDist = dist;
				best_tank = player.currentDecorator();
			}
		}

		if (bestDist < ai.scanDist && best_tank != null) {
			ai.state.addState(new AICompState(AICompState.AI_TARGET_FOUND));
			ai.state.addState(new AICompState(AICompState.AI_ROAM_FOUND));
			ai.pursueTarget = best_tank.getCord();
		} else {
			ai.state.removeState(new AICompState(AICompState.AI_TARGET_FOUND));
		}
	}
}
