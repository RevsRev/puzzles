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
            if (i >= 0) {
                for (int j = 1; j <= i; j++) {
                    start += 1;
                    start = (start + 100) % 100;
                    if (start == 0) {
                        count++;
                    }
                }
            } else {
                for (int j = -1; j >= i; j--) {
                    start -= 1;
                    start = (start + 100) % 100;
                    if (start == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static List<Integer> getDialMovements(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<Integer> dialsList = resourceLoader.resources().stream().map(s -> {
            if (s.contains("L")) {
                s = s.replace("L", "");
                return -1 * Integer.parseInt(s);
            } else {
                return Integer.parseInt(s.replace("R", ""));
            }
        }).toList();
        return dialsList;
    }
}
