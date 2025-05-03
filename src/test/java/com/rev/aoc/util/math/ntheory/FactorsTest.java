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
    @MethodSource("primeFactorsTestParameters")
    public void testPrimeFactors(final Pair<Long, Map<Long,Long>> inputAndPrimeFactors) {
        Assertions.assertEquals(inputAndPrimeFactors.getRight(), Factors.primeFactors(inputAndPrimeFactors.getLeft()));
    }

    @ParameterizedTest
    @MethodSource("factorsTestParameters")
    public void testFactors(final Pair<Long, List<Long>> inputAndFactors) {
        Assertions.assertEquals(inputAndFactors.getRight(), Factors.factors(inputAndFactors.getLeft()));
    }

    public static List<Pair<Long, List<Long>>> factorsTestParameters() {
        return List.of(
                Pair.of(1L, List.of(1L)),
                Pair.of(2L, List.of(1L, 2L)),
                Pair.of(3L, List.of(1L, 3L)),
                Pair.of(16L, List.of(1L, 2L, 4L, 8L, 16L)),
                Pair.of(72L, List.of(1L, 2L, 3L, 4L, 6L, 8L, 9L, 12L, 18L, 24L, 36L, 72L)),
                Pair.of(12L, List.of(1L, 2L, 3L, 4L, 6L, 12L)),
                Pair.of(210L, List.of(1L, 2L, 3L, 5L, 6L, 7L, 10L, 14L, 15L, 21L, 30L, 35L, 42L, 70L, 105L, 210L)),
                Pair.of(1050L, List.of(1L, 2L, 3L, 5L, 6L, 7L, 10L, 14L, 15L, 21L, 25L, 30L, 35L, 42L, 50L, 70L, 75L, 105L, 150L, 175L, 210L, 350L, 525L, 1050L))
        );
    }

    public static List<Pair<Long, Map<Long, Long>>> primeFactorsTestParameters() {
        return List.of(
                Pair.of(1L, Map.of()),
                Pair.of(2L, Map.of(2L, 1L)),
                Pair.of(3L, Map.of(3L, 1L)),
                Pair.of(16L, Map.of(2L, 4L)),
                Pair.of(72L, Map.of(2L, 3L, 3L, 2L)),
                Pair.of(12L, Map.of(2L, 2L, 3L, 1L)),
                Pair.of(210L, Map.of(2L, 1L, 3L, 1L, 5L, 1L, 7L, 1L)),
                Pair.of(1050L, Map.of(2L, 1L, 3L, 1L, 5L, 2L, 7L, 1L))
        );
    }

}
