package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.aoc.framework.load.LoaderUtils;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D19 {

    public static final int REGEX_MAX_LENGTH = 100;

    @SuppressWarnings("checkstyle:MagicNumber")
    @AocProblemI(year = 2024, day = 19, part = 1)
    public Long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> strings = resourceLoader.resources();
        Set<String>[] regexes = LoaderUtils.emptyArray(new HashSet[0], 100, () -> new HashSet());

        int start = 0;
        while (start < strings.size() && !strings.get(start).isEmpty()) {
            String[] split = strings.get(start).replaceAll("\\s+", "").split(",");
            for (int i = 0; i < split.length; i++) {
                int len = split[i].length();
                regexes[len - 1].add(split[i]);
            }
            start++;
        }

        int end = regexes.length;
        while (end > 0 && regexes[end - 1].isEmpty()) {
            end--;
        }
        regexes = Arrays.copyOfRange(regexes, 0, end);

        start++;
        List<String> targets = new ArrayList<>();
        while (start < strings.size()) {
            targets.add(strings.get(start));
            start++;
        }

        long count = 0;
        for (int i = 0; i < targets.size(); i++) {
            if (canDesign(targets.get(i), regexes) != 0) {
                count++;
            }
        }
        return count;
    }

    @AocProblemI(year = 2024, day = 19, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<String> strings = resourceLoader.resources();
        Set<String>[] regexes = LoaderUtils.emptyArray(new HashSet[0], REGEX_MAX_LENGTH, () -> new HashSet());

        int start = 0;
        while (start < strings.size() && !strings.get(start).isEmpty()) {
            String[] split = strings.get(start).replaceAll("\\s+", "").split(",");
            for (int i = 0; i < split.length; i++) {
                int len = split[i].length();
                regexes[len - 1].add(split[i]);
            }
            start++;
        }

        int end = regexes.length;
        while (end > 0 && regexes[end - 1].isEmpty()) {
            end--;
        }
        regexes = Arrays.copyOfRange(regexes, 0, end);

        start++;
        List<String> targets = new ArrayList<>();
        while (start < strings.size()) {
            targets.add(strings.get(start));
            start++;
        }

        long count = 0;
        for (int i = 0; i < targets.size(); i++) {
            count += canDesign(targets.get(i), regexes);
        }
        return count;
    }

    private long canDesign(final String target, final Set<String>[] regexes) {
        Long[] cachedResults = LoaderUtils.emptyArray(new Long[0], target.length(), () -> null);
        return canDesign(target, regexes, cachedResults, 0);
    }

    private long canDesign(final String target, final Set<String>[] regexes, final Long[] cachedResults,
                           final int offset) {
        if (offset == target.length()) {
            return 1;
        }

        if (cachedResults[offset] != null) {
            return cachedResults[offset];
        } else {
            cachedResults[offset] = 0L;
        }

        int end = offset + 1;
        while (end < offset + 1 + regexes.length && end <= target.length()) {
            long designsAtEnd = canDesign(target, regexes, cachedResults, end);
            if (designsAtEnd != 0 && regexes[end - offset - 1].contains(target.substring(offset, end))) {
                cachedResults[offset] = cachedResults[offset] + designsAtEnd;
            }
            end++;
        }
        return cachedResults[offset];
    }
}
