package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.Arrays;

public final class L016 {

    @LeetProblem(number = 16)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return threeSumClosest((int[]) problemResourceLoader.resources()[0],
                (Integer) problemResourceLoader.resources()[1]);
    }

    public int threeSumClosest(final int[] nums, final int target) {
        final int len = nums.length;

        int closestFirst = -1;
        int closestSecond = -1;
        int closestThird = -1;

        int closestDiff = Integer.MAX_VALUE;


        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    final int sum = nums[i] + nums[j] + nums[k];
                    final int diff = Math.abs(target - sum);
                    if (diff < closestDiff) {
                        closestFirst = i;
                        closestSecond = j;
                        closestThird = k;
                        closestDiff = diff;
                    }
                    if (closestDiff == 0) {
                        return sum;
                    }
                }
            }
        }
        return nums[closestFirst] + nums[closestSecond] + nums[closestThird];
    }

    // Moral of the story - sort the array first ! (Olog(n))
    public int betterSolution(final int[] nums, final int target) {
        Arrays.sort(nums);
        int closestSum = nums[0] + nums[1] + nums[2]; // Initialize closest sum with the sum of the first three elements

        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;

            while (j < k) {
                final int sum = nums[i] + nums[j] + nums[k];

                if (Math.abs(target - sum) < Math.abs(target - closestSum)) {
                    closestSum = sum; // Update closest sum if the current sum is closer to the target
                }

                if (sum < target) {
                    j++; // Increment j to increase the sum
                } else {
                    k--; // Decrement k to decrease the sum
                }
            }
        }

        return closestSum;
    }

}
