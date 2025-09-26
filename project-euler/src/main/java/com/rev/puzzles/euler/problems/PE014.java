package com.rev.puzzles.euler.problems;

import com.rev.puzzles.euler.framework.PeProblem;
import com.rev.puzzles.euler.framework.PeTestData;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.utils.dp.DynamicProgram;

import java.util.List;
import java.util.function.BiFunction;

public final class PE014 {

    @PeProblem(number = 14)
    @PeTestData(inputs = {"10"}, solutions = {"9"})
    public long longestCollatzSequence(final ProblemResourceLoader<List<String>> inputs) {
        return longestCollatzSequence(Long.parseLong(inputs.resources().get(0)));
    }

    private long longestCollatzSequence(final long target) {

        final CollatzDpFunction func = new CollatzDpFunction();

        DynamicProgram<Long, Long> program = new DynamicProgram<>(func);

        for (long l = 1; l < target; l++) {
            program.compute(l);
        }

        return func.longestChainStartingValue;
    }

    private static final class CollatzDpFunction implements BiFunction<DynamicProgram<Long, Long>, Long, Long> {

        private Long longestChainLength = 0L;
        private Long longestChainStartingValue = 0L;

        @Override
        public Long apply(final DynamicProgram<Long, Long> cache, final Long input) {
            if (input == 1L) {
                return input;
            }

            Long value;
            if (input % 2 == 0) {
                value = 1 + cache.compute(input / 2);
            } else {
                value = 1 + cache.compute(3 * input + 1);
            }

            if (value > longestChainLength) {
                longestChainLength = value;
                longestChainStartingValue = input;
            }
            return value;
        }
    }

}
