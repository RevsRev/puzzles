package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.List;

public final class L078 {
    @LeetProblem(number = 78)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return subsets((int[]) problemResourceLoader.resources()[0]);
//        return subsetsAlt((int[])problemResourceLoader.resources()[0]);
    }

    public List<List<Integer>> subsets(final int[] nums) {
        final List<List<Integer>> retval = new ArrayList<>();
        retval.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            final List<List<Integer>> newSubsets = new ArrayList<>();
            for (int j = 0; j < retval.size(); j++) {
                final List<Integer> copy = new ArrayList<>();
                copy.addAll(retval.get(j));
                copy.add(nums[i]);
                newSubsets.add(copy);
            }
            retval.addAll(newSubsets);
        }
        return retval;
    }

    public List<List<Integer>> subsetsAlt(final int[] nums) {
        final List<List<Integer>> retval = new ArrayList<>();
        final int limit = 1 << nums.length;
        for (int i = 0; i < limit; i++) {
            final List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < nums.length; j++) {
                if (((1 << j) & i) != 0) {
                    subset.add(nums[j]);
                }
            }
            retval.add(subset);
        }
        return retval;
    }
}
