package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MapBackBuilder implements Builder {

	private final ArenaMap map;

	public MapBackBuilder(final ArenaMap map) {
		this.map = map;
	}

	@Override
	public ArenaMap getBuildable() {
		return this.map;
	}

	@Override
	public Builder build() {
		return this.makeLava().makeWater().makeBorders().makeMaze();
	}

	public MapBackBuilder makeBorders() {
		int edge = this.map.edge;
		for (int i = 0; i < edge; i++) {
			for (int j = 0; j < edge; j++) {
				if (i == 0 || j == 0 || i + 1 == edge || j + 1 == edge) {
					this.map.map[i][j].value = Utils.MAP_BORDER;
					this.map.map[i][j].obstacle = true;
				}
			}
		}
		return this;
	}

	public MapBackBuilder makeLava() {
		double dist, prob;
		final int count = map.edge / 10;
		final int[] lava_x = new int[count];
		final int[] lava_y = new int[count];
		for (int i = 0; i < count; i++) {
			lava_x[i] = Utils.random.nextInt(map.edge);
			lava_y[i] = Utils.random.nextInt(map.edge);
		}
		for (int i = 0; i < map.edge; i++) {
			for (int j = 0; j < map.edge; j++) {
				for (int k = 0; k < count; k++) {
					dist = Math.sqrt(Math.pow(lava_y[k] - i, 2) + Math.pow(lava_x[k] - j, 2));
					prob = 0.5 / (dist * dist + 0.0001);
					if (prob * 1000 > (double)(Utils.random.nextInt(1000))) {
						map.map[j][i].value = Utils.MAP_LAVA;
						map.map[j][i].defValue = Utils.MAP_LAVA;
					}
				}
			}
		}

		return this;
	}

	public MapBackBuilder makeWater() {
		double dist, prob;
		final int count = map.edge / 10;
		final int[] lava_x = new int[count];
		final int[] lava_y = new int[count];
		for (int i = 0; i < count; i++) {
			lava_x[i] = Utils.random.nextInt(map.edge);
			lava_y[i] = Utils.random.nextInt(map.edge);
		}
		for (int i = 0; i < map.edge; i++) {
			for (int j = 0; j < map.edge; j++) {
				for (int k = 0; k < count; k++) {
					dist = Math.sqrt(Math.pow(lava_y[k] - i, 2) + Math.pow(lava_x[k] - j, 2));
					prob = 0.4 / (dist * dist + 0.0001);
					if (prob * 1000 > (double)Utils.random.nextInt(1000)) {
						map.map[j][i].value = Utils.MAP_WATER;
						map.map[j][i].defValue = Utils.MAP_WATER;
					}
				}
			}
		}

		return this;
	}

	private boolean checkAvailable(final int ox, final int oy, final int nx, final int ny) {
		final int dx = ox - nx;
		final int dy = oy - ny;

		return (dx != 0
			&& map.map[ny - 1][nx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny][nx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny + 1][nx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny - 1][nx + dx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny][nx + dx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny + 1][nx + dx].value > Utils.MAP_NON_OBSTACLE)
			|| (dy != 0
			&& map.map[ny][nx - 1].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny][nx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny][nx + 1].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny + dy][nx - 1].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny + dy][nx].value > Utils.MAP_NON_OBSTACLE
			&& map.map[ny + dy][nx + 1].value > Utils.MAP_NON_OBSTACLE);
	}

	private boolean step(final Stack<Integer> st) {
		int st_last;
		boolean status;

		if (!st.empty()) {
			st_last = st.peek();
		} else {
			return false;
		}

		while (!st.empty()) {
			int y = (st_last / map.edge);
			int x = (st_last % map.edge);

			int rot = Utils.random.nextInt() & 3; //takes 2 last bits
			if ((rot & 1) == 0) { //additional randomness(reverse rotation)
				rot = (rot + 2) & 3;
			}

			for (int i = 0; i < 4; i++) {
				switch (rot) {
					case 0:
						status = checkAvailable(x - 1, y, x - 2, y);
						if (status) {
							st.push(st_last - 1);
							map.map[y][x - 1].value = Utils.MAP_WALL;
							map.map[y][x - 1].obstacle = true;
							return true;
						}
						break;
					case 1:
						status = checkAvailable(x, y - 1, x, y - 2);
						if (status) {
							st.push(st_last - map.edge);
							map.map[y - 1][x].value = Utils.MAP_WALL;
							map.map[y - 1][x].obstacle = true;
							return true;
						}
						break;
					case 2:
						status = checkAvailable(x + 1, y, x + 2, y);
						if (status) {
							st.push(st_last + 1);
							map.map[y][x + 1].value = Utils.MAP_WALL;
							map.map[y][x + 1].obstacle = true;
							return true;
						}
						break;
					case 3:
						status = checkAvailable(x, y + 1, x, y + 2);
						if (status) {
							st.push(st_last + map.edge);
							map.map[y + 1][x].value = Utils.MAP_WALL;
							map.map[y + 1][x].obstacle = true;
							return true;
						}
						break;
				}
				rot = (rot + 1) % 4;
			}
			st_last = st.pop();
		}
		return false;
	}

	public MapBackBuilder makeMaze() {
		boolean changed = true;
		boolean status;

		final List<Stack<Integer>> stArr = new ArrayList<>();

		for (int i = 0; i < map.edge; i++) {
			final Stack<Integer> s = new Stack<>();
			s.push((map.edge * (Utils.random.nextInt(map.edge - 4) + 2) + (Utils.random.nextInt(map.edge - 4) + 2)));
			stArr.add(s);
		}

		for (int i = 0; i < stArr.size(); i++) {
			map.map[stArr.get(i).peek() / map.edge][(int)stArr.get(i).peek() % map.edge].value = Utils.MAP_WALL;
			map.map[stArr.get(i).peek() / map.edge][(int)stArr.get(i).peek() % map.edge].obstacle = true;
		}

		while (changed) {
			changed = false;
			for (int i = 0; i < stArr.size(); i++) {
				status = step(stArr.get(i));
				changed = status || changed;
			}
		}

		return this;
	}
}
