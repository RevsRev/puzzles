package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.Sum;

import java.util.List;

public final class PE001 {

    @PeProblem(number = 1)
    @PeTestData(inputs = {"10"}, solutions = {"23"})
    public long multiplesOf3Or5(final ProblemResourceLoader<List<String>> inputs) {
        return multiplesOf3Or5(Long.parseLong(inputs.resources().get(0)));
    }

    private long multiplesOf3Or5(final long limit) {
        long leqLimit = limit - 1;
        return 3 * Sum.sum1toN(leqLimit / 3) + 5 * Sum.sum1toN(leqLimit / 5) - 15 * Sum.sum1toN(leqLimit / 15);
    }

}
