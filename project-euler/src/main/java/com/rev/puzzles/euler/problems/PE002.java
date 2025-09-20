package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.math.seq.CachingFib;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

public final class PE002 {

    @PeProblem(number = 2)
    @PeTestData(inputs = {"89"}, solutions = {"44"})
    public long sumOfEvenFibNumbers(final ProblemResourceLoader<List<String>> inputs) {
        return sumOfEvenFibNumbers(Integer.parseInt(inputs.resources().get(0)));
    }

    private long sumOfEvenFibNumbers(final int limit) {
        final CachingFib cachingFib = new CachingFib(1, 2);
        final AtomicLong sumOfEvenTerms = new AtomicLong();
        final BiConsumer<Integer, Long> consumer = (index, fib) -> {
            if (fib % 2 == 0) {
                sumOfEvenTerms.addAndGet(fib);
            }
        };
        cachingFib.consumeLimit(limit, consumer);
        return sumOfEvenTerms.get();
    }

}
