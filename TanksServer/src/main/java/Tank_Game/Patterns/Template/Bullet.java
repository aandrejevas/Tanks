package Tank_Game.Patterns.Template;

import Tank_Game.Main;
import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;
import processing.net.Client;
import utils.TOutputStream;
import utils.TWritable;
import utils.Utils;

import java.util.Collection;
import java.util.Iterator;

public abstract class Bullet {

	public static abstract class Side extends Bullet {
		protected byte damage;
		public boolean move() {
			if (System.nanoTime() - super.start_time > super.timeout) {
				final boolean state = moveImpl();
				super.start_time = System.nanoTime();
				if (state)
					Main.this_server.write(Utils.REMOVE_BULLET, index);
				return state;
			}
			return false;
		}

		@Override
		protected byte doDamage() {
			return damage;
		}

		public void setDamage(byte damage) {
			this.damage = damage;
		}
	}

	public static class Left extends Side {
		public Left(final Tank tank) {
			x = tank.getX() - 1;
			y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, index, x, y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {

			if (Main.map.map[y][x - 1].obstacle) {
				Iterator<Client> iterator = Main.clients.keySet().iterator();
				while (iterator.hasNext()) {
					Client client = iterator.next();
					Invoker invoker = Main.clients.get(client);

					if (invoker.currentDecorator().getY() == y && invoker.currentDecorator().getX() == x-1){
						callDoDamage((TWritable) client.output, invoker.currentDecorator());
					}
				}
				return true;
			} else {
				--x;
				Main.this_server.write(Utils.BULLET_LEFT, index);
				return false;
			}
		}
	}

	public static class Right extends Side {
		public Right(final Tank tank) {
			x = tank.getX() + 1;
			y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, index, x, y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[y][x + 1].obstacle) {

				Iterator<Client> iterator = Main.clients.keySet().iterator();
				while (iterator.hasNext()) {
					Client client = iterator.next();
					Invoker invoker = Main.clients.get(client);
					if (invoker.currentDecorator().getY() == y && invoker.currentDecorator().getX() == x+1){
						callDoDamage((TWritable) client.output, invoker.currentDecorator());
						break;
					}
				}
				return true;
			} else {
				++x;
				Main.this_server.write(Utils.BULLET_RIGHT, index);
				return false;
			}
		}
	}

	public static class Up extends Side {
		public Up(final Tank tank) {
			x = tank.getX();
			y = tank.getY() - 1;
			Main.this_server.write(Utils.ADD_BULLET, index, x, y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[y - 1][x].obstacle) {
				Iterator<Client> iterator = Main.clients.keySet().iterator();
				while (iterator.hasNext()) {
					Client client = iterator.next();
					Invoker invoker = Main.clients.get(client);
					if (invoker.currentDecorator().getY() == y-1 && invoker.currentDecorator().getX() == x){
						callDoDamage((TWritable) client.output,invoker.currentDecorator());
						break;
					}
				}
				return true;
			} else {
				--y;
				Main.this_server.write(Utils.BULLET_UP, index);
				return false;
			}
		}
	}

	public static class Down extends Side {
		public Down(final Tank tank) {
			x = tank.getX();
			y = tank.getY() + 1;
			Main.this_server.write(Utils.ADD_BULLET, index, x, y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[y + 1][x].obstacle) {
				Iterator<Client> iterator = Main.clients.keySet().iterator();
				while (iterator.hasNext()) {
					Client client = iterator.next();
					Invoker invoker = Main.clients.get(client);

					if (invoker.currentDecorator().getY() == y + 1 && invoker.currentDecorator().getX() == x){
						callDoDamage((TWritable) client.output,invoker.currentDecorator());
						break;
					}
				}
				return true;
			} else {
				++y;
				Main.this_server.write(Utils.BULLET_DOWN, index);
				return false;
			}
		}
	}

	private static int count = 0;

	private final long timeout = 50_000_000;
	private long start_time;
	public final int index;
	protected int x, y;
	public Side _side;
	public byte damage;
	public Bullet bullet = this;

	public Bullet() {
		index = count++;
		start_time = System.nanoTime();
	}

	public Bullet(Tank tank, int side) {
		if (side == 1){
			_side = new Left(tank);
		}else if(side == 2){
			_side = new Right(tank);
		}else if (side == 3){
			_side = new Up(tank);
		}else if (side == 4) {
			_side = new Down(tank);
		}
		_side.setDamage(doDamage());
		index = count++;
		start_time = System.nanoTime();
	}

	public boolean move(){
		return false;
	};

	public final void callDoDamage(TWritable client, Decorator decorator){
		if (doDamage() == Utils.SHOT_NORMAL){
			damage(decorator, Utils.SHOT_NORMAL);
		}else if (doDamage() == Utils.SHOT_BLUE){
			damage(decorator, Utils.SHOT_BLUE);
		}else if (doDamage() == Utils.SHOT_RED){
			damage(decorator, Utils.SHOT_RED);
		}
		client.write(Utils.SET_HEALTH, decorator.getIndex(), decorator.getHealth());
	}

	public void damage(Decorator decorator, byte dmg){
		if (dmg == Utils.SHOT_NORMAL) {
			decorator.setHealth(decorator.getHealth() - 5);
		}else if (dmg == Utils.SHOT_BLUE){
			decorator.setHealth(decorator.getHealth() - 10);
		}else if (dmg == Utils.SHOT_RED){
			decorator.setHealth(decorator.getHealth() - 15);
		}
	}

	protected byte doDamage(){
		return Utils.SHOT_NORMAL;
	};

	protected boolean moveImpl() {
		return _side.moveImpl();
	}
}
