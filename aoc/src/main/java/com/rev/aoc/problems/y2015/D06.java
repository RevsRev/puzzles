package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.AocProblemI;
import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.util.geom.PointRectangle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public final class D06 {

    private static final int PROBLEM_WIDTH = 1000;
    private static final int PROBLEM_HEIGHT = 1000;

    private static final int OFF = 0;
    private static final int ON = 1;

    private static final int TURN_OFF = 0;
    private static final int TURN_ON = 1;
    private static final int TOGGLE = 2;

    @AocProblemI(year = 2015, day = 6, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<Pair<PointRectangle, Integer>> pairs = computeRectangleAndInstructions(resourceLoader.resources());

        long count = 0;
        int[][] grid = new int[PROBLEM_HEIGHT][PROBLEM_WIDTH];
        for (Pair<PointRectangle, Integer> pair : pairs) {
            PointRectangle rect = pair.getLeft();
            int instruction = pair.getRight();

            for (int i = rect.x(); i <= rect.x() + rect.w(); i++) {
                for (int j = rect.y(); j <= rect.y() + rect.h(); j++) {
                    if (instruction == TURN_ON) {
                        if (grid[i][j] == OFF) {
                            grid[i][j] = ON;
                            count++;
                        }
                    } else if (instruction == TURN_OFF) {
                        if (grid[i][j] == ON) {
                            grid[i][j] = OFF;
                            count--;
                        }
                    } else {
                        if (grid[i][j] == OFF) {
                            grid[i][j] = ON;
                            count++;
                        } else {
                            grid[i][j] = OFF;
                            count--;
                        }
                    }
                }
            }

        }
        return count;
    }

    @AocProblemI(year = 2015, day = 6, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<Pair<PointRectangle, Integer>> pairs = computeRectangleAndInstructions(resourceLoader.resources());

        long totalBrightness = 0;
        int[][] grid = new int[PROBLEM_HEIGHT][PROBLEM_WIDTH];
        for (Pair<PointRectangle, Integer> pair : pairs) {
            PointRectangle rect = pair.getLeft();
            int instruction = pair.getRight();

            for (int i = rect.x(); i <= rect.x() + rect.w(); i++) {
                for (int j = rect.y(); j <= rect.y() + rect.h(); j++) {
                    if (instruction == TURN_ON) {
                        grid[i][j] += 1;
                        totalBrightness++;
                    } else if (instruction == TURN_OFF) {
                        if (grid[i][j] > 0) {
                            grid[i][j]--;
                            totalBrightness--;
                        }
                    } else {
                        grid[i][j] += 2;
                        totalBrightness += 2;
                    }
                }
            }

        }
        return totalBrightness;
    }

    private List<Pair<PointRectangle, Integer>> computeRectangleAndInstructions(final List<String> lines) {
        List<Pair<PointRectangle, Integer>> retval = new ArrayList<>();

        for (String line : lines) {
            String s = line.replaceAll("\\s", "");
            final int instruction;
            if (s.startsWith("turnoff")) {
                instruction = TURN_OFF;
                s = s.replace("turnoff", "");
            } else if (s.startsWith("toggle")) {
                instruction = TOGGLE;
                s = s.replace("toggle", "");
            } else {
                instruction = TURN_ON;
                s = s.replace("turnon", "");
            }
            String[] corners = s.split("through");
            String[] firstCorner = corners[0].split(",");
            String[] secondCorner = corners[1].split(",");
            final int x = Integer.parseInt(firstCorner[0]);
            final int y = Integer.parseInt(firstCorner[1]);
            final int w = Integer.parseInt(secondCorner[0]) - x;
            final int h = Integer.parseInt(secondCorner[1]) - y;
            retval.add(Pair.of(new PointRectangle(x, y, w, h), instruction));
        }

        return retval;
    }
}
