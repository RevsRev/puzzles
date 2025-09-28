package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.number.AmicableNumbers;

import java.util.Collection;
import java.util.List;

public final class PE021 {

    @PeProblem(number = 21)
    @PeTestData(inputs = {"285"}, solutions = {"504"})
    public long amicableNumbers(final ProblemResourceLoader<List<String>> inputs) {
        return amicableNumbers(Integer.parseInt(inputs.resources().get(0)));
    }

    private long amicableNumbers(final int n) {
        final AmicableNumbers amicableNumbers = new AmicableNumbers();
        final Collection<Long> amicableNumbersLeqN = amicableNumbers.getAmicableNumbersLeqN(n - 1);
        long sum = 0;
        for (final Long amicable : amicableNumbersLeqN) {
            sum += amicable;
        }
        return sum;
    }
}
