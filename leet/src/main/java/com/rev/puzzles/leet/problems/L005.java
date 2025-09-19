package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L005 {

    @LeetProblem(number = 5)
    public String apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return longestPalindrome((String) problemResourceLoader.resources()[0]);
    }

    // 1 <= s.length <= 1000
    public String longestPalindrome(final String s) {
        final char[] chars = s.toCharArray();
        int middle = 0;
        final int length = chars.length;
        String longestPalindrome = String.copyValueOf(chars, 0, 1);
        while (middle < chars.length) {
            final char middleChar = chars[middle];
            final boolean evenSearch = middle + 1 < length && chars[middle + 1] == middleChar;

            int range = Math.min(middle, chars.length - 1 - middle);
            int i = 0;
            while (i <= range) {
                if (chars[middle - i] != chars[middle + i]) {
                    break;
                }
                i++;
            }
            if (2 * i - 1 > longestPalindrome.length()) {
                longestPalindrome = String.copyValueOf(chars, middle - (i - 1), 2 * i - 1);
            }

            if (evenSearch) {
                range = Math.min(middle, chars.length - middle - 2);
                i = 0;
                while (i <= range) {
                    if (chars[middle - i] != chars[middle + 1 + i]) {
                        break;
                    }
                    i++;
                }
                if (2 * i > longestPalindrome.length()) {
                    longestPalindrome = String.copyValueOf(chars, middle - (i - 1), 2 * i);
                }
            }

            middle++;
        }
        return longestPalindrome;
    }
}
