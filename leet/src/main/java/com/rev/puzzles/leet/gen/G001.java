package com.rev.puzzles.leet.gen;

import com.rev.puzzles.framework.framework.impl.NoOpResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblemGen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class G001 {

    private static final int RANGE = 1000000000;
    private static final int LEN_MIN = 2;
    private static final int LEN_MAX = 10000;

    @LeetProblemGen(number = 1)
    public String apply(final NoOpResourceLoader<?> ignored) {
        final GenRand r = new GenRand();
        final int target = r.nextInt(-RANGE, RANGE + 1);

        final int[] nums = new int[r.nextInt(LEN_MIN, LEN_MAX + 1)];
        final Set<Integer> generated = new HashSet<>();

        boolean solutionExists = false;
        while (!solutionExists) {
            generated.clear();
            for (int i = 0; i < nums.length; i++) {
                int next = r.nextInt(-RANGE, RANGE + 1);
                while (solutionExists && generated.contains(target - next)) {
                    next = r.nextInt(-RANGE, RANGE + 1);
                }

                nums[i] = next;
                generated.add(next);
                if (generated.contains(target - next)) {
                    solutionExists = true;
                }
            }
        }
        return String.format("%s%n%s", Arrays.toString(nums), target);
    }
}
