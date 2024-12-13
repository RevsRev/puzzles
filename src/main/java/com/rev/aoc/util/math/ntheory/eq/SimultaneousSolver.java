package com.rev.aoc.util.math.ntheory.eq;

import com.rev.aoc.util.math.linalg.matrix.Mat2;
import com.rev.aoc.util.math.linalg.vec.Vec2;

public final class SimultaneousSolver {

    /**
     * ax + by = c
     * Ax + By = C
     */
    private final Mat2 lhs;
    private final Vec2 rhs;

    public SimultaneousSolver(Mat2 lhs, Vec2 rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public boolean canSolve() {
        return Math.abs(lhs.det()) > 0.0001;
    }

    public Vec2 solve() {
        Mat2 inverse = lhs.inverse();
        return inverse.mult(rhs);
    }
}
