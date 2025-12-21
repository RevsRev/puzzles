package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.parse.LoaderUtils;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.utils.arr.CellDirection;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rev.puzzles.utils.arr.CellDirection.UP;

public final class D06 {

    public static final char EMPTY_CHAR = '.';
    private static final char START_CHAR = '^';
    private static final char OBSTACLE_CHAR = '#';
    private static final char NEW_OBSTACLE_CHAR = 'O';
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    @SuppressWarnings("checkstyle:MagicNumber")
    private static final int VISIT_UP_FLAG = 1 << 27;
    private static final int VISIT_RIGHT_FLAG = VISIT_UP_FLAG << 1;
    private static final int VISIT_DOWN_FLAG = VISIT_RIGHT_FLAG << 1;
    private static final int VISIT_LEFT_FLAG = VISIT_DOWN_FLAG << 1;

    private static final int START_INT = START_CHAR;
    private static final int EMPTY_INT = EMPTY_CHAR;
    private static final int OBSTACLE_INT = OBSTACLE_CHAR;
    private static final int NEW_OBSTACLE_INT = NEW_OBSTACLE_CHAR;

    @SuppressWarnings("checkstyle:ParameterNumber")
    private static void traverseWithoutLoops(final int[][] map, final int height, final int width, final int i,
                                             final int j, final CellDirection startDir,
                                             final Set<Pair<Integer, Integer>> visited) {
        visited.add(Pair.of(i, j));
        int nextI = i + startDir.getI();
        int nextJ = j + startDir.getJ();

        if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
            return;
        }

        if (map[nextI][nextJ] == NEW_OBSTACLE_INT || map[nextI][nextJ] == OBSTACLE_INT) {
            traverseWithoutLoops(map, height, width, i, j, startDir.next(), visited);
            return;
        }
        traverseWithoutLoops(map, height, width, nextI, nextJ, startDir, visited);
    }

    @SuppressWarnings("checkstyle:ParameterNumber")
    private static boolean traverseWithLoops(final int[][] map, final int height, final int width, final int i,
                                             final int j, final CellDirection startDir, final boolean placedObstacle,
                                             final Set<Pair<Integer, Integer>> loopObsaclePositions) {
        int visitedFlag = getFlag(startDir);
        if ((map[i][j] & visitedFlag) == visitedFlag) {
            return true;
        }
        map[i][j] |= visitedFlag;

        int nextI = i + startDir.getI();
        int nextJ = j + startDir.getJ();

        if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
            map[i][j] = map[i][j] & (Integer.MAX_VALUE ^ visitedFlag);
            return false;
        }

        if (map[nextI][nextJ] == NEW_OBSTACLE_INT || map[nextI][nextJ] == OBSTACLE_INT) {
            boolean found =
                    traverseWithLoops(map, height, width, i, j, startDir.next(), placedObstacle, loopObsaclePositions);
            map[i][j] = map[i][j] & (Integer.MAX_VALUE ^ visitedFlag);
            return found;
        }

        boolean found = false;
        if (!placedObstacle
                && (map[nextI][nextJ] & (VISIT_UP_FLAG | VISIT_RIGHT_FLAG | VISIT_LEFT_FLAG | VISIT_DOWN_FLAG)) == 0) {
            map[nextI][nextJ] = NEW_OBSTACLE_INT;
            if (traverseWithLoops(map, height, width, i, j, startDir.next(), true, loopObsaclePositions)) {
                found = true;
                loopObsaclePositions.add(Pair.of(nextI, nextJ));
            }
            map[nextI][nextJ] = EMPTY_INT;
        }
        found |= traverseWithLoops(map, height, width, nextI, nextJ, startDir, placedObstacle, loopObsaclePositions);
        map[i][j] = map[i][j] & (Integer.MAX_VALUE ^ visitedFlag);
        return found;
    }

    private static int getFlag(final CellDirection dir) {
        switch (dir) {
            case DOWN -> {
                return VISIT_DOWN_FLAG;
            }
            case RIGHT -> {
                return VISIT_RIGHT_FLAG;
            }
            case UP -> {
                return VISIT_UP_FLAG;
            }
            default -> {
                return VISIT_LEFT_FLAG;
            }
        }
    }

    private static void throwProblemExecutionException(final StackOverflowError e) {
        String errorMessage = "JVM stacksize too small. Set -Xss2M or higher";
        throw new ProblemExecutionException(errorMessage, e);
    }

    @AocProblemI(year = 2024, day = 6, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        try {
            int[][] map = LoaderUtils.loadResourcesAsIntMatrix(resourceLoader.resources(), s -> s.split(""),
                    s -> (int) s.charAt(0));
            int[] start = LoaderUtils.findOne(map, START_INT);

            int height = map.length;
            int width = map[0].length;

            Set<Pair<Integer, Integer>> visited = new HashSet<>();
            traverseWithoutLoops(map, height, width, start[0], start[1], UP, visited);
            return (long) visited.size();
        } catch (StackOverflowError e) {
            throwProblemExecutionException(e);
        }
        return 0L;
    }

    @AocProblemI(year = 2024, day = 6, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        try {
            int[][] map = LoaderUtils.loadResourcesAsIntMatrix(resourceLoader.resources(), s -> s.split(""),
                    s -> (int) s.charAt(0));
            int[] start = LoaderUtils.findOne(map, START_INT);

            int width = map[0].length;

            int i = start[0];
            int j = start[1];

            int height = map.length;

            Set<Pair<Integer, Integer>> blockingPositions = new HashSet<>();
            traverseWithLoops(map, height, width, i, j, UP, false, blockingPositions);
            return (long) blockingPositions.size();
        } catch (StackOverflowError e) {
            throwProblemExecutionException(e);
        }
        return 0L;
    }
}
