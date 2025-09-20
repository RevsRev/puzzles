package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

class CachingFibTest {

    @Test
    public void cachingFibTest() {
        // 1, 2, 3, 4,  5,  6
        // 2, 5, 7, 12, 19, 31
        final CachingFib fib = new CachingFib(2, 5);
        Assertions.assertEquals(31, fib.fibN(6));
    }

    @Test
    public void testConsumer() {
        // 1, 2, 3, 4, 5, 6,  7
        // 1, 2, 3, 5, 8, 13, 21
        final CachingFib cachingFib = new CachingFib(1, 2);
        final AtomicLong oddSum = new AtomicLong();
        final BiConsumer<Integer, Long> consumer = (index, fib) -> {
            if (index % 2 == 1) {
                oddSum.addAndGet(fib);
            }
        };
        cachingFib.consume(7, consumer);
        Assertions.assertEquals(33, oddSum.get());
    }

}