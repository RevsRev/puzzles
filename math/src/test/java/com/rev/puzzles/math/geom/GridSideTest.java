package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

class GridSideTest {

    @ParameterizedTest
    @MethodSource("containsTestParams")
    void testContains(final ContainsTestParams testParams) {
        final GridSide s = testParams.s();
        final Point p = testParams.p();

        Assertions.assertEquals(testParams.expected, s.contains(p));
    }

    public static Collection<ContainsTestParams> containsTestParams() {
        return List.of(
                new ContainsTestParams(GridSide.create(new Point(0, 4), new Point(4, 4)), new Point(2, 4), true),
                new ContainsTestParams(GridSide.create(new Point(4, 4), new Point(0, 4)), new Point(2, 4), true),
                new ContainsTestParams(GridSide.create(new Point(4, 0), new Point(0, 0)), new Point(2, 0), true),
                new ContainsTestParams(GridSide.create(new Point(0, 0), new Point(4, 0)), new Point(2, 0), true),

                new ContainsTestParams(GridSide.create(new Point(0, 4), new Point(4, 4)), new Point(6, 4), false),
                new ContainsTestParams(GridSide.create(new Point(4, 4), new Point(0, 4)), new Point(6, 4), false),
                new ContainsTestParams(GridSide.create(new Point(4, 0), new Point(0, 0)), new Point(2, 6), false),
                new ContainsTestParams(GridSide.create(new Point(0, 0), new Point(4, 0)), new Point(2, 6), false)
        );
    }

    public record ContainsTestParams(GridSide s, Point p, boolean expected) {
    }

}