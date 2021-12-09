package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Template.NormalBullet;
import Tank_Game.Patterns.Template.RedBullet;

public class AIShoot implements AIState {
	@Override
	public void perform(final AI_Player ai) {
//        println("AI shoot at x:"+ai.fireTarget[0]+" y:"+ai.fireTarget[1]);
		final AICompState ast = (AICompState)ai.state.getState(AICompState.AI_AIMED);
		if (ast.hasState(AICompState.AI_SHOT_CLOSE)) {
			Main.bullets.add(new RedBullet(ai, null));
		} else {
			Main.bullets.add(new NormalBullet(ai, null));
		}

		ai.state.removeState(new AICompState(AICompState.AI_AIMED));
		ai.state.removeState(new AICompState(AICompState.AI_TARGET_LOCKED));
	}
}
