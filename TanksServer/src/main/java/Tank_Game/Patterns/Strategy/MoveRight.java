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
        if (Main.map.map[cord[1]][cord[0] + 1].obstacle) {
            switch (direction[0]) {
                default:
                    direction[0] = RIGHT;
                    sendMove(Utils.POINT_RIGHT, index);
                case RIGHT: return;
            }
        } else {
            Main.map.map[cord[1]][cord[0]+1].value = Main.map.map[cord[1]][cord[0]].value;
            Main.map.map[cord[1]][cord[0]].value = Main.map.map[cord[1]][cord[0]].defValue;
            Main.map.map[cord[1]][cord[0]].obstacle = false;
            Main.map.map[cord[1]][cord[0]+1].obstacle = true;
            cord[0]++;
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
