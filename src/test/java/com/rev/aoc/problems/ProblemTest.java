package com.rev.aoc.problems;

import com.rev.aoc.framework.AocProblemLoader;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public final class ProblemTest {

    private static final AocProblemLoader loader = new AocProblemLoader();
    private static final SortedMap<AocCoordinate, AocProblem> allProblems = loader.loadProblemsInRange(null, null);

    @ParameterizedTest
    @MethodSource("getHappyPaths")
    public void testAocSolutions(final Map.Entry<AocCoordinate, Pair<Long, Long>> problemAndResult) {
        AocCoordinate key = problemAndResult.getKey();
        AocProblem solution = allProblems.get(key);
        if (solution == null) {
            System.out.printf("[\u001B[31mWARNING\u001B[0m] %s has a test but the solution has not been implemented%n", key);
            return;
        }
        Pair<Long, Long> results = problemAndResult.getValue();
        long partOneResult = solution.partOne();
        long partTwoResult = solution.partTwo();
        long expectedPartOneResult = results.getLeft();
        long expectedPartTwoResult = results.getRight();
        assertResult(key, expectedPartOneResult, partOneResult);
        assertResult(key, expectedPartTwoResult, partTwoResult);
    }

    @Test
    public void testAllSolutionsHaveTests() {
        Map<AocCoordinate, Pair<Long, Long>> happyPaths = getHappyPathsMap();
        for (AocCoordinate coord : allProblems.keySet()) {
            if (!happyPaths.containsKey(coord)) {
                System.out.printf("[\u001B[31mWARNING\u001B[0m] %s has a solution but no tests have been implemented%n", coord);
            }
        }
    }

    public static Set<Map.Entry<AocCoordinate, Pair<Long, Long>>> getHappyPaths() {
        return getHappyPathsMap().entrySet();
    }

    private static Map<AocCoordinate, Pair<Long, Long>> getHappyPathsMap() {
        Map<AocCoordinate, Pair<Long, Long>> expectedResults = new HashMap<>();
        expectedResults.put(new AocCoordinate(2024, 1), Pair.of(11L, 31L));
        expectedResults.put(new AocCoordinate(2024, 2), Pair.of(2L, 4L));
        expectedResults.put(new AocCoordinate(2024, 3), Pair.of(161L, 48L));
        expectedResults.put(new AocCoordinate(2024, 4), Pair.of(18L, 9L));
        expectedResults.put(new AocCoordinate(2024, 5), Pair.of(143L, 123L));
        expectedResults.put(new AocCoordinate(2024, 6), Pair.of(41L, 6L));
        expectedResults.put(new AocCoordinate(2024, 7), Pair.of(3749L, 11387L));
        expectedResults.put(new AocCoordinate(2024, 8), Pair.of(14L, 34L));
        expectedResults.put(new AocCoordinate(2024, 9), Pair.of(1928L, 2858L));
        expectedResults.put(new AocCoordinate(2024, 10), Pair.of(36L, 81L));
        expectedResults.put(new AocCoordinate(2024, 11), Pair.of(55312L, 65601038650482L));
        expectedResults.put(new AocCoordinate(2024, 12), Pair.of(1930L, 1206L));
        expectedResults.put(new AocCoordinate(2024, 14), Pair.of(21L, 7687L)); //TODO - Make better?
        expectedResults.put(new AocCoordinate(2024, 15), Pair.of(10092L, 9021L));
        expectedResults.put(new AocCoordinate(2024, 16), Pair.of(11048L, 64L));
        expectedResults.put(new AocCoordinate(2024, 17), Pair.of(4635635210L, 64L));
        return expectedResults;
    }

    private static void assertResult(final AocCoordinate coord, long expected, long actual) {
        if (actual == -1) {
            System.out.printf("[\u001B[31mWARNING\u001B[0m] %s has a test but the solution has not been implemented%n", coord);
            return;
        }
        Assertions.assertEquals(expected, actual);
    }
}