package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import utils.Utils;

import static processing.core.PApplet.println;

public class AIAim extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
        int dx = ai.fireTarget[0] - ai.cord[0];
        int dy = ai.fireTarget[1] - ai.cord[1];

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

        ai.state |= AI_Player.AI_AIMED;
        ai.move();
    }
}
