package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L048 {
    @LeetProblem(number = 48)
    public Object[] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        rotate((int[][]) problemResourceLoader.resources()[0]);
        return problemResourceLoader.resources();
    }

    public void rotate(final int[][] matrix) {
        final int limit = matrix.length / 2;
        for (int i = 0; i < limit; i++) {
            final int end = matrix.length - i - 1;
            for (int j = i; j < end; j++) {
                rotate(matrix, i, j);
            }
        }
    }

    private void rotate(final int[][] matrix, final int i, final int j) {
        final int n = matrix.length - 1;
        final int first = matrix[i][j];
        matrix[i][j] = matrix[n - j][i];
        matrix[n - j][i] = matrix[n - i][n - j];
        matrix[n - i][n - j] = matrix[j][n - i];
        matrix[j][n - i] = first;
    }

}
