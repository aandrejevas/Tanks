package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import utils.Utils;

import java.util.List;

import static processing.core.PApplet.println;

public class AILockTarget extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
//        println("AI lock target");
        Object[] players = Main.clients.values().toArray();

        Tank best_tank = null;
        int bestDist = Integer.MAX_VALUE;

        for (int i = 0; i < players.length; i++) {
            if (isClearSight(ai.cord, ((Tank)players[i]).cord)) {
                int dist = menhadenDist(ai.cord, ((Tank)players[i]).cord);
                if (dist < bestDist) {
                    bestDist = dist;
                    best_tank = (Tank)players[i];
                }
            }
        }

        if (bestDist < ai.sightDist && best_tank != null) {
            ai.state |= AI_Player.AI_TARGET_LOCKED;
            ai.fireTarget = best_tank.cord;
            ai.fireTargetDist = bestDist;
            if(isAimed(ai, best_tank.cord)) {
                ai.state |= AI_Player.AI_AIMED;
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
        int dx = to[0] - ai.cord[0];
        int dy = to[1] - ai.cord[1];
        switch (ai.direction[0]) {
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
