package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.geom.Direction;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.TreeMap;

public final class D20 extends AocProblem<Long, Long> {

    public static final int SAVE_THRESHOLD = 100;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 20);
    }

    @AocProblemI(year = 2024, day = 20, part = 1)
    @Override
    protected Long partOneImpl() {
        char[][] chars = LoaderUtils.loadResourcesAsCharMatrix(loadResources());
        return solvePartOne(chars, 2);
    }

    @AocProblemI(year = 2024, day = 20, part = 2)
    @Override
    protected Long partTwoImpl() {
        char[][] chars = LoaderUtils.loadResourcesAsCharMatrix(loadResources());
        return solvePartOne(chars, 20);
    }

    private long solvePartOne(final char[][] chars, final int maxCheatHop) {
        int[] start = LoaderUtils.findOne(chars, 'S');
        int[] end = LoaderUtils.findOne(chars, 'E');

        final Integer[][] traversedState = new Integer[chars.length][chars[0].length];

        traverseMaze(chars, traversedState, start[0], start[1]);

        NavigableMap<Integer, Integer> savedAmounts = new TreeMap<>((i, j) -> Integer.compare(j, i));
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (traversedState[i][j] != null) {
                    Map<Integer, Integer> cellCheatSaveAmounts =
                            countCheatSaveAmounts(traversedState, i, j, maxCheatHop);
                    for (Map.Entry<Integer, Integer> savedAmountAndCount : cellCheatSaveAmounts.entrySet()) {
                        Integer savedAmount = savedAmountAndCount.getKey();
                        Integer savedCount = savedAmounts.getOrDefault(savedAmount, 0);
                        savedAmounts.put(savedAmount, savedCount + savedAmountAndCount.getValue());
                    }
                }
            }
        }

        long count = 0;
        for (Map.Entry<Integer, Integer> savedAmount : savedAmounts.entrySet()) {
            long saved = savedAmount.getKey();
            if (saved < SAVE_THRESHOLD) {
                break;
            }
            count += savedAmount.getValue();
        }
        return count;
    }

    private Map<Integer, Integer> countCheatSaveAmounts(final Integer[][] traversedState,
                                                        final int i,
                                                        final int j, int maxCheatHop) {
        Map<Integer, Integer> savedAmounts = new HashMap<>();
        for (int x = -maxCheatHop; x <= maxCheatHop; x++) {
            int yLimit = maxCheatHop - Math.abs(x);
            for (int y = -yLimit; y <= yLimit; y++) {
                int taxiDistance = Math.abs(x) + Math.abs(y);
                if (taxiDistance >= 1 && taxiDistance <= maxCheatHop
                        && 0 <= i + x && i + x < traversedState.length
                        && 0 <= j + y && j + y < traversedState[0].length
                        && traversedState[i + x][j + y] != null) {
                    int saved = traversedState[i + x][j + y] - traversedState[i][j] - taxiDistance;
                    if (saved > 0) {
                        int count = savedAmounts.getOrDefault(saved, 0);
                        savedAmounts.put(saved, count + 1);
                    }
                }
            }
        }
        return savedAmounts;
    }

    private void traverseMaze(final char[][] chars,
                              final Integer[][] traversalState,
                              final int startI,
                              final int startJ) {

        Queue<Integer> visitQueue = new ArrayDeque<>();
        visitQueue.add(startI);
        visitQueue.add(startJ);
        visitQueue.add(0);

        while (!visitQueue.isEmpty()) {
            int i = visitQueue.remove();
            int j = visitQueue.remove();
            int depth = visitQueue.remove();

            if (i < 0 || i >= chars.length || j < 0 || j >= chars[0].length) {
                continue;
            }

            if (chars[i][j] == '#') {
                continue;
            }

            if (traversalState[i][j] != null) {
                continue;
            }

            traversalState[i][j] = depth;

            for (Direction dir : Direction.UP) {
                int nextI = i + dir.getI();
                int nextJ = j + dir.getJ();
                visitQueue.add(nextI);
                visitQueue.add(nextJ);
                visitQueue.add(depth + 1);
            }
        }
    }
}
