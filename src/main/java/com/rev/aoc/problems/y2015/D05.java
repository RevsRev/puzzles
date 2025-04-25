package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.List;
import java.util.regex.Pattern;

public final class D05 extends AocProblem<Long, Long> {

    private static final String VOWEL_REGEX = ".*[a|e|i|o|u]{1}.*[a|e|i|o|u]{1}.*[a|e|i|o|u]{1}.*";
    private static final String DOUBLE_REGEX = ".*(.)\\1.*";
    private static final String IGNORE_STRINGS_REGEX = ".*(ab|cd|pq|xy).*";
    private static final String DOUBLE_DOUBLE_REGEX = ".*(..).*\\1.*";
    private static final String SANDWICH_REGEX = ".*(.).\\1.*";

    private static final Pattern VOWEL_PATTERN = Pattern.compile(VOWEL_REGEX);
    private static final Pattern DOUBLE_PATTERN = Pattern.compile(DOUBLE_REGEX);
    private static final Pattern IGNORE_STRINGS_PATTERN = Pattern.compile(IGNORE_STRINGS_REGEX);
    private static final Pattern DOUBLE_DOUBLE_PATTERN = Pattern.compile(DOUBLE_DOUBLE_REGEX);
    private static final Pattern SANDWICH_PATTERN = Pattern.compile(SANDWICH_REGEX);

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 5);
    }

    @Override
    protected Long partOneImpl() {
        List<String> lines = loadResources();
        long count = 0;
        for (String line : lines) {
            if (matches(line, VOWEL_PATTERN)
                    && matches(line, DOUBLE_PATTERN)
                    && !matches(line, IGNORE_STRINGS_PATTERN)) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected Long partTwoImpl() {
        List<String> lines = loadResources();
        long count = 0;
        for (String line : lines) {
            if (matches(line, DOUBLE_DOUBLE_PATTERN) && matches(line, SANDWICH_PATTERN)) {
                count++;
            }
        }
        return count;
    }

    private static boolean matches(final String s, final Pattern p) {
        return p.matcher(s).matches();
    }
}
