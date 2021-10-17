package Tank_Game;

import Tank_Game.Patterns.Strategy.MoveAlgorithm;
import utils.Utils;

import java.util.function.Function;

public class Tank implements Cloneable {

	protected static final byte LEFT = Utils.ADD_LEFT_TANK, RIGHT = Utils.ADD_RIGHT_TANK, UP = Utils.ADD_UP_TANK, DOWN = Utils.ADD_DOWN_TANK;

	public int index;

	public int type;
	private int damage = 10;

	private MoveAlgorithm moveAlgorithm;
	public int[] cord = new int[2];
	public byte[] direction = new byte[1];

	public Tank(int index, int ally_or_enemy) {
		this.index = index;
		this.type = ally_or_enemy;
		do {
			cord[0] = Utils.random().nextInt(Main.edge);
			cord[1] = Utils.random().nextInt(Main.edge);
		} while (Main.map.map[cord[1]][cord[0]].value < Utils.MAP_NON_OBSTACLE);
		Main.map.map[cord[1]][cord[0]].value = Utils.MAP_PLAYER;
		direction[0] = UP;
	}

	public Tank() {

	}

	public Tank setAlgorithm(MoveAlgorithm moveAlgorithm){
		this.moveAlgorithm =  moveAlgorithm;
		return this;
	}

	public MoveAlgorithm getMoveAlgorithm() {
		return moveAlgorithm;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void move(){
		moveAlgorithm.move(cord, direction, index);
	}

	public void shoot(){

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Tank tank = (Tank)super.clone();
		tank.index = this.index;
		tank.type = this.type;
		tank.damage = this.damage;
		if (this.moveAlgorithm != null)
			tank.moveAlgorithm = (MoveAlgorithm) this.moveAlgorithm.clone();

		int[] cord = new int[2];
		cord[0] = this.cord[0];
		cord[1] = this.cord[1];
		tank.cord = cord;

		byte[] direction = new byte[1];
		direction[0] = this.direction[0];
		tank.direction = direction;
		return tank;
	}

	public Tank thisTank(){
		return this;
	}
}
