package com.rev.puzzles.utils.arr;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.function.Function;

import static com.rev.puzzles.utils.arr.ArrayUtils.reversed;

public final class SearchArray<T> {

    private final T[][] arr;

    @Getter @Setter
    private boolean searchColumns = true;
    @Getter @Setter
    private boolean searchRows = true;
    @Getter @Setter
    private boolean searchLRDownDiagonal = true;
    @Getter @Setter
    private boolean searchLRUpDiagonal = true;

    public SearchArray(final T[][] arr) {
        this.arr = arr;
    }

    public boolean wordSearch(final T[] target) {
        return search(target.length, array -> Arrays.equals(array, target));
    }

    public boolean search(final int wordSize, final Function<T[], Boolean> predicate) {
        return searchRows(wordSize, predicate) || searchColumns(wordSize, predicate)
                || searchLRUpDiagonal(wordSize, predicate) || searchLRDownDiagonal(wordSize, predicate);
    }

    private boolean searchColumns(final int wordSize, final Function<T[], Boolean> predicate) {
        if (!searchColumns) {
            return false;
        }

        final int len = arr[0].length;
        final T[] candidate = Arrays.copyOf(arr[0], wordSize);
        for (int j = 0; j < len; j++) {
            for (int start = 0; start < arr.length + 1 - wordSize; start++) {
                for (int i = 0; i < wordSize; i++) {
                    candidate[i] = arr[start + i][j];
                }
                if (predicate.apply(candidate) || predicate.apply(reversed(candidate))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean searchRows(final int wordSize, final Function<T[], Boolean> predicate) {
        if (!searchRows) {
            return false;
        }

        final T[] candidate = Arrays.copyOf(arr[0], wordSize);
        for (int i = 0; i < arr.length; i++) {
            final int len = arr[i].length;
            if (len < wordSize) {
                continue;
            }
            for (int start = 0;  start < len + 1 - wordSize; start++) {
                System.arraycopy(arr[i], start, candidate, 0, wordSize);
                if (predicate.apply(candidate) || predicate.apply(reversed(candidate))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean searchLRDownDiagonal(final int wordSize, final Function<T[], Boolean> predicate) {
        if (!searchLRDownDiagonal) {
            return false;
        }

        final T[] candidate = Arrays.copyOf(arr[0], wordSize);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                final int iEnd = i + wordSize;
                final int jEnd = j + wordSize;
                if (iEnd > arr.length || jEnd > arr.length) {
                    continue;
                }
                for (int a = 0; a < wordSize; a++) {
                    int iIndex = i + a;
                    int jIndex = j + a;
                    candidate[a] = arr[iIndex][jIndex];
                }

                if (predicate.apply(candidate) || predicate.apply(reversed(candidate))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean searchLRUpDiagonal(final int wordSize, final Function<T[], Boolean> predicate) {
        if (!searchLRUpDiagonal) {
            return false;
        }

        final T[] candidate = Arrays.copyOf(arr[0], wordSize);
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr[i].length - 1; j >= 0; j--) {
                final int iEnd = i + wordSize;
                final int jEnd = j + 1 - wordSize;
                if (iEnd > arr.length || jEnd < 0) {
                    continue;
                }
                for (int a = 0; a < wordSize; a++) {
                    int iIndex = i + a;
                    int jIndex = j - a;
                    candidate[a] = arr[iIndex][jIndex];
                }

                if (predicate.apply(candidate) || predicate.apply(reversed(candidate))) {
                    return true;
                }
            }
        }
        return false;
    }

}
