package utils;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Stack;

public class MapBuilder extends Builder {

    private ArenaMap map;

    public MapBuilder(ArenaMap map) {
        this.map = map;
    }

    @Override
    public ArenaMap getBuildable() {
        return this.map;
    }

    @Override
    public Builder Build(boolean background) {
        if (background) {
            return this.makeBackground().makeLava().makeWater().makeBorders().makeMaze();
        } else {
            return this.makeLava().makeWater().makeBorders().makeMaze();
        }
    }

    public MapBuilder makeBorders() {
        int edge = this.map.edge;
        for (int i = 0; i < edge; i++) {
            for (int j = 0; j < edge; j++) {
                if (i == 0 || j  == 0 || i+1 == edge || j+1 == edge) {
                    this.map.map[i][j].value = Utils.MAP_BORDER;
                    this.map.map[i][j].obstacle = true;
                }
            }
        }
        return this;
    }

    public MapBuilder makeBackground() {
        for (int i = 0; i < this.map.edge; i++) {
            for (int j = 0; j < this.map.edge; j++) {
                this.map.background[i][j].value = Utils.MAP_EMPTY;
            }
        }
        return this;
    }

    public MapBuilder makeLava() {
        double dist, prob;
        int count = map.edge / 10;
        int[] lava_x = new int[count];
        int[] lava_y = new int[count];
        for (int i = 0; i < 3; i++) {
            lava_x[i] = Utils.GetRand(map) % map.edge;
            lava_y[i] = Utils.GetRand(map) % map.edge;
        }
        for(int i = 0; i < map.edge; i++) {
            for(int j = 0; j < map.edge; j++) {
                for(int k = 0; k < count; k++) {
                    dist = Math.sqrt(Math.pow(lava_y[k]-i, 2) + Math.pow(lava_x[k]-j, 2));
                    prob = 0.5 / (dist*dist + 0.0001);
                    if (prob * 1000 > (double)(Utils.GetRand(map) % 1000)) {
                        map.map[j][i].value = Utils.MAP_LAVA;
                    }
                }
            }
        }

        return this;
    }

    public MapBuilder makeWater() {
        double dist, prob;
        int count = map.edge / 10;
        int[] lava_x = new int[count];
        int[] lava_y = new int[count];
        for (int i = 0; i < 3; i++) {
            lava_x[i] = Utils.GetRand(map) % map.edge;
            lava_y[i] = Utils.GetRand(map) % map.edge;
        }
        for(int i = 0; i < map.edge; i++) {
            for(int j = 0; j < map.edge; j++) {
                for(int k = 0; k < count; k++) {
                    dist = Math.sqrt(Math.pow(lava_y[k]-i, 2) + Math.pow(lava_x[k]-j, 2));
                    prob = 0.4 / (dist*dist + 0.0001);
                    if (prob * 1000 > (double)(Utils.GetRand(map) % 1000)) {
                        map.map[j][i].value = Utils.MAP_WATER;
                    }
                }
            }
        }

        return this;
    }


    private boolean CheckAvailable(int ox, int oy, int nx, int ny)
    {
        int dx = ox - nx;
        int dy = oy - ny;

        return (dx != 0 &&
            map.map[ny-1][nx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny][nx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny+1][nx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny-1][nx+dx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny][nx+dx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny+1][nx+dx].value > Utils.MAP_NON_OBSTACLE) ||
        (dy != 0 &&
            map.map[ny][nx-1].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny][nx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny][nx+1].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny+dy][nx-1].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny+dy][nx].value > Utils.MAP_NON_OBSTACLE &&
            map.map[ny+dy][nx+1].value > Utils.MAP_NON_OBSTACLE);
    }


    private boolean Step(Stack<Integer> st)
    {
        int st_last  = 0;
        boolean status = false;

        if (!st.empty()) {
            st_last = st.peek();
        } else {
            return false;
        }

        while (!st.empty()) {
            int y = (st_last / map.edge);
            int x = (st_last % map.edge);

            int rot = Utils.GetRand(map) & 3; //takes 2 last bits
            if ((rot & 1) == 0) { //additional randomness(reverse rotation)
                rot = (rot + 2) & 3;
            }

            for (int i = 0; i < 4; i++) {
                switch(rot) {
                    case 0:
                        status = CheckAvailable(x-1, y, x-2, y);
                        if (status) {
                            st.push(st_last -1);
                            map.map[y][x-1].value =  Utils.MAP_WALL;
                            map.map[y][x-1].obstacle = true;
                            return true;
                        }
                        break;
                    case 1:
                        status = CheckAvailable(x, y-1, x, y-2);
                        if (status){
                            st.push(st_last-map.edge);
                            map.map[y-1][x].value =  Utils.MAP_WALL;
                            map.map[y-1][x].obstacle = true;
                            return true;
                        }
                        break;
                    case 2:
                        status = CheckAvailable(x+1, y, x+2, y);
                        if (status) {
                            st.push(st_last+1);
                            map.map[y][x+1].value =  Utils.MAP_WALL;
                            map.map[y][x+1].obstacle = true;
                            return true;
                        }
                        break;
                    case 3:
                        status = CheckAvailable(x, y+1, x, y+2);
                        if (status) {
                            st.push(st_last+map.edge);
                            map.map[y+1][x].value =  Utils.MAP_WALL;
                            map.map[y+1][x].obstacle = true;
                            return true;
                        }
                        break;
                }
                rot = (rot+1) % 4;
            }
            st_last = st.pop();
        }
        return false;
    }

    public MapBuilder makeMaze() {
        boolean changed = true;
        boolean status = false;

        ArrayList<Stack<Integer>> stArr = new ArrayList<Stack<Integer>>();

        for (int i = 0; i < map.edge; i++) {
            Stack<Integer> s = new Stack<Integer>();
            s.push((map.edge * (Utils.GetRand(map) % (map.edge - 4) + 2) + (Utils.GetRand(map) % (map.edge - 4) + 2)));
            stArr.add(s);
        }

        for(int i = 0; i < stArr.size(); i++) {
            map.map[stArr.get(i).peek() / map.edge][(int)stArr.get(i).peek() % map.edge].value =  Utils.MAP_WALL;
            map.map[stArr.get(i).peek() / map.edge][(int)stArr.get(i).peek() % map.edge].obstacle = true;
        }

        while (changed) {
            changed = false;
            for (int i = 0; i < stArr.size(); i++) {
                status = Step(stArr.get(i));
                changed = status || changed;
            }
        }

        return this;
    }
}
