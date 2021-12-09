package Tank_Game.Patterns.Template;

import Tank_Game.Main;
import static Tank_Game.Main.map;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;
import java.util.Iterator;
import java.util.Map;
import processing.net.Client;
import utils.ArenaBlock;
import utils.TWritable;
import utils.Utils;

public abstract class Bullet {

	public static abstract class Side {
		protected final Bullet bullet;

		public Side(final Bullet b) {
			bullet = b;
		}

		public boolean move() {
			if (System.nanoTime() - bullet.start_time > bullet.timeout) {
				final boolean state = moveImpl();
				bullet.start_time = System.nanoTime();
				if (state)
					Main.this_server.write(Utils.REMOVE_BULLET, bullet.index);
				return state;
			}
			return false;
		}

		protected abstract boolean moveImpl();
	}

	public static class Left extends Side {
		public Left(final Tank tank, final Bullet b) {
			super(b);
			bullet.x = tank.getX() - 1;
			bullet.y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {

			if (Main.map.map[bullet.y][bullet.x - 1].obstacle) {
				bullet.callDoDamage(bullet.x - 1, bullet.y);
				return true;
			} else {
				--bullet.x;
				Main.this_server.write(Utils.BULLET_LEFT, bullet.index);
				return false;
			}
		}
	}

	public static class Right extends Side {
		public Right(final Tank tank, final Bullet b) {
			super(b);
			bullet.x = tank.getX() + 1;
			bullet.y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[bullet.y][bullet.x + 1].obstacle) {
				bullet.callDoDamage(bullet.x + 1, bullet.y);
				return true;
			} else {
				++bullet.x;
				Main.this_server.write(Utils.BULLET_RIGHT, bullet.index);
				return false;
			}
		}
	}

	public static class Up extends Side {
		public Up(final Tank tank, final Bullet b) {
			super(b);
			bullet.x = tank.getX();
			bullet.y = tank.getY() - 1;
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[bullet.y - 1][bullet.x].obstacle) {
				bullet.callDoDamage(bullet.x, bullet.y - 1);
				return true;
			} else {
				--bullet.y;
				Main.this_server.write(Utils.BULLET_UP, bullet.index);
				return false;
			}
		}
	}

	public static class Down extends Side {
		public Down(final Tank tank, final Bullet b) {
			super(b);
			bullet.x = tank.getX();
			bullet.y = tank.getY() + 1;
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, tank.getShotType());
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[bullet.y + 1][bullet.x].obstacle) {
				bullet.callDoDamage(bullet.x, bullet.y + 1);
				return true;
			} else {
				++bullet.y;
				Main.this_server.write(Utils.BULLET_DOWN, bullet.index);
				return false;
			}
		}
	}

	private static int count = 0;

	protected final long timeout = 50_000_000;
	protected long start_time;
	public final int index;
	protected int x, y;
	private final Side _side;

	public Bullet(final Tank tank) {
		index = count++;
		switch (tank.getDirection()) {
			case Tank.LEFT:
				_side = new Left(tank, this);
				break;
			case Tank.RIGHT:
				_side = new Right(tank, this);
				break;
			case Tank.UP:
				_side = new Up(tank, this);
				break;
			case Tank.DOWN:
				_side = new Down(tank, this);
				break;
			default:
				throw new RuntimeException();
		}
		start_time = System.nanoTime();
	}

	public boolean move() {
		return _side.move();
	}

	public final void callDoDamage(final int x, final int y) {
		final Iterator<Map.Entry<Client, Decorator>> iterator = Main.clients.iterator();
		while (iterator.hasNext()) {
			final Map.Entry<Client, Decorator> entry = iterator.next();
			final Decorator decorator = entry.getValue();
			if (decorator.getX() == x && decorator.getY() == y) {

				final TWritable client = (TWritable)entry.getKey().output;
				if (decorator.getArmor() > 0) {
					decorator.setArmor(Math.max(decorator.getArmor() - doDamageArmor(), 0));
					client.write(Utils.SET_ARMOR, decorator.getIndex(), decorator.getArmor());
				} else {
					decorator.setHealth(decorator.getHealth() - doDamage());
					client.write(Utils.SET_HEALTH, decorator.getIndex(), decorator.getHealth());
				}
				break;
			}
		}

		final Iterator<Decorator> inv = Main.enemies.iterator();
		while (inv.hasNext()) {
			final Decorator enem = inv.next();
			if (enem.getX() == x && enem.getY() == y) {
				enem.setHealth(enem.getHealth() - doDamage());
				if (enem.getHealth() <= 0) {
					inv.remove();
					final ArenaBlock block = map.map[enem.getY()][enem.getX()];
					block.value = block.defValue;
					block.obstacle = false;
					Main.this_server.write(Utils.REMOVE_TANK, enem.getIndex());
				}
				break;
			}
		}
	}

	protected abstract int doDamage();

	protected abstract int doDamageArmor();
}
