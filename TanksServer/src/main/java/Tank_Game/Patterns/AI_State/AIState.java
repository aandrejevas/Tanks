package Tank_Game.Patterns.AI_State;

import Tank_Game.Patterns.Factory.AI_Player;

public interface AIState {
    public void perform(AI_Player ai);
}
