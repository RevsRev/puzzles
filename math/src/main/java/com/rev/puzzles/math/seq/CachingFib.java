package com.rev.puzzles.math.seq;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public final class CachingFib {

    private final ArrayList<Long> fibs = new ArrayList<>();

    public CachingFib(final long a, final long b) {
        fibs.add(a);
        fibs.add(b);
    }

    public long fibN(final int n) {
        final int index = n - 1;
        if (index < fibs.size()) {
            return fibs.get(index);
        }
        fibs.ensureCapacity(n);
        for (int i = fibs.size() - 1; i <= index; i++) {
            fibs.add(fibs.get(i) + fibs.get(i - 1));
        }
        return fibs.get(index);
    }

    public void consume(final int n, final BiConsumer<Integer, Long> consumer) {
        fibN(n);
        for (int i = 1; i <= n; i++) {
            consumer.accept(i, fibs.get(i - 1));
        }
    }

}
