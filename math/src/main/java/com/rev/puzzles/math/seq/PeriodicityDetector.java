package com.rev.puzzles.math.seq;

import java.math.BigInteger;

public interface PeriodicityDetector {
    boolean detect();

    int getDetectedPeriodLength();

    BigInteger getSequenceElement(BigInteger n);
}
