package com.rev.puzzles.algo.arr;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Optional;

public final class ArraySums {

    private ArraySums() {
    }

    /**
     * Find the elements of an array summing to the target in O(n)
     * @param arr
     * @param target
     * @return the indices of the original array that sum to the target, or empty if no solution exists.
     */
    public static Optional<Pair<Integer, Integer>> findElementsSummingTo(final long[] arr, final long target) {

        final HashMap<Long, Integer> partialSumsAndIndexes = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (partialSumsAndIndexes.containsKey(arr[i])) {
                return Optional.of(Pair.of(partialSumsAndIndexes.get(arr[i]), i));
            }
            final long partialSum = target - arr[i];
            partialSumsAndIndexes.put(partialSum, i);
        }
        return Optional.empty();
    }

}
