package com.rev.puzzles.math.linalg.vec;

import lombok.Getter;

import java.util.Objects;

public final class Vec3 {
    @Getter
    private double x;
    @Getter
    private double y;
    @Getter
    private double z;

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(final Vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public double dot(final Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double abs() {
        return Math.sqrt(this.dot(this));
    }

    public void plus(final Vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void minus(final Vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public static Vec3 minus(final Vec3 first, final Vec3 second) {
        return new Vec3(
                first.x - second.x,
                first.y - second.y,
                first.z - second.z
        );
    }

    public void mult(double a) {
        x *= a;
        y *= a;
        z *= a;
    }

    private static final double EPSILON = 0.001;

    public void normalize() {
        double abs = this.abs();
        this.x /= abs;
        this.y /= abs;
        this.z /= abs;
    }

    public static Vec3 normalize(final Vec3 p) {
        double x = p.x;
        double y = p.y;
        double z = p.z;
        double magnitude = Math.sqrt(x * x + y * y + z * z);

        if (magnitude < EPSILON) {
            return new Vec3(0, 0, 0);
        }
        return new Vec3(x / magnitude, y / magnitude, z / magnitude);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Vec3 vec3 = (Vec3) o;
        return Double.compare(x, vec3.x) == 0 && Double.compare(y, vec3.y) == 0 && Double.compare(z, vec3.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
