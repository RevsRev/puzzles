package com.rev.aoc.util.geom;

public final class Triangle {

    private Triangle() {
    }

    public static boolean validTriangle(final long a, final long b, final long c) {
        return a < b + c && b < a + c && c < a + b;
    }
}
