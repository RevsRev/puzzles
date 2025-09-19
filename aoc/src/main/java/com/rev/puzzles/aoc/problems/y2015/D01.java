package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.util.List;

public final class D01 {

    public static final int BASEMENT = -1;
    private static final char UP = '(';
    private static final char DOWN = ')';

    @AocProblemI(year = 2015, day = 1, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
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
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
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
