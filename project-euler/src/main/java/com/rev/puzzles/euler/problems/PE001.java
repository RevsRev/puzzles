package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.Sum;

public final class PE001 {

    public static final int LIMIT = 1000;

    @PeProblem(number = 1)
    public long multiplesOf3Or5(final ProblemResourceLoader<?> ignored) {
        return multiplesOf3Or5(LIMIT);
    }

    private long multiplesOf3Or5(final long limit) {
        long leqLimit = limit - 1;
        return 3 * Sum.sum1toN(leqLimit / 3) + 5 * Sum.sum1toN(leqLimit / 5) - 15 * Sum.sum1toN(leqLimit / 15);
    }

}
