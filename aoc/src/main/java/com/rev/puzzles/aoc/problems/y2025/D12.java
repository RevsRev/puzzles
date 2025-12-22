package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D12 {

    @AocProblemI(year = 2025, day = 12, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> resources = resourceLoader.resources();

        final List<char[][]> presents = new ArrayList<>();
        final Set<Pair<Rectangle, List<Integer>>> bins = new HashSet<>();

        for (int i = 0; i < resources.size(); i++) {
            final String s = resources.get(i);
            if (s.isBlank()) {
                continue;
            }

            if (s.contains("x")) {
                final String[] split = s.split(":");
                final String[] dimensions = split[0].trim().split("x");
                final Rectangle rect = new Rectangle(Integer.parseInt(dimensions[0]), Integer.parseInt(dimensions[1]));
                final String[] countsStrArr = split[1].trim().split("\\s+");
                final List<Integer> counts = Arrays.stream(countsStrArr).map(Integer::parseInt).toList();
                bins.add(Pair.of((rect), counts));
                continue;
            }

            if (s.contains(":")) {
                final char[][] present = new char[3][3];
                for (int j = 0; j < 3; j++) {
                    present[j] = resources.get(i + 1 + j).toCharArray();
                }
                presents.add(present);
            }
        }

        int feasibleCount = 0;
        int restrictedFeasibleCount = 0;
        for (final Pair<Rectangle, List<Integer>> binAndEntries : bins) {
            final Rectangle bin = binAndEntries.getKey();
            final List<Integer> presentCounts = binAndEntries.getValue();

            int presentArea = 0;
            int restrictedArea = 0;
            for (int i = 0; i < presentCounts.size(); i++) {
                restrictedArea += 9 * presentCounts.get(i);
                presentArea += presentCounts.get(i) * computeArea(presents.get(i));
            }

            final int rectArea = 9 * (bin.width / 3) * (bin.height / 3);

            if (presentArea <= rectArea) {
                feasibleCount++;
            }
            if (restrictedArea <= rectArea) {
                restrictedFeasibleCount++;
            }
        }

        if (feasibleCount != restrictedFeasibleCount) {
            throw new ProblemExecutionException("Stupid method does not work for this stupid problem!");
        }

        return feasibleCount;
    }

    private static int computeArea(final char[][] present) {
        int h = present.length;
        int w = present[0].length;

        int area = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (present[i][j] == '#') {
                    area++;
                }
            }
        }
        return area;
    }

    @AocProblemI(year = 2025, day = 12, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        return -1L;
    }

    private record Rectangle(int width, int height) {
    }
}
