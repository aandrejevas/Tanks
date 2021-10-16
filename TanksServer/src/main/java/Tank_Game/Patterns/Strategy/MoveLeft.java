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
        if (Main.map.map[cord[1]][cord[0] - 1].obstacle) {
            switch (direction[0]) {
                default:
                    direction[0] = LEFT;
                    sendMove(Utils.POINT_LEFT, index);
                case LEFT: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]-1].value = Main.map.map[cord[1]][cord[0]].value;
            Main.map.map[cord[1]][cord[0]].value = Main.map.map[cord[1]][cord[0]].defValue;
            Main.map.map[cord[1]][cord[0]].obstacle = false;
            Main.map.map[cord[1]][cord[0]-1].obstacle = true;
            cord[0]--;
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
