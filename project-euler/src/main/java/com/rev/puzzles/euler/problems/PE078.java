package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.part.IntegerPartitions;

import java.math.BigInteger;
import java.util.List;

public final class PE078 {

    @PeProblem(number = 78)
    @PeTestData(inputs = {"7"}, solutions = {"5"})
    public BigInteger coinPartitions(final ProblemResourceLoader<List<String>> inputs) {
        return coinPartitions(new BigInteger(inputs.resources().get(0)));
    }

    private BigInteger coinPartitions(final BigInteger divisor) {
        final IntegerPartitions partitions = IntegerPartitions.create();
        BigInteger n = BigInteger.valueOf(0L);

        while (!partitions.partitions(n).mod(divisor).equals(BigInteger.ZERO)) {
            n = n.add(BigInteger.ONE);
        }
        return n;
    }
}
