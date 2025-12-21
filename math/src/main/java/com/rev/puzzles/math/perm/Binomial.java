package com.rev.puzzles.math.perm;

import java.util.ArrayList;
import java.util.List;

public final class Binomial {

    private static final List<List<Long>> PASCALS_TRIANGLE = new ArrayList<>();

    private Binomial() {
    }

    /**
     * Compute the value of n choose m.
     * <p>
     * We could use the formula n!/(m!(n-m)!), however this is trickier to use as it involves computing
     * large factorials (or implementing some clever cancelling of factors).
     * <p>
     * Instead, we take the approach of caching pascals triangle, which is O(n^2) for
     * a triangle of height n. In most problems, this will suffice.
     *
     * @param n
     * @param m
     * @return n choose m
     */
    public static long nCm(final int n, final int m) {
        computeNthRow(n);
        return PASCALS_TRIANGLE.get(n).get(m);
    }

    private static void computeNthRow(final int n) {
        if (PASCALS_TRIANGLE.size() > n) {
            return;
        }

        if (PASCALS_TRIANGLE.isEmpty()) {
            PASCALS_TRIANGLE.add(List.of(1L));
        }

        while (PASCALS_TRIANGLE.size() <= n) {
            final List<Long> lastRow = PASCALS_TRIANGLE.get(PASCALS_TRIANGLE.size() - 1);
            final List<Long> nextRow = new ArrayList<>(lastRow.size() + 1);
            nextRow.add(1L);
            for (int i = 0; i < lastRow.size() - 1; i++) {
                nextRow.add(lastRow.get(i) + lastRow.get(i + 1));
            }
            nextRow.add(1L);
            PASCALS_TRIANGLE.add(nextRow);
        }
    }

}
