package com.rev.puzzles.aoc.problems.y2025;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class D02 {

    @AocProblemI(year = 2025, day = 2, part = 1)
    public long partOneImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<Long> ids = getIds(resourceLoader);

        long invalidIdSum = 0;

        for (int i = 0; i < ids.size(); i++) {
            final long id = ids.get(i);
            if (!isValidId(id)) {
                invalidIdSum += id;
            }
        }
        return invalidIdSum;
    }

    @AocProblemI(year = 2025, day = 2, part = 2)
    public long partTwoImpl(final ProblemResourceLoader<List<String>> resourceLoader) {
        List<Long> ids = getIds(resourceLoader);

        long invalidIdSum = 0;

        for (int i = 0; i < ids.size(); i++) {
            final long id = ids.get(i);
            if (!isValidIdPart2(id)) {
                invalidIdSum += id;
            }
        }
        return invalidIdSum;
    }

    private static List<Long> getIds(final ProblemResourceLoader<List<String>> resourceLoader) {
        final List<String> idRanges = Arrays.stream(resourceLoader.resources().get(0).split(",")).toList();
        List<Long> ids = new ArrayList<>();

        for (final String idRange : idRanges) {
            final String[] split = idRange.split("-");
            final long start = Long.parseLong(split[0]);
            final long end = Long.parseLong(split[1]);
            for (long i = start; i <= end; i++) {
                ids.add(i);
            }
        }
        return ids;
    }

    private boolean isValidId(final long id) {
        String s = "" + id;

        if (s.length() % 2 == 1) {
            return true;
        }
        final String firstHalf = s.substring(0, s.length() / 2);
        final String secondHalf = s.substring(s.length() / 2);
        return !firstHalf.equals(secondHalf);
    }

    private static boolean isValidIdPart2(final long id) {
        String s = "" + id;

        for (int bucketSize = 1; bucketSize <= s.length() / 2; bucketSize++) {
            if (s.length() % bucketSize != 0) {
                continue;
            }
            String previous = s.substring(0, bucketSize);
            final int numBuckets = s.length() / bucketSize;
            boolean isValidBucketSize = false;
            for (int j = 1; j < numBuckets; j++) {
                String next = s.substring(j * bucketSize, (j + 1) * bucketSize);
                if (!previous.equals(next)) {
                    isValidBucketSize = true;
                    break;
                }
            }

            if (!isValidBucketSize) {
                return false;
            }
        }
        return true;
    }
}
