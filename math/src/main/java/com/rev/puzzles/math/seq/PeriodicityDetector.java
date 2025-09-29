package com.rev.puzzles.math.seq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class PeriodicityDetector {

    private final int maxPeriod;
    private final int maxThreshold;
    private final int periodDetectionCycles;
    private final Supplier<BigInteger> sequence;

    private boolean run = false;
    private BigInteger[] cachedSequence;
    private int detectedPeriodLength = -1;

    private PeriodicityDetector(
            final int maxPeriod,
            final int maxThreshold,
            final int periodDetectionCycles,
            final Supplier<BigInteger> sequence
    ) {
        this.maxPeriod = maxPeriod;
        this.maxThreshold = maxThreshold;
        this.periodDetectionCycles = periodDetectionCycles;
        this.sequence = sequence;
    }

    public boolean detect() {
        run = true;

        cachedSequence = new BigInteger[maxThreshold];

        for (int i = 0; i < maxThreshold; i++) {
            cachedSequence[i] = sequence.get();
        }

        final List<BigInteger> subSequence = new ArrayList<>();
        for (int i = 0; i < maxPeriod; i++) {
            subSequence.add(cachedSequence[cachedSequence.length - 1 - i]);
            final int periodCandidate = i + 1;
            boolean periodDetected = true;
            for (int j = 1; j < periodDetectionCycles; j++) {
                for (int k = 0; k < periodCandidate; k++) {
                    if (!cachedSequence[cachedSequence.length - 1 - periodCandidate * j - k]
                            .equals(subSequence.get(k))) {
                        periodDetected = false;
                        break;
                    }
                }
                if (!periodDetected) {
                    break;
                }
            }

            if (periodDetected) {
                detectedPeriodLength = periodCandidate;
                break;
            }
        }

        return detectedPeriodLength != -1;
    }

    public int getDetectedPeriodLength() {
        validate();
        return detectedPeriodLength;
    }

    public BigInteger getSequenceElement(final BigInteger n) {
        validate();

        if (n.compareTo(BigInteger.valueOf(cachedSequence.length)) < 1) {
            return cachedSequence[n.intValue()];
        }

        final BigInteger lookAhead = n.subtract(BigInteger.valueOf(cachedSequence.length));
        final int mod = lookAhead.mod(BigInteger.valueOf(detectedPeriodLength)).intValue();
        return cachedSequence[cachedSequence.length - detectedPeriodLength + mod];
    }

    private void validate() {
        if (detectedPeriodLength == -1) {
            if (run) {
                throw new IllegalStateException("No period was detected");
            }
            throw new IllegalStateException("You must run detect first!");
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

        public Builder setMaxPeriod(final int maxPeriod) {
            this.maxPeriod = maxPeriod;
            return this;
        }

        public Builder setMaxThreshold(final int maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        public Builder setPeriodDetectionCycles(final int periodDetectionCycles) {
            this.periodDetectionCycles = periodDetectionCycles;
            return this;
        }

        public PeriodicityDetector build() {
            validate();
            return new PeriodicityDetector(maxPeriod, maxThreshold, periodDetectionCycles, sequence);
        }

        private void validate() {
            if (maxThreshold < maxPeriod * periodDetectionCycles) {
                throw new IllegalStateException(
                        "Illegal arguments. Must have maxThreshold >= maxPeriod * periodDetectionCycles");
            }
        }
    }
}
