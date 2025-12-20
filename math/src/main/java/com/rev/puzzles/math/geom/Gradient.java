package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.ntheory.primes.Gcd;

import java.util.Objects;

public final class Gradient {
    private final long rise;
    private final long run;

    private Gradient(long rise, long run) {
        this.rise = rise;
        this.run = run;
    }

    public static Gradient create(long rise, long run) {
        long sign = rise * run > 0 ? 1 : -1;
        long gcd = sign * Gcd.gcd(Math.abs(rise), Math.abs(run));
        return new Gradient(rise / gcd, run / gcd);
    }

    public boolean parallelOrAntiParallel(final Gradient other) {
        return parallelOrAntiParallel(this, other);
    }

    public boolean perpendicular(final Gradient other) {
        return perpendicular(this, other);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Gradient) obj;
        return this.rise == that.rise &&
                this.run == that.run;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rise, run);
    }

    @Override
    public String toString() {
        return "Gradient[" +
                "rise=" + rise + ", " +
                "run=" + run + ']';
    }

    private static boolean parallelOrAntiParallel(final Gradient gradient, final Gradient other) {
        //We've already factored out gcd's in the create method
        return gradient.equals(other) || new Gradient(-1 * gradient.rise, -1 * gradient.run).equals(other);
    }

    private static boolean perpendicular(final Gradient gradient, final Gradient other) {
        return gradient.rise * other.rise + gradient.run * other.run == 0;
    }

}
