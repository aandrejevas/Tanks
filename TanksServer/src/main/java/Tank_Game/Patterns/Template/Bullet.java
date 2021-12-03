package Tank_Game.Patterns.Template;

import Tank_Game.Main;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;
import java.util.Iterator;
import java.util.Map;
import processing.net.Client;
import utils.TWritable;
import utils.Utils;

public abstract class Bullet {

	public static abstract class Side extends Bullet {
		protected int damage;
		protected int armor;

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
		protected int doDamage() {
			return damage;
		}

		public void setDamage(int damage) {
			this.damage = damage;
		}

		public void setArmor(int armor) {
			this.armor = armor;
		}

		@Override
		protected int doDamageArmor() {
			return armor;
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
				final Iterator<Map.Entry<Client, Decorator>> iterator = Main.clients.iterator();
				while (iterator.hasNext()) {
					final Map.Entry<Client, Decorator> entry = iterator.next();
					if (entry.getValue().getY() == y && entry.getValue().getX() == x - 1) {
						callDoDamage((TWritable)entry.getKey().output, entry.getValue());
						break;
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
				final Iterator<Map.Entry<Client, Decorator>> iterator = Main.clients.iterator();
				while (iterator.hasNext()) {
					final Map.Entry<Client, Decorator> entry = iterator.next();
					if (entry.getValue().getY() == y && entry.getValue().getX() == x + 1) {
						callDoDamage((TWritable)entry.getKey().output, entry.getValue());
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
				final Iterator<Map.Entry<Client, Decorator>> iterator = Main.clients.iterator();
				while (iterator.hasNext()) {
					final Map.Entry<Client, Decorator> entry = iterator.next();
					if (entry.getValue().getY() == y - 1 && entry.getValue().getX() == x) {
						callDoDamage((TWritable)entry.getKey().output, entry.getValue());
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
				final Iterator<Map.Entry<Client, Decorator>> iterator = Main.clients.iterator();
				while (iterator.hasNext()) {
					final Map.Entry<Client, Decorator> entry = iterator.next();
					if (entry.getValue().getY() == y + 1 && entry.getValue().getX() == x) {
						callDoDamage((TWritable)entry.getKey().output, entry.getValue());
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

	public Bullet(final Tank tank, final int side) {
		switch (side) {
			case 1:
				_side = new Left(tank);
				break;
			case 2:
				_side = new Right(tank);
				break;
			case 3:
				_side = new Up(tank);
				break;
			case 4:
				_side = new Down(tank);
				break;
			default:
				break;
		}
		_side.setDamage(doDamage());
		_side.setArmor(doDamageArmor());
		index = count++;
		start_time = System.nanoTime();
	}

	public boolean move() {
		return false;
	}

	public final void callDoDamage(TWritable client, Decorator decorator) {
		if (decorator.getArmor() > 0) {
			decorator.setArmor(decorator.getArmor() - doDamageArmor());
			client.write(Utils.SET_ARMOR, decorator.getIndex(), decorator.getArmor());
		} else {
			decorator.setHealth(decorator.getHealth() - doDamage());
			client.write(Utils.SET_HEALTH, decorator.getIndex(), decorator.getHealth());
		}
	}

	protected int doDamage() {
		return 0;
	}

	;

	protected int doDamageArmor() {
		return 0;
	}

	;

	protected boolean moveImpl() {
		return _side.moveImpl();
	}
}
