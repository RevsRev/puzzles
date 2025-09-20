package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.parse.LoaderUtils;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class D15 {

    public static final char ROBOT_CHAR = '@';

    @SuppressWarnings("checkstyle:MagicNumber")
    private static final Map<Character, int[]> DIRECTIONS = new HashMap<>() {{
        put('^', new int[]{-1, 0});
        put('>', new int[]{0, 1});
        put('v', new int[]{1, 0});
        put('<', new int[]{0, -1});
    }};
    private static final Map<Character, Character[]> EXPANDED_CHARS = new HashMap<>() {{
        put('#', new Character[]{'#', '#'});
        put('O', new Character[]{'[', ']'});
        put('.', new Character[]{'.', '.'});
        put('@', new Character[]{'@', '.'});
    }};

    @SuppressWarnings("checkstyle:MagicNumber")
    private static boolean canMove(final char[][] warehouse, int i, int j, final int[] dir, boolean checkingNeighbour) {
        char nextChar = warehouse[i + dir[0]][j + dir[1]];
        if (nextChar == '#') {
            return false;
        }

        char cellChar = warehouse[i][j];
        boolean upOrDown = dir[0] != 0;
        if (!upOrDown || cellChar == '@') {
            if (nextChar == '.') {
                return true;
            }
            return canMove(warehouse, i + dir[0], j + dir[1], dir, false);
        }

        int neighbourDir = cellChar == '[' ? 1 : -1;
        if (checkingNeighbour) {
            if (nextChar == '.') {
                return true;
            }
            return canMove(warehouse, i + dir[0], j, dir, false);
        }
        //Check I can move my neighbour up/down and I can also move up/down
        return (nextChar == '.' || canMove(warehouse, i + dir[0], j, dir, false)) && canMove(warehouse, i,
                j + neighbourDir, dir, true);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static void move(final char[][] warehouse, int i, int j, final int[] dir, boolean movingNeighbour) {
        char cellChar = warehouse[i][j];
        if (cellChar == '#' || cellChar == '.') {
            return; //we've reached the end of a movement
        }

        boolean upOrDown = dir[0] != 0;
        if (upOrDown && !movingNeighbour) {
            if (cellChar == '[') {
                move(warehouse, i, j + 1, dir, true);
            }
            if (cellChar == ']') {
                move(warehouse, i, j - 1, dir, true);
            }
        }

        move(warehouse, i + dir[0], j + dir[1], dir, false);
        warehouse[i + dir[0]][j + dir[1]] = cellChar;
        warehouse[i][j] = '.';
    }

    @AocProblemI(year = 2024, day = 15, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Pair<char[][], char[]> warehouseAndRobotMoves = loadCoordinatesAndMoves(resourceLoader);
        char[][] warehouse = warehouseAndRobotMoves.getLeft();
        char[] moves = warehouseAndRobotMoves.getRight();

        int[] robotPos = findRobotPosition(warehouse);
        for (int moveI = 0; moveI < moves.length; moveI++) {
            char move = moves[moveI];
            int[] dir = DIRECTIONS.get(move);
            int shiftEnd = 1;
            while (warehouse[robotPos[0] + shiftEnd * dir[0]][robotPos[1] + shiftEnd * dir[1]] == 'O') {
                shiftEnd++;
            }
            if (warehouse[robotPos[0] + shiftEnd * dir[0]][robotPos[1] + shiftEnd * dir[1]] == '#') {
                continue;
            }
            while (shiftEnd >= 1) {
                warehouse[robotPos[0] + shiftEnd * dir[0]][robotPos[1] + shiftEnd * dir[1]] =
                        warehouse[robotPos[0] + (shiftEnd - 1) * dir[0]][robotPos[1] + (shiftEnd - 1) * dir[1]];
                shiftEnd--;
            }
            warehouse[robotPos[0]][robotPos[1]] = '.';
            robotPos[0] = robotPos[0] + dir[0];
            robotPos[1] = robotPos[1] + dir[1];
        }

        return computeGpsSum(warehouse);
    }

    @AocProblemI(year = 2024, day = 15, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        Pair<char[][], char[]> warehouseAndRobotMoves = loadCoordinatesAndMoves(resourceLoader);
        char[][] warehouse = expand(warehouseAndRobotMoves.getLeft());
        char[] moves = warehouseAndRobotMoves.getRight();

        int[] robotPos = findRobotPosition(warehouse);
        for (int moveI = 0; moveI < moves.length; moveI++) {
            char move = moves[moveI];
            int[] dir = DIRECTIONS.get(move);
            if (canMove(warehouse, robotPos[0], robotPos[1], dir, false)) {
                move(warehouse, robotPos[0], robotPos[1], dir, false);
                robotPos[0] += dir[0];
                robotPos[1] += dir[1];
            }
        }
        return computeGpsSum(warehouse);
    }

    private long computeGpsSum(final char[][] warehouse) {
        long gpsCoordinateSum = 0;
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[0].length; j++) {
                char c = warehouse[i][j];
                if (c == 'O' || c == '[') {
                    gpsCoordinateSum += gpsCoordinate(i, j);
                }
            }
        }
        return gpsCoordinateSum;
    }

    private char[][] expand(final char[][] unexpanded) {
        int height = unexpanded.length;
        int width = unexpanded[0].length;
        char[][] expanded = new char[height][2 * width + 1];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = unexpanded[i][j];
                Character[] replacements = EXPANDED_CHARS.get(c);
                expanded[i][2 * j] = replacements[0];
                expanded[i][2 * j + 1] = replacements[1];
            }
        }
        return expanded;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private long gpsCoordinate(int i, int j) {
        return 100L * i + j;
    }

    private int[] findRobotPosition(final char[][] warehouse) {
        for (int i = 0; i < warehouse.length; i++) {
            for (int j = 0; j < warehouse[0].length; j++) {
                if (warehouse[i][j] == ROBOT_CHAR) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private Pair<char[][], char[]> loadCoordinatesAndMoves(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> strings = resourceLoader.resources();
        int blankLineIndex = 0;
        while (blankLineIndex < strings.size() && !strings.get(blankLineIndex).trim().matches("")) {
            blankLineIndex++;
        }

        List<String> warehouse = strings.subList(0, blankLineIndex);
        List<String> moves = strings.subList(blankLineIndex + 1, strings.size());
        return Pair.of(LoaderUtils.linesToCharMatrix(warehouse), LoaderUtils.linesToCharArray(moves));

    }
}
