package com.rev.aoc.util.search;

import java.util.function.Function;

public final class BinarySolutionSearch {

    private BinarySolutionSearch() {
    }

    /**
     * For certain problems, we may be given a set of values and we want to find the first value in the list
     * that satisfies a certain condition.
     * E.g. if we are dropping eggs off rungs of a ladder, we want to find the first rung the egg breaks at.
     */
    public static int search(int rangeStart, int rangeEnd, final Function<Integer, Boolean> predicate) {
        if (rangeStart == rangeEnd) {
            return rangeStart;
        }
        int mid = (rangeEnd + rangeStart) / 2;
        if (predicate.apply(mid)) {
            return search(rangeStart, mid, predicate);
        }
        return search(mid + 1, rangeEnd, predicate);
    }

}
