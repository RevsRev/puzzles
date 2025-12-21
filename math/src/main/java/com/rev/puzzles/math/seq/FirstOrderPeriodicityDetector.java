package com.rev.puzzles.math.seq;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Supplier;

public final class FirstOrderPeriodicityDetector implements PeriodicityDetector {

    private final DifferenceSequence differenceSequence;
    private final DirectPeriodicityDetector differenceDetector;

    private FirstOrderPeriodicityDetector(final DifferenceSequence differenceSequence,
                                          final DirectPeriodicityDetector differenceDetector) {
        this.differenceSequence = differenceSequence;
        this.differenceDetector = differenceDetector;
    }

    @Override
    public boolean detect() {
        return differenceDetector.detect();
    }

    @Override
    public int getDetectedPeriodLength() {
        return differenceDetector.getDetectedPeriodLength();
    }

    @Override
    public BigInteger getSequenceElement(final BigInteger n) {
        int detectedPeriodLength = getDetectedPeriodLength();

        if (n.compareTo(BigInteger.valueOf(differenceSequence.cachedSequence.length)) < 1) {
            return differenceSequence.cachedSequence[n.intValue()];
        }

        //length - 1 is the last index we know the exact value of (in our cached sequence)
        final BigInteger lookAhead = n.subtract(BigInteger.valueOf(differenceSequence.cachedSequence.length - 1));

        final List<BigInteger> differencesSequence = differenceDetector.getDetectedPeriodicSubsequence();
        final BigInteger periodLength = BigInteger.valueOf(detectedPeriodLength);
        final int mod = lookAhead.mod(periodLength).intValue();

        /*
        To make things easier, let's start our calculation at n - mod. This way, we only have to add the sum of the
        differences sequence an integer number of times.
        */
        final int start = differenceSequence.cachedSequence.length - 1 - (detectedPeriodLength - mod);
        final BigInteger quotient = n.subtract(BigInteger.valueOf(start)).divide(periodLength);

        BigInteger blockSum = BigInteger.ZERO;
        for (final BigInteger diff : differencesSequence) {
            blockSum = blockSum.add(diff);
        }

        return differenceSequence.cachedSequence[start].add(quotient.multiply(blockSum));
    }

    private static final class DifferenceSequence implements Supplier<BigInteger> {

        private final Supplier<BigInteger> sequence;

        private int index = 0;
        private final BigInteger[] cachedSequence;

        private DifferenceSequence(final Supplier<BigInteger> sequence, final int maxThreshold) {
            this.sequence = sequence;
            this.cachedSequence = new BigInteger[maxThreshold];
        }

        @Override
        public BigInteger get() {
            if (index == 0) {
                cachedSequence[index++] = sequence.get();
            }

            final BigInteger previous = cachedSequence[index - 1];
            final BigInteger next = sequence.get();
            cachedSequence[index++] = next;
            return next.subtract(previous);
        }
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private static final int DEFAULT_MAX_PERIOD = 1_000;
        private static final int DEFAULT_THRESHOLD = 100_000;
        private static final int DEFAULT_PERIOD_DETECTION_CYCLES = 5;

        private final Supplier<BigInteger> sequence;

        private int maxPeriod = DEFAULT_MAX_PERIOD;
        private int maxThreshold = DEFAULT_THRESHOLD;
        private int periodDetectionCycles = DEFAULT_PERIOD_DETECTION_CYCLES;

        public Builder(final Supplier<BigInteger> sequence) {
            this.sequence = sequence;
        }

        public FirstOrderPeriodicityDetector.Builder setMaxPeriod(final int maxPeriod) {
            this.maxPeriod = maxPeriod;
            return this;
        }

        public FirstOrderPeriodicityDetector.Builder setMaxThreshold(final int maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        public FirstOrderPeriodicityDetector.Builder setPeriodDetectionCycles(final int periodDetectionCycles) {
            this.periodDetectionCycles = periodDetectionCycles;
            return this;
        }

        public FirstOrderPeriodicityDetector build() {
            validate();

            final DifferenceSequence differenceSequence = new DifferenceSequence(sequence, maxThreshold);
            final DirectPeriodicityDetector directDetector =
                    new DirectPeriodicityDetector.Builder(differenceSequence).setMaxPeriod(maxPeriod)
                            .setMaxThreshold(maxThreshold - 1).setPeriodDetectionCycles(periodDetectionCycles).build();

            return new FirstOrderPeriodicityDetector(differenceSequence, directDetector);
        }

        private void validate() {
            if (maxThreshold < maxPeriod * periodDetectionCycles) {
                throw new IllegalStateException(
                        "Illegal arguments. Must have maxThreshold >= maxPeriod * periodDetectionCycles");
            }
        }
    }
}
