package utils;

import processing.core.PShape;
import utils.Utils;

public class ArenaBlock {
    public int x, y;
    public byte value;

    public ArenaBlock(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = Utils.MAP_EMPTY;
    }
}
