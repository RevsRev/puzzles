package com.rev.puzzles.math.part;

import com.rev.puzzles.algo.DynamicProgram;
import com.rev.puzzles.math.seq.Pentagonal;

import java.util.function.BiFunction;

public final class IntegerPartitions {

    private final DynamicProgram<Long, Long> dynamicProgram;

    private IntegerPartitions(final DynamicProgram<Long, Long> dynamicProgram) {
        this.dynamicProgram = dynamicProgram;
    }

    public static IntegerPartitions create() {

        final IpDpFunc dpFunc = new IpDpFunc();
        final DynamicProgram<Long, Long> dynamicProgram = new DynamicProgram<>(dpFunc);

        return new IntegerPartitions(dynamicProgram);
    }

    public long partitions(final long n) {
        return dynamicProgram.compute(n);
    }

    private static final class IpDpFunc implements BiFunction<DynamicProgram<Long, Long>, Long, Long> {

        @Override
        public Long apply(final DynamicProgram<Long, Long> dp, final Long n) {
            if (n < 0) {
                return 0L;
            }
            if (n == 0L) {
                return 1L;
            }

            long k = 1;
            long pent = Pentagonal.pentagonal(k);
            long partitions = 0;
            while (pent <= n) {
                long sign = k % 2 == 0 ? -1 : 1;
                partitions += sign * dp.compute(n - pent);
                if (k > 0) {
                    k = -k;
                } else {
                    k = -k + 1;
                }
                pent = Pentagonal.pentagonal(k);
            }
            return partitions;
        }
    }
}
