package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class D22 extends AocProblem<Long, Long> {
    private static final long PRUNE_MOD = 1 << 24;
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

        List<int[]> priceChanges = new ArrayList<>();
        for (int i : inputs) {
            priceChanges.add(calculatePriceChanges(i, PART_ONE_ITERATIONS));
        }
        Map<List<Integer>, Map<Integer, Integer>> runsAndScores = cacheSequences(inputs, priceChanges);
        long maxScore = Integer.MIN_VALUE;
        for (Map<Integer, Integer> scores : runsAndScores.values()) {
            if (scores == null || scores.isEmpty()) {
                continue;
            }
            long score = 0;
            for (int val : scores.values()) {
                score += val;
            }
            if (score > maxScore) {
                maxScore = score;
            }
        }
        return maxScore;
    }

    private Map<List<Integer>, Map<Integer, Integer>> cacheSequences(final int[] inputs,
                                                                     final List<int[]> priceChanges) {
        Map<List<Integer>, Map<Integer, Integer>> runsAndScores = new HashMap<>();
        for (int i = 0; i < inputs.length; i++) {
            cacheSequences(inputs[i], priceChanges.get(i), runsAndScores);
        }
        return runsAndScores;
    }

    private void cacheSequences(final int seed,
                                final int[] priceChanges,
                                final Map<List<Integer>, Map<Integer, Integer>> runsAndScores) {
        Set<List<Integer>> visited = new HashSet<>();
        long hash = seed;
        for (int i = 0; i < priceChanges.length; i++) {
            hash = hashOnce(hash);
            if (i >= 3) {
                List<Integer> run = new ArrayList<>(4);
                for (int j = i - 3; j <= i; j++) {
                    run.add(priceChanges[j]);
                }
                if (!visited.contains(run)) {
                    visited.add(run);
                    runsAndScores.computeIfAbsent(run, k -> new HashMap<>()).put(seed, (int) (hash % 10));
                }
            }
        }
    }

    private int[] calculatePriceChanges(long secretNumber, int iterations) {
        int[] priceChanges = new int[iterations - 1];
        for (int i = 0; i < iterations - 1; i++) {
            long nextSecretNumber = hashOnce(secretNumber);
            priceChanges[i] = (int) (nextSecretNumber % 10 - secretNumber % 10);
            secretNumber = nextSecretNumber;
        }
        return priceChanges;
    }

    private long calculateHash(long secretNumber, long iterations) {
        for (int i = 0; i < iterations; i++) {
            secretNumber = hashOnce(secretNumber);
        }
        return secretNumber;
    }

    private static long hashOnce(long secretNumber) {
        secretNumber = (secretNumber ^ (secretNumber << 6)) & (PRUNE_MOD - 1);
        secretNumber = (secretNumber ^ (secretNumber >> 5)) & (PRUNE_MOD - 1);
        secretNumber = (secretNumber ^ (secretNumber << 11)) & (PRUNE_MOD - 1);
        return secretNumber;
    }
}
