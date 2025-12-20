package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GradientTest {

    @ParameterizedTest
    @MethodSource("testParams")
    void testGradients(final GradientTestParams testParams) {
        final Gradient first = Gradient.create(testParams.firstRise, testParams.firstRun);
        final Gradient second = Gradient.create(testParams.secondRise, testParams.secondRun);
        Assertions.assertAll(
                () -> assertEquals(testParams.parallel, first.parallel(second)),
                () -> assertEquals(testParams.perpendicular, first.perpendicular(second))
        );
    }

    @MethodSource("gradientTestParams")
    public static Collection<GradientTestParams> testParams() {
        return List.of(
                new GradientTestParams(1, 2, 3, 6, true, false),
                new GradientTestParams(-1, -2, -3, -6, true, false),
                new GradientTestParams(1, 2, 3, 7, false, false),
                new GradientTestParams(-1, -2, -3, -7, false, false),
                new GradientTestParams(1, 2, -2, 1, false, true),
                new GradientTestParams(1, 2, -2, 4, false, false)
        );
    }

    public record GradientTestParams(long firstRise, long firstRun, long secondRise, long secondRun, boolean parallel, boolean perpendicular) {

    }
}