
package Tank_Game.Patterns.AI_State;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;

public abstract class AIAlgorithm
{
	public abstract void perform(AI_Player ai);


	protected void sendMove(final byte message, int index) {
		Main.this_server.write(message, index);
	}
}
