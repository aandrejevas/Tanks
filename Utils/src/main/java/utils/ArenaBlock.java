package utils;

public class ArenaBlock {
	public int x, y;
	public byte value;
	public byte debugValue;
	public byte defValue;
	public Drop drop;
	public boolean obstacle;

	public ArenaBlock(final int x, final int y) {
		this.x = x;
		this.y = y;
		this.value = Utils.MAP_EMPTY;
		this.debugValue = 0;
		this.defValue = Utils.MAP_EMPTY;
		this.obstacle = false;
		this.drop = null;
	}
}
