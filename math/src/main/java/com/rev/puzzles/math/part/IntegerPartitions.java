package com.rev.puzzles.math.part;

import com.rev.puzzles.algo.dp.DynamicProgram;
import com.rev.puzzles.math.seq.Pentagonal;

import java.math.BigInteger;
import java.util.function.BiFunction;

public final class IntegerPartitions {

    private final DynamicProgram<BigInteger, BigInteger> dynamicProgram;

    private IntegerPartitions(final DynamicProgram<BigInteger, BigInteger> dynamicProgram) {
        this.dynamicProgram = dynamicProgram;
    }

    public static IntegerPartitions create() {

        final IpDpFunc dpFunc = new IpDpFunc();
        final DynamicProgram<BigInteger, BigInteger> dynamicProgram = new DynamicProgram<>(dpFunc);

        return new IntegerPartitions(dynamicProgram);
    }

    public BigInteger partitions(final BigInteger n) {
        return dynamicProgram.compute(n);
    }

    private static final class IpDpFunc
            implements BiFunction<DynamicProgram<BigInteger, BigInteger>, BigInteger, BigInteger> {

        @Override
        public BigInteger apply(final DynamicProgram<BigInteger, BigInteger> dp, final BigInteger n) {
            if (n.compareTo(BigInteger.ZERO) < 0) {
                return BigInteger.ZERO;
            }
            if (n.equals(BigInteger.ZERO)) {
                return BigInteger.ONE;
            }

            long k = 1;
            BigInteger pent = BigInteger.valueOf(Pentagonal.pentagonal(k));
            BigInteger partitions = BigInteger.ZERO;
            while (pent.compareTo(n) <= 0) {
                long sign = k % 2 == 0 ? -1 : 1;
                partitions = partitions.add(dp.compute(n.subtract(pent)).multiply(BigInteger.valueOf(sign)));
                if (k > 0) {
                    k = -k;
                } else {
                    k = -k + 1;
                }
                pent = BigInteger.valueOf(Pentagonal.pentagonal(k));
            }
            return partitions;
        }
    }
}
