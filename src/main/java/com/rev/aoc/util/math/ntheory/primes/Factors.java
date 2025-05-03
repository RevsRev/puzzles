package com.rev.aoc.util.math.ntheory.primes;


import com.rev.aoc.util.math.ntheory.util.Pow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class Factors {

    public static final int DEFAULT_CACHE_INITIALIZATION_SIZE = 1000;
    private final SieveOfEratosthenes sieve;

    public Factors(final SieveOfEratosthenes sieve) {
        this.sieve = sieve;
    }

    public static Factors create() {
        return new Factors(SieveOfEratosthenes.create(DEFAULT_CACHE_INITIALIZATION_SIZE));
    }

    public TreeMap<Long, Long> primeFactors(final long n) {

        TreeMap<Long, Long> factors = new TreeMap<>();

        if (n == 1) {
            return factors;
        }

        sieve.extend((long) Math.sqrt(n));
        List<Long> primes = sieve.getPrimes();

        int index = Collections.binarySearch(primes, n);
        if (index >= 0) {
            factors.put(n, 1L);
            return factors;
        }

        index = -index - 1;

        long reducedN = n;
        for (int i = 0; i < index && reducedN > 1; i++) {
            long prime = primes.get(i);
            long j = 0;
            while (reducedN % prime == 0) {
                reducedN = reducedN / prime;
                j++;
            }
            if (j != 0) {
                factors.put(prime, j);
            }
        }

        if (reducedN > 1) {
            factors.put(reducedN, 1L);
        }
        return factors;
    }

    public List<Long> factors(long n) {
        TreeMap<Long, Long> primeFactors = primeFactors(n);
        int capacity = (int) (Math.log(n) / Math.log(2)) + 1;
        final List<Long> factors = new ArrayList<>(capacity);

        factors(factors, primeFactors, 1, 1);
        Collections.sort(factors);
        return factors;
    }

    private static void factors(
            final List<Long> factors,
            final TreeMap<Long, Long> primeFactors,
            final long factor,
            final long low) {

        Map.Entry<Long, Long> ceilingEntry = primeFactors.ceilingEntry(low);

        if (ceilingEntry == null) {
            factors.add(factor);
            return;
        }

        final long prime = ceilingEntry.getKey();
        final long multiplicity = ceilingEntry.getValue();

        long f = factor;
        for (int i = 0; i <= multiplicity; i++) {
            factors(factors, primeFactors, f, prime + 1);
            f *= prime;
        }
        primeFactors.ceilingEntry(prime + 1);
    }

    public long eulerTotient(long n) {

        if (n == 0) {
            return 0;
        }

        long retval = 1;

        Map<Long, Long> factors = primeFactors(n);
        Iterator<Long> it = factors.keySet().iterator();
        while (it.hasNext()) {
            long prime = it.next();
            long exponent = factors.get(prime);
            retval *= Pow.pow(prime, exponent - 1) * (prime - 1);
        }
        return retval;
    }

    public static long fastEulerTotient(long n) {
        return fastEulerTotient(n, new HashMap<>());
    }

    public static long fastEulerTotient(long n, final Map<Long, Long> totientCache) {

        if (totientCache.containsKey(n)) {
            return totientCache.get(n);
        }


        if (n == 1) {
            totientCache.put(1L, 1L);
            return 1;
        }

        if (n % 2 == 0) {
            long pow = 1;
            long a = 2;
            long b = n / 2;
            while (b % 2 == 0) {
                a = 2 * a;
                b = b / 2;
                pow += 1;
            }

            if (b == 1) {
                return Pow.pow(2, pow - 1);
            }

            long result = fastEulerTotient(b, totientCache) * Pow.pow(2, pow - 1);
            totientCache.put(n, result);
            return result;
        }

        long limit = (long) Math.ceil(Math.sqrt(n));
        for (long i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                long pow = 1;
                long a = i;
                long b = n / i;
                while (b % i == 0) {
                    a = i * a;
                    b = b / i;
                    pow += 1;
                }

                if (b == 1) {
                    return (i - 1) * Pow.pow(i, pow - 1);
                }

                long result = fastEulerTotient(b, totientCache) * (i - 1) * Pow.pow(i, pow - 1);
                totientCache.put(n, result);
                return result;
            }
        }

        //n is a prime
        long result = n - 1;
        totientCache.put(n, result);
        return result;
    }
}
