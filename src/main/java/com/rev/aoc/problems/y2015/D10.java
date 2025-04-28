package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

public final class D10 extends AocProblem<Long, Long> {

    private static final int PROBLEM_ITERATIONS = 50;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 10);
    }

    @Override
    protected Long partOneImpl() {
        final String seed = loadResources().get(0);
        final StringBuilder sb = new StringBuilder(seed);

        int start = 0;
        int end = sb.length();
        for (int iterations = 0; iterations < PROBLEM_ITERATIONS; iterations++) {
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

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected Long partTwoImpl() {
        return null;
    }
}
