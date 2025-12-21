package com.rev.puzzles.math.ntheory.number;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmicableNumbersTest {

    @Test
    public void testAmicable() {
        final AmicableNumbers amicableNumbers = new AmicableNumbers();
        assertTrue(amicableNumbers.areAmicable(220L, 284L));
    }

    @Test
    public void testNotAmicable() {
        final AmicableNumbers amicableNumbers = new AmicableNumbers();
        assertFalse(amicableNumbers.areAmicable(220L, 283L));
    }

    @Test
    public void testGetAmicableLeqN() {
        final AmicableNumbers amicableNumbers = new AmicableNumbers();
        final Collection<Long> amicableNumbersLeqN = amicableNumbers.getAmicableNumbersLeqN(1210L);

        assertEquals(Set.of(220L, 284L, 1184L, 1210L), amicableNumbersLeqN);
    }

}