package com.rev.aoc.util.math.primes;

public final class Gcd {

    private Gcd() {
    }

    public static long gcd(long a, long b) {
        if (a < b) {
            long c = a;
            a = b;
            b = c;
        }

        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }

        long rem = a % b;
        while (rem != 0) {
            a = b;
            b = rem;
            rem = a % b;
        }
        return b;
    }

}
