package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public final class D03 {

    @AocProblemI(year = 2025, day = 3, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {

        final List<String> banks = resourceLoader.resources();

        return getSumOfBatteryBanks(banks, 2);
    }

    @AocProblemI(year = 2025, day = 3, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> banks = resourceLoader.resources();

        return getSumOfBatteryBanks(banks, 12);
    }

    private static long getSumOfBatteryBanks(final List<String> banks, final int stringLength) {
        long sum = 0;

        final int input = stringLength - 1;

        for (final String bank : banks) {
            int fromIndex = 0;
            int toIndex = bank.length() - input;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < stringLength; i++) {
                final Pair<Integer, Long> result = getLargestRemainingNumber(bank, fromIndex, toIndex);
                fromIndex = result.getLeft() + 1;
                toIndex++;
                sb.append(result.getRight());
            }

            final String result = sb.toString();
            sum += Long.parseLong(result);
        }

        return sum;
    }

    private static Pair<Integer, Long> getLargestRemainingNumber(final String bank,
                                                                 final int fromIndex,
                                                                 final int toIndex
    ) {
        long largestFirstNumber = 0;
        int startIndex = fromIndex;
        for (int i = fromIndex; i < toIndex; i++) {
            long candidate = Long.parseLong(bank.substring(i, i + 1));
            if (candidate > largestFirstNumber) {
                largestFirstNumber = candidate;
                startIndex = i;
            }
        }
        return Pair.of(startIndex, largestFirstNumber);
    }
}
