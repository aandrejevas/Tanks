/**
 * @(#) AI_Player.java
 */

package Tank_Game.Patterns.Factory;

import Tank_Game.*;
import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.AI_State.*;
import Tank_Game.Patterns.Strategy.MoveUp;

import java.util.Stack;

import static processing.core.PApplet.println;

public class AI_Player extends Tank
{
    public AICompState state;
    public int sightDist = 20;
    public int shotChangeDist = 8;
    public int scanDist = 7;

    public int[] pursueTarget = new int[2];
    public int[] fireTarget = new int[2];

    public int fireTargetDist = 0;

    public Stack<Integer[]> path = new Stack<Integer[]>();

    public AI_Player(int playerIndex) {
        super(playerIndex, 1);
        this.setAlgorithm(new MoveUp());
        this.state = new AICompState();
    }

    public void AIThink() {
        (new AIEnemyScan()).perform(this);

        if (!state.hasState(AICompState.AI_PURSUING) && !state.hasState(AICompState.AI_ROAM_FOUND)) {
            (new AIRoamScan()).perform(this);
        }

        (new AILockTarget()).perform(this);

        if (state.hasState(AICompState.AI_AIMED)) {
            (new AIShoot()).perform(this);
            return;
        } else if (state.hasState(AICompState.AI_TARGET_LOCKED)){
            (new AIAim()).perform(this);
            return;
        } else {
            (new AIPursueTarget()).perform(this);
        }

        if (state.hasState(AICompState.AI_PURSUING)) {
            (new AIDrive()).perform(this);
        }
    }
}
