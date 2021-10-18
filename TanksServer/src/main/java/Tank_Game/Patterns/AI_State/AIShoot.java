package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;

public class AIShoot implements AIState {
	@Override
	public void perform(final AI_Player ai) {
		//println("AI shoot at x:" + ai.fireTarget[0] + " y:" + ai.fireTarget[1]);

		ai.state.removeState(AICompState.AI_AIMED);
		ai.state.removeState(AICompState.AI_TARGET_LOCKED);
	}
}
