package com.rev.puzzles.algo.arr;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ArraySumsTest {

    @Test
    public void shouldFindElementsSummingTo() {
        final long[] arr = new long[]{-74, -3, 0, 32, 191, 281, 1098};
        final Optional<Pair<Integer, Integer>> elementsSummingTo = ArraySums.findElementsSummingTo(arr, 1095);
        Assertions.assertTrue(elementsSummingTo.isPresent());
        Assertions.assertEquals(Pair.of(1, 6), elementsSummingTo.get());
    }

    @Test
    public void shouldNotFindElementsSummingTo() {
        final long[] arr = new long[]{-74, -3, 0, 32, 191, 281, 1098};
        final Optional<Pair<Integer, Integer>> elementsSummingTo = ArraySums.findElementsSummingTo(arr, 33);
        Assertions.assertTrue(elementsSummingTo.isEmpty());
    }

}