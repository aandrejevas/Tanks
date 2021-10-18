package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;

import static processing.core.PApplet.println;

public class AIAim implements AIState
{
    public void perform(AI_Player ai) {
        int dx = ai.fireTarget[0] - ai.getCordByIndex(0);
        int dy = ai.fireTarget[1] - ai.getCordByIndex(1);

        if (dy < 0) {
            ai.setAlgorithm(new MoveUp());
        } else if (dy > 0) {
            ai.setAlgorithm(new MoveDown());
        } else if (dx < 0) {
            ai.setAlgorithm(new MoveLeft());
        } else if (dx > 0) {
            ai.setAlgorithm(new MoveRight());
        } else {
            println("aim failed");
        }

        ai.state.addState(new AICompState(AICompState.AI_AIMED));
        ai.move();
    }
}
