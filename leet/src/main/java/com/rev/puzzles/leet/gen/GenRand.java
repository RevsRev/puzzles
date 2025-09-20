package com.rev.puzzles.leet.gen;

import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public final class GenRand implements RandomGenerator {

    private final Random r;

    public GenRand() {
        this.r = new Random();
    }

    @Override
    public boolean isDeprecated() {
        return r.isDeprecated();
    }

    @Override
    public DoubleStream doubles() {
        return r.doubles();
    }

    @Override
    public DoubleStream doubles(final double randomNumberOrigin, final double randomNumberBound) {
        return r.doubles(randomNumberOrigin, randomNumberBound);
    }

    @Override
    public DoubleStream doubles(final long streamSize) {
        return r.doubles(streamSize);
    }

    @Override
    public DoubleStream doubles(final long streamSize, final double randomNumberOrigin,
                                final double randomNumberBound) {
        return r.doubles(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public IntStream ints() {
        return r.ints();
    }

    @Override
    public IntStream ints(final int randomNumberOrigin, final int randomNumberBound) {
        return r.ints(randomNumberOrigin, randomNumberBound);
    }

    @Override
    public IntStream ints(final long streamSize) {
        return r.ints(streamSize);
    }

    @Override
    public IntStream ints(final long streamSize, final int randomNumberOrigin, final int randomNumberBound) {
        return r.ints(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public LongStream longs() {
        return r.longs();
    }

    @Override
    public LongStream longs(final long randomNumberOrigin, final long randomNumberBound) {
        return r.longs(randomNumberOrigin, randomNumberBound);
    }

    @Override
    public LongStream longs(final long streamSize) {
        return r.longs(streamSize);
    }

    @Override
    public LongStream longs(final long streamSize, final long randomNumberOrigin, final long randomNumberBound) {
        return r.longs(streamSize, randomNumberOrigin, randomNumberBound);
    }

    @Override
    public boolean nextBoolean() {
        return r.nextBoolean();
    }

    @Override
    public void nextBytes(final byte[] bytes) {
        r.nextBytes(bytes);
    }

    @Override
    public float nextFloat() {
        return r.nextFloat();
    }

    @Override
    public float nextFloat(final float bound) {
        return r.nextFloat(bound);
    }

    @Override
    public float nextFloat(final float origin, final float bound) {
        return r.nextFloat(origin, bound);
    }

    @Override
    public double nextDouble() {
        return r.nextDouble();
    }

    @Override
    public double nextDouble(final double bound) {
        return r.nextDouble(bound);
    }

    @Override
    public double nextDouble(final double origin, final double bound) {
        return r.nextDouble(origin, bound);
    }

    @Override
    public int nextInt() {
        return r.nextInt();
    }

    @Override
    public int nextInt(final int bound) {
        return r.nextInt(bound);
    }

    @Override
    public int nextInt(final int origin, final int bound) {
        return r.nextInt(origin, bound);
    }

    @Override
    public long nextLong() {
        return r.nextLong();
    }

    @Override
    public long nextLong(final long bound) {
        return r.nextLong(bound);
    }

    @Override
    public long nextLong(final long origin, final long bound) {
        return r.nextLong(origin, bound);
    }

    @Override
    public double nextGaussian() {
        return r.nextGaussian();
    }

    @Override
    public double nextGaussian(final double mean, final double stddev) {
        return r.nextGaussian(mean, stddev);
    }

    @Override
    public double nextExponential() {
        return r.nextExponential();
    }
}
