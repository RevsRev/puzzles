package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;

public final class D01 extends AocProblem<Long, Long> {

    private static final char UP = '(';
    private static final char DOWN = ')';
    public static final int BASEMENT = -1;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 1);
    }

    @Override
    @AocProblemI(year = 2015, day = 1, part = 1)
    protected Long partOneImpl() {
        String input = loadResources().get(0);
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

    @Override
    @AocProblemI(year = 2015, day = 1, part = 2)
    protected Long partTwoImpl() {
        String input = loadResources().get(0);
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
