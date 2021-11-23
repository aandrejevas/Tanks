package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.AI_Composite.Component;
import Tank_Game.Patterns.Template.BlueBullet;
import Tank_Game.Patterns.Template.Bullet;
import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Template.NormalBullet;
import Tank_Game.Patterns.Template.RedBullet;
import Tank_Game.Tank;
import processing.core.PApplet;
import utils.Utils;

import java.util.List;

public class AIShoot implements AIState {
	@Override
	public void perform(AI_Player ai) {
//        println("AI shoot at x:"+ai.fireTarget[0]+" y:"+ai.fireTarget[1]);
		int side = 0;
		switch (ai.getDirection()) {
			case Tank.LEFT:
				if (!Main.map.map[ai.getY()][ai.getX() - 1].obstacle)side = 1; // Main.bullets.add(new Bullet.Left(ai));
				break;
			case Tank.RIGHT:
				if (!Main.map.map[ai.getY()][ai.getX() + 1].obstacle)side = 2; // Main.bullets.add(new Bullet.Right(ai));
				break;
			case Tank.UP:
				if (!Main.map.map[ai.getY() - 1][ai.getX()].obstacle)side = 3; // Main.bullets.add(new Bullet.Up(ai));
				break;
			case Tank.DOWN:
				if (!Main.map.map[ai.getY() + 1][ai.getX()].obstacle)side = 4; // Main.bullets.add(new Bullet.Down(ai));
				break;
		}


		if (side != 0){
			AICompState ast = (AICompState)ai.state.getState(AICompState.AI_AIMED);
			Main.bullets.add(new NormalBullet(ai, side));
			if (ast.hasState(AICompState.AI_SHOT_CLOSE)) {
				Main.bullets.add(new RedBullet(ai, side));
			}
		}

		ai.state.removeState(new AICompState(AICompState.AI_AIMED));
		ai.state.removeState(new AICompState(AICompState.AI_TARGET_LOCKED));
	}
}
