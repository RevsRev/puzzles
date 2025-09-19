package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L059 {
    @LeetProblem(number = 59)
    public int[][] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return generateMatrix((int) problemResourceLoader.resources()[0]);
    }

    public int[][] generateMatrix(final int n) {
        final int[][] retval = new int[n][n];
        final int mid = (n - 1) / 2;
        int cellNum = 1;
        for (int i = 0; i <= mid; i++) {
            for (int j = i; j <= n - 1 - i; j++) {
                retval[i][j] = cellNum;
                cellNum++;
            }
            for (int j = i + 1; j < n - 1 - i; j++) {
                retval[j][n - 1 - i] = cellNum;
                cellNum++;
            }
            for (int j = n - 1 - i; j > i; j--) {
                retval[n - 1 - i][j] = cellNum;
                cellNum++;
            }
            for (int j = n - 1 - i; j > i; j--) {
                retval[j][i] = cellNum;
                cellNum++;
            }
        }
        return retval;
    }
}
