package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

public final class L007 {
    @LeetProblem(number = 7)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return reverse((int) problemResourceLoader.resources()[0]);
    }

    public int reverse(int x) {

        int result = 0;
        final boolean isNegative = x < 0;
        if (isNegative) {
            x = -x;
        }

        while (x > 0) {
            final int a = result;
            final int b = x % 10;
            result *= 10;
            result += b;
            if ((result - b) / 10 != a) {
                return 0;
            }

            x /= 10;
        }

        if (isNegative) {
            return -result;
        }
        return result;
    }
}
