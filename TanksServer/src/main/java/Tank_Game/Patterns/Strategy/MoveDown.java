/**
 * @(#) MoveDown.java
 */

package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import utils.Utils;

public class MoveDown extends MoveAlgorithm
{

    @Override
    public void move(int[] cord, byte[] direction, int index) {
        if (Main.map.map[cord[1] + 1][cord[0]].value < Utils.MAP_NON_OBSTACLE) {
            switch (direction[0]) {
                default:
                    direction[0] = DOWN;
                    sendMove(Utils.POINT_DOWN, index);
                case DOWN: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]].value = Main.map.defMap[cord[1]][cord[0]].value;
            Main.map.map[++cord[1]][cord[0]].value = Utils.MAP_PLAYER;
            switch (direction[0]) {
                default:
                    direction[0] = DOWN;
                    sendMove(Utils.TURN_DOWN, index);
                    return;
                case DOWN:
                    sendMove(Utils.MOVE_DOWN, index);
                    return;
            }
        }
    }
}
