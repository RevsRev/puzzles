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

        long score = 0;
        while (!unvisited.isEmpty()) {
            Pair<Integer, Integer> seed = unvisited.iterator().next();
            Integer i = seed.getLeft();
            Integer j = seed.getRight();
            Pair<Integer, Integer> perimeterAndArea =
                    calcPerimeterAndArea(plots, unvisited, width, height, i, j);
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
            Pair<Integer, Integer> pAndA = calcPerimeterAndArea(plots, unvisited, width, height, i, j);
//            score += sides * pAndA.getRight();
        }
        return score;
    }

    private Pair<Integer, Integer> calcPerimeterAndArea(final char[][] plots,
                                                        final Set<Pair<Integer, Integer>> unvisited,
                                                        final int width,
                                                        final int height,
                                                        final int i,
                                                        final int j) {
        unvisited.remove(Pair.of(i, j));
        char c = plots[i][j];
        int perimeter = 0;
        int area = 1;
        for (int dirIndex = 0; dirIndex < DIRECTIONS.length; dirIndex++) {
            int nextI = i + DIRECTIONS[dirIndex][0];
            int nextJ = j + DIRECTIONS[dirIndex][1];
            if (nextI < 0 || nextI >= height || nextJ < 0 || nextJ >= width) {
                perimeter++;
                continue;
            }
            if (plots[nextI][nextJ] != c) {
                perimeter++;
            } else if (unvisited.contains(Pair.of(nextI, nextJ))) {
                Pair<Integer, Integer> pAndA =
                        calcPerimeterAndArea(plots, unvisited, width, height, nextI, nextJ);
                perimeter += pAndA.getLeft();
                area += pAndA.getRight();
            }
        }
        return Pair.of(perimeter, area);
    }
}
