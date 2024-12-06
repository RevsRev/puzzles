package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class D06 extends AocProblem {

    private static final char START_CHAR = '^';
    private static final char OBSTACLE_CHAR = '#';
    private static final int[][] DIRECTIONS = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}};

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 6);
    }

    @Override
    protected long partOneImpl() {
        char[][] map = loadResourcesAsCharArray();
        int[] position = findStart(map);
        int dirIndex = 0;

        Map<Pair<Integer,Integer>, Set<Integer>> visited = new HashMap<>();
        traverse(map, position, visited, dirIndex);
        return visited.size();
    }

    @Override
    protected long partTwoImpl() {
        char[][] map = loadResourcesAsCharArray();
        int[] position = findStart(map);

        int height = map.length;
        int width = map[0].length;
        int dirIndex = 0;

        Map<Pair<Integer,Integer>, Set<Integer>> visited = new HashMap<>();
        traverse(map, position, visited, dirIndex);

        int count = 0;
        for (Pair<Integer,Integer> path : visited.keySet()) {
            Integer i = path.getLeft();
            Integer j = path.getRight();
            if (i == position[0] && j.equals(position[1])) {
                continue;
            }
            char c = map[i][j];
            map[i][j] = OBSTACLE_CHAR;
            if (traverse(map, position, new HashMap<>(), dirIndex)) {
                count++;
            }
            map[i][j] =c;
        }
        return count;
    }

    /**
     * Return true if stuck in a loop
     */
    private static boolean traverse(char[][] map, int[] startPosition,
                                    Map<Pair<Integer,Integer>, Set<Integer>> visited,
                                    int dirIndex) {
        int[] position = new int[]{startPosition[0], startPosition[1]};
        int height = map.length;
        int width = map[0].length;

        while (position[0] >= 0 && position[0] < height && position[1] >= 0 && position[1] < width) {
            Pair<Integer, Integer> key = Pair.of(position[0], position[1]);
            Set<Integer> visitedOrientations = visited.computeIfAbsent(key, k -> new HashSet<>());
            if (visitedOrientations.contains(dirIndex)) {
                return true;
            } else {
                visitedOrientations.add(dirIndex);
            }

            int[] direction = DIRECTIONS[dirIndex];
            int nextI  = position[0] + direction[0];
            int nextJ = position[1] + direction[1];
            if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
                break;
            }
            if (map[nextI][nextJ] != OBSTACLE_CHAR) {
                map[position[0]][position[1]] = 'X'; //debug
                position[0] = nextI;
                position[1] = nextJ;
            } else {
                dirIndex = (dirIndex + 1) % 4;
            }
        }
        return false;
    }

    private int[] findStart(char[][] map) {
        int height = map.length;
        int width = map[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j] == START_CHAR) {
                    return new int[]{i,j};
                }
            }
        }
        throw new RuntimeException("Badly defined problem");
    }
}
