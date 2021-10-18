package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import utils.Utils;

import static processing.core.PApplet.println;

public class AILockTarget implements AIState
{
    @Override
    public void perform(AI_Player ai) {
//        println("AI lock target");
        Object[] players = Main.clients.values().toArray();

        Tank best_tank = null;
        int bestDist = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            if (isClearSight(ai.getCord(), ((Invoker)players[i]).currentDecorator().getCord())) {
                int dist = menhadenDist(ai.getCord(), ((Invoker)players[i]).currentDecorator().getCord());
                if (dist < bestDist) {
                    bestDist = dist;
                    best_tank = ((Invoker) players[i]).currentDecorator();
                }
            }
        }

        if (bestDist < ai.sightDist && best_tank != null) {
            ai.state.addState(new AICompState(AICompState.AI_TARGET_LOCKED));
            ai.fireTarget = best_tank.getCord().clone();
            ai.fireTargetDist = bestDist;
            if(isAimed(ai, ai.fireTarget)) {
                ai.state.addState(new AICompState(AICompState.AI_AIMED));
            }
        }
    }

    private int menhadenDist(int[] from, int[] to) {
        return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
    }

    private boolean isClearSight(int[] from, int[] to) {
        int f = 0;
        int t = 0;
        if(from[0] == to[0]) {
            if (from[1] > to[1]) {
                f = to[1]+1;
                t = from[1];
            } else {
                f = from[1]+1;
                t = to[1];
            }
            for (int i = f; i < t; i++) {
                if (Main.map.isObstacle(from[0], i)) {
                    return false;
                }
            }
            return true;
        } else if(from[1] == to[1]) {
            if (from[0] > to[0]) {
                f = to[0]+1;
                t = from[0];
            } else {
                f = from[0]+1;
                t = to[0];
            }
            for (int i = f; i < t; i++) {
                if (Main.map.isObstacle(i, from[1])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    private boolean isAimed(AI_Player ai, int[] to) {
        int dx = to[0] - ai.getCordByIndex(0);
        int dy = to[1] - ai.getCordByIndex(1);
        switch (ai.getDirection()) {
            case Utils.ADD_UP_TANK:
                return dy < 0;
            case Utils.ADD_DOWN_TANK:
                return dy > 0;
            case Utils.ADD_LEFT_TANK:
                return dx < 0;
            case Utils.ADD_RIGHT_TANK:
                return dx > 0;
        }
        return false;
    }
}
