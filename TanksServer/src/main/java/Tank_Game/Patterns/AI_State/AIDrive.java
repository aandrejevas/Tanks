package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import processing.core.PApplet;

public class AIDrive implements AIState {
	@Override
	public void perform(AI_Player ai) {
		if (ai.path.empty()) {
//            println("stack empty");
			ai.state.removeState(AICompState.AI_ROAM_FOUND);
			ai.state.removeState(AICompState.AI_PURSUING);
			return;
		}

		Integer[] next = ai.path.pop();

		int dy = next[1] - ai.y;
		int dx = next[0] - ai.x;

		if (dy == -1) {
			ai.setAlgorithm(MoveUp.instance);
		} else if (dy == 1) {
			ai.setAlgorithm(MoveDown.instance);
		} else if (dx == -1) {
			ai.setAlgorithm(MoveLeft.instance);
		} else if (dx == 1) {
			ai.setAlgorithm(MoveRight.instance);
		} else {
			PApplet.println("ai failed to move SL:" + ai.path.size() + " dx:" + dx + " dy:" + dy);
		}
		ai.move();

	}
}
