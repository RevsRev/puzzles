package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.aoc.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.framework.problem.ResourceLoader;

public final class D01 {

    private static final char UP = '(';
    private static final char DOWN = ')';
    public static final int BASEMENT = -1;

    @AocProblemI(year = 2015, day = 1, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        String input = resourceLoader.resources().get(0);
        long floor = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == UP) {
                floor++;
            } else {
                floor--;
            }
        }
        return floor;
    }

    @AocProblemI(year = 2015, day = 1, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        String input = resourceLoader.resources().get(0);
        long floor = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == UP) {
                floor++;
            } else {
                floor--;
            }
            if (floor == BASEMENT) {
                return (long) i + 1;
            }
        }
        throw new ProblemExecutionException();
    }
}
