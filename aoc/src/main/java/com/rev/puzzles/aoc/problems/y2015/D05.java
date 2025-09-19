package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.List;
import java.util.regex.Pattern;

public final class D05 {

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

    private static boolean matches(final String s, final Pattern p) {
        return p.matcher(s).matches();
    }

    @AocProblemI(year = 2015, day = 5, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> lines = resourceLoader.resources();
        long count = 0;
        for (String line : lines) {
            if (matches(line, VOWEL_PATTERN) && matches(line, DOUBLE_PATTERN) && !matches(line,
                    IGNORE_STRINGS_PATTERN)) {
                count++;
            }
        }
        return count;
    }

    @AocProblemI(year = 2015, day = 5, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> lines = resourceLoader.resources();
        long count = 0;
        for (String line : lines) {
            if (matches(line, DOUBLE_DOUBLE_PATTERN) && matches(line, SANDWICH_PATTERN)) {
                count++;
            }
        }
        return count;
    }
}
