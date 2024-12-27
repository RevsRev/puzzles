package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class D22 extends AocProblem<Long, Long> {
    private static final int PRUNE_MOD = 1 << 24;
    private static final int PART_ONE_ITERATIONS = 2000;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 22);
    }

    @Override
    protected Long partOneImpl() {
        List<String> lines = loadResources();
        int[] inputs = LoaderUtils.linesToIntArray(lines, s -> new String[] {s});

        long sum = 0;
        for (int i : inputs) {
            sum += calculateHash(i, PART_ONE_ITERATIONS);
        }
        return sum;
    }

    @Override
    protected Long partTwoImpl() {
        List<String> lines = loadResources();
        int[] inputs = LoaderUtils.linesToIntArray(lines, s -> new String[] {s});

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
