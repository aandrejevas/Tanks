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
	public void perform(final AI_Player ai) {
		int dx = ai.fireTarget.x - ai.x;
		int dy = ai.fireTarget.y - ai.y;

		if (dy < 0) {
			ai.setAlgorithm(MoveUp.instance);
		} else if (dy > 0) {
			ai.setAlgorithm(MoveDown.instance);
		} else if (dx < 0) {
			ai.setAlgorithm(MoveLeft.instance);
		} else if (dx > 0) {
			ai.setAlgorithm(MoveRight.instance);
		} else {
			PApplet.println("aim failed");
		}

		ai.state.addState(AICompState.AI_AIMED);
		ai.move();
	}
}
