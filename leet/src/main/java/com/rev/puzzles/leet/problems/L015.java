package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class L015 {
    @LeetProblem(number = 15)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return threeSum((int[]) problemResourceLoader.resources()[0]);
    }

    public List<List<Integer>> threeSum(final int[] nums) {
        final List<List<Integer>> retval = new ArrayList<>();
        Arrays.sort(nums);
        int iIndex = 0;
        while (iIndex < nums.length - 2 && nums[iIndex] <= 0) {
            final int i = nums[iIndex];
            int jIndex = iIndex + 1;
            while (jIndex < nums.length - 1 && nums[iIndex] + nums[jIndex] <= 0) {
                final int j = nums[jIndex];
                final int k = -(i + j);

                if (Arrays.binarySearch(nums, jIndex + 1, nums.length, k) >= 0) {
                    retval.add(List.of(i, j, k));
                }
                while (jIndex < nums.length - 1 && nums[jIndex] == j && nums[iIndex] + nums[jIndex] <= 0) {
                    jIndex++;
                }
            }
            while (iIndex < nums.length - 2 && nums[iIndex] == i && nums[iIndex] <= 0) {
                iIndex++;
            }
        }
        return retval;
    }
}
