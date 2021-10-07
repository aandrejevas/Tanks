package utils;

public class ArenaMap {

    public int seed;
    public int edge;
    public ArenaBlock[][] map;
    public ArenaBlock[][] background;

    public ArenaMap(int seed, int edge, boolean def) {
        this.seed = seed;
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


}
