package com.rev.puzzles.math.ntheory.digit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

class DigitTest {

    @Test
    public void digitProductTest() {
        Assertions.assertEquals(54, Digit.digitProduct(1239));
    }

    @Test
    public void digitSumTest() {
        Assertions.assertEquals(10L, Digit.digitSum(new BigInteger("1234")));
    }

}