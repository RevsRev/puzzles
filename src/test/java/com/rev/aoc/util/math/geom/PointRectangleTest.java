package com.rev.aoc.util.math.geom;

import com.rev.aoc.util.geom.PointRectangle;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.Set;

public class PointRectangleTest {

    @ParameterizedTest
    @MethodSource("getOverlappingRectangles")
    public void testOverlappingRectangles(final Pair<PointRectangle, PointRectangle> overlapping) {
        Assertions.assertTrue(PointRectangle.overlapping(overlapping.getLeft(), overlapping.getRight()));
    }

    @ParameterizedTest
    @MethodSource("getNonOverlappingRectangles")
    public void testNonOverlappingRectangles(final Pair<PointRectangle, PointRectangle> overlapping) {
        Assertions.assertFalse(PointRectangle.overlapping(overlapping.getLeft(), overlapping.getRight()));
    }

    @ParameterizedTest
    @MethodSource("getOverlappingWithRegions")
    public void testOverlappingRegions(final Pair<Pair<PointRectangle, PointRectangle>, Optional<PointRectangle>> rectanglesAndOverlap) {
        final Pair<PointRectangle, PointRectangle> rectangles = rectanglesAndOverlap.getLeft();
        final Optional<PointRectangle> overlap = PointRectangle.getOverlappingRegion(rectangles.getLeft(), rectangles.getRight());
        Assertions.assertEquals(rectanglesAndOverlap.getRight(), overlap);
    }

    @ParameterizedTest
    @MethodSource("getOverlappingWithCompliment")
    public void testCompliment(final Pair<Pair<PointRectangle, PointRectangle>, Set<PointRectangle>> rectanglesAndCompliment) {
        final Pair<PointRectangle, PointRectangle> rectangles = rectanglesAndCompliment.getLeft();
        System.out.println(rectangles);
        final Set<PointRectangle> overlap = PointRectangle.getCompliment(rectangles.getLeft(), rectangles.getRight());
        Assertions.assertEquals(rectanglesAndCompliment.getRight(), overlap);
    }


    public static Set<Pair<Pair<PointRectangle, PointRectangle>, Optional<PointRectangle>>> getOverlappingWithRegions() {
        return Set.of(
                Pair.of(Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(1, 1, 2, 2)), Optional.of(new PointRectangle(1, 1, 1, 1))),
                Pair.of(Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(2, 2, 2, 2)), Optional.of(new PointRectangle(2, 2, 0, 0))),
                Pair.of(Pair.of(new PointRectangle(0, 0, 3, 1), new PointRectangle(1, -1, 1, 3)), Optional.of(new PointRectangle(1, 0, 1, 1)))
        );
    }

    public static Set<Pair<Pair<PointRectangle, PointRectangle>, Set<PointRectangle>>> getOverlappingWithCompliment() {
        return Set.of(
                Pair.of(Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(1, 1, 2, 2)),
                        Set.of(
                                new PointRectangle(0, 0, 0, 0),
                                new PointRectangle(0, 1, 0, 1),
                                new PointRectangle(1, 0, 1, 0)
                        )),
                Pair.of(Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(2, 2, 2, 2)),
                        Set.of(
                                new PointRectangle(0, 0, 1, 1),
                                new PointRectangle(2, 0, 0, 1),
                                new PointRectangle(0, 2, 1, 0)
                        )),
                Pair.of(Pair.of(new PointRectangle(0, 0, 3, 1), new PointRectangle(1, -1, 1, 3)),
                        Set.of(
                                new PointRectangle(0, 0, 0, 1),
                                new PointRectangle(3, 0, 0, 1)
                        )),
                Pair.of(Pair.of(new PointRectangle(0, 0, 5, 5), new PointRectangle(2, 2, 1, 1)),
                        Set.of(
                                new PointRectangle(0, 0, 1, 1),
                                new PointRectangle(2, 0, 1, 1),
                                new PointRectangle(4, 0, 1, 1),
                                new PointRectangle(0, 2, 1, 1),
                                new PointRectangle(0, 4, 1, 1),
                                new PointRectangle(2, 4, 1, 1),
                                new PointRectangle(4, 2, 1, 1),
                                new PointRectangle(4, 4, 1, 1)
                        ))
        );
    }

    public static Set<Pair<PointRectangle, PointRectangle>> getOverlappingRectangles() {
        return Set.of(
                Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(1, 1, 2, 2)),
                Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(2, 2, 2, 2)), //overlapping at a point
                Pair.of(new PointRectangle(0, 0, 3, 1), new PointRectangle(1, -1, 1, 3))
        );
    }

    public static Set<Pair<PointRectangle, PointRectangle>> getNonOverlappingRectangles() {
        return Set.of(
                Pair.of(new PointRectangle(0, 0, 2, 2), new PointRectangle(2, 3, 2, 2))
        );
    }

}
