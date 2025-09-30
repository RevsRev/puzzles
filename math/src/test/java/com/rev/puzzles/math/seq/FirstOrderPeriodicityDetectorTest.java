package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class FirstOrderPeriodicityDetectorTest {

    @Test
    public void shouldDetectDirectPeriodicSequence() {
        final BigInteger[] sequence = new BigInteger[30];
        for (int i = 0; i < 30; i++) {
            sequence[i] = BigInteger.valueOf(i % 3);
        }

        final AtomicInteger index = new AtomicInteger(0);

        final FirstOrderPeriodicityDetector detector = new FirstOrderPeriodicityDetector.Builder(
                () -> sequence[index.getAndIncrement()])
                .setMaxPeriod(3)
                .setMaxThreshold(30)
                .setPeriodDetectionCycles(5)
                .build();

        assertTrue(detector.detect());
        assertEquals(3, detector.getDetectedPeriodLength());
        assertEquals(BigInteger.ONE, detector.getSequenceElement(BigInteger.valueOf(100)));
    }

    @Test
    public void shouldDetectLinearSequence() {
        final BigInteger[] sequence = new BigInteger[30];
        for (int i = 0; i < 30; i++) {
            sequence[i] = BigInteger.valueOf(2 * i);
        }

        final AtomicInteger index = new AtomicInteger(0);

        final FirstOrderPeriodicityDetector detector = new FirstOrderPeriodicityDetector.Builder(
                () -> sequence[index.getAndIncrement()])
                .setMaxPeriod(3)
                .setMaxThreshold(30)
                .setPeriodDetectionCycles(5)
                .build();

        assertTrue(detector.detect());
        assertEquals(1, detector.getDetectedPeriodLength());
        assertEquals(BigInteger.valueOf(200), detector.getSequenceElement(BigInteger.valueOf(100)));
    }

    @Test
    public void moreComplicatedShouldDetectPeriods() {
        final BigInteger seventyOne = BigInteger.valueOf(71);
        final AtomicLong index = new AtomicLong(0);
        final FirstOrderPeriodicityDetector detector = new FirstOrderPeriodicityDetector.Builder(() -> {
            final long n = index.getAndIncrement();
            if (n < 1000) {
                return BigInteger.valueOf(n);
            }
            //combination of linear + mod, gives an increasing sequence with periodic differences
            return BigInteger.valueOf(n).add(BigInteger.valueOf(n * n).mod(seventyOne));
        }).build();

        assertTrue(detector.detect());
        assertEquals(71, detector.getDetectedPeriodLength());
        final BigInteger testIndex = BigInteger.valueOf(230_001L);
        assertEquals(
                testIndex.add(testIndex.multiply(testIndex).mod(seventyOne)),
                detector.getSequenceElement(testIndex)
        );
    }

    @Test
    public void testUlamHasPeriodicDifferences() {
        final Ulam ulam = new Ulam(BigInteger.valueOf(2), BigInteger.valueOf(5));
        final AtomicInteger index = new AtomicInteger(0);

        final FirstOrderPeriodicityDetector detector = new FirstOrderPeriodicityDetector.Builder(
                () -> ulam.at(index.getAndIncrement())
        )
                .setMaxThreshold(1001)
                .setMaxPeriod(200)
                .build();

        assertTrue(detector.detect());
    }

}