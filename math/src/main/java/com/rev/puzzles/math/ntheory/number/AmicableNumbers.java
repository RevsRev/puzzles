package com.rev.puzzles.math.ntheory.number;

import com.rev.puzzles.math.ntheory.primes.Factors;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public final class AmicableNumbers {

    private final TreeMap<Long, Long> amicableCache = new TreeMap<>();
    private final Factors factors = Factors.create();
    private long hwm = 0;

    public Collection<Long> getAmicableNumbersLeqN(final long n) {
        fillCache(n);

        final Collection<Long> amicableNumbersLeqN = new HashSet<>();

        final NavigableMap<Long, Long> numbersToCheck = amicableCache.subMap(amicableCache.floorKey(1L), true, n, true);

        for (final Map.Entry<Long, Long> numberAndFactorSum : numbersToCheck.entrySet()) {
            final long first = numberAndFactorSum.getKey();
            final long firstFactorSum = numberAndFactorSum.getValue();

            if (first == firstFactorSum) {
                continue; //perfect numbers are never amicable
            }

            final Long secondFactorSum = numbersToCheck.getOrDefault(firstFactorSum, -1L);
            if (secondFactorSum == -1L) {
                continue;
            }

            if (secondFactorSum == first) {
                amicableNumbersLeqN.add(first);
                amicableNumbersLeqN.add(firstFactorSum);
            }
        }
        return amicableNumbersLeqN;
    }

    public boolean areAmicable(final long a, final long b) {
        return a != b && b == getSumOfFactors(a) && a == getSumOfFactors(b);
    }

    private void fillCache(final long n) {
        if (n <= hwm) {
            return;
        }

        for (long i = hwm + 1; i <= n; i++) {
            long sumOfFactors = getSumOfFactors(i);
            amicableCache.put(i, sumOfFactors);
        }
        hwm = n;
    }

    private long getSumOfFactors(final long i) {
        final List<Long> factorsOfN = factors.factors(i);
        long sumOfFactors = 0;
        for (int j = 0; j < factorsOfN.size() - 1; j++) { //skip the last factor (n)
            sumOfFactors += factorsOfN.get(j);
        }
        return sumOfFactors;
    }

}
