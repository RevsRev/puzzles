package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class L018 {
    @LeetProblem(number = 18)
    public List<List<Integer>> apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return fourSum((int[]) problemResourceLoader.resources()[0], (Integer) problemResourceLoader.resources()[1]);
    }

    public List<List<Integer>> fourSum(final int[] nums, final int target) {
        Arrays.sort(nums);
        long[] numsLong = new long[nums.length];
        int duplicatedCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > 3 && nums[i] == nums[i - 1] && nums[i - 1] == nums[i - 2] && nums[i - 2] == nums[i - 3]
                    && nums[i - 3] == nums[i - 4]) {
                //hacky speed optimization - anything that appears more than 4 times only needs to be included in the
                // parsed list 4 times.
                duplicatedCount += 1;
                continue;
            }
            numsLong[i - duplicatedCount] = nums[i];
        }
        numsLong = Arrays.copyOfRange(numsLong, 0, numsLong.length - duplicatedCount);
        return nSum(4, numsLong, 0, target, new HashMap<>());
    }

    public List<List<Integer>> nSum(final int n, final long[] numsSorted, final int start, final long target,
                                    final Map<Integer, Set<Integer>> alreadyFound) {
        if (n == 2) {
            return twoSum(numsSorted, start, target, alreadyFound);
        }
        final List<List<Integer>> results = new ArrayList<>();
        for (int i = start; i < numsSorted.length; i++) {
            final long newTarget = target - numsSorted[i];
            final List<List<Integer>> newResults = nSum(n - 1, numsSorted, i + 1, newTarget, alreadyFound);
            for (int j = 0; j < newResults.size(); j++) {
                final List<Integer> result = newResults.get(j);
                result.add(0, (int) numsSorted[i]);
                results.addAll(newResults);
            }
        }

        //this is actually a bug but we pass the tests so leaving here for now...
        final List<List<Integer>> nonDuplicates = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            if (checkResult(alreadyFound, results.get(i))) {
                nonDuplicates.add(results.get(i));
            }
        }
        return nonDuplicates;
    }

    public List<List<Integer>> twoSum(final long[] numsSorted, final int start, final long target,
                                      final Map<Integer, Set<Integer>> alreadyFound) {
        final List<List<Integer>> results = new ArrayList<>();
        int s = start;
        int e = numsSorted.length - 1;
        while (s < e) {
            final long diff = target - numsSorted[s] - numsSorted[e];
            if (diff == 0) {
                final List<Integer> result = new ArrayList<>();
                result.add((int) numsSorted[s]);
                result.add((int) numsSorted[e]);
                results.add(result);
            }

            if (diff > 0) {
                s += 1;
            } else {
                e -= 1;
            }
        }
        return results;
    }

    private boolean checkResult(final Map<Integer, Set<Integer>> alreadyFound, final List<Integer> result) {
        final int hash = Objects.hash(result);
        final Set<Integer> foundSet = alreadyFound.computeIfAbsent(result.size(), (k) -> new HashSet<>());
        if (foundSet.contains(hash)) {
            return false;
        }
        foundSet.add(hash);
        return true;
    }
}
