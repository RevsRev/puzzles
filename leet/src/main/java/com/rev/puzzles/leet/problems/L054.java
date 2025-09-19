package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;

public final class L054 {
    @LeetProblem(number = 54)
    public List<Integer> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return spiralOrder((int[][]) problemResourceLoader.resources()[0]);
    }

    public List<Integer> spiralOrder(final int[][] matrix) {
        final List<Integer> retval = new ArrayList<>();
        spiralOrder(matrix, retval, 0, 0);
        return retval;
    }

    public void spiralOrder(final int[][] matrix, final List<Integer> ordering, int iCornerIndex, int jCornerIndex) {
        final int width = matrix[0].length;
        final int height = matrix.length;
        final int iLen = height - iCornerIndex - 1;
        final int jLen = width - jCornerIndex - 1;
        for (int j = jCornerIndex; j <= jLen; j++) {
            ordering.add(matrix[iCornerIndex][j]);
        }
        for (int i = iCornerIndex + 1; i <= iLen; i++) {
            ordering.add(matrix[i][jLen]);
        }
        for (int j = jLen - 1; j >= jCornerIndex + 1 && iLen != iCornerIndex; j--) {
            ordering.add(matrix[iLen][j]);
        }
        for (int i = iLen; i >= iCornerIndex + 1 && jLen != jCornerIndex; i--) {
            ordering.add(matrix[i][jCornerIndex]);
        }
        iCornerIndex++;
        jCornerIndex++;
        if (iCornerIndex <= (height - 1) / 2 && jCornerIndex <= (width - 1) / 2) {
            spiralOrder(matrix, ordering, iCornerIndex, jCornerIndex);
        }
    }
}
