package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L038 {
    private static String runLengthEncoding(final String nStr) {
        final int len = nStr.length();
        final StringBuilder rleSb = new StringBuilder();
        int i = 0;
        while (i < len) {
            int j = 1;
            while (i + j < len && nStr.charAt(i) == nStr.charAt(i + j)) {
                j++;
            }
            rleSb.append(j);
            rleSb.append(nStr.charAt(i));
            i += j;
        }
        return rleSb.toString();
    }

    @LeetProblem(number = 38)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return countAndSay((int) problemResourceLoader.resources()[0]);
    }

    public String countAndSay(final int n) {
        if (n == 1) {
            return "1";
        }

        final String nStr = countAndSay(n - 1);
        return runLengthEncoding(nStr);
    }
}
