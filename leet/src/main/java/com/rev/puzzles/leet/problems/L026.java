package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L026 {
    @LeetProblem(number = 26)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return removeDuplicates((int[]) problemResourceLoader.resources()[0]);
    }

    public int removeDuplicates(final int[] nums) {
        int shift = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length - 1) {
                nums[i - shift] = nums[i];
                continue;
            }

            if (nums[i] == nums[i + 1]) {
                shift += 1;
            } else {
                nums[i - shift] = nums[i];
            }
        }
        return nums.length - shift;
    }
}
