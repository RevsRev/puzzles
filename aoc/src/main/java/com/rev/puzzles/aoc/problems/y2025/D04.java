package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.parse.LoaderUtils;

import java.util.List;

public final class D04 {

    public static final char PAPER_ROLL = '@';

    @AocProblemI(year = 2025, day = 4, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> resources = resourceLoader.resources();
        final char[][] chars = LoaderUtils.linesToCharMatrix(resources);

        long count = 0;
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[0].length; j++) {
                if (chars[i][j] == PAPER_ROLL && neighboursCount(chars, i, j) < 4) {
                    count++;
                }
            }
        }
        return count;
    }

    @AocProblemI(year = 2025, day = 4, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> resources = resourceLoader.resources();
        final char[][] chars = LoaderUtils.linesToCharMatrix(resources);

        long count = 0;
        long loopCount = 0;

        do {
            loopCount = 0;
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars[0].length; j++) {
                    if (chars[i][j] == PAPER_ROLL && neighboursCount(chars, i, j) < 4) {
                        loopCount++;
                        count++;
                        chars[i][j] = '.';
                    }
                }
            }
        } while (loopCount != 0);

        return count;

    }

    private int neighboursCount(final char[][] chars, final int i, final int j) {
        int lowI = Math.max(i - 1, 0);
        int maxI = Math.min(i + 1, chars.length - 1);

        int lowJ = Math.max(j - 1, 0);
        int maxJ = Math.min(j + 1, chars[0].length - 1);

        int count = 0;
        for (int i2 = lowI; i2 <= maxI; i2++) {
            for (int j2 = lowJ; j2 <= maxJ; j2++) {
                if (i2 == i && j2 == j) {
                    continue;
                }
                if (chars[i2][j2] == PAPER_ROLL) {
                    count++;
                }
            }
        }
        return count;
    }
}
