package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import utils.Utils;

public class AIRoamScan implements AIState {
	@Override
	public void perform(AI_Player ai) {
//        println("AI roam scan");
		int minX = Math.max(0, ai.getCordByIndex(0) - ai.scanDist);
		int maxX = Math.min(Main.map.edge - 1, ai.getCordByIndex(0) + ai.scanDist);
		int minY = Math.max(0, ai.getCordByIndex(1) - ai.scanDist);
		int maxY = Math.min(Main.map.edge - 1, ai.getCordByIndex(1) + ai.scanDist);

		do {
			ai.pursueTarget[0] = Utils.random().nextInt(minX, maxX);
			ai.pursueTarget[1] = Utils.random().nextInt(minY, maxY);
		} while (Main.map.getBlockValue(ai.pursueTarget) < Utils.MAP_NON_OBSTACLE);
		ai.state.addState(new AICompState(AICompState.AI_ROAM_FOUND));
	}
}
