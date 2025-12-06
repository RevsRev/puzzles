package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.parse.LoaderUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class D06 {

    @AocProblemI(year = 2025, day = 6, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final List<List<Long>> numbers = new ArrayList<>();
        final List<String> operations = new ArrayList<>();

        final List<String> strings = resourceLoader.resources();

        for (int i = 0; i < strings.size() - 1; i++) {
            numbers.add(new ArrayList<>());
        }

        for (int i = 0; i < strings.size(); i++) {
            final String[] split = strings.get(i).split("\\s+");
            if (i == strings.size() - 1) {
                operations.addAll(Arrays.stream(split).toList());
                continue;
            }

            for (int j = 0; j < split.length; j++) {
                numbers.get(i).add(Long.parseLong(split[j]));
            }
        }

        long total = 0;
        for (int i = 0; i < operations.size(); i++) {
            final String operation = operations.get(i);
            if ("+".equals(operation)) {
                long aggregate = 0;
                for (int j = 0; j < numbers.size(); j++) {
                    final List<Long> row = numbers.get(j);
                    aggregate += row.get(i);
                }
                total += aggregate;
            } else {
                long aggregate = 1;
                for (int j = 0; j < numbers.size(); j++) {
                    final List<Long> row = numbers.get(j);
                    aggregate *= row.get(i);
                }
                total += aggregate;
            }
        }

        return total;
    }

    @AocProblemI(year = 2025, day = 6, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final char[][] characters =
                LoaderUtils.loadResourcesAsCharMatrix(resourceLoader.resources(), String::toCharArray);

        long total = 0;
        final char empty = ' ';
        char operand = '+';
        long aggregate = 0;

        for (int i = 0; i < characters[0].length; i++) {

            if (empty != characters[characters.length - 1][i]) {
                operand = characters[characters.length - 1][i];
                aggregate = operand == '+' ? 0 : 1;
            }

            final StringBuilder sb = new StringBuilder();
            for (int j = 0; j < characters.length - 1; j++) {
                sb.append(characters[j][i]);
            }


            final String longString = sb.toString().trim();
            if (!longString.isBlank()) {
                final long number = Long.parseLong(longString);
                if (operand == '+') {
                    aggregate += number;
                } else {
                    aggregate *= number;
                }
            }

            if (longString.isBlank() || i == characters[0].length - 1) {
                total += aggregate;
            }
        }

        return total;
    }
}
