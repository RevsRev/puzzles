package com.rev.puzzles.aoc.problems;

import com.rev.puzzles.aoc.framework.AnnotationProblemLoader;
import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.aoc.framework.AocResourceLoader;
import com.rev.puzzles.framework.framework.ProblemLoader;
import com.rev.puzzles.framework.framework.problem.Problem;
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

    private static final ProblemLoader<AocCoordinate> LOADER = new AnnotationProblemLoader<>(
            AocProblemI.class,
            problemI -> new AocCoordinate(
                    problemI.year(),
                    problemI.day(),
                    problemI.part()
            )
    );

    private static final SortedMap<AocCoordinate, Problem<?>> ALL_PROBLEMS =
            LOADER.loadProblemsInRange(null, null);

    @ParameterizedTest
    @MethodSource("getHappyPaths")
    public void testAocSolutions(final Map.Entry<AocCoordinate, Pair<Object, Object>> problemAndResult) {
        AocCoordinate partOneKey = problemAndResult.getKey();
        AocCoordinate partTwoKey = new AocCoordinate(
                partOneKey.getYear(),
                partOneKey.getDay(),
                2
        );
        Problem<?> partOne = ALL_PROBLEMS.get(partOneKey);
        Problem<?> partTwo = ALL_PROBLEMS.get(partTwoKey);
        if (partOne == null) {
            System.out.printf(
                    "[\u001B[31mWARNING\u001B[0m] %s has a test but the solution has not been implemented%n", partOneKey);
            return;
        }
        if (partTwo == null) {
            System.out.printf(
                    "[\u001B[31mWARNING\u001B[0m] %s has a test but the solution has not been implemented%n", partTwoKey);
            return;
        }
        Pair<Object, Object> results = problemAndResult.getValue();
        Object partOneResult = partOne.solve(AocResourceLoader.loadResources(partOneKey));
        Object partTwoResult = partTwo.solve(AocResourceLoader.loadResources(partTwoKey));
        Object expectedPartOneResult = results.getLeft();
        Object expectedPartTwoResult = results.getRight();
        assertResult(partOneKey, expectedPartOneResult, partOneResult);
        assertResult(partTwoKey, expectedPartTwoResult, partTwoResult);
    }

    @Test
    public void testAllSolutionsHaveTests() {
        Map<AocCoordinate, Pair<Object, Object>> happyPaths = getHappyPathsMap();
        for (AocCoordinate coord : ALL_PROBLEMS.keySet()) {
            if (!happyPaths.containsKey(coord)) {
                System.out.printf(
                        "[\u001B[31mWARNING\u001B[0m] %s has a solution but no tests have been implemented%n", coord);
            }
        }
    }

    public static Set<Map.Entry<AocCoordinate, Pair<Object, Object>>> getHappyPaths() {
        return getHappyPathsMap().entrySet();
    }

    private static Map<AocCoordinate, Pair<Object, Object>> getHappyPathsMap() {
        Map<AocCoordinate, Pair<Object, Object>> expectedResults = new HashMap<>();
        expectedResults.put(new AocCoordinate(2024, 1, 1), Pair.of(11L, 31L));
        expectedResults.put(new AocCoordinate(2024, 2, 1), Pair.of(2L, 4L));
        expectedResults.put(new AocCoordinate(2024, 3, 1), Pair.of(161L, 48L));
        expectedResults.put(new AocCoordinate(2024, 4, 1), Pair.of(18L, 9L));
        expectedResults.put(new AocCoordinate(2024, 5, 1), Pair.of(143L, 123L));
        expectedResults.put(new AocCoordinate(2024, 6, 1), Pair.of(41L, 6L));
        expectedResults.put(new AocCoordinate(2024, 7, 1), Pair.of(3749L, 11387L));
        expectedResults.put(new AocCoordinate(2024, 8, 1), Pair.of(14L, 34L));
        expectedResults.put(new AocCoordinate(2024, 9, 1), Pair.of(1928L, 2858L));
        expectedResults.put(new AocCoordinate(2024, 10, 1), Pair.of(36L, 81L));
        expectedResults.put(new AocCoordinate(2024, 11, 1), Pair.of(55312L, 65601038650482L));
        expectedResults.put(new AocCoordinate(2024, 12, 1), Pair.of(1930L, 1206L));
        expectedResults.put(new AocCoordinate(2024, 14, 1), Pair.of(21L, 7687L)); //TODO - Make better?
        expectedResults.put(new AocCoordinate(2024, 15, 1), Pair.of(10092L, 9021L));
        expectedResults.put(new AocCoordinate(2024, 16, 1), Pair.of(11048L, 64L));
        expectedResults.put(new AocCoordinate(2024, 17, 1), Pair.of("5,7,3,0", 105576L));
        expectedResults.put(new AocCoordinate(2024, 19, 1), Pair.of(6L, 16L));
        expectedResults.put(new AocCoordinate(2024, 20, 1), Pair.of(0L, 0L));
        expectedResults.put(new AocCoordinate(2024, 21, 1), Pair.of(126384L, 154115708116294L));
        expectedResults.put(new AocCoordinate(2024, 22, 1), Pair.of(37990510L, 23L));
        expectedResults.put(new AocCoordinate(2024, 23, 1), Pair.of(7L, "co,de,ka,ta"));
        expectedResults.put(new AocCoordinate(2024, 24, 1), Pair.of(2024L, "cqk,fph,gds,jrs,wrk,z15,z21,z34"));
        expectedResults.put(new AocCoordinate(2024, 25, 1), Pair.of(3L, "n/a"));
        return expectedResults;
    }

    private static void assertResult(final AocCoordinate coord,
                                     final Object expected,
                                     final Object actual) {
        if (actual == null) {
            System.out.printf(
                    "[\u001B[31mWARNING\u001B[0m] %s has a test but the solution has not been implemented%n", coord);
            return;
        }
        Assertions.assertEquals(expected, actual);
    }
}
