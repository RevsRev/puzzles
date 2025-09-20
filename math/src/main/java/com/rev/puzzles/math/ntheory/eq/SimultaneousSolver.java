package com.rev.puzzles.math.ntheory.eq;

import com.rev.puzzles.math.linalg.matrix.Mat2;
import com.rev.puzzles.math.linalg.vec.Vec2;

@SuppressWarnings("checkstyle:MagicNumber")
public final class SimultaneousSolver {

    /**
     * ax + by = c
     * Ax + By = C
     */
    private final Mat2 lhs;
    private final Vec2 rhs;

    public SimultaneousSolver(final Mat2 lhs, final Vec2 rhs) {
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
