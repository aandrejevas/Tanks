
package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import utils.Utils;

import static processing.core.PApplet.println;

public class AIShoot extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
        println("AI shoot at x:"+ai.fireTarget[0]+" y:"+ai.fireTarget[1]);



        ai.state = ai.state & ~(AI_Player.AI_TARGET_LOCKED | AI_Player.AI_AIMED);
    }
}
