package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.ntheory.primes.Factors;
import com.rev.puzzles.math.ntheory.util.Pow;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class PE005 {

    @PeProblem(number = 5)
    @PeTestData(inputs = {"10"}, solutions = {"2520"})
    public long smallestNumberDivisibleBy1ToN(final ProblemResourceLoader<List<String>> inputs) {
        return smallestNumberDivisibleBy1ToN(Long.parseLong(inputs.resources().get(0)));
    }

    private long smallestNumberDivisibleBy1ToN(final long n) {
        final Factors factors = Factors.create();
        final TreeMap<Long, Long> largestPrimeMultiplicities = new TreeMap<>();
        for (long i = 2; i <= n; i++) {
            final TreeMap<Long, Long> primeFactorsOfI = factors.primeFactors(i);
            primeFactorsOfI.forEach(
                    (p, pow) -> {
                        final long largestPowSoFar = largestPrimeMultiplicities.computeIfAbsent(p, k -> pow);
                        if (largestPowSoFar < pow) {
                            largestPrimeMultiplicities.put(p, pow);
                        }
                    }
            );
        }

        long result = 1;
        for (final Map.Entry<Long, Long> primeAndPow : largestPrimeMultiplicities.entrySet()) {
            result *= Pow.pow(primeAndPow.getKey(), primeAndPow.getValue());
        }
        return result;
    }

}
