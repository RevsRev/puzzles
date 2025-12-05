package com.rev.puzzles.aoc.problems.y2025;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.ArrayList;
import java.util.List;

public final class D05 {

    @AocProblemI(year = 2025, day = 5, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> resources = resourceLoader.resources();

        final TreeRangeSet<Long> numbersRanges = TreeRangeSet.create();
        final List<Long> ingredients = new ArrayList<>();

        resources.stream().forEach(s -> {
            if (s.contains("-")) {
                final String[] split = s.split("-");
                long low = Long.parseLong(split[0]);
                long high = Long.parseLong(split[1]);
                numbersRanges.add(Range.closedOpen(low, high));
            } else {
                if (!s.isBlank()) {
                    ingredients.add(Long.parseLong(s));
                }
            }
        });

        long count = 0;
        for (final Long ingredient : ingredients) {
            if (numbersRanges.contains(ingredient)) {
                count++;
            }
        }
        return count;
    }

    @AocProblemI(year = 2025, day = 5, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> resources = resourceLoader.resources();

        final TreeRangeSet<Long> numbersRanges = TreeRangeSet.create();
        final List<Long> ingredients = new ArrayList<>();

        resources.stream().forEach(s -> {
            if (s.contains("-")) {
                final String[] split = s.split("-");
                long low = Long.parseLong(split[0]);
                long high = Long.parseLong(split[1]);
                numbersRanges.add(Range.closedOpen(low, high + 1));
            } else {
                if (!s.isBlank()) {
                    ingredients.add(Long.parseLong(s));
                }
            }
        });

        long count = 0;
        for (final Range<Long> range : numbersRanges.asRanges()) {
            count += range.upperEndpoint() - range.lowerEndpoint();
        }
        return count;
    }
}
