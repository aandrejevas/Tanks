
package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import jdk.jshell.execution.Util;
import utils.ArenaMap;
import utils.Utils;

import java.util.Random;

import static processing.core.PApplet.println;

public class AIRoamScan extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
//        println("AI roam scan");
        int minX = Math.max(0, ai.cord[0] - ai.scanDist);
        int maxX = Math.min(Main.map.edge-1, ai.cord[0] + ai.scanDist);
        int minY = Math.max(0, ai.cord[1] - ai.scanDist);
        int maxY = Math.min(Main.map.edge-1, ai.cord[1] + ai.scanDist);

        do {
            ai.pursueTarget[0] = Utils.random().nextInt(minX, maxX);
            ai.pursueTarget[1] = Utils.random().nextInt(minY, maxY);
        } while (Main.map.getBlockValue(ai.pursueTarget) < Utils.MAP_NON_OBSTACLE);
        ai.state |= AI_Player.AI_ROAM_FOUND;
    }
}
