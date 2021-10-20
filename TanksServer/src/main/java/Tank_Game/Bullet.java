package Tank_Game;

import utils.Utils;

public abstract class Bullet {

	public static class Left extends Bullet {
		public Left(final Tank tank) {
			x = tank.getX() - 1;
			y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, index, x, y);
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[x - 1][y].obstacle) {
				return true;
			} else {
				--x;
				Main.this_server.write(Utils.BULLET_LEFT, index);
				return false;
			}
		}
	}

	public static class Right extends Bullet {
		public Right(final Tank tank) {
			x = tank.getX() + 1;
			y = tank.getY();
			Main.this_server.write(Utils.ADD_BULLET, index, x, y);
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[x + 1][y].obstacle) {
				return true;
			} else {
				++x;
				Main.this_server.write(Utils.BULLET_RIGHT, index);
				return false;
			}
		}
	}

	public static class Up extends Bullet {
		public Up(final Tank tank) {
			x = tank.getX();
			y = tank.getY() - 1;
			Main.this_server.write(Utils.ADD_BULLET, index, x, y);
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[x][y - 1].obstacle) {
				return true;
			} else {
				--y;
				Main.this_server.write(Utils.BULLET_UP, index);
				return false;
			}
		}
	}

	public static class Down extends Bullet {
		public Down(final Tank tank) {
			x = tank.getX();
			y = tank.getY() + 1;
			Main.this_server.write(Utils.ADD_BULLET, index, x, y);
		}

		@Override
		protected boolean moveImpl() {
			if (Main.map.map[x][y + 1].obstacle) {
				return true;
			} else {
				++y;
				Main.this_server.write(Utils.BULLET_DOWN, index);
				return false;
			}
		}
	}

	private static int count = 0;

	private final long timeout = 100_000_000;
	private long start_time;
	public final int index;
	protected int x, y;

	public Bullet() {
		index = count++;
		start_time = System.nanoTime();
	}

	public boolean move() {
		if (System.nanoTime() - start_time > timeout) {
			final boolean state = moveImpl();
			start_time = System.nanoTime();
			return state;
		}
		return false;
	}

	protected abstract boolean moveImpl();
}
