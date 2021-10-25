package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;

public class AIEnemyScan implements AIState {
	@Override
	public void perform(AI_Player ai) {
//        println("AI enemy scan");
		Object[] players = Main.clients.values().toArray();

		Tank best_tank = null;
		int bestDist = Integer.MAX_VALUE;

		for (int i = 0; i < players.length; i++) {
			int dist = menhadenDist(ai.getCord(), ((Invoker)players[i]).currentDecorator().getCord());
			if (dist < bestDist) {
				bestDist = dist;
				best_tank = ((Invoker)players[i]).currentDecorator();
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

	private int menhadenDist(int[] from, int[] to) {
		return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
	}
}
