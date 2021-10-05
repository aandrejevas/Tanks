/**
 * @(#) MoveUp.java
 */

package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import utils.Utils;

public class MoveUp extends MoveAlgorithm
{

    @Override
    public void move(int[] cord, byte[] direction, final int index) {
        if (Main.map.map[cord[1] - 1][cord[0]].value < Utils.MAP_NON_OBSTACLE) {
            switch (direction[0]) {
                default:
                    direction[0] = UP;
                    sendMove(Utils.POINT_UP, index);
                case UP: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]].value = Main.map.defMap[cord[1]][cord[0]].value;
            Main.map.map[--cord[1]][cord[0]].value = Utils.MAP_PLAYER;
            switch (direction[0]) {
                default:
                    direction[0] = UP;
                    sendMove(Utils.TURN_UP, index);
                    return;
                case UP:
                    sendMove(Utils.MOVE_UP, index);
                    return;
            }
        }
    }
}
