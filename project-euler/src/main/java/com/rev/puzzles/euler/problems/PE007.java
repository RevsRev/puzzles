package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.SieveOfEratosthenes;

import java.util.List;

public final class PE007 {

    @PeProblem(number = 7)
    @PeTestData(inputs = {"6"}, solutions = {"13"})
    public long nThPrime(final ProblemResourceLoader<List<String>> inputs) {
        return nThPrime(Integer.parseInt(inputs.resources().get(0)));
    }

    private long nThPrime(final int n) {
        final SieveOfEratosthenes sieve = SieveOfEratosthenes.create(n);
        return sieve.getNthPrime(n);
    }

}
