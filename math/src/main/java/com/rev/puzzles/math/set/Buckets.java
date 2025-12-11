package com.rev.puzzles.math.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Consumer;

public final class Buckets {

    private Buckets() {
    }

    public static long fillEntireCombinations(final long[] buckets, final long target, final boolean reuseBuckets) {
        return fillEntireCombinations(buckets, target, reuseBuckets, sol -> {
        });
    }

    public static long fillEntireCombinations(
            final long[] buckets,
            final long target,
            final boolean reuseBuckets,
            final Consumer<List<Long>> solutionListener) {

        long[] sortedBuckets = Arrays.copyOf(buckets, buckets.length);
        Arrays.sort(sortedBuckets);
        return fillEntireCombinations(
                sortedBuckets,
                target,
                reuseBuckets,
                sortedBuckets.length - 1,
                0,
                new Stack<>(),
                solutionListener);
    }

    private static long fillEntireCombinations(
            final long[] sortedBuckets,
            final long target,
            final boolean reuseBuckets,
            final int index,
            final long sum,
            final Stack<Long> solution,
            final Consumer<List<Long>> solutionListener) {

        if (index < 0) {
            if (sum == target) {
                solutionListener.accept(new ArrayList<>(solution));
                return 1;
            }
            return 0;
        }

        long numCombinations = 0;
        final long bucketSize = sortedBuckets[index];
        final long limit = reuseBuckets ? (target - sum) / bucketSize : Math.min(1, (target - sum) / bucketSize);
        for (int i = 0; i <= limit; i++) {
            for (int j = 0; j < i; j++) {
                solution.push(bucketSize);
            }
            numCombinations += fillEntireCombinations(
                    sortedBuckets,
                    target,
                    reuseBuckets,
                    index - 1,
                    sum + i * bucketSize,
                    solution,
                    solutionListener);

            for (int j = 0; j < i; j++) {
                solution.pop();
            }
        }
        return numCombinations;
    }

}
