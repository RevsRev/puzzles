package com.rev.puzzles.math.prob;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;
import java.util.Set;

class DiscreteMonteCarloTest {

    @Test
    public void testMonteCarloWithSixSidedDie() {
        final Distribution<Integer> die = new SixSidedDie();
        final Set<Integer> values = Set.of(1, 2, 3, 4, 5, 6);
        final DiscreteMonteCarlo<Integer> monteCarlo = new DiscreteMonteCarlo<>(die, values, 100, 0.0001);

        final Map<Integer, Double> probabilities = monteCarlo.run();
        Assertions.assertEquals(values, probabilities.keySet());
        probabilities.values().forEach(v -> Assertions.assertTrue(Math.abs(v - 1 / 6d) < 0.01));
    }

    private static final class SixSidedDie implements Distribution<Integer> {

        private final Random r = new Random();

        @Override
        public Integer sample() {
            return r.nextInt(6) + 1;
        }
    }

}