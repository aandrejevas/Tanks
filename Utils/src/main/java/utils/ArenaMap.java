package utils;

public class ArenaMap {

	public int edge;
	public final ArenaBlock[][] map;
	public final ArenaBlock[][] background;

	public ArenaMap(final int edge, final boolean def) {
		this.edge = edge;

		this.map = new ArenaBlock[edge][edge];
		this.background = new ArenaBlock[edge][edge];
		if (def) {
			for (int i = 0; i < edge; i++) {
				for (int j = 0; j < edge; j++) {
					this.map[j][i] = new ArenaBlock(j, i);
					this.background[j][i] = new ArenaBlock(j, i);
				}
			}
		}
	}

	public void setBlock(ArenaBlock block) {
		this.map[block.y][block.x] = block;
	}

	public void setDefBlock(ArenaBlock block) {
		this.background[block.y][block.x] = block;
	}

	public boolean isObstacle(int x, int y) {
		return map[y][x].value < Utils.MAP_NON_OBSTACLE;
	}

	public byte getBlockValue(int x, int y) {
		return map[y][x].value;
	}

	public byte getBlockValue(int[] cord) {
		return map[cord[1]][cord[0]].value;
	}

	public boolean hasPlayer(int x, int y) {
		byte val = map[y][x].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_TIGER;
	}

	public boolean hasPlayer(int[] cord) {
		byte val = map[cord[1]][cord[0]].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_TIGER;
	}

	public boolean hasAlly(int x, int y) {
		byte val = map[y][x].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_SHERMAN;
	}

	public boolean hasAlly(int[] cord) {
		byte val = map[cord[1]][cord[0]].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_SHERMAN;
	}

	public boolean hasEnemy(int x, int y) {
		return map[y][x].value == Utils.MAP_TIGER;
	}

	public boolean hasEnemy(int[] cord) {
		return map[cord[1]][cord[0]].value == Utils.MAP_TIGER;
	}

}
