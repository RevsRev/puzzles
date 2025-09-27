package com.rev.puzzles.math.ntheory.digit;

import java.math.BigInteger;

public final class Digit {

    private Digit() {
    }

    public static long digitProduct(long n) {
        long product = 1;
        while (n > 0) {
            product *= (n % 10);
            n = n / 10;
        }
        return product;
    }

    @SuppressWarnings("checkstyle:FinalParameters")
    public static long digitSum(BigInteger n) {
        long sum = 0;
        while (n.compareTo(BigInteger.ZERO) > 0) {
            sum += n.mod(BigInteger.TEN).longValue();
            n = n.divide(BigInteger.TEN);
        }
        return sum;
    }

}
