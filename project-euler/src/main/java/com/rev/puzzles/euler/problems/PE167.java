package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.math.seq.FirstOrderPeriodicityDetector;
import com.rev.puzzles.math.seq.Ulam;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class PE167 {

    @PeProblem(number = 167)
    public BigInteger investigatingUlamSequences(final ProblemResourceLoader<List<String>> inputs) {
        return investigatingUlamSequences(new BigInteger(inputs.resources().get(0)));
    }

    private BigInteger investigatingUlamSequences(final BigInteger ulamSequenceIndex) {

        BigInteger sum = BigInteger.ZERO;

        for (long n = 2; n <= 10; n++) {
            final Ulam ulam = Ulam.create(2, 2 * n + 1);

            final AtomicInteger index = new AtomicInteger(0);

            final FirstOrderPeriodicityDetector detector = new FirstOrderPeriodicityDetector.Builder(
                    () -> BigInteger.valueOf(ulam.at(index.getAndIncrement())))
                    .setMaxThreshold(11_000_000)
                    .setMaxPeriod(2_100_000)
                    .build();

            if (!detector.detect()) {
                throw new ProblemExecutionException("No periodicity detected for U(%s,%s)".formatted(2, 2 * n + 1));
            }

            final BigInteger sequenceElement = detector.getSequenceElement(
                    ulamSequenceIndex.subtract(BigInteger.ONE)); //Our sequence is zero indexed

            sum = sum.add(sequenceElement);
        }
        return sum;
    }

}
