package Tank_Game;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import utils.Utils;

import java.util.function.Function;

public class Tank {

	protected static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;
	protected static int counter = 0;

	public final int index;

	public int ally_or_enemy;

	private MoveAlgorithm moveAlgorithm;
	public int[] cord = new int[2];
	public byte[] direction = new byte[1];

	public Tank(int index, int ally_or_enemy) {
		this.index = index;
		this.ally_or_enemy = ally_or_enemy;
		do {
			cord[0] = Utils.random().nextInt(Main.edge);
			cord[1] = Utils.random().nextInt(Main.edge);
		} while (Main.map.map[cord[1]][cord[0]].value < Utils.MAP_NON_OBSTACLE);
		Main.map.map[cord[1]][cord[0]].value = Utils.MAP_PLAYER;
		direction[0] = UP;
	}


	public Tank setAlgorithm(MoveAlgorithm moveAlgorithm){
		this.moveAlgorithm =  moveAlgorithm;
		return this;
	}

	public void move(){
		moveAlgorithm.move(cord, direction, index);
	}
}
