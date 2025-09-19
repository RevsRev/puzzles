package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L055 {
    @LeetProblem(number = 55)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return canJump((int[]) problemResourceLoader.resources()[0]);
    }

    //In general, we can jump to a range
    //We then want to determine the next range we can reach
    public boolean canJump(final int[] nums) {
        if (nums.length == 1) {
            return true;
        }

        int jumpRangeMin = 0;
        int jumpRangeMax = 0;
        while (true) {
            int nextMax = jumpRangeMax;
            for (int i = jumpRangeMin; i <= jumpRangeMax; i++) {
                nextMax = Math.max(i + nums[i], nextMax);
            }
            if (nextMax > jumpRangeMax) {
                jumpRangeMin = jumpRangeMax + 1;
                jumpRangeMax = nextMax;
            } else {
                break;
            }

            if (nextMax >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }
}
