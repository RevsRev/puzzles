package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class L056 {
    @LeetProblem(number = 56)
    public int[][] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return merge((int[][]) problemResourceLoader.resources()[0]);
    }

    public int[][] merge(final int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));

        int[] arr = intervals[0];
        final List<int[]> merged = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            final int[] next = intervals[i];
            if (next[0] <= arr[1]) {
                arr[1] = Math.max(next[1], arr[1]);
            } else {
                merged.add(arr);
                arr = next;
            }
        }
        merged.add(arr);
        return merged.toArray(new int[merged.size()][2]);
    }
}
