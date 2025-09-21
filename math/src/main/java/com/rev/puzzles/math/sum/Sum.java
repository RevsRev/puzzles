package com.rev.puzzles.math.sum;

public final class Sum {

    private Sum() {
    }

    public static long sumMtoN(final long m, final long n) {
        return sum1toN(n) - sum1toN(m - 1);
    }

    public static long sum1toN(final long n) {
        return n * (n + 1) / 2;
    }

    public static long sumSq1toN(final long n) {
        return n * (n + 1) * (2 * n + 1) / 6;
    }

}
