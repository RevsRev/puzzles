package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;

public final class D25 extends AocProblem<Long, String> {


    public static final int STARTING_CODE = 20151125;
    public static final int CODE_MULT = 252533;
    public static final int CODE_MOD = 33554393;
    public static final int PROBLEM_ROW = 3010;
    public static final int PROBLEM_COLUMN = 3019;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 25);
    }

    @AocProblemI(year = 2015, day = 25, part = 1)
    @Override
    protected Long partOneImpl() {
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
    @Override
    protected String partTwoImpl() {
        return "n/a";
    }
}
