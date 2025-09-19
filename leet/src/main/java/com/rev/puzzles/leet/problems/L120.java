package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;

public final class L120 {
    @LeetProblem(number = 120)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return minimumTotal((List<List<Integer>>) problemResourceLoader.resources()[0]);
    }

    public int minimumTotal(final List<List<Integer>> triangle) {
        final List<Integer[]> results = new ArrayList<>();
        for (int i = 0; i < triangle.size(); i++) {
            final Integer[] result = new Integer[i + 1];
            for (int j = 0; j < i; j++) {
                result[j] = null;
            }
            results.add(result);
        }
        final int end = results.size() - 1;
        int min = minimumTotal(triangle, results, end, 0);
        for (int i = 1; i <= end; i++) {
            min = Math.min(min, minimumTotal(triangle, results, end, i));
        }
        return min;
    }

    private int minimumTotal(final List<List<Integer>> triangle, final List<Integer[]> results, final int i,
                             final int j) {
        if (results.get(i)[j] != null) {
            return results.get(i)[j];
        }

        if (i == 0) {
            results.get(0)[0] = triangle.get(0).get(0);
            return results.get(0)[0];
        }

        if (j != 0) {
            results.get(i)[j] = triangle.get(i).get(j) + minimumTotal(triangle, results, i - 1, j - 1);
        }
        if (j != results.get(i).length - 1) {
            final Integer result = results.get(i)[j];
            final int val = triangle.get(i).get(j) + minimumTotal(triangle, results, i - 1, j);
            if (result == null) {
                results.get(i)[j] = val;
            } else {
                results.get(i)[j] = Math.min(result, val);
            }
        }
        return results.get(i)[j];
    }
}
