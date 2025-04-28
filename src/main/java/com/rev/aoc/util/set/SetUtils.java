package com.rev.aoc.util.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
}
