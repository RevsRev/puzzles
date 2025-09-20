package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.Factors;

import java.util.List;
import java.util.TreeMap;

public final class PE003 {

    @PeProblem(number = 3)
    @PeTestData(inputs = {"13195"}, solutions = {"29"})
    public long largestPrimeFactor(final ProblemResourceLoader<List<String>> inputs) {
        return largestPrimeFactor(Long.parseLong(inputs.resources().get(0)));
    }

    private long largestPrimeFactor(final long n) {
        final Factors factors = Factors.create();
        final TreeMap<Long, Long> primeFactors = factors.primeFactors(n);
        return primeFactors.lastKey();
    }

}
