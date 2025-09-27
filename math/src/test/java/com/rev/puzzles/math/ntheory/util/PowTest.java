package com.rev.puzzles.math.ntheory.util;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowTest {

    @Test
    public void testPowLong() {
        assertEquals(1, Pow.pow(2, 0));
        assertEquals(9, Pow.pow(3, 2));
        assertEquals(125, Pow.pow(5, 3));
    }

    @Test
    public void testPowBigInteger() {
        assertEquals(new BigInteger("1"), Pow.pow(new BigInteger("2"), 0));
        assertEquals(new BigInteger("9"), Pow.pow(new BigInteger("3"), 2));
        assertEquals(new BigInteger("125"), Pow.pow(new BigInteger("5"), 3));
    }
}