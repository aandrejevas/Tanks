
package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Tank;
import utils.ArenaMap;
import utils.Utils;

public abstract class AIAlgorithm
{
	public abstract void perform(AI_Player ai);


	protected void sendMove(final byte message, int index) {
		Utils.send(Main.this_server::write, message, index);
	}
}
