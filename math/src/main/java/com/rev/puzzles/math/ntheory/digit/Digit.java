package com.rev.puzzles.math.ntheory.digit;

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

}
