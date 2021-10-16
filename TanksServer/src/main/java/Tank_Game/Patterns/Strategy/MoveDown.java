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
        if (Main.map.map[cord[1] + 1][cord[0]].obstacle) {
            switch (direction[0]) {
                default:
                    direction[0] = DOWN;
                    sendMove(Utils.POINT_DOWN, index);
                case DOWN: return;
            }
        } else {
            Main.map.map[cord[1]+1][cord[0]].value = Main.map.map[cord[1]][cord[0]].value;
            Main.map.map[cord[1]][cord[0]].value = Main.map.map[cord[1]][cord[0]].defValue;
            Main.map.map[cord[1]][cord[0]].obstacle = false;
            Main.map.map[cord[1]+1][cord[0]].obstacle = true;
            cord[1]++;
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
