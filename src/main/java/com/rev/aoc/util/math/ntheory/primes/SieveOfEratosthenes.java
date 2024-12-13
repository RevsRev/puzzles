package com.rev.aoc.util.math.ntheory.primes;

import java.util.Iterator;
import java.util.LinkedHashSet;

public final class SieveOfEratosthenes {
    private SieveOfEratosthenes() {
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static LinkedHashSet<Long> sieveOfEratosthenes(long n) {

        LinkedHashSet<Long> primes = new LinkedHashSet<>();

        int segmentSize = 1000000;
        long segmentStart = 2;
        long segmentEnd = Math.min(segmentStart + segmentSize, n);
        int loopSize = (int) (segmentEnd - segmentStart);

        do {
            boolean[] compositeSieve = new boolean[segmentSize];

            //filter out factors of primes we've found in other iterations
            for (int i = 0; i < loopSize; i++) {
                if (compositeSieve[i]) {
                    continue;
                }
                Iterator<Long> primesIt = primes.iterator();
                while (primesIt.hasNext()) {
                    long prime = primesIt.next();
                    if ((i + segmentStart) % prime == 0) {
                        compositeSieve[i] = true;
                        break;
                    }
                }
            }

            //Now go through the composites to find new primes
            for (int i = 0; i < loopSize; i++) {
                if (compositeSieve[i]) {
                    continue;
                }

                long prime = segmentStart + i;
                primes.add(prime);
                for (int j = i + 1; j < loopSize; j++) {
                    if ((j + segmentStart) % prime == 0) {
                        compositeSieve[j] = true;
                    }
                }
            }

            segmentStart += segmentSize;
            segmentEnd = Math.min(segmentStart + segmentSize, n);
            loopSize = (int) (segmentEnd - segmentStart);
        } while (segmentEnd < n);
        return primes;
    }
}
