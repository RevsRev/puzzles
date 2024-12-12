package com.rev.aoc.util.math.linear.vec;

import lombok.Getter;

public final class Vec2 {
    @Getter
    private double x;
    @Getter
    private double y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(final Vec2 v) {
        x = v.x;
        y = v.y;
    }
    public double dot(final Vec2 v) {
        return x * v.x + y * v.y;
    }

    public double abs() {
        return Math.sqrt(this.dot(this));
    }
    public void plus(final Vec2 v) {
        x += v.x;
        y += v.y;
    }

    public void mult(double a) {
        x *= a;
        y *= a;
    }

    private static final double EPSILON = 0.001;

    public void normalize() {
        double abs = this.abs();
        this.x /= abs;
        this.y /= abs;
    }

    public static Vec2 normalize(final Vec2 p) {
        double x = p.x;
        double y = p.y;
        double magnitude = Math.sqrt(x * x + y * y);

        if (magnitude < EPSILON) {
            return new Vec2(0, 0);
        }
        return new Vec2(x / magnitude, y / magnitude);
    }
}
