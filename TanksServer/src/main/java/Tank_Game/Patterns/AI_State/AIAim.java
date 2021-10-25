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

		if (dy < 0) {
			ai.setMoveAlgorithm(MoveUp.instance);
		} else if (dy > 0) {
			ai.setMoveAlgorithm(MoveDown.instance);
		} else if (dx < 0) {
			ai.setMoveAlgorithm(MoveLeft.instance);
		} else if (dx > 0) {
			ai.setMoveAlgorithm(MoveRight.instance);
		} else {
			PApplet.println("aim failed");
		}

		ai.state.addState(new AICompState(AICompState.AI_AIMED));
		ai.move();
	}
}
