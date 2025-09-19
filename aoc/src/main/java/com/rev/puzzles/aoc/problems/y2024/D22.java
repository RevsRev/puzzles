package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.load.LoaderUtils;
import com.rev.puzzles.aoc.framework.AocProblemI;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.rev.puzzles.framework.framework.ResourceLoader;

public final class D22 {
    private static final int PRUNE_MOD = 1 << 24;
    private static final int PART_ONE_ITERATIONS = 2000;

    @AocProblemI(year = 2024, day = 22, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        int[] inputs = LoaderUtils.linesToIntArray(lines, s -> new String[]{s});

        long sum = 0;
        for (int i : inputs) {
            sum += calculateHash(i, PART_ONE_ITERATIONS);
        }
        return sum;
    }

    @AocProblemI(year = 2024, day = 22, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        List<String> lines = resourceLoader.resources();
        int[] inputs = LoaderUtils.linesToIntArray(lines, s -> new String[]{s});

        Map<List<Integer>, Integer> runsAndScores = new HashMap<>();
        long bestScore = Long.MIN_VALUE;
        for (int i : inputs) {
            bestScore = computePriceChangeScores(i, runsAndScores, bestScore);
        }
        return bestScore;
    }

    private long computePriceChangeScores(final int seed,
                                          final Map<List<Integer>, Integer> runsAndScores,
                                          long bestScoreSoFar) {
        int price = seed;
        Queue<Integer> priceChanges = new ArrayDeque<>();
        Set<List<Integer>> visited = new HashSet<>();
        for (int i = 0; i < PART_ONE_ITERATIONS; i++) {
            int nextPrice = hashOnce(price);
            priceChanges.add((nextPrice % 10) - (price % 10));
            if (priceChanges.size() > 4) {
                priceChanges.remove();
            }
            if (priceChanges.size() == 4) {
                List<Integer> priceChangesCpy = List.copyOf(priceChanges);
                if (!visited.contains(priceChangesCpy)) {
                    visited.add(priceChangesCpy);
                    int score = runsAndScores.getOrDefault(priceChangesCpy, 0);
                    int newScore = score + (nextPrice % 10);
                    if (newScore > bestScoreSoFar) {
                        bestScoreSoFar = newScore;
                    }
                    runsAndScores.put(priceChangesCpy, newScore);
                }
            }
            price = nextPrice;
        }
        return bestScoreSoFar;
    }

    private int calculateHash(int secretNumber, int iterations) {
        for (int i = 0; i < iterations; i++) {
            secretNumber = hashOnce(secretNumber);
        }
        return secretNumber;
    }

    private static int hashOnce(int secretNumber) {
        secretNumber = (secretNumber ^ (secretNumber << 6)) & (PRUNE_MOD - 1);
        secretNumber = (secretNumber ^ (secretNumber >> 5)) & (PRUNE_MOD - 1);
        secretNumber = (secretNumber ^ (secretNumber << 11)) & (PRUNE_MOD - 1);
        return secretNumber;
    }
}
