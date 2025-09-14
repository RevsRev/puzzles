package com.rev.puzzles.framework.util.arr;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static long sum(final long[] packages) {
        return sum(packages, packages.length);
    }

    public static long sum(final long[] packages, final long end) {
        long sum = 0;
        for (int i = 0; i < Math.min(packages.length, end); i++) {
            sum += packages[i];
        }
        return sum;
    }

    public static long mult(final long[] packages) {
        return mult(packages, packages.length);
    }

    public static long mult(final long[] packages, final long end) {
        long mult = 1;
        for (int i = 0; i < Math.min(packages.length, end); i++) {
            mult *= packages[i];
        }
        return mult;
    }

    public static long sum(final Long[] packages) {
        return sum(packages, packages.length);
    }

    public static long sum(final Long[] packages, final long end) {
        long sum = 0;
        for (int i = 0; i < Math.min(packages.length, end); i++) {
            sum += packages[i];
        }
        return sum;
    }

    public static long mult(final Long[] packages) {
        return mult(packages, packages.length);
    }

    public static long mult(final Long[] packages, final long end) {
        long mult = 1;
        for (int i = 0; i < Math.min(packages.length, end); i++) {
            mult *= packages[i];
        }
        return mult;
    }
}
