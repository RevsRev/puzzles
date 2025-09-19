package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L053 {
    @LeetProblem(number = 53)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return maxSubArray((int[]) problemResourceLoader.resources()[0]);
    }

    public int maxSubArray(final int[] nums) {
        int maxSubArr = nums[0];
        int maxSubArrEndingAtI = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (maxSubArrEndingAtI < 0) {
                maxSubArrEndingAtI = nums[i];
            } else {
                maxSubArrEndingAtI += nums[i];
            }
            maxSubArr = Math.max(maxSubArr, maxSubArrEndingAtI);
        }
        return maxSubArr;
    }
}
