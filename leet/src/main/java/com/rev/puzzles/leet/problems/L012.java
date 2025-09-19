package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L012 {
    private static final String I = "I";
    private static final String V = "V";
    private static final String X = "X";
    private static final String L = "L";
    private static final String C = "C";
    private static final String D = "D";
    private static final String M = "M";

    private static final String N = "N";
    private static final String O = "O";

    @LeetProblem(number = 12)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return intToRoman((Integer) problemResourceLoader.resources()[0]);
    }

    public String intToRoman(int num) {
        final String[] numerals = new String[]{I, V, X, L, C, D, M, N, O};

        final StringBuilder sbNumRoman = new StringBuilder();
        int iteration = 0;
        while (num != 0) {
            final String one = numerals[2 * iteration];
            final String five = numerals[2 * iteration + 1];
            final String ten = numerals[2 * iteration + 2];

            final int units = num % 10;
            if (units == 9) {
                sbNumRoman.append(ten);
                sbNumRoman.append(one);
            } else if (units >= 5) {
                for (int i = 0; i < units - 5; i++) {
                    sbNumRoman.append(one);
                }
                sbNumRoman.append(five);
            } else if (units == 4) {
                sbNumRoman.append(five);
                sbNumRoman.append(one);
            } else {
                for (int i = 0; i < units; i++) {
                    sbNumRoman.append(one);
                }
            }
            num = num / 10;
            iteration++;
        }
        return sbNumRoman.reverse().toString();
    }
}
