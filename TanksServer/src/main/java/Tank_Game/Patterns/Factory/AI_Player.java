package Tank_Game.Patterns.Factory;

import Tank_Game.Patterns.AI_Composite.AICompState;
import Tank_Game.Patterns.AI_Composite.Component;
import Tank_Game.Patterns.AI_State.AIAim;
import Tank_Game.Patterns.AI_State.AIDrive;
import Tank_Game.Patterns.AI_State.AIEnemyScan;
import Tank_Game.Patterns.AI_State.AILockTarget;
import Tank_Game.Patterns.AI_State.AIPursueTarget;
import Tank_Game.Patterns.AI_State.AIRoamScan;
import Tank_Game.Patterns.AI_State.AIShoot;
import Tank_Game.Patterns.Strategy.MoveUp;
import Tank_Game.Tank;
import java.util.Stack;
import mediator.Mediator;
import utils.Utils;

public class AI_Player extends Tank {
	private static final byte[] message = new byte[] { Utils.MESSAGE, 0x00, 0x00, 0x00, 0x07, '[', 'A', 'I', ']', ' ', '<', '3' };

	private int shots_shot = 0;

	public final Component state;
	public final int sightDist = 20;
	public final int shotChangeDist = 4;
	public final int scanDist = 7;

	public int[] pursueTarget = new int[2];
	public int[] fireTarget = new int[2];

	public int fireTargetDist = 0;

	public Stack<Integer[]> path = new Stack<>();

	public AI_Player(final int playerIndex, final Mediator m) {
		super(playerIndex, Utils.MAP_TIGER, m);
		this.setMoveAlgorithm(MoveUp.instance);
		this.state = new AICompState();
	}

	public void AIThink() {
		(new AIEnemyScan()).perform(this);

		if (!state.hasState(AICompState.AI_PURSUING) && !state.hasState(AICompState.AI_ROAM_FOUND)) {
			(new AIRoamScan()).perform(this);
		}

		(new AILockTarget()).perform(this);

		if (state.hasState(AICompState.AI_AIMED)) {
			if (++shots_shot % 10 == 0) {
				mediator.sendMessage(message);
			}
			(new AIShoot()).perform(this);
			return;
		} else if (state.hasState(AICompState.AI_TARGET_LOCKED)) {
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
