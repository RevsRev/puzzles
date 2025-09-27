package com.rev.puzzles.math.ntheory.util;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactorialTest {

    @Test
    public void testFactorial() {
        assertEquals(new BigInteger("3628800"), Factorial.factorial(new BigInteger("10")));
    }

}