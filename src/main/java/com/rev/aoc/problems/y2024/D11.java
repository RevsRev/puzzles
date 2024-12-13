package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;
import com.rev.aoc.util.math.ntheory.util.Pow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class D11 extends AocProblem {
    public static final int PART_ONE_ITERATIONS = 25;
    public static final int PART_TWO_ITERATIONS = 75;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 11);
    }

    @Override
    protected long partOneImpl() {
        long[] stones = loadResourcesAsLongArray();
        return countStones(stones, PART_ONE_ITERATIONS);
    }
    @Override
    protected long partTwoImpl() {
        long[] stones = loadResourcesAsLongArray();
        return countStones(stones, PART_TWO_ITERATIONS);
    }

    private long countStones(final long[] longs, int numBlinks) {
        Map<Long, Long> stoneToCountMap = new HashMap<>();
        for (int i = 0; i < longs.length; i++) {
            Long count = stoneToCountMap.getOrDefault(longs[i], 0L);
            stoneToCountMap.put(longs[i], count + 1);
        }

        stoneToCountMap = countStonesAfterBlinks(stoneToCountMap, numBlinks);
        return countTotalStones(stoneToCountMap);
    }

    private long countTotalStones(final Map<Long, Long> stoneToCountMap) {
        long count = 0;
        for (Map.Entry<Long, Long> stoneAndCount : stoneToCountMap.entrySet()) {
            count += stoneAndCount.getValue();
        }
        return count;
    }

    private Map<Long, Long> countStonesAfterBlinks(final Map<Long, Long> stones, int numBlinks) {
        if (numBlinks == 0) {
            return stones;
        }

        Map<Long, Long> nextInput = new HashMap<>();
        for (Map.Entry<Long, Long> vals : stones.entrySet()) {
            long stoneVal = vals.getKey();
            long numTimes = vals.getValue();

            if (stoneVal == 0L) {
                long currentCount = nextInput.getOrDefault(1L, 0L);
                nextInput.put(1L, currentCount + numTimes);
                continue;
            }

            int length = (int) Math.log10(stoneVal) + 1;
            if (length % 2 == 0) {
                long lengthOnTwo = length / 2;
                long div = Pow.pow(10L, lengthOnTwo);
                long leftStone = stoneVal / div;
                long rightStone = stoneVal % div;

                long currentLeftCount = nextInput.getOrDefault(leftStone, 0L);
                nextInput.put(leftStone, currentLeftCount + numTimes);
                long currentRightCount = nextInput.getOrDefault(rightStone, 0L);
                nextInput.put(rightStone, currentRightCount + numTimes);
                continue;
            }
            long nextVal = stoneVal * 2024L;
            long nextCount = nextInput.getOrDefault(nextVal, 0L);
            nextInput.put(nextVal, nextCount + numTimes);
        }
        return countStonesAfterBlinks(nextInput, numBlinks - 1);
    }

    public long[] loadResourcesAsLongArray() {
        String separatorRegex = "\\s+";
        List<String> lines = loadResources();
        List<String[]> linesSplit = new ArrayList<>(lines.size());

        int size = 0;
        for (String line : lines) {
            String[] split = line.split(separatorRegex);
            linesSplit.add(split);
            size += split.length;
        }

        long[] arr = new long[size];
        int index = 0;
        for (int i = 0; i < linesSplit.size(); i++) {
            for (int j = 0; j < linesSplit.get(i).length; j++) {
                arr[index] = Long.parseLong(linesSplit.get(i)[j]);
                index++;
            }
        }
        return arr;
    }
}
