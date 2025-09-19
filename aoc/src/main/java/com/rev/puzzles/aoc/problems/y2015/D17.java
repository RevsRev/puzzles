package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.util.set.Buckets;

import java.util.List;
import java.util.function.Consumer;

public final class D17 {

    private static final long TARGET = 150;

    @AocProblemI(year = 2015, day = 17, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> strings = resourceLoader.resources();
        final long[] buckets = new long[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            buckets[i] = Long.parseLong(strings.get(i));
        }
        return Buckets.fillEntireCombinations(buckets, TARGET, false);
    }

    @AocProblemI(year = 2015, day = 17, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
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
