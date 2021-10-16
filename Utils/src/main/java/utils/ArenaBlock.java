package utils;

public class ArenaBlock {
    public int x, y;
    public byte value;
    public byte debugValue;
    public byte defValue;
    public Drop drop;
    public boolean obstacle;

    public ArenaBlock(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = Utils.MAP_EMPTY;
        this.debugValue = Utils.MAP_EMPTY;
        this.defValue = Utils.MAP_EMPTY;
        this.obstacle = false;
        this.drop = null;
    }
}
