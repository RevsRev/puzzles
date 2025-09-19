package com.rev.puzzles.leet.problems;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.leet.framework.LeetProblem;

@SuppressWarnings("checkstyle:FinalParameters")
public final class L008 {
    @LeetProblem(number = 8)
    public Integer apply(final ProblemResourceLoader<Object[]> problemResourceLoader) {
        return myAtoi((String) problemResourceLoader.resources()[0]);
    }

    public int myAtoi(final String s) {

        final int len = s.length();
        long result = 0;
        boolean positive = true;

        int i = 0;
        while (i < len) {
            if (s.charAt(i) != ' ') {
                if (!Character.isDigit(s.charAt(i))) {
                    if (s.charAt(i) == '-') {
                        positive = false;
                        i++;
                    } else if (s.charAt(i) == '+') {
                        positive = true;
                        i++;
                    } else {
                        return 0;
                    }
                }

                if (i == len) {
                    return 0;
                }

                if (!Character.isDigit(s.charAt(i))) {
                    return 0;
                }

                int j = 0;
                result = 0;
                while (i + j < len && Character.isDigit(s.charAt(i + j))) {
                    final int digit = Integer.parseInt(s.substring(i + j, i + j + 1));
                    result *= 10;
                    result += digit;
                    if (result > Integer.MAX_VALUE) {
                        break;
                    }
                    j++;
                }
                break;
            }
            i++;
        }

        if (!positive) {
            result *= -1;
        }

        if (result > Integer.MAX_VALUE) {
            result = Integer.MAX_VALUE;
        } else if (result < Integer.MIN_VALUE) {
            result = Integer.MIN_VALUE;
        }

        return (int) result;
    }
}
