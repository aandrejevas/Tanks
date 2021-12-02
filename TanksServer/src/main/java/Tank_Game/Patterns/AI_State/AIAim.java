package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import processing.core.PApplet;

public class AIAim implements AIState {
	@Override
	public void perform(AI_Player ai) {
		int dx = ai.fireTarget[0] - ai.getX();
		int dy = ai.fireTarget[1] - ai.getY();
		int d = 0;

		if (dy < 0) {
			ai.setMoveAlgorithm(MoveUp.instance);
			d = Math.abs(dy);
		} else if (dy > 0) {
			ai.setMoveAlgorithm(MoveDown.instance);
			d = Math.abs(dy);
		} else if (dx < 0) {
			ai.setMoveAlgorithm(MoveLeft.instance);
			d = Math.abs(dx);
		} else if (dx > 0) {
			ai.setMoveAlgorithm(MoveRight.instance);
			d = Math.abs(dx);
		} else {
			PApplet.println("aim failed");
		}

		final AICompState st = new AICompState(AICompState.AI_AIMED);

		if (d <= ai.shotChangeDist) {
			st.addState(new AICompState(AICompState.AI_SHOT_CLOSE));
		} else {
			st.addState(new AICompState(AICompState.AI_SHOT_NORMAL));
		}

		ai.state.addState(st);

		ai.move();
	}
}
