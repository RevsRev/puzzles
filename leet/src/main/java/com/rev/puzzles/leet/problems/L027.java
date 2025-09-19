package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L027 {
    @LeetProblem(number = 27)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return removeElement((int[]) problemResourceLoader.resources()[0],
                (Integer) problemResourceLoader.resources()[1]);
    }

    public int removeElement(final int[] nums, final int val) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                continue;
            }
            nums[j++] = nums[i];
        }
        return j;
    }
}
