package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.SieveOfEratosthenes;

import java.util.List;

public final class PE010 {

    @PeProblem(number = 10)
    @PeTestData(inputs = {"10"}, solutions = {"17"})
    public long sumOfPrimes(final ProblemResourceLoader<List<String>> inputs) {
        return sumOfPrimes(Long.parseLong(inputs.resources().get(0)));
    }

    private long sumOfPrimes(final long n) {
        final SieveOfEratosthenes sieve = SieveOfEratosthenes.create(n);
        final List<Long> primes = sieve.getPrimes();

        long primeSum = 0;
        for (final Long prime : primes) {
            primeSum += prime;
        }
        return primeSum;
    }

}
