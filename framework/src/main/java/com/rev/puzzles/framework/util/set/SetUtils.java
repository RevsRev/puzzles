package com.rev.puzzles.framework.util.set;

import com.rev.puzzles.framework.util.arr.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public final class SetUtils {

    private SetUtils() {
    }

    public static <T> Set<T> copyRemoveAll(final Set<T> set, final Set<T> remove) {
        Set<T> copy = new HashSet<>(set);
        copy.removeAll(remove);
        return copy;
    }

    public static <T> Set<T> copyRetainAll(final Set<T> set, final Set<T> retain) {
        Set<T> copy = new HashSet<>(set);
        copy.retainAll(retain);
        return copy;
    }

    public static <T> T[] copyRemoveAll(final T[] set, final T[] remove) {
        Set<T> removeSet = new HashSet<>(Arrays.asList(remove));

        int newLength = 0;
        final T[] retval = Arrays.copyOf(set, set.length);
        for (T t : set) {
            if (!removeSet.contains(t)) {
                retval[newLength++] = t;
            }
        }
        return Arrays.copyOfRange(retval, 0, newLength);
    }

    public static <T> List<T[]> subsetsOfSizeN(final T[] input, final T[] set) {
        return subsetsOfSizeN(input, set, 0);
    }

    public static <T> List<T[]> subsetsOfSizeN(final T[] input, final T[] set, final int inputStart) {
        final List<T[]> results = new ArrayList<>();
        Consumer<T[]> consumer = arr -> results.add(Arrays.copyOf(set, set.length));
        setsOfSizeN(input, set, inputStart, 0, consumer);
        return results;
    }

    private static <T> void setsOfSizeN(final T[] input,
                                        final T[] set,
                                        final int inputIndex,
                                        final int setIndex,
                                        final Consumer<T[]> consumer) {
        if (setIndex == set.length) {
            consumer.accept(set);
            return;
        }

        if (inputIndex == input.length) {
            return;
        }

        for (int i = inputIndex; i < input.length; i++) {
            set[setIndex] = input[i];
            setsOfSizeN(input, set, i + 1, setIndex + 1, consumer);
        }
    }

    public static <T> List<T[]> subsetsOfSizeLeqN(final T[] input, final T[] set) {
        return subsetsOfSizeLeqN(input, set, 0);
    }

    public static <T> List<T[]> subsetsOfSizeLeqN(final T[] input, final T[] set, final int inputStart) {
        return constrainedSetsOfSizeLeqN(
                input,
                set,
                inputStart,
                Constraint.unconstrained()
        );
    }

    public static <T> List<T[]> constrainedSetsOfSizeLeqN(
            final T[] input,
            final T[] set,
            final Constraint<T> constraint) {
        return constrainedSetsOfSizeLeqN(input, set, 0, constraint);
    }

    public static <T> List<T[]> constrainedSetsOfSizeLeqN(
            final T[] input,
            final T[] set,
            final int inputStart,
            final Constraint<T> constraint) {
        final List<T[]> results = new ArrayList<>();
        Consumer<T[]> consumer = arr -> results.add(arr);
        constrainedSetsOfSizeLeqN(input, set, inputStart, 0, constraint, consumer);
        return results;
    }

    private static <T> void constrainedSetsOfSizeLeqN(
            final T[] input,
            final T[] set,
            final int inputIndex,
            final int setIndex,
            final Constraint<T> constraint,
            final Consumer<T[]> consumer) {

        if (setIndex <= set.length) {
            if (constraint.satisfied(setIndex, set)) {
                consumer.accept(Arrays.copyOfRange(set, 0, setIndex));
            }
        }

        if (inputIndex == input.length || setIndex == set.length || constraint.earlyExit(setIndex, set)) {
            return;
        }

        for (int i = inputIndex; i < input.length; i++) {
            set[setIndex] = input[i];
            constrainedSetsOfSizeLeqN(input, set, i + 1, setIndex + 1, constraint, consumer);
        }
    }

    public static final class Constraint<T> {
        private final BiFunction<Integer, T[], Boolean> earlyExit;
        private final BiFunction<Integer, T[], Boolean> constraint;

        public Constraint(final BiFunction<Integer, T[], Boolean> earlyExit,
                          final BiFunction<Integer, T[], Boolean> constraint) {
            this.earlyExit = earlyExit;
            this.constraint = constraint;
        }

        public static <T> Constraint<T> unconstrained() {
            return new Constraint<>((i, arr) -> false, (i, arr) -> true);
        }

        public boolean earlyExit(final int setLength, final T[] set) {
            return earlyExit.apply(setLength, set);
        }

        public boolean satisfied(final int setLength, final T[] set) {
            return constraint.apply(setLength, set);
        }
    }

    public static List<long[][]> divideIntoEqualBins(final long[] arr, final int numberOfBins) {
        final List<long[][]> solutions = new ArrayList<>();
        final BiConsumer<long[][], int[]> solutionConsumer = (sol, lens) -> solutions.add(copy(sol, lens));

        divideIntoEqualBins(arr, numberOfBins, solutionConsumer);
        return solutions;
    }

    public static void divideIntoEqualBins(
            final long[] arr,
            final int numberOfBins,
            final BiConsumer<long[][], int[]> solutionConsumer) {
        if (arr.length == 0) {
            return;
        }

        final long sum = ArrayUtils.sum(arr);
        if (sum % numberOfBins != 0) {
            throw new IllegalArgumentException(String.format(
                    "Cannot divide '%s' equally amongst '%s' bins",
                    sum,
                    numberOfBins)
            );
        }

        final long binSize = sum / numberOfBins;
        final int[] binEnds = new int[numberOfBins];
        final long[] binSums = new long[numberOfBins];
        final long[][] bins = new long[numberOfBins][arr.length];
        for (int i = 0; i < numberOfBins; i++) {
            bins[i] = Arrays.copyOfRange(arr, 0, arr.length);
        }

        divideIntoEqualBins(arr, 0, bins, binSize, binEnds, binSums, solutionConsumer);
    }

    private static void divideIntoEqualBins(
            final long[] arr,
            final int arrayIndex,
            final long[][] bins,
            final long binSize,
            final int[] binEnds,
            final long[] binSums,
            final BiConsumer<long[][], int[]> solutionConsumer
    ) {

        if (arrayIndex == arr.length) {
            solutionConsumer.accept(bins, binEnds);
            return;
        }

        long value = arr[arrayIndex];
        for (int j = 0; j < bins.length; j++) {
            if (binSums[j] + value <= binSize) {
                binSums[j] += value;
                int binEnd = binEnds[j];
                bins[j][binEnd] = value;
                binEnds[j] = binEnd + 1;

                divideIntoEqualBins(arr, arrayIndex + 1, bins, binSize, binEnds, binSums, solutionConsumer);

                binEnds[j] = binEnd;
                binSums[j] -= value;
            }
        }

    }

    public static long[][] copy(final long[][] bins, final int[] binEnds) {
        long[][] copy = new long[bins.length][];
        for (int i = 0; i < bins.length; i++) {
            copy[i] = Arrays.copyOfRange(bins[i], 0, binEnds[i]);
        }
        return copy;
    }
}
