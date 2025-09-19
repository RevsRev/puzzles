package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class L060 {
    private static final Map<Integer, Integer> FACTORIALS = new HashMap<>();

    private static LinkedList<Character> getOrderedChars(final int n) {
        final LinkedList<Character> retval = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            retval.add(Integer.toString(i).toCharArray()[0]);
        }
        return retval;
    }

    private static int factorial(final int n) {
        if (FACTORIALS.containsKey(n)) {
            return FACTORIALS.get(n);
        }
        if (n == 0) {
            FACTORIALS.put(0, 1);
            return 1;
        }

        final int result = n * factorial(n - 1);
        FACTORIALS.put(n, result);
        return result;
    }

    @LeetProblem(number = 60)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return getPermutation((int) problemResourceLoader.resources()[0], (int) problemResourceLoader.resources()[1]);
    }

    public String getPermutation(final int n, final int k) {
        final StringBuilder sb = new StringBuilder();
        final LinkedList<Character> chars = getOrderedChars(n);
        getPermutation(chars, k - 1, sb);
        return sb.toString();
    }

    //0 Indexed ordering
    private void getPermutation(final LinkedList<Character> chars, final int k, final StringBuilder sb) {
        final int size = chars.size();

        if (size == 0) {
            return;
        }

        final int mod = factorial(size - 1);
        final int index = k / mod;
        sb.append(chars.remove(index));
        getPermutation(chars, k % mod, sb);
    }
}
