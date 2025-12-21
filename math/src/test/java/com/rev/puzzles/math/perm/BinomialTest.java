package com.rev.puzzles.math.perm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinomialTest {

    @Test
    public void testBinomialCoefficientsAreCorrect() {
        assertEquals(1, Binomial.nCm(0, 0));
        assertEquals(1, Binomial.nCm(1, 0));
        assertEquals(1, Binomial.nCm(1, 1));
        assertEquals(2, Binomial.nCm(2, 1));
        assertEquals(6, Binomial.nCm(4, 2));
    }

}