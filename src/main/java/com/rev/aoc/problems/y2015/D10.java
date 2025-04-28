package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

public final class D10 extends AocProblem<Long, Long> {

    private static final int PART_ONE_ITERATIONS = 40;
    private static final int PART_TWO_ITERATIONS = 50;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 10);
    }

    @Override
    protected Long partOneImpl() {
        return solve(PART_ONE_ITERATIONS);
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected Long partTwoImpl() {
        return solve(PART_TWO_ITERATIONS);
    }

    private long solve(final int totalIterations) {
        final String seed = loadResources().get(0);
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
