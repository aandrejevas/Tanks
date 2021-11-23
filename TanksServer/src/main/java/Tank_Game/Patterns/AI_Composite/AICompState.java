package Tank_Game.Patterns.AI_Composite;

import java.util.ArrayList;
import java.util.List;

public class AICompState extends Component {

    public static final int AI_NONE = 0, AI_PURSUING = 1, AI_TARGET_LOCKED = 2, AI_TARGET_FOUND = 3,
            AI_ROAM_FOUND = 4, AI_AIMED = 5;
    public static final int AI_SHOT_NORMAL = 0, AI_SHOT_CLOSE = 1;

    private int state;
    private List<Component> states;

    public AICompState(int state) {
        this.state = state;
        states = new ArrayList<Component>();
    }

    public AICompState() {
        this.state = AI_NONE;
        states = new ArrayList<Component>();
    }

    public void addState(Component state) {
        for (Component s : states) {
            if (((AICompState)s).state == ((AICompState)state).state){
                return;
            }
        }
        states.add(state);
    }

    public void removeState(Component state) {
        ArrayList<Component> removables = new ArrayList<Component>();
        for (Component s : states) {
            if (((AICompState)s).state == ((AICompState)state).state){
                removables.add(s);
            }
        }
        states.removeAll(removables);
    }

    public List<Component> getStates(){
        return states;
    }

    public boolean hasState(int state) {
        for (Component s : states) {
            if (((AICompState)s).state == state){
                return true;
            }
        }
        return false;
    }

    public Component getState(int state) {
        for (Component s : states) {
            if (((AICompState)s).state == state){
                return s;
            }
        }
        return null;
    }
}
