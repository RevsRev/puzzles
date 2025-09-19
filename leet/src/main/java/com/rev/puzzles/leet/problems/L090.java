package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class L090 {
    @LeetProblem(number = 90)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
//        return subsetsWithDup((int[])problemResourceLoader.resources()[0]);
//        return subsetsWithDup2((int[])problemResourceLoader.resources()[0]);
        return subsetsWithDup3((int[]) problemResourceLoader.resources()[0]);
    }


    //first approach - use hashing to avoid duplicates (must also sort nums first)
    public List<List<Integer>> subsetsWithDup(final int[] nums) {
        Arrays.sort(nums);
        final Set<List<Integer>> uniquePermutations = new HashSet<>();
        uniquePermutations.add(new ArrayList<>());
        for (int i = 0; i < nums.length; i++) {
            final Set<List<Integer>> newSubsets = new HashSet<>();
            newSubsets.addAll(uniquePermutations);
            final Iterator<List<Integer>> it = uniquePermutations.iterator();
            while (it.hasNext()) {
                final List<Integer> copy = new ArrayList<>();
                copy.addAll(it.next());
                copy.add(nums[i]);
                newSubsets.add(copy);
            }
            uniquePermutations.addAll(newSubsets);
        }
        return List.copyOf(uniquePermutations);
    }

    //second approach - consider e.g. 2 duplicated twice. Then consider permutations with {}, {2}, {2,2},
    //Where these three options are mutually exclusive.
    //This approach is marginally faster according to LeetCode, but still not optimal :(
    public List<List<Integer>> subsetsWithDup2(final int[] nums) {
        final Map<Integer, Integer> numAndCounts = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final Integer count = numAndCounts.computeIfAbsent(nums[i], k -> 0);
            numAndCounts.put(nums[i], count + 1);
        }
        return subsetsWithDup2(numAndCounts);
    }

    private List<List<Integer>> subsetsWithDup2(final Map<Integer, Integer> numAndCounts) {
        final List<List<Integer>> retval = new ArrayList<>();
        if (numAndCounts.isEmpty()) {
            return List.of(List.of());
        }
        final int val = numAndCounts.keySet().iterator().next();
        final int count = numAndCounts.get(val);
        numAndCounts.remove(val);
        final List<List<Integer>> addTo = subsetsWithDup2(numAndCounts);
        for (int i = 0; i <= count; i++) {
            final List<List<Integer>> copy = new ArrayList<>();
            for (int j = 0; j < addTo.size(); j++) {
                final List<Integer> subcopy = new ArrayList<>();
                subcopy.addAll(addTo.get(j));
                for (int k = 0; k < i; k++) {
                    subcopy.add(val);
                }
                copy.add(subcopy);
            }
            retval.addAll(copy);
        }
        return retval;
    }


    //Leet code optimal solution
    public List<List<Integer>> subsetsWithDup3(final int[] nums) {
        final List<List<Integer>> answer = new ArrayList<>();

        // Count Sort - O(k)
        final int[] countMap = new int[21];
        for (final int num : nums) {
            countMap[num + 10]++;
        }
        for (int i = 0, j = 0; i < 21; i++) {
            while (countMap[i]-- > 0) {
                nums[j++] = i - 10;
            }
        }
        // Sorting done !

        final List<Integer> empty = new ArrayList<>();
        addSubSets(answer, empty, 0, nums);
        return answer;
    }

    private void addSubSets(final List<List<Integer>> answer, final List<Integer> curSet, final int pos,
                            final int[] set) {
        answer.add(curSet);

        for (int i = pos; i < set.length; i++) {

            if (i == pos || set[i] != set[i - 1]) {
                final List<Integer> nextSet = new ArrayList<>(curSet);
                nextSet.add(set[i]);
                addSubSets(answer, nextSet, i + 1, set);
            }
        }
    }
}
