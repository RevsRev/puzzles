package com.rev.puzzles.framework.util.math.perm;

import com.rev.puzzles.framework.util.math.perm.Permutations;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public final class PermutationsTest {

    @ParameterizedTest
    @MethodSource("getTestCases")
    public void testPermutations(final Pair<Object[], List<Object[]>> input) {
        Object[] in = input.getLeft();
        List<Object[]> out = input.getRight();
        Assertions.assertArrayEquals(out.toArray(), Permutations.permutations(in).toArray());
    }

    @ParameterizedTest
    @MethodSource("getUniquePermutationsTestCases")
    public void testUniquePermutations(final Pair<Object[], List<Object[]>> input) {
        Object[] in = input.getLeft();
        List<Object[]> out = input.getRight();
        Assertions.assertArrayEquals(out.toArray(), Permutations.uniquePermutations(in).toArray());
    }

    //TODO - Improve testing to make sure arrays are equal even if out of order...
    public static List<Pair<Object[], List<Object[]>>> getTestCases() {
        return List.of(
                Pair.of(new Object[]{1}, List.<Object[]>of(new Object[]{1})),
                Pair.of(new Object[]{1, 2}, List.of(new Object[]{1, 2}, new Object[]{2, 1}))
//                Pair.of(new Object[]{1, 2, 3}, List.of(new Object[]{1, 2, 3}, new Object[]{1, 3, 2},
//                        new Object[]{2, 1, 3}, new Object[]{2, 3, 1}, new Object[]{3, 1, 2}, new Object[]{3, 2, 1}))
        );
    }
    //TODO - Improve testing to make sure arrays are equal even if out of order...
    public static List<Pair<Object[], List<Object[]>>> getUniquePermutationsTestCases() {
        return List.of(
                Pair.of(new Object[]{1, 1}, List.<Object[]>of(new Object[]{1, 1})),
                Pair.of(new Object[]{1, 2, 2}, List.of(new Object[]{1, 2, 2},
                        new Object[]{2, 2, 1}, new Object[]{2, 1, 2}))
        );
    }


}
