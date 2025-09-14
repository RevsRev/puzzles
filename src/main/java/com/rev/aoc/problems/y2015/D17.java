package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.set.Buckets;

import java.util.List;
import java.util.function.Consumer;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D17 extends AocProblem<Long, Long> {

    private static final long TARGET = 150;

    @AocProblemI(year = 2015, day = 17, part = 1)
    @Override
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> strings = resourceLoader.resources();
        final long[] buckets = new long[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            buckets[i] = Long.parseLong(strings.get(i));
        }
        return Buckets.fillEntireCombinations(buckets, TARGET, false);
    }

    @AocProblemI(year = 2015, day = 17, part = 2)
    @Override
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<String> strings = resourceLoader.resources();
        final long[] buckets = new long[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            buckets[i] = Long.parseLong(strings.get(i));
        }

        SolutionListener listener = new SolutionListener();
        Buckets.fillEntireCombinations(buckets, TARGET, false, listener);
        return listener.minimumSolutionCount;
    }

    private static final class SolutionListener implements Consumer<List<Long>> {

        private long minimumSolutionLength = Long.MAX_VALUE;
        private long minimumSolutionCount = 0;

        @Override
        public void accept(final List<Long> longs) {
            if (longs.size() < minimumSolutionLength) {
                minimumSolutionLength = longs.size();
                minimumSolutionCount = 1;
            } else if (longs.size() == minimumSolutionLength) {
                minimumSolutionCount++;
            }
        }
    }
}
