package com.rev.puzzles.math.ntheory.util;

import java.math.BigInteger;

public final class Factorial {

    private Factorial() {
    }

    public static BigInteger factorial(final BigInteger n) {
        if (BigInteger.ZERO.equals(n)) {
            return BigInteger.ONE;
        }
        return n.multiply(factorial(n.subtract(BigInteger.ONE)));
    }
}
