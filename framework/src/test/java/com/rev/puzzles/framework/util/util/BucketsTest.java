package com.rev.puzzles.framework.util.util;

import com.rev.puzzles.framework.util.set.Buckets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class BucketsTest {

    @ParameterizedTest
    @MethodSource("getTestCases")
    public void testBucket(final Pair<long[], Long> inputAndResult) {
        final long[] input = inputAndResult.getLeft();
        final long expected = inputAndResult.getRight();
        Assertions.assertEquals(expected, Buckets.fillEntireCombinations(input, 25, false));
    }

    public static List<Pair<long[], Long>> getTestCases() {
        return List.of(
                Pair.of(new long[]{15, 20, 5, 10, 5}, 4L)
        );
    }

}
