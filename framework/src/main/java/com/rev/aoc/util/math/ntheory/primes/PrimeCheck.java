package com.rev.aoc.util.math.ntheory.primes;


import com.rev.aoc.util.math.ntheory.modular.Mod;

import java.util.Collections;

public final class PrimeCheck {

    private static final long FERMAT_PRIME_CHECK_LIMIT = 300;
    public static final int INITIAL_SEARCH_SIZE = 1000;
    private final SieveOfEratosthenes primeCache;

    private PrimeCheck(final SieveOfEratosthenes primeCache) {
        this.primeCache = primeCache;
    }

    public static PrimeCheck create() {
        SieveOfEratosthenes primeCache = SieveOfEratosthenes.create(INITIAL_SEARCH_SIZE);
        return new PrimeCheck(primeCache);
    }

    public boolean primeCheck(long n) {
        if (n == 1) {
            return false;
        }

        if (Collections.binarySearch(primeCache.getPrimes(), n) >= 0) {
            return true;
        }

        if (n < primeCache.getHighWaterMark()) {
            return false;
        }

        for (long prime : primeCache.getPrimes()) {
            if (prime > FERMAT_PRIME_CHECK_LIMIT) {
                break;
            }
            if (Mod.pow(n, prime - 1, prime) != 1) {
                return false;
            }
        }

        primeCache.extend((long) Math.sqrt(n));
        for (long prime : primeCache.getPrimes()) {
            if (n % prime == 0) {
                return false;
            }
        }
        return true;
    }
}
