package com.rev.puzzles.utils.arr;

public final class ArrayUtils {

    private ArrayUtils() {
    }

    public static <T> T[] reversed(final T[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            T start = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = start;
        }
        return arr;
    }

}
