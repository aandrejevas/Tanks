/**
 * @(#) PlayerCreator.java
 */

package Tank_Game.Patterns.Factory;

import Tank_Game.*;

public class PlayerCreator extends Creator
{

    @Override
    public Tank factoryMethod(int playerIndex, boolean type) {

        if (type){
            return new Real_Player(playerIndex);
        }else{
            return new AI_Player(playerIndex);
        }
    }
}
