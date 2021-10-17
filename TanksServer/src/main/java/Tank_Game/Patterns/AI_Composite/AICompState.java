package Tank_Game.Patterns.AI_Composite;

import java.util.ArrayList;
import java.util.List;

public class AICompState {

    public static final int AI_NONE = 0, AI_PURSUING = 1, AI_TARGET_LOCKED = 2, AI_TARGET_FOUND = 4,
            AI_ROAM_FOUND = 8, AI_AIMED = 16;

    private int state;
    private List<AICompState> states;

    public AICompState(int state) {
        this.state = state;
        states = new ArrayList<AICompState>();
    }

    public AICompState() {
        this.state = AI_NONE;
        states = new ArrayList<AICompState>();
    }

    public void addState(AICompState state) {
        for (AICompState s : states) {
            if (s.state == state.state){
                return;
            }
        }
        states.add(state);
    }

    public void removeState(AICompState state) {
        ArrayList<AICompState> removables = new ArrayList<AICompState>();
        for (AICompState s : states) {
            if (s.state == state.state){
                removables.add(s);
            }
        }
        states.removeAll(removables);
    }

    public List<AICompState> getStates(){
        return states;
    }

    public boolean hasState(int state) {
        for (AICompState s : states) {
            if (s.state == state){
                return true;
            }
        }
        return false;
    }
}
