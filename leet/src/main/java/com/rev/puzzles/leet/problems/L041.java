package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L041 {
    @LeetProblem(number = 41)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return firstMissingPositive((int[]) problemResourceLoader.resources()[0]);
    }


    public int firstMissingPositive(final int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                nums[i] = 0;
            }
        }

        final int intMin = Integer.MIN_VALUE;
        final int notIntMin = ~intMin;

        for (int i = 0; i < nums.length; i++) {
            final int num = nums[i];
            final int unsignedNum = num & notIntMin;
            if (unsignedNum <= nums.length && unsignedNum > 0) {
                nums[unsignedNum - 1] = nums[unsignedNum - 1] | intMin;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            final int num = nums[i];
            if (num >= 0) {
                return i + 1;
            }
        }
        return nums.length + 1;
    }
}
