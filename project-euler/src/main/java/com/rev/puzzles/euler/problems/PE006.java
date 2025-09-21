package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.util.Pow;
import com.rev.puzzles.math.sum.Sum;

import java.util.List;

public final class PE006 {

    @PeProblem(number = 6)
    @PeTestData(inputs = {"10"}, solutions = {"2640"})
    public long sumSquareDifference(final ProblemResourceLoader<List<String>> inputs) {
        return sumSquareDifference(Long.parseLong(inputs.resources().get(0)));
    }

    private long sumSquareDifference(final long n) {
        return Pow.pow(Sum.sum1toN(n), 2) - Sum.sumSq1toN(n);
    }

}
