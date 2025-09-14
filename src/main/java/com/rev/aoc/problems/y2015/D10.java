package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D10 extends AocProblem<Long, Long> {

    private static final int PART_ONE_ITERATIONS = 40;
    private static final int PART_TWO_ITERATIONS = 50;

    @AocProblemI(year = 2015, day = 10, part = 1)
    @Override
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        return solve(resourceLoader, PART_ONE_ITERATIONS);
    }

    @AocProblemI(year = 2015, day = 10, part = 1)
    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        return solve(resourceLoader, PART_TWO_ITERATIONS);
    }

    private long solve(final ResourceLoader resourceLoader, final int totalIterations) {
        final String seed = resourceLoader.resources().get(0);
        final StringBuilder sb = new StringBuilder(seed);

        int start = 0;
        int end = sb.length();
        for (int iterations = 0; iterations < totalIterations; iterations++) {
            int i = start;
            while (i < end) {
                int j = 0;
                while (i + j < end && sb.charAt(i + j) == sb.charAt(i)) {
                    j++;
                }
                sb.append(j).append(sb.charAt(i));
                i += j;
            }
            start = end;
            end = sb.length();
        }

        return (long) end - start;
    }
}
