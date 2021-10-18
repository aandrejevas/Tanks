package Tank_Game.Patterns.AI_Composite;

import java.util.HashSet;
import java.util.Set;

public class AICompState {

	public static final Integer AI_NONE = 0, AI_PURSUING = 1, AI_TARGET_LOCKED = 2, AI_TARGET_FOUND = 4,
		AI_ROAM_FOUND = 8, AI_AIMED = 16;

	private final Set<Integer> states;

	public AICompState() {
		states = new HashSet<>();
	}

	public void addState(final int state) {
		states.add(state);
	}

	public void removeState(final int state) {
		states.remove(state);
	}

	public boolean hasState(final int state) {
		return states.contains(state);
	}
}
