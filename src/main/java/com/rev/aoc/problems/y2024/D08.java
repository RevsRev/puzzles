package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class D08 extends AocProblem<Long, Long> {

    public static final char EMPTY_CHAR = '.';

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 8);
    }

    @Override
    protected Long partOneImpl() {
        char[][] grid = LoaderUtils.loadResourcesAsCharMatrix(loadResources());
        int height = grid.length;
        int width = grid[0].length;

        Map<Character, Set<Pair<Integer, Integer>>> antennas = loadAntennas(grid);
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();
        for (Map.Entry<Character, Set<Pair<Integer, Integer>>> entry : antennas.entrySet()) {
            Set<Pair<Integer, Integer>> antennasForChar = entry.getValue();
            List<Pair<Integer, Integer>> antennasForCharList = antennasForChar.stream().toList();
            addAntinodesForAntennas(height, width, antinodes, antennasForCharList, false);
        }
        return (long) antinodes.size();
    }

    @Override
    protected Long partTwoImpl() {
        char[][] grid = LoaderUtils.loadResourcesAsCharMatrix(loadResources());
        int height = grid.length;
        int width = grid[0].length;

        Map<Character, Set<Pair<Integer, Integer>>> antennas = loadAntennas(grid);
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();
        for (Map.Entry<Character, Set<Pair<Integer, Integer>>> entry : antennas.entrySet()) {
            Set<Pair<Integer, Integer>> antennasForChar = entry.getValue();
            List<Pair<Integer, Integer>> antennasForCharList = antennasForChar.stream().toList();
            addAntinodesForAntennas(height, width, antinodes, antennasForCharList, true);
        }
        return (long) antinodes.size();
    }

    private void addAntinodesForAntennas(final int height,
                                         final int width,
                                         final Set<Pair<Integer, Integer>> antinodes,
                                         final List<Pair<Integer, Integer>> antennasForCharList,
                                         final boolean partTwo) {
        for (int i = 0; i < antennasForCharList.size(); i++) {
            for (int j = i + 1; j < antennasForCharList.size(); j++) {
                Pair<Integer, Integer> top = antennasForCharList.get(i);
                Pair<Integer, Integer> bottom = antennasForCharList.get(j);
                Pair<Integer, Integer> forwardsDiff =
                        Pair.of(bottom.getLeft() - top.getLeft(), bottom.getRight() - top.getRight());
                Pair<Integer, Integer> backwardsDiff =
                        Pair.of(top.getLeft() - bottom.getLeft(), top.getRight() - bottom.getRight());

                iterateOverVectors(height, width, antinodes, partTwo, top, forwardsDiff);
                iterateOverVectors(height, width, antinodes, partTwo, bottom, backwardsDiff);
            }
        }
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    private void iterateOverVectors(int height, int width, final Set<Pair<Integer, Integer>> antinodes, boolean partTwo,
                                    Pair<Integer, Integer> top, final Pair<Integer, Integer> diff) {
        int iterations = 0;
        while (inBounds(height, width, top)) {
            if (!partTwo && iterations == 1 && inBounds(height, width, top)) {
                antinodes.add(top);
            } else if (partTwo) {
                if (inBounds(height, width, top)) {
                    antinodes.add(top);
                }
            }
            top = Pair.of(top.getLeft() - diff.getLeft(), top.getRight() - diff.getRight());
            iterations++;
        }
    }

    private boolean inBounds(int height, int width, final Pair<Integer, Integer> candidate) {
        int i = candidate.getLeft();
        int j = candidate.getRight();
        return i >= 0 && i < height && j >= 0 && j < width;
    }

    private Map<Character, Set<Pair<Integer, Integer>>> loadAntennas(final char[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        Map<Character, Set<Pair<Integer, Integer>>> antennas = new HashMap<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] != EMPTY_CHAR) {
                    antennas.computeIfAbsent(grid[i][j], k -> new HashSet<>()).add(Pair.of(i, j));
                }
            }
        }
        return antennas;
    }
}
