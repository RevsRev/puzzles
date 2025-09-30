package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class UlamTest {

    @Test
    public void shouldGiveCorrectUlamSequence() {
        final Ulam ulam = new Ulam(BigInteger.ONE, BigInteger.TWO);
        assertEquals(BigInteger.valueOf(1), ulam.at(0));
        assertEquals(BigInteger.valueOf(2), ulam.at(1));
        assertEquals(BigInteger.valueOf(3), ulam.at(2));
        assertEquals(BigInteger.valueOf(4), ulam.at(3));
        assertEquals(BigInteger.valueOf(6), ulam.at(4));
        assertEquals(BigInteger.valueOf(8), ulam.at(5));
        assertEquals(BigInteger.valueOf(11), ulam.at(6));
    }

}