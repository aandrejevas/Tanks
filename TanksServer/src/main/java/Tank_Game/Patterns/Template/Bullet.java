package Tank_Game.Patterns.Template;

import Tank_Game.ClientInfo;
import Tank_Game.Main;
import Tank_Game.Patterns.Decorator.Decorator;
import Tank_Game.Tank;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;
import processing.net.Client;
import utils.ArenaBlock;
import utils.TWritable;
import utils.Utils;

public abstract class Bullet {

	private static final Predicate<Bullet> left = (final Bullet bullet) -> {
		if (Main.map.map[bullet.y][bullet.x - 1].obstacle && (bullet.y != bullet.tank.getY() || bullet.x - 1 != bullet.tank.getX())) {
			bullet.callDoDamage(bullet.x - 1, bullet.y);
			return true;
		} else {
			--bullet.x;
			Main.this_server.write(Utils.BULLET_LEFT, bullet.index);
			return false;
		}
	};

	private static final Predicate<Bullet> right = (final Bullet bullet) -> {
		if (Main.map.map[bullet.y][bullet.x + 1].obstacle && (bullet.y != bullet.tank.getY() || bullet.x + 1 != bullet.tank.getX())) {
			bullet.callDoDamage(bullet.x + 1, bullet.y);
			return true;
		} else {
			++bullet.x;
			Main.this_server.write(Utils.BULLET_RIGHT, bullet.index);
			return false;
		}
	};

	private static final Predicate<Bullet> up = (final Bullet bullet) -> {
		if (Main.map.map[bullet.y - 1][bullet.x].obstacle && (bullet.y - 1 != bullet.tank.getY() || bullet.x != bullet.tank.getX())) {
			bullet.callDoDamage(bullet.x, bullet.y - 1);
			return true;
		} else {
			--bullet.y;
			Main.this_server.write(Utils.BULLET_UP, bullet.index);
			return false;
		}
	};

	private static final Predicate<Bullet> down = (final Bullet bullet) -> {
		if (Main.map.map[bullet.y + 1][bullet.x].obstacle && (bullet.y + 1 != bullet.tank.getY() || bullet.x != bullet.tank.getX())) {
			bullet.callDoDamage(bullet.x, bullet.y + 1);
			return true;
		} else {
			++bullet.y;
			Main.this_server.write(Utils.BULLET_DOWN, bullet.index);
			return false;
		}
	};

	private static int count = 0;

	protected final long timeout = 50_000_000;
	protected long start_time;
	public final int index;
	protected int x, y;
	private final Predicate<Bullet> side;
	public final Tank tank;
	public final Client client;

	public Bullet(final Tank t, final Client c) {
		client = c;
		tank = t;
		index = count++;
		x = tank.getX();
		y = tank.getY();
		Main.this_server.write(Utils.ADD_BULLET, index, x, y, tank.getShotType());
		switch (tank.getDirection()) {
			case Tank.LEFT:
				side = left;
				break;
			case Tank.RIGHT:
				side = right;
				break;
			case Tank.UP:
				side = up;
				break;
			case Tank.DOWN:
				side = down;
				break;
			default:
				throw new RuntimeException();
		}
		start_time = System.nanoTime();
	}

	public boolean move() {
		if (System.nanoTime() - start_time > timeout) {
			final boolean state = side.test(this);
			start_time = System.nanoTime();
			if (state)
				Main.this_server.write(Utils.REMOVE_BULLET, index);
			return state;
		}
		return false;
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
					client.write(Utils.SET_ARMOR, /*decorator.getIndex(), */ decorator.getArmor());
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
						client.write(Utils.SET_HEALTH, /*decorator.getIndex(), */ decorator.getHealth());
					}
				}
				return;
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
					final ClientInfo info = Main.indexes.get(client);
					++info.points;
					Main.this_server.write(Utils.REMOVE_AI_TANK, enem.getIndex(), info.index);
					if (++Main.enemies_dead == 3) {
						Main.game = false;
						Main.this_server.write(Utils.GAME_END);
					}
				}
				return;
			}
		}
	}

	protected abstract int doDamage();

	protected abstract int doDamageArmor();
}
