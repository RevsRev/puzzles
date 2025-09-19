package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L045 {
    @LeetProblem(number = 45)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return jump((int[]) problemResourceLoader.resources()[0]);
    }

    public int jump(final int[] nums) {
        final int[] jumpsCache = new int[nums.length];
        for (int i = 0; i < jumpsCache.length; i++) {
            jumpsCache[i] = Integer.MAX_VALUE - 1;
        }
        jumpsCache[nums.length - 1] = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            final int jumpSize = nums[i];
            final int end = Math.min(nums.length - 1, i + jumpSize);
            for (int j = i; j <= end; j++) {
                jumpsCache[i] = Math.min(jumpsCache[j] + 1, jumpsCache[i]);
            }
        }
        return jumpsCache[0];
    }
}
