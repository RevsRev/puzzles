package com.rev.aoc.util.math.ntheory.primes;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class SieveOfEratosthenes {

    private static final int DEFAULT_MAX_SEGMENT_SIZE = 1000000000;
    private final List<Long> primes;
    private final int maxSegmentSize;
    private long highWaterMark;

    private SieveOfEratosthenes(final long highWaterMark, final List<Long> primes, final int maxSegmentSize) {
        this.highWaterMark = highWaterMark;
        this.primes = primes;
        this.maxSegmentSize = maxSegmentSize;
    }

    public static SieveOfEratosthenes create(final long initialSearch) {
        return create(initialSearch, DEFAULT_MAX_SEGMENT_SIZE);
    }

    public static SieveOfEratosthenes create(final long initialSearch, final int maxSegmentSize) {
        List<Long> primes = new ArrayList<>();
        primes.add(2L);
        long highWaterMark = sieve(initialSearch, 2, maxSegmentSize, primes);
        return new SieveOfEratosthenes(highWaterMark, primes, maxSegmentSize);
    }

    private static long sieve(
            final long n,
            final long highWaterMark,
            final int maxSegmentSize,
            final List<Long> primes) {
        long segmentStart = highWaterMark + 1;
        long segmentSize = Math.min(maxSegmentSize, (int) (n + 1 - segmentStart));
        long segmentEnd = segmentStart + segmentSize;

        while (segmentSize > 0) {
            boolean[] compositeSieve = new boolean[maxSegmentSize];

            //filter out factors of primes we've found in other iterations
            for (int i = 0; i < segmentSize; i++) {
                if (compositeSieve[i]) {
                    continue;
                }
                for (final long prime : primes) {
                    if ((i + segmentStart) % prime == 0) {
                        compositeSieve[i] = true;
                        break;
                    }
                }
            }

            //Now go through the composites to find new primes
            for (int i = 0; i < segmentSize; i++) {
                if (compositeSieve[i]) {
                    continue;
                }

                long prime = segmentStart + i;
                primes.add(prime);
                for (int j = 1; j <= (segmentSize - i) / prime; j++) {
                    compositeSieve[(int) (i + j * prime)] = true;
                }
            }

            segmentStart = segmentEnd;
            segmentSize = Math.min(maxSegmentSize, (int) (n + 1 - segmentStart));
            segmentEnd = segmentStart + segmentSize;
        }

        return segmentEnd - 1;
    }

    public void extend(final long n) {
        if (n < highWaterMark) {
            return;
        }
        highWaterMark = sieve(n, highWaterMark, maxSegmentSize, primes);
    }
}
