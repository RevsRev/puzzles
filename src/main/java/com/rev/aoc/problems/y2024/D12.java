package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

public final class D12 extends AocProblem {
    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 12);
    }

    @Override
    protected long partOneImpl() {
        char[][] plots = loadResourcesAsCharMatrix();
        Set<Pair<Integer, Integer>> unvisited = new HashSet<>();

        int height = plots.length;
        int width = plots[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                unvisited.add(Pair.of(i, j));
            }
        }
        char[][] borders = new char[2 * height + 1][2 * width + 1];

        long score = 0;
        while (!unvisited.isEmpty()) {
            Pair<Integer, Integer> seed = unvisited.iterator().next();
            Integer i = seed.getLeft();
            Integer j = seed.getRight();
            Pair<Integer, Integer> perimeterAndArea =
                    calcPerimeterAndArea(plots, unvisited, width, height, i, j, borders);
            score += perimeterAndArea.getLeft() * perimeterAndArea.getRight();
        }
        return score;
    }


    @Override
    protected long partTwoImpl() {
        char[][] plots = loadResourcesAsCharMatrix();
        Set<Pair<Integer, Integer>> unvisited = new HashSet<>();

        int height = plots.length;
        int width = plots[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                unvisited.add(Pair.of(i, j));
            }
        }

        long score = 0;
        while (!unvisited.isEmpty()) {
            Pair<Integer, Integer> seed = unvisited.iterator().next();
            Integer i = seed.getLeft();
            Integer j = seed.getRight();
            char[][] borders = new char[2 * height + 1][2 * width + 1];
            Pair<Integer, Integer> pAndA = calcPerimeterAndArea(plots, unvisited, width, height, i, j, borders);
            long sides = countSides(borders);
            score += sides * pAndA.getRight();
        }
        return score;
    }

    private long countSides(final char[][] borders) {
        int height = borders.length;
        int width = borders[0].length;

        boolean[][] visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visited[i][j] = false;
            }
        }

        long sides = 0;
        for (int i = 0; i < height; i += 2) {
            for (int j = 0; j < width; j += 2) {
                if (visited[i][j]) {
                    continue;
                }
                if (borders[i][j] == '-') {
                    int k = j;
                    while (k < width && borders[i][k] == '-') {
                        visited[i][k] = true;
                        k += 2;
                    }
                    k = j - 1;
                    while (k >= 0 && borders[i][k] == '-') {
                        visited[i][k] = true;
                        k -= 2;
                    }
                    sides += 1;
                }
                if (borders[i][j] == '|') {
                    int k = i;
                    while (k < height && borders[k][j] == '|') {
                        visited[i][k] = true;
                        k += 2;
                    }
                    k = i - 1;
                    while (k >= 0 && borders[k][j] == '|') {
                        visited[i][k] = true;
                        k -= 2;
                    }
                    sides += 1;
                }
            }
        }
        return sides;
    }

    private Pair<Integer, Integer> calcPerimeterAndArea(final char[][] plots,
                                                        final Set<Pair<Integer, Integer>> unvisited,
                                                        final int width,
                                                        final int height,
                                                        final int i,
                                                        final int j,
                                                        final char[][] borders) {
        unvisited.remove(Pair.of(i, j));
        char c = plots[i][j];
        int perimeter = 0;
        int area = 1;
        for (int dirIndex = 0; dirIndex < DIRECTIONS.length; dirIndex++) {
            int nextI = i + DIRECTIONS[dirIndex][0];
            int nextJ = j + DIRECTIONS[dirIndex][1];
            if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
                addBorder(borders, i, nextI, j, nextJ);
                perimeter++;
                continue;
            }
            borders[2 * i + 1][2 * j + 1] = c;
            if (plots[nextI][nextJ] != c) {
                addBorder(borders, i, nextI, j, nextJ);
                perimeter++;
            } else if (unvisited.contains(Pair.of(nextI, nextJ))) {
                Pair<Integer, Integer> pAndA =
                        calcPerimeterAndArea(plots, unvisited, width, height, nextI, nextJ, borders);
                perimeter += pAndA.getLeft();
                area += pAndA.getRight();
            }
        }
        return Pair.of(perimeter, area);
    }

    private void addBorder(final char[][] borders,
                           final int i,
                           final int nextI,
                           final int j,
                           final int nextJ) {
        int iStart = Math.min(2 * i + 1, 2 * nextI + 1) + 1;
        int iEnd = Math.max(2 * i + 1, 2 * nextI + 1) + 1;
        int jStart = Math.min(2 * j + 1, 2 * nextJ + 1) + 1;
        int jEnd = Math.min(2 * j + 1, 2 * nextJ + 1) + 1;
        if (i == nextI) {
            char borderChar = '-';
            for (int k = jStart; k < jEnd; k++) {
                borders[iStart][k] = '-';
            }
            return;
        }

        for (int k = iStart; k < iEnd; k++) {
            borders[i][jStart] = '-';
        }
    }
}
