package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.perm.Binomial;

import java.util.List;

public final class PE015 {

    @PeProblem(number = 15)
    @PeTestData(inputs = {"2"}, solutions = {"6"})
    public long latticePaths(final ProblemResourceLoader<List<String>> inputs) {
        return latticePaths(Integer.parseInt(inputs.resources().get(0)));
    }

    private long latticePaths(final int gridSize) {
        return Binomial.nCm(2 * gridSize, gridSize);
    }
}
