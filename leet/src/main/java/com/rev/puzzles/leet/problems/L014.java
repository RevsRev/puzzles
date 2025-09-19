package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L014 {

    @LeetProblem(number = 14)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return longestCommonPrefix((String[]) problemResourceLoader.resources()[0]);
    }

    public String longestCommonPrefix(final String[] strs) {

        int i = 0;
        final StringBuilder sbResult = new StringBuilder();
        while (true) {
            if (strs[0].length() <= i) {
                break;
            }
            final char c = strs[0].charAt(i);
            boolean stop = false;
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != c) {
                    stop = true;
                    break;
                }
            }
            if (stop) {
                return sbResult.toString();
            }
            i++;
            sbResult.append(c);
        }
        return sbResult.toString();
    }
}
