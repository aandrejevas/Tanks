/**
 * @(#) AI_Player.java
 */

package Tank_Game.Patterns.Factory;

import Tank_Game.*;
import Tank_Game.Patterns.AI_Strategy.*;
import Tank_Game.Patterns.Strategy.MoveUp;

import java.util.Stack;

import static processing.core.PApplet.println;

public class AI_Player extends Tank
{

    public static final int AI_NONE = 0, AI_PURSUING = 1, AI_TARGET_LOCKED = 2, AI_TARGET_FOUND = 4,
            AI_ROAM_FOUND = 8, AI_AIMED = 16;
    public int state = AI_NONE;
    public int sightDist = 12;
    public int shotChangeDist = 7;
    public int scanDist = 6;

    public int[] pursueTarget = new int[2];
    public int[] fireTarget = new int[2];

    public int fireTargetDist = 0;

    public Stack<Integer[]> path = new Stack<Integer[]>();

    private AIAlgorithm aiAlg;

    public AI_Player(int playerIndex) {
        super(playerIndex, 1);
        this.setAlgorithm(new MoveUp());
    }

    public AI_Player setAIAlgorithm(AIAlgorithm aiAlg){
        this.aiAlg = aiAlg;
        return this;
    }

    private void behave() {
        aiAlg.perform(this);
    }

    public void AIThink() {
        setAIAlgorithm(new AIEnemyScan());
        behave();

        if (AI_NONE == (state & (AI_PURSUING | AI_ROAM_FOUND))) {
            setAIAlgorithm(new AIRoamScan());
            behave();
        }

        setAIAlgorithm(new AILockTarget());
        behave();

        if (AI_NONE != (state & AI_AIMED)) {
            setAIAlgorithm(new AIShoot());
            behave();
            return;
        } else  if (AI_NONE != (state & AI_TARGET_LOCKED)) {
            setAIAlgorithm(new AIAim());
            behave();
            return;
        } else if (AI_NONE != (state & AI_ROAM_FOUND)) {
            setAIAlgorithm(new AIPursueTarget());
            behave();
        }

        if (AI_NONE != (state & AI_PURSUING)) {
            setAIAlgorithm(new AIDrive());
            behave();
        }
    }
}
