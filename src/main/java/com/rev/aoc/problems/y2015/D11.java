package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;

import java.util.Set;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D11 extends AocProblem<String, String> {

    public static final int PART_TWO_ITERATIONS = 2;

    @AocProblemI(year = 2015, day = 11, part = 1)
    @Override
    protected String partOneImpl(final ResourceLoader resourceLoader) {
        char[] password = resourceLoader.resources().get(0).toCharArray();
        do {
            countUp(password, 'a', 'z');
        } while (!validPassword(password));
        return new String(password);
    }

    @AocProblemI(year = 2015, day = 11, part = 1)
    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected String partTwoImpl(final ResourceLoader resourceLoader) {
        char[] password = resourceLoader.resources().get(0).toCharArray();
        for (int i = 0; i < PART_TWO_ITERATIONS; i++) {
            do {
                countUp(password, 'a', 'z');
            } while (!validPassword(password));
        }
        return new String(password);
    }

    private static boolean validPassword(final char[] password) {
        return hasTwoSetsOfRepeatedChar(password) && hasRunOfThree(password) && !containsForbiddenCharacters(password);
    }

    private static boolean containsForbiddenCharacters(final char[] password) {
        Set<Character> forbiddenCharacters = Set.of('i', 'o', 'l');
        for (int i = 0; i < password.length; i++) {
            if (forbiddenCharacters.contains(password[i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasRunOfThree(final char[] password) {
        int i = 0;
        while (i < password.length) {
            int j = 0;
            while (i + j < password.length && password[i + j] == password[i] + j) {
                j++;
            }
            if (j >= 3) {
                return true;
            }
            i += j;
        }
        return false;
    }

    private static boolean hasTwoSetsOfRepeatedChar(final char[] password) {
        int i = 0;
        int runCount = 0;
        while (i < password.length) {
            int j = 0;
            while (i + j < password.length && password[i + j] == password[i]) {
                j++;
            }
            if (j >= 2) {
                runCount++;
            }

            if (runCount == 2) {
                return true;
            }

            i += j;
        }
        return false;
    }

    private static void countUp(final char[] count, final char zero, final char base) {
        countUp(count, zero, base, count.length - 1);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private static void countUp(final char[] count, final char zero, final char base, final int i) {
        if (i == -1) {
            countUp(count, zero, base, count.length - 1);
            return;
        }

        if (count[i] == base) {
            count[i] = zero;
            countUp(count, zero, base, i - 1);
            return;
        }

        count[i]++;
    }
}
