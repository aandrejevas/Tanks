package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import Tank_Game.Tank;

import java.util.List;

import static processing.core.PApplet.println;

public class AIDrive extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
        if (ai.path.empty()) {
//            println("stack empty");
            ai.state &= ~(AI_Player.AI_PURSUING | AI_Player.AI_ROAM_FOUND);
            return;
        }

        Integer[] next = ai.path.pop();

        int dy = next[1] - ai.cord[1];
        int dx = next[0] - ai.cord[0];

        if (dy == -1) {
            ai.setAlgorithm(new MoveUp());
        }
        else if (dy == 1) {
            ai.setAlgorithm(new MoveDown());
        }
        else if (dx == -1) {
            ai.setAlgorithm(new MoveLeft());
        }
        else if (dx == 1) {
            ai.setAlgorithm(new MoveRight());
        }
        else {
            println("ai failed to move SL:"+ai.path.size()+" dx:"+dx+" dy:"+dy);
        }
        ai.move();

    }
}
