package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.List;

public final class D25 {


    public static final int STARTING_CODE = 20151125;
    public static final int CODE_MULT = 252533;
    public static final int CODE_MOD = 33554393;
    public static final int PROBLEM_ROW = 3010;
    public static final int PROBLEM_COLUMN = 3019;

    @AocProblemI(year = 2015, day = 25, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final long triangleBase = (long) PROBLEM_ROW + (long) PROBLEM_COLUMN - 1;
        final long triangleNumber = (triangleBase - 1) * (triangleBase) / 2;
        final long codeSequence = triangleNumber + (long) PROBLEM_COLUMN - 1;

        long code = STARTING_CODE;
        for (int i = 0; i < codeSequence; i++) {
            code = (code * CODE_MULT) % CODE_MOD;
        }
        return code;
    }

    @AocProblemI(year = 2015, day = 25, part = 2)
    public String partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        return "n/a";
    }
}
