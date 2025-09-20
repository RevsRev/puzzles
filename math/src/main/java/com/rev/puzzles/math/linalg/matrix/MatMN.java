package com.rev.puzzles.math.linalg.matrix;

import lombok.Getter;

import java.util.Random;

public final class MatMN {
    @Getter
    private final int width;
    @Getter
    private final int height;

    @Getter
    private final double[][] mat;

    public MatMN(int height, int width) {
        this.width = width;
        this.height = height;
        this.mat = new double[height][width];
    }

    public MatMN(final double[][] mat) {
        this.width = mat[0].length;
        this.height = mat.length;
        this.mat = mat;

    }

    public static MatMN random(int width, int height, double min, double max) {
        Random r = new Random(System.currentTimeMillis());
        double[][] randArr = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                randArr[i][j] = min + (max - min) * r.nextDouble();
            }
        }
        return new MatMN(randArr);
    }

    public MatMN mult(final MatMN other) {
        MatMN resultMatrix = new MatMN(new double[height][other.width]);
        mult(other, resultMatrix);
        return resultMatrix;
    }

    public void mult(final MatMN other, final MatMN resultMatrix) {
        if (width != other.height) {
            throw new IllegalArgumentException(
                    String.format("Cannot multiply a %sx%s matrix by a %sx%s matrix", height, width, other.height,
                            other.width));
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < other.width; j++) {
                double prod = 0;
                for (int k = 0; k < width; k++) {
                    prod += mat[i][k] * other.mat[k][j];
                }
                resultMatrix.mat[i][j] = prod;
            }
        }
    }

    public void mult(double scalar) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mat[i][j] *= scalar;
            }
        }
    }

}
