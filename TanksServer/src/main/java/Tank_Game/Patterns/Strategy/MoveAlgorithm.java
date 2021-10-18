package Tank_Game.Patterns.Strategy;

import Tank_Game.Main;
import Tank_Game.Tank;

public interface MoveAlgorithm {
	void move(final Tank tank);

	default void sendMove(final byte message, int index) {
		Main.this_server.write(message, index);
	}
}
