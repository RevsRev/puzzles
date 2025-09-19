package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class L057 {
    @LeetProblem(number = 57)
    public int[][] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return insert((int[][]) problemResourceLoader.resources()[0], (int[]) problemResourceLoader.resources()[1]);
    }

    public int[][] insert(final int[][] intervals, final int[] newInterval) {
        int insertionPoint = Arrays.binarySearch(intervals, newInterval, Comparator.comparingInt(i -> i[0]));
        if (insertionPoint < 0) {
            insertionPoint = -insertionPoint - 1;
        }
        final int[][] newIntervals = new int[intervals.length + 1][2];
        for (int i = 0; i < newIntervals.length; i++) {
            if (i < insertionPoint) {
                newIntervals[i] = intervals[i];
            } else if (i == insertionPoint) {
                newIntervals[i] = newInterval;
            } else {
                newIntervals[i] = intervals[i - 1];
            }
        }
        return merge(newIntervals);
    }

    //Shamelessly ripped off of L056
    public int[][] merge(final int[][] intervals) {
        //Already sorted so don't need this here.
        //Arrays.sort(intervals, Comparator.comparingInt(i -> i[0]));

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
