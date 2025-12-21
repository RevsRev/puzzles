package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectPeriodicityDetectorTest {

    @Test
    public void shouldDetectPeriods() {
        final BigInteger[] sequence = new BigInteger[10];
        sequence[0] = BigInteger.ONE;
        sequence[1] = BigInteger.TWO;
        sequence[2] = BigInteger.ONE;
        sequence[3] = BigInteger.TWO;
        sequence[4] = BigInteger.ONE;
        sequence[5] = BigInteger.TWO;
        sequence[6] = BigInteger.ONE;
        sequence[7] = BigInteger.TWO;
        sequence[8] = BigInteger.ONE;
        sequence[9] = BigInteger.TWO;

        final AtomicInteger index = new AtomicInteger(0);

        final PeriodicityDetector detector =
                new DirectPeriodicityDetector.Builder(() -> sequence[index.getAndIncrement()]).setMaxPeriod(2)
                        .setMaxThreshold(10).setPeriodDetectionCycles(5).build();

        assertTrue(detector.detect());
        assertEquals(2, detector.getDetectedPeriodLength());
        assertEquals(BigInteger.ONE, detector.getSequenceElement(BigInteger.valueOf(22)));
    }

    @Test
    public void shouldNotDetectPeriods() {
        final BigInteger[] sequence = new BigInteger[10];
        sequence[0] = BigInteger.ONE;
        sequence[1] = BigInteger.TWO;
        sequence[2] = BigInteger.ONE;
        sequence[3] = BigInteger.TWO;
        sequence[4] = BigInteger.ONE;
        sequence[5] = BigInteger.TWO;
        sequence[6] = BigInteger.ZERO;
        sequence[7] = BigInteger.TWO;
        sequence[8] = BigInteger.ONE;
        sequence[9] = BigInteger.TWO;

        final AtomicInteger index = new AtomicInteger(0);

        final PeriodicityDetector detector =
                new DirectPeriodicityDetector.Builder(() -> sequence[index.getAndIncrement()]).setMaxPeriod(2)
                        .setMaxThreshold(10).setPeriodDetectionCycles(5).build();

        assertFalse(detector.detect());
    }

    @Test
    public void moreComplicatedShouldDetectPeriods() {
        final BigInteger seventyOne = BigInteger.valueOf(71);
        final AtomicLong index = new AtomicLong(0);
        final PeriodicityDetector detector = new DirectPeriodicityDetector.Builder(() -> {
            final long n = index.getAndIncrement();
            if (n < 1000) {
                return BigInteger.valueOf(n);
            }
            return BigInteger.valueOf(n * n).mod(seventyOne);
        }).build();

        assertTrue(detector.detect());
        assertEquals(71, detector.getDetectedPeriodLength());
        assertEquals(BigInteger.valueOf((230_001L * 230_001L) % 71),
                detector.getSequenceElement(BigInteger.valueOf(230_001L)));
    }

    @Test
    public void moreComplicatedShouldNotDetectPeriods() {
        final AtomicLong index = new AtomicLong(0);
        final PeriodicityDetector detector = new DirectPeriodicityDetector.Builder(() -> {
            final long n = index.getAndIncrement();
            return BigInteger.valueOf(n * n);
        }).build();

        assertFalse(detector.detect());
    }

}