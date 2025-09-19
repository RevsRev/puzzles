package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class L047 {
    @LeetProblem(number = 47)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return permuteUnique((int[]) problemResourceLoader.resources()[0]);
    }

    public List<List<Integer>> permuteUnique(final int[] nums) {
        if (nums.length == 1) {
            return List.of(List.of(nums[0]));
        }
        final Set<List<Integer>> results = new LinkedHashSet<>();
        permute(nums, 0, results);
        return toList(results);
    }

    private void permute(final int[] nums, final int start, final Set<List<Integer>> results) {
        if (start == nums.length - 2) {
            results.add(toList(nums));
            swap(nums, start, start + 1);
            results.add(toList(nums));
            swap(nums, start, start + 1);
            return;
        }

        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            permute(nums, start + 1, results);
            swap(nums, start, i);
        }
    }

    private void swap(final int[] nums, final int i, final int j) {
        if (i == j) {
            return;
        }
        final int a = nums[i];
        nums[i] = nums[j];
        nums[j] = a;
    }

    private List<Integer> toList(final int[] nums) {
        final List<Integer> retval = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            retval.add(nums[i]);
        }
        return retval;
    }

    private List<List<Integer>> toList(final Set<List<Integer>> results) {
        final List<List<Integer>> asList = new ArrayList<>();
        for (final List<Integer> result : results) {
            asList.add(result);
        }
        return asList;
    }
}
