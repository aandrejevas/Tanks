/**
 * @(#) MoveLeft.java
 */

package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import utils.Utils;

public class MoveLeft extends MoveAlgorithm
{

    @Override
    public void move(int[] cord, byte[] direction, int index) {
        if (Main.map.map[cord[1]][cord[0] - 1].value < Utils.MAP_NON_OBSTACLE) {
            switch (direction[0]) {
                default:
                    direction[0] = LEFT;
                    sendMove(Utils.POINT_LEFT, index);
                case LEFT: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]].value = Main.map.defMap[cord[1]][cord[0]].value;
            Main.map.map[cord[1]][--cord[0]].value = Utils.MAP_PLAYER;
            switch (direction[0]) {
                default:
                    direction[0] = LEFT;
                    sendMove(Utils.TURN_LEFT, index);
                    return;
                case LEFT:
                    sendMove(Utils.MOVE_LEFT, index);
                    return;
            }
        }
    }
}
