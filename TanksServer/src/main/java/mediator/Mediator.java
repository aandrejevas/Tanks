package mediator;

import Tank_Game.Patterns.Singletone.Game_Context;

public class Mediator {
	private final Game_Context context;

	public Mediator(final Game_Context c) {
		context = c;
	}

	public void sendMessage(final byte[] data, final int offset, final int length) {
		context.receiveMessage(data, offset, length);
	}

	public void sendMessage(final byte[] data) {
		context.receiveMessage(data);
	}
}
