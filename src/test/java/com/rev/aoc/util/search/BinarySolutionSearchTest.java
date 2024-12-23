package com.rev.aoc.util.search;

import org.jgrapht.alg.util.Triple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Function;

public final class BinarySolutionSearchTest {

    @ParameterizedTest
    @MethodSource("getTestCases")
    public void binarySolutionSearchTest(final Triple<Integer, Integer, Integer> testCase) {
        int start = testCase.getFirst();
        int end = testCase.getSecond();
        int expected = testCase.getThird();
        Function<Integer, Boolean> predicate = i -> i >= expected;
        int result = BinarySolutionSearch.search(start, end, predicate);
        Assertions.assertEquals(expected, result);
    }

    public static List<Triple<Integer, Integer, Integer>> getTestCases() {
        return List.of(
                Triple.of(0, 12, 4),
                Triple.of(4, 12, 4),
                Triple.of(0, 12, 9),
                Triple.of(7, 12, 10),
                Triple.of(12, 12, 12),
                Triple.of(20456, 923712, 923111)
        );
    }

}
