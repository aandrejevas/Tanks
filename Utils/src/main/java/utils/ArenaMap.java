package utils;

import utils.Iterator.Iterate;
import utils.Iterator.MIterator;

public class ArenaMap implements Iterate {

	public final int edge;
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

	public void setBlock(final ArenaBlock block) {
		this.map[block.y][block.x] = block;
	}

	public void setDefBlock(final ArenaBlock block) {
		this.background[block.y][block.x] = block;
	}

	public boolean isObstacle(final int x, final int y) {
		return map[y][x].value < Utils.MAP_NON_OBSTACLE;
	}

	public byte getBlockValue(final int x, final int y) {
		return map[y][x].value;
	}

	public byte getBlockValue(int[] cord) {
		return map[cord[0]][cord[1]].value;
	}

	public boolean hasPlayer(final int x, final int y) {
		byte val = map[y][x].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_TIGER;
	}

	public boolean hasAlly(final int x, final int y) {
		byte val = map[y][x].value;
		return val >= Utils.MAP_PLAYER && val <= Utils.MAP_SHERMAN;
	}

	public boolean hasEnemy(final int x, final int y) {
		return map[y][x].value == Utils.MAP_TIGER;
	}

	@Override
	public ArenaMapIterator createIterator() {
		return new ArenaMapIterator();
	}

	private class ArenaMapIterator implements MIterator<ArenaBlock> {
		private int i = 0, j = 0;

		@Override
		public boolean hasNext() {
			return edge > i * j;
		}

		@Override
		public ArenaBlock nextIJ() {
			if (j < edge-1){
				j++;
			}else if(i < edge){
				i++;
				j = 0;
			}
			return map[i][j];
		}

		@Override
		public void reset() {
			i = 0;
			j = 0;
		}

		@Override
		public int keyI() {
			return i;
		}

		@Override
		public int keyJ() {
			return j;
		}

		@Override
		public ArenaBlock value() {
			return map[i][j];
		}
	}
}
