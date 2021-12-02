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
	public void perform(final AI_Player ai) {
		if (ai.path.empty()) {
//            println("stack empty");
			ai.state.removeState(new AICompState(AICompState.AI_ROAM_FOUND));
			ai.state.removeState(new AICompState(AICompState.AI_PURSUING));
			return;
		}

		final Integer[] next = ai.path.pop();

		final int dy = next[1] - ai.getY();
		final int dx = next[0] - ai.getX();

		if (dy == -1) {
			ai.setMoveAlgorithm(MoveUp.instance);
		} else if (dy == 1) {
			ai.setMoveAlgorithm(MoveDown.instance);
		} else if (dx == -1) {
			ai.setMoveAlgorithm(MoveLeft.instance);
		} else if (dx == 1) {
			ai.setMoveAlgorithm(MoveRight.instance);
		} else {
			PApplet.println("ai failed to move SL:" + ai.path.size() + " dx:" + dx + " dy:" + dy);
		}
		ai.move();

	}
}
