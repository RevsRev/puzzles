package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.geom.Direction;
import com.rev.aoc.util.search.BinarySolutionSearch;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D18 extends AocProblem<Long, String> {

    private static final int CORRUPTED_FLAG = 1;
    private static final int VISITED_FLAG = CORRUPTED_FLAG << 1;
    private static final int PROBLEM_WIDTH = 71;
    private static final int PROBLEM_HEIGHT = 71;

    @SuppressWarnings("checkstyle:MagicNumber")
    @AocProblemI(year = 2024, day = 18, part = 1)
    @Override
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        int[] ints = LoaderUtils.linesToIntArray(resourceLoader.resources(), s -> s.split(","));
        int limit = 1024;
        final Map<Pair<Integer, Integer>, Integer> visited = getVisited(limit, ints);
        return (long) visited.get(Pair.of(PROBLEM_HEIGHT - 1, PROBLEM_WIDTH - 1));
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @AocProblemI(year = 2024, day = 18, part = 2)
    @Override
    public String partTwoImpl(final ResourceLoader resourceLoader) {
        int[] ints = LoaderUtils.linesToIntArray(resourceLoader.resources(), s -> s.split(","));
        int start = 1025;
        final Map<Pair<Integer, Integer>, Integer> visited = new HashMap<>();
        int firstFailing = BinarySolutionSearch.search(1025, ints.length / 2, i -> {
            visited.clear();
            visited.putAll(getVisited(i, ints));
            return !visited.containsKey(Pair.of(PROBLEM_HEIGHT - 1, PROBLEM_WIDTH - 1));
        });
        int yCoord = ints[firstFailing * 2 - 1];
        int xCoord = ints[firstFailing * 2 - 2];
        return xCoord + "," + yCoord;
    }

    private static Map<Pair<Integer, Integer>, Integer> getVisited(int limit, final int[] ints) {
        int[][] mem = LoaderUtils.emptyIntMatrix(PROBLEM_HEIGHT, PROBLEM_WIDTH);
        for (int i = 0; i < Math.min(2 * limit, ints.length); i += 2) {
            mem[ints[i + 1]][ints[i]] = CORRUPTED_FLAG;
        }

        final Map<Pair<Integer, Integer>, Integer> visited = new HashMap<>();
        visited.put(Pair.of(0, 0), 0);

        Set<Pair<Integer, Integer>> frontier = new HashSet<>();
        frontier.add(Pair.of(0, 0));
        while (!frontier.isEmpty()) {
            final Set<Pair<Integer, Integer>> nextFrontier = new HashSet<>();
            for (Pair<Integer, Integer> tile : frontier) {
                int i = tile.getLeft();
                int j = tile.getRight();
                int nextCost = visited.get(tile) + 1;
                for (Direction direction : Direction.values()) {
                    int nextI = i + direction.getI();
                    int nextJ = j + direction.getJ();
                    if (nextI >= 0 && nextI < PROBLEM_HEIGHT && nextJ >= 0 && nextJ < PROBLEM_WIDTH) {
                        if (mem[nextI][nextJ] == CORRUPTED_FLAG) {
                            continue;
                        }
                        Pair<Integer, Integer> key = Pair.of(nextI, nextJ);
                        if (visited.getOrDefault(key, Integer.MAX_VALUE) > nextCost) {
                            visited.put(key, nextCost);
                            nextFrontier.add(key);
                        }
                    }
                }
            }
            frontier = nextFrontier;
        }
        return visited;
    }
}
