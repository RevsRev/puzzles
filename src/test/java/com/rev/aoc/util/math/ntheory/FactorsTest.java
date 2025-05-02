package com.rev.aoc.util.math.ntheory;

import com.rev.aoc.util.math.ntheory.primes.Factors;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;

public class FactorsTest {

    @ParameterizedTest
    @MethodSource("factorsTestParameters")
    public void testPrimeFactors(final Pair<Long, Map<Long,Long>> inputAndPrimeFactors) {
        Assertions.assertEquals(inputAndPrimeFactors.getRight(), Factors.primeFactors(inputAndPrimeFactors.getLeft()));
    }

    public static List<Pair<Long, Map<Long, Long>>> factorsTestParameters() {
        return List.of(
                Pair.of(1L, Map.of()),
                Pair.of(2L, Map.of(2L, 1L)),
                Pair.of(16L, Map.of(2L, 4L)),
                Pair.of(72L, Map.of(2L, 3L, 3L, 2L)),
                Pair.of(12L, Map.of(2L, 2L, 3L, 1L)),
                Pair.of(210L, Map.of(2L, 1L, 3L, 1L, 5L, 1L, 7L, 1L)),
                Pair.of(1050L, Map.of(2L, 1L, 3L, 1L, 5L, 2L, 7L, 1L))
        );
    }

}
