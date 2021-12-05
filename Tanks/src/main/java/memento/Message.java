package memento;

import java.nio.CharBuffer;

public class Message {
	private final CharBuffer state;

	public Message() {
		state = CharBuffer.allocate(50);
	}

	public CharBuffer get() {
		return state;
	}

	public Memento saveState() {
		return new Memento(state);
	}

	public void restoreState(final Memento memento) {
		state.position(memento.getState().length);
		state.put(0, memento.getState());
	}

	public static class Memento {
		private final char[] state;

		private Memento(final CharBuffer s) {
			state = new char[s.position()];
			System.arraycopy(s.array(), 0, state, 0, s.position());
		}

		private char[] getState() {
			return state;
		}
	}
}
