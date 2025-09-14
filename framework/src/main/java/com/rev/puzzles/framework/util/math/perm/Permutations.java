package com.rev.puzzles.framework.util.math.perm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class Permutations {

    private Permutations() {
    }

    public static <T> Collection<T[]> uniquePermutations(final T[] input) {
        List<T[]> permutations = permutations(input);
        Map<Integer, T[]> computedHash = new HashMap<>();
        for (T[] perm : permutations) {
            int hash = Arrays.hashCode(perm);
            computedHash.put(hash, perm);
        }
        return computedHash.values();
    }

    public static <T> List<T[]> permutations(final T[] input) {
        final List<T[]> results = new ArrayList<>();
        Consumer<T[]> consumer = arr -> results.add(Arrays.copyOf(input, input.length));
        permute(input, 0, consumer);
        return results;
    }
    private static <T> void permute(final T[] input, final int index, final Consumer<T[]> consumer) {

        if (index == input.length) {
            consumer.accept(input);
            return;
        }

        permute(input, index + 1, consumer);
        for (int i = index + 1; i < input.length; i++) {
            T swap = input[i];
            input[i] = input[index];
            input[index] = swap;
            permute(input, index + 1, consumer);
        }
    }

    public static Collection<char[]> uniquePermutations(final char[] input) {
        List<char[]> permutations = permutations(input);
        Map<Integer, char[]> computedHash = new HashMap<>();
        for (char[] perm : permutations) {
            int hash = Arrays.hashCode(perm);
            computedHash.put(hash, perm);
        }
        return computedHash.values();
    }

    public static List<char[]> permutations(final char[] input) {
        final List<char[]> results = new ArrayList<>();
        Consumer<char[]> consumer = arr -> results.add(Arrays.copyOf(input, input.length));
        permute(input, 0, consumer);
        return results;
    }
    private static void permute(final char[] input, final int index, final Consumer<char[]> consumer) {

        if (index == input.length - 1) {
            consumer.accept(input);
            return;
        }

        for (int i = index + 1; i < input.length; i++) {
            char swap = input[i];
            input[i] = input[index];
            input[index] = swap;
            permute(input, index + 1, consumer);
        }
    }

    public static <T> List<List<T>> getCombinations(final List<List<T>> input) {
        List<T> perm = new ArrayList<>(input.size());
        final List<List<T>> allPerms = new ArrayList<>();
        getCombinations(input, perm, allPerms::add, 0);
        return allPerms;
    }

    private static <T> void getCombinations(final List<List<T>> input,
                                                     final List<T> perm,
                                                     final Consumer<List<T>> permConsumer,
                                                     int index) {
        if (index == input.size()) {
            permConsumer.accept(List.copyOf(perm));
            return;
        }

        for (int i = 0; i < input.get(index).size(); i++) {
            if (perm.size() == index) {
                perm.add(input.get(index).get(i));
            } else {
                perm.set(index, input.get(index).get(i));
            }
            getCombinations(input, perm, permConsumer, index + 1);
        }
    }

}
