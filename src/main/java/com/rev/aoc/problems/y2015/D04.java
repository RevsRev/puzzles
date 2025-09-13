package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D04 extends AocProblem<String, String> {

    private final MessageDigest digest = MessageDigest.getInstance("MD5");

    public D04() throws NoSuchAlgorithmException {
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 4);
    }

    @Override
    @AocProblemI(year = 2015, day = 4, part = 1)
    protected String partOneImpl(final ResourceLoader resourceLoader) {
        final String input = resourceLoader.resources().get(0);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            final String check = input + i;
            byte[] hash = digest.digest(check.getBytes(StandardCharsets.US_ASCII));

            if (((char) hash[0]) == 0 && ((char) hash[1]) == 0 && ((char) hash[2]) < 16) {
                return check;
            }
        }
        throw new ProblemExecutionException();
    }

    @Override
    @AocProblemI(year = 2015, day = 4, part = 2)
    protected String partTwoImpl(final ResourceLoader resourceLoader) {
        final String input = resourceLoader.resources().get(0);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            final String check = input + i;
            byte[] hash = digest.digest(check.getBytes(StandardCharsets.US_ASCII));

            if (((char) hash[0]) == 0 && ((char) hash[1]) == 0 && ((char) hash[2]) == 0) {
                return check;
            }
        }
        throw new ProblemExecutionException();
    }
}
