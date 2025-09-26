package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.Factors;

import java.util.List;

public final class PE012 {


    @PeProblem(number = 12)
    @PeTestData(inputs = {"5"}, solutions = {"28"})
    public long highlyDivisibleTriangleNumber(final ProblemResourceLoader<List<String>> inputs) {
        return highlyDivisibleTriangleNumber(Long.parseLong(inputs.resources().get(0)));
    }

    private long highlyDivisibleTriangleNumber(final long minNumberOfDivisors) {
        final Factors factors = Factors.create();
        long triangleIndex = 0;
        long numFactors = 0;
        long triangle = 0;
        do {
            triangleIndex++;

            final long firstFactor = triangleIndex % 2 == 0 ? triangleIndex / 2 : triangleIndex;
            final long secondFactor = triangleIndex % 2 == 0 ? triangleIndex + 1 : (triangleIndex + 1) / 2;

            triangle = firstFactor * secondFactor;

            numFactors = factors.numFactors(firstFactor, secondFactor);
        } while (numFactors <= minNumberOfDivisors);

        return triangle;
    }

}
