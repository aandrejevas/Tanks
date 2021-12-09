package Tank_Game.Patterns.Template;

import Tank_Game.Main;
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
		public Left(final Bullet b) {
			super(b);
			bullet.x = bullet.tank.getX() - 1;
			bullet.y = bullet.tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, bullet.tank.getShotType());
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
		public Right(final Bullet b) {
			super(b);
			bullet.x = bullet.tank.getX() + 1;
			bullet.y = bullet.tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, bullet.tank.getShotType());
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
		public Up(final Bullet b) {
			super(b);
			bullet.x = bullet.tank.getX();
			bullet.y = bullet.tank.getY() - 1;
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, bullet.tank.getShotType());
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
		public Down(final Bullet b) {
			super(b);
			bullet.x = bullet.tank.getX();
			bullet.y = bullet.tank.getY() + 1;
			Main.this_server.write(Utils.ADD_BULLET, bullet.index, bullet.x, bullet.y, bullet.tank.getShotType());
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
	public final Tank tank;

	public Bullet(final Tank t) {
		tank = t;
		index = count++;
		switch (tank.getDirection()) {
			case Tank.LEFT:
				_side = new Left(this);
				break;
			case Tank.RIGHT:
				_side = new Right(this);
				break;
			case Tank.UP:
				_side = new Up(this);
				break;
			case Tank.DOWN:
				_side = new Down(this);
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
				if (decorator.getArmor() != 0) {
					decorator.setArmor(Math.max(decorator.getArmor() - doDamageArmor(), 0));
					client.write(Utils.SET_ARMOR, decorator.getIndex(), decorator.getArmor());
				} else {
					decorator.setHealth(decorator.getHealth() - doDamage());
					if (decorator.getHealth() <= 0) {
						client.write(Utils.GAME_END);
						final ArenaBlock block = Main.map.map[decorator.getY()][decorator.getX()];
						block.value = block.defValue;
						block.obstacle = false;
						Main.this_server.write(Utils.REMOVE_TANK, decorator.getIndex());
						Main.clients.remove(entry.getKey());
					} else {
						client.write(Utils.SET_HEALTH, decorator.getIndex(), decorator.getHealth());
					}
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
					final ArenaBlock block = Main.map.map[enem.getY()][enem.getX()];
					block.value = block.defValue;
					block.obstacle = false;
					Main.this_server.write(Utils.REMOVE_AI_TANK, enem.getIndex(), tank.getIndex());
					if (++Main.enemies_dead == 3) {
						Main.game = false;
						Main.this_server.write(Utils.GAME_END);
					}
				}
				break;
			}
		}
	}

	protected abstract int doDamage();

	protected abstract int doDamageArmor();
}
