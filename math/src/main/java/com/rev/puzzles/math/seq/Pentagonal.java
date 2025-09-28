package com.rev.puzzles.math.seq;

public final class Pentagonal {

    private Pentagonal() {
    }

    public static long pentagonal(final long n) {
        return (n * (3 * n - 1)) / 2;
    }
}
