package com.rev.aoc.util.math.set;

import com.rev.aoc.util.set.SetUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public final class SetsTest {

    @ParameterizedTest
    @MethodSource("getSizeNTestCases")
    public void testSizeSubsetsOfSizeN(Pair<Pair<Object[], Object[]>, List<Object[]>> input) {
        Object[] in = input.getLeft().getLeft();
        Object[] perm = input.getLeft().getRight();
        List<Object[]> out = input.getRight();
        Assertions.assertArrayEquals(out.toArray(), SetUtils.subsetsOfSizeN(in, perm).toArray());
    }

    @ParameterizedTest
    @MethodSource("getSizeLeqNTestCases")
    public void testSizeSubsetsOfSizeLeqN(Pair<Pair<Integer[], Integer[]>, List<Integer[]>> input) {
        Object[] in = input.getLeft().getLeft();
        Object[] perm = input.getLeft().getRight();
        List<Integer[]> out = input.getRight();
        Assertions.assertArrayEquals(out.toArray(), SetUtils.subsetsOfSizeLeqN(in, perm).toArray());
    }

    public static List<Pair<Pair<Object[], Object[]>, List<Object[]>>> getSizeNTestCases() {
        return List.of(
                Pair.of(Pair.of(new Object[]{1}, new Object[1]), List.<Object[]>of(new Object[]{1})),
                Pair.of(Pair.of(new Object[]{1, 2}, new Object[1]), List.of(new Object[]{1}, new Object[]{2})),
                Pair.of(Pair.of(new Object[]{1, 2, 3, 4}, new Object[2]), List.of(
                        new Object[]{1, 2},
                        new Object[]{1, 3},
                        new Object[]{1, 4},
                        new Object[]{2, 3},
                        new Object[]{2, 4},
                        new Object[]{3, 4}
                )),
                Pair.of(Pair.of(new Object[]{1, 2, 3, 4}, new Object[3]), List.of(
                        new Object[]{1, 2, 3},
                        new Object[]{1, 2, 4},
                        new Object[]{1, 3, 4},
                        new Object[]{2, 3, 4}
                ))
        );
    }

    public static List<Pair<Pair<Integer[], Integer[]>, List<Integer[]>>> getSizeLeqNTestCases() {
        return List.of(
                Pair.of(Pair.of(new Integer[]{1}, new Integer[1]), List.of(new Integer[0], new Integer[]{1})),
                Pair.of(Pair.of(new Integer[]{1, 2}, new Integer[1]), List.of(new Integer[0], new Integer[]{1}, new Integer[]{2})),
                Pair.of(Pair.of(new Integer[]{1, 2, 3, 4}, new Integer[2]), List.of(
                        new Integer[0],
                        new Integer[]{1},
                        new Integer[]{1, 2},
                        new Integer[]{1, 3},
                        new Integer[]{1, 4},
                        new Integer[]{2},
                        new Integer[]{2, 3},
                        new Integer[]{2, 4},
                        new Integer[]{3},
                        new Integer[]{3, 4},
                        new Integer[]{4}
                )),
                Pair.of(Pair.of(new Integer[]{1, 2, 3, 4}, new Integer[3]), List.of(
                        new Integer[0],
                        new Integer[]{1},
                        new Integer[]{1, 2},
                        new Integer[]{1, 2, 3},
                        new Integer[]{1, 2, 4},
                        new Integer[]{1, 3},
                        new Integer[]{1, 3, 4},
                        new Integer[]{1, 4},
                        new Integer[]{2},
                        new Integer[]{2, 3},
                        new Integer[]{2, 3, 4},
                        new Integer[]{2, 4},
                        new Integer[]{3},
                        new Integer[]{3, 4},
                        new Integer[]{4}
                ))
        );
    }

}
