package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class L001 {
    @LeetProblem(number = 1)
    public int[] apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return twoSum((int[]) problemResourceLoader.resources()[0], (Integer) problemResourceLoader.resources()[1]);
    }

    public int[] twoSum(final int[] nums, final int target) {
        final Map<Integer, List<Integer>> numsMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            numsMap.computeIfAbsent(nums[i], k -> new ArrayList<>());
            numsMap.get(nums[i]).add(i);
        }

        final Set<Integer> values = numsMap.keySet();
        final Iterator<Integer> valsIt = values.iterator();
        while (valsIt.hasNext()) {
            final Integer val = valsIt.next();
            final Integer otherVal = target - val;

            if (val.equals(otherVal) && numsMap.get(val).size() >= 2 && 2 * val == target) {
                final int[] result = {numsMap.get(val).get(0), numsMap.get(val).get(1)};
                return result;
            }

            if (values.contains(otherVal)) {
                final int[] result = {numsMap.get(val).get(0), numsMap.get(otherVal).get(0)};
                return result;
            }
        }
        return null;
    }
}
