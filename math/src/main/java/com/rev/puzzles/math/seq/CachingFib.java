package com.rev.puzzles.math.seq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.BiConsumer;

public final class CachingFib {

    public static final double GOLDEN_RATIO = (1 + Math.sqrt(5)) / 2;

    private final ArrayList<Long> fibs = new ArrayList<>();

    public CachingFib(final long a, final long b) {
        fibs.add(a);
        fibs.add(b);
    }

    public long fibN(final int n) {
        if (n < fibs.size()) {
            return fibs.get(n);
        }
        fibs.ensureCapacity(n + 1);
        for (int i = fibs.size() - 1; i <= n; i++) {
            fibs.add(fibs.get(i) + fibs.get(i - 1));
        }
        return fibs.get(n);
    }

    public int fibLimit(final long limit) {
        if (limit > fibs.get(fibs.size() - 1)) {
            final int estimatedIndex = estimateIndex(limit);
            fibN(estimatedIndex);
            while (limit > fibs.get(fibs.size() - 1)) {
                fibN(fibs.size() + 1);
            }
        }

        final int index = Collections.binarySearch(fibs, limit);
        if (index < 0) {
            return -(index + 1) - 1;
        }
        return index;
    }

    private int estimateIndex(final long limit) {
        return (int) ((Math.log(limit) - Math.log(fibs.get(1) - fibs.get(0) / GOLDEN_RATIO) / Math.log(GOLDEN_RATIO))
                + 1);
    }

    public void consumeN(final int n, final BiConsumer<Integer, Long> consumer) {
        fibN(n);
        for (int i = 0; i < n; i++) {
            consumer.accept(i, fibs.get(i));
        }
    }

}
