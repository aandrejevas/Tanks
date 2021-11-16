package Tank_Game.Patterns.AI_Composite;

import java.util.List;

public abstract class Component {

    public abstract void addState(Component state);

    public abstract void removeState(Component state);

    public abstract List<Component> getStates();

    public abstract boolean hasState(int state);


}
