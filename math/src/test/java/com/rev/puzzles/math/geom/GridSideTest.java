package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointSideIntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
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
        final GridPoint p = testParams.p();

        Assertions.assertEquals(testParams.expected, s.contains(p));
    }

    @ParameterizedTest
    @MethodSource("intersectionTestParams")
    void testContains(final IntersectionTestParams testParams) {
        IntersectionResult actual = testParams.first.intersect(testParams.second);
        Assertions.assertEquals(testParams.expected, actual);
    }

    public static Collection<IntersectionTestParams> intersectionTestParams() {
        return List.of(

                //Parallel or empty intersection results
                new IntersectionTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(2, 4)), GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(0, 4), new GridPoint(2, 4)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(2, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(2, 4), new GridPoint(4, 4)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(2, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(0, 4), new GridPoint(2, 4)), new PointIntersectionResult(new GridPoint(2, 4))),
                new IntersectionTestParams(GridSide.create(new GridPoint(2, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(0, 3), new GridPoint(2, 3)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(2, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(0, 4), new GridPoint(1, 4)), new EmptyIntersectionResult()),

                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 0)), GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 0)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 2), new GridPoint(4, 0)), GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(4, 2), new GridPoint(4, 0)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 2)), GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), new PointSideIntersectionResult(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 2)))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 2)), GridSide.create(new GridPoint(4, 0), new GridPoint(4, 2)), new PointIntersectionResult(new GridPoint(4, 2))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 2)), GridSide.create(new GridPoint(3, 0), new GridPoint(3, 2)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 2)), GridSide.create(new GridPoint(4, 0), new GridPoint(4, 1)), new EmptyIntersectionResult()),

                //Point or empty intersection results
                new IntersectionTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 3)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 4)), new PointIntersectionResult(new GridPoint(2, 4))),
                new IntersectionTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 5)), new PointIntersectionResult(new GridPoint(2, 4))),

                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(0, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 3)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(0, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 4)), new PointIntersectionResult(new GridPoint(2, 4))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(0, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(2, 5)), new PointIntersectionResult(new GridPoint(2, 4))),

                new IntersectionTestParams(GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(3, 2)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(4, 2)), new PointIntersectionResult(new GridPoint(4, 2))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 0), new GridPoint(4, 4)), GridSide.create(new GridPoint(2, 2), new GridPoint(5, 2)), new PointIntersectionResult(new GridPoint(4, 2))),

                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 0)), GridSide.create(new GridPoint(2, 2), new GridPoint(3, 2)), new EmptyIntersectionResult()),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 0)), GridSide.create(new GridPoint(2, 2), new GridPoint(4, 2)), new PointIntersectionResult(new GridPoint(4, 2))),
                new IntersectionTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(4, 0)), GridSide.create(new GridPoint(2, 2), new GridPoint(5, 2)), new PointIntersectionResult(new GridPoint(4, 2)))
        );
    }

    public static Collection<ContainsTestParams> containsTestParams() {
        return List.of(
                new ContainsTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), new GridPoint(2, 4), true),
                new ContainsTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(0, 4)), new GridPoint(2, 4), true),
                new ContainsTestParams(GridSide.create(new GridPoint(4, 0), new GridPoint(0, 0)), new GridPoint(2, 0), true),
                new ContainsTestParams(GridSide.create(new GridPoint(0, 0), new GridPoint(4, 0)), new GridPoint(2, 0), true),

                new ContainsTestParams(GridSide.create(new GridPoint(0, 4), new GridPoint(4, 4)), new GridPoint(6, 4), false),
                new ContainsTestParams(GridSide.create(new GridPoint(4, 4), new GridPoint(0, 4)), new GridPoint(6, 4), false),
                new ContainsTestParams(GridSide.create(new GridPoint(4, 0), new GridPoint(0, 0)), new GridPoint(2, 6), false),
                new ContainsTestParams(GridSide.create(new GridPoint(0, 0), new GridPoint(4, 0)), new GridPoint(2, 6), false)
        );
    }

    public record ContainsTestParams(GridSide s, GridPoint p, boolean expected) {
    }

    public record IntersectionTestParams(GridSide first, GridSide second, IntersectionResult expected) {
    }

}