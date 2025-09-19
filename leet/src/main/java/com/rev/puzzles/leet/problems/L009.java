package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L009 {
    @LeetProblem(number = 9)
    public Boolean apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return isPalindrome((int) problemResourceLoader.resources()[0]);
    }

    public boolean isPalindrome(final int x) {
        if (x < 0) {
            return false;
        }
        int a = x;
        int b = 0;

        while (a > 0) {
            final int digit = a % 10;
            a = a / 10;
            b *= 10;
            b += digit;
        }
        return x == b;
    }
}
