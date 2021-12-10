package Tank_Game.Patterns.AI_Composite;

import java.util.List;

public interface Component {

	void addState(final Component state);

	void removeState(final Component state);

	List<Component> getStates();

	int getState();

	boolean hasState(final int state);

	Component getState(final int state);

}
