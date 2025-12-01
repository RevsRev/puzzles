package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.List;

public final class D01 {

    @AocProblemI(year = 2025, day = 1, part = 1)
    public int partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<Integer> dialsList = getDialMovements(resourceLoader);

        int start = 50;
        int count = 0;
        for (final Integer i : dialsList) {
            start += i;
            start = (start + 100) % 100;
            if (start == 0) {
                count++;
            }
        }
        return count;
    }

    @AocProblemI(year = 2025, day = 1, part = 2)
    public int partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<Integer> dialsList = getDialMovements(resourceLoader);

        int start = 50;
        int count = 0;
        for (final Integer i : dialsList) {
            final int remainder = Math.abs(i) % 100;
            final int completeRotations = Math.abs(i) / 100;

            if (start != 0 && remainder != 0) {
                if (i < 0 && start - remainder <= 0) {
                    count++;
                } else if (i > 0 && start + remainder >= 100) {
                    count++;
                }
            }

            count += completeRotations;

            start += i % 100;
            start = (start + 100) % 100;

        }
        return count;
    }

    private static List<Integer> getDialMovements(final ProblemResourceLoader<List<String>> resourceLoader) {
        return resourceLoader.resources().stream().map(s -> {
            if (s.contains("L")) {
                s = s.replace("L", "");
                return -1 * Integer.parseInt(s);
            } else {
                return Integer.parseInt(s.replace("R", ""));
            }
        }).toList();
    }
}
