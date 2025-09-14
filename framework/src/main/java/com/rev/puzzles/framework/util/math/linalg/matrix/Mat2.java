package com.rev.puzzles.framework.util.math.linalg.matrix;


import com.rev.puzzles.framework.util.math.linalg.vec.Vec2;

public final class Mat2 {
    /**
        a b
        c d
     **/
    private double a;
    private double b;
    private double c;
    private double d;

    public Mat2(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Mat2 mult(final Mat2 r) {
        return new Mat2(a * r.a + b * r.c, a * r.b + b * r.d, c * r.a + d * r.c, c * r.b + d * r.d);
    }

    public Vec2 mult(final Vec2 v) {
        return new Vec2(a * v.getX() + b * v.getY(), c * v.getX() + d * v.getY());
    }

    public Mat2 inverse() {
        double det = det();
        return new Mat2(d / det, -b / det, -c / det, a / det);
    }
    public double det() {
        return a * d - c * b;
    }

    public static Mat2 rot(double theta) {
        double c = Math.cos(theta);
        double s = Math.sin(theta);
        return new Mat2(c, -s, s, c);
    }
}
