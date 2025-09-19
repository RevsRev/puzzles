package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L031 {
    @LeetProblem(number = 31)
    public Class<Void> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        nextPermutation((int[]) problemResourceLoader.resources()[0]);
        return Void.TYPE;
    }

    public void nextPermutation(final int[] nums) {
        int i = nums.length - 1;
        while (i > 0 && nums[i - 1] >= nums[i]) {
            i--;
        }

        if (i == 0) {
            reverse(i, nums);
        } else {
            int j = nums.length - 1;
            while (j > i) {
                if (nums[j] > nums[i - 1]) {
                    break;
                }
                j--;
            }
            final int a = nums[i - 1];
            nums[i - 1] = nums[j];
            nums[j] = a;
            reverse(i, nums);
        }
    }

    private void reverse(final int startIndex, final int[] nums) {
        for (int i = 0; i + startIndex < nums.length - 1 - i; i++) {
            final int a = nums[startIndex + i];
            nums[startIndex + i] = nums[nums.length - 1 - i];
            nums[nums.length - 1 - i] = a;
        }
    }
}
