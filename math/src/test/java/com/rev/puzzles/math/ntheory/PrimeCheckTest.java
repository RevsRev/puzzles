package com.rev.puzzles.math.ntheory;

import com.rev.puzzles.math.ntheory.primes.PrimeCheck;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public class PrimeCheckTest {

    @ParameterizedTest
    @MethodSource("primeCheckTestParams")
    public void testPrimeCheck(final Pair<Long, Boolean> primeAndResult) {
        Assertions.assertEquals(primeAndResult.getRight(), PrimeCheck.create().primeCheck(primeAndResult.getLeft()));
    }

    public static List<Pair<Long, Boolean>> primeCheckTestParams() {
        return List.of(
                Pair.of(1L, false),
                Pair.of(2L, true),
                Pair.of(4L, false),
                Pair.of(3L, true),
                Pair.of(11L, true),
                Pair.of(15L, false),
                Pair.of(113L, true),
                Pair.of(215L, false),
                Pair.of(9839L, true),
                Pair.of(21662657L, false)
        );
    }

}
