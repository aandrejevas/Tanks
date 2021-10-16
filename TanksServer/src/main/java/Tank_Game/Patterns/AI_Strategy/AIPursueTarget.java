package Tank_Game.Patterns.AI_Strategy;

import Tank_Game.Main;
import Tank_Game.Patterns.Factory.AI_Player;
import Tank_Game.Patterns.Factory.PlayerCreator;
import Tank_Game.Patterns.Strategy.MoveDown;
import Tank_Game.Patterns.Strategy.MoveLeft;
import Tank_Game.Patterns.Strategy.MoveRight;
import Tank_Game.Patterns.Strategy.MoveUp;
import Tank_Game.Tank;
import processing.core.PApplet;
import utils.ArenaBlock;
import utils.Utils;

import java.util.*;

import static processing.core.PApplet.println;
import static utils.Utils.*;

public class AIPursueTarget extends AIAlgorithm
{
    @Override
    public void perform(AI_Player ai) {
//        println("AI pursue");
        if (ai.path.empty()) {
            boolean[][] vis = new boolean[Main.map.edge][Main.map.edge];
            ai.path = BFS(Main.map.map, vis, toCord(ai.cord), ai.pursueTarget);
        }

        ai.state |= AI_Player.AI_PURSUING;
    }

    static int[] dRow = { -1, 0, 1, 0 };
    static int[] dCol = { 0, 1, 0, -1 };

    static boolean isValid(ArenaBlock[][] grid, boolean[][] vis, int y, int x) {
        if (y < 0 || x < 0 || y >= Main.map.edge || x >= Main.map.edge) {
            return false;
        }

        byte val = Main.map.getBlockValue(x, y);
        if (val < MAP_NON_OBSTACLE) {
            return false;
        }

        if (vis[y][x]) {
            return false;
        }

        return true;
    }

    private boolean cordEqual(Integer[] a, Integer[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }

    private Integer[] toCord(int idx) {
        Integer[] arr = new Integer[2];
        arr[0] = idx % Main.map.edge;
        arr[1] = idx / Main.map.edge;
        return arr;
    }

    private Integer[] toCord(int[] def) {
        Integer[] arr = new Integer[2];
        arr[0] = def[0];
        arr[1] = def[1];
        return arr;
    }

    private Integer[] toCord(int x, int y) {
        Integer[] arr = new Integer[2];
        arr[0] = x;
        arr[1] = y;
        return arr;
    }

    private int toIdx(Integer[] arr) {
        return Main.map.edge*arr[1] + arr[0];
    }

    private int toIdx(int x, int y) {
        return Main.map.edge*y + x;
    }


    private Stack<Integer[]> BFS(ArenaBlock[][] grid, boolean[][] vis, Integer[] start, int[] target) {
        int e = Main.map.edge;
//        Cord[][] map = new Cord[e][e];
        Integer[] map = new Integer[e*e];
        int adjx = -1;
        int adjy = -1;
        Queue<Integer[] > q = new LinkedList<>();

        q.add(toCord(start[0], start[1]));
        map[toIdx(start)] = toIdx(start);

        vis[start[1]][start[0]] = true;

        while (!q.isEmpty()) {
            Integer[] cell = q.peek();
            int x = cell[0];
            int y = cell[1];
            q.remove();

            for(int i = 0; i < 4; i++) {
                adjx = x + dCol[i];
                adjy = y + dRow[i];

                if (isValid(grid, vis, adjy, adjx)) {
                    q.add(toCord(adjx, adjy));
                    map[toIdx(adjx, adjy)] = toIdx(x, y);
                    vis[adjy][adjx] = true;

                }

                if (target[0] == adjx && target[1] == adjy) {
                    return mapToPath(map, start, toCord(x, y));
                }
            }
        }
        return mapToPath(map, start, toCord(adjx, adjy));
    }

    private Stack<Integer[]> mapToPath(Integer[] map, Integer[] base, Integer[] dest) {
        Integer[] cord = toCord(dest[0], dest[1]);
        int idx = toIdx(cord);
        Stack<Integer[]> stack = new Stack<Integer[]>();

        while(!cordEqual(base, cord)) {
            if (idx == map[idx]) {
                break;
            }
            stack.push(cord);
            idx = map[idx];
            cord = toCord(idx);
        }

        return stack;
    }

    private int menhadenDist(int[] from, int[] to) {
        return Math.abs(from[0] - to[0]) + Math.abs(from[1] - to[1]);
    }

    private boolean isClearSight(int[] from, int[] to) {
        return from[0] == to[0] || from[1] == to[1];
    }
}
