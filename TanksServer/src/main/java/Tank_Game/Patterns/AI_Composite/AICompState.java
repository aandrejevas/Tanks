package Tank_Game.Patterns.AI_Composite;

import java.util.ArrayList;
import java.util.List;

public class AICompState implements Component {

	public static final int AI_NONE = 0, AI_PURSUING = 1, AI_TARGET_LOCKED = 2, AI_TARGET_FOUND = 3,
		AI_ROAM_FOUND = 4, AI_AIMED = 5;
	public static final int AI_SHOT_NORMAL = 0, AI_SHOT_CLOSE = 1;

	private final int state;
	private final List<Component> states;

	public AICompState(final int state) {
		this.state = state;
		states = new ArrayList<>();
	}

	public AICompState() {
		this.state = AI_NONE;
		states = new ArrayList<>();
	}

	@Override
	public void addState(final Component state) {
		if (!hasState(state.getState()))
			states.add(state);
	}

	@Override
	public void removeState(final Component state) {
		states.removeIf((final Component s) -> s.getState() == state.getState());
	}

	@Override
	public List<Component> getStates() {
		return states;
	}

	@Override
	public int getState() {
		return state;
	}

	@Override
	public boolean hasState(final int state) {
		return states.stream().anyMatch((final Component s) -> (s.getState() == state));
	}

	@Override
	public Component getState(final int state) {
		return states.stream().filter((final Component s) -> s.getState() == state).findFirst().orElse(null);
	}
}
