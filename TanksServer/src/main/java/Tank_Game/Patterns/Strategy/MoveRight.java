/**
 * @(#) MoveRight.java
 */

package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import utils.Utils;

public class MoveRight extends MoveAlgorithm
{

    @Override
    public void move(int[] cord, byte[] direction, int index) {
        if (Main.map.map[cord[1]][cord[0] + 1].value < Utils.MAP_NON_OBSTACLE) {
            switch (direction[0]) {
                default:
                    direction[0] = RIGHT;
                    sendMove(Utils.POINT_RIGHT, index);
                case RIGHT: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]].value = Main.map.defMap[cord[1]][cord[0]].value;
            Main.map.map[cord[1]][++cord[0]].value = Utils.MAP_PLAYER;
            switch (direction[0]) {
                default:
                    direction[0] = RIGHT;
                    sendMove(Utils.TURN_RIGHT, index);
                    return;
                case RIGHT:
                    sendMove(Utils.MOVE_RIGHT, index);
                    return;
            }
        }
    }
}
