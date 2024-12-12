package com.rev.aoc.util.math.primes;


import com.rev.aoc.util.math.modular.Mod;
import lombok.Getter;

import java.util.Iterator;
import java.util.LinkedHashSet;

public final class PrimeCheck {
    private static final long PRIME_CACHE_SIZE = 10000;
    @Getter
    private static LinkedHashSet<Long> primeCache = SieveOfEratosthenes.sieveOfEratosthenes(PRIME_CACHE_SIZE);

    private static final long PRIME_WITNESSES_SIZE = 50;
    private static LinkedHashSet<Long> primeWitnesses = SieveOfEratosthenes.sieveOfEratosthenes(PRIME_WITNESSES_SIZE);

    private PrimeCheck() {
    }

    public static boolean primeCheck(long n) {
        //Fermat prime check
        Iterator<Long> itWitnesses = primeWitnesses.iterator();
        while (itWitnesses.hasNext()) {
            long prime = itWitnesses.next();
            if (n == prime) {
                return true;
            }
            if (Mod.pow(n, prime - 1, prime) != 1) {
                return false;
            }
        }

        //Just in case I ever accidentally remove from witnesses...
        if (n == 2 || n == 3) {
            return true;
        }

        if (n % 2 == 0) {
            return false;
        }

        //Do the usual check
        long limit = (long) Math.ceil(Math.sqrt(n)) + 1;
        for (int i = 5; i <= limit; i += 2) {
            if ((n % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
