package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.Arrays;

public final class L033 {
    @LeetProblem(number = 33)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return search((int[]) problemResourceLoader.resources()[0], (Integer) problemResourceLoader.resources()[1]);
    }

    public int search(final int[] nums, final int target) {
        final int pivot = getPivot(nums);
        int retval = -1;
        if (target >= nums[0]) {
            retval = Arrays.binarySearch(nums, 0, pivot, target);
        } else {
            retval = Arrays.binarySearch(nums, pivot, nums.length, target);
        }

        if (retval < 0) {
            return -1;
        }
        return retval;
    }

    private int getPivot(final int[] nums) {
        final int firstAfterPivot = nums[0];
        int low = 0;
        int high = nums.length - 1;
        int mid = (low + high + 1) / 2;
        while (low < high) {
            if (firstAfterPivot < nums[mid]) {
                low = mid;
            } else {
                high = mid - 1;
            }
            mid = (high + low + 1) / 2;
        }

        return low + 1;
    }
}
