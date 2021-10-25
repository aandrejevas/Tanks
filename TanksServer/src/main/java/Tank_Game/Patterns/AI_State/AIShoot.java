package Tank_Game.Patterns.AI_State;

import Tank_Game.Bullet;
import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;

public class AIShoot implements AIState {
	@Override
	public void perform(AI_Player ai) {
//        println("AI shoot at x:"+ai.fireTarget[0]+" y:"+ai.fireTarget[1]);

		switch (ai.getDirection()) {
			case Tank.LEFT:
				if (!Main.map.map[ai.getY()][ai.getX() - 1].obstacle) Main.bullets.add(new Bullet.Left(ai));
				break;
			case Tank.RIGHT:
				if (!Main.map.map[ai.getY()][ai.getX() + 1].obstacle) Main.bullets.add(new Bullet.Right(ai));
				break;
			case Tank.UP:
				if (!Main.map.map[ai.getY() - 1][ai.getX()].obstacle) Main.bullets.add(new Bullet.Up(ai));
				break;
			case Tank.DOWN:
				if (!Main.map.map[ai.getY() + 1][ai.getX()].obstacle) Main.bullets.add(new Bullet.Down(ai));
				break;
		}

		ai.state.removeState(new AICompState(AICompState.AI_AIMED));
		ai.state.removeState(new AICompState(AICompState.AI_TARGET_LOCKED));
	}
}
