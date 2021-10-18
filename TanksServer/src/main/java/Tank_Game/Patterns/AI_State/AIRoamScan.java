package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import utils.Utils;

public class AIRoamScan implements AIState {
	@Override
	public void perform(final AI_Player ai) {
//        println("AI roam scan");
		int minX = Math.max(0, ai.x - ai.scanDist);
		int maxX = Math.min(Main.map.edge - 1, ai.x + ai.scanDist);
		int minY = Math.max(0, ai.y - ai.scanDist);
		int maxY = Math.min(Main.map.edge - 1, ai.y + ai.scanDist);

		ai.pursueTarget = Main.clients.get(Utils.random(Main.this_server.clients));
		ai.state.addState(new AICompState(AICompState.AI_ROAM_FOUND));
	}
}
