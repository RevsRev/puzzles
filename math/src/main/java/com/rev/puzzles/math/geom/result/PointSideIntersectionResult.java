package com.rev.puzzles.math.geom.result;

import com.rev.puzzles.math.geom.DirectionV2;
import com.rev.puzzles.math.geom.PointSide;

public final class PointSideIntersectionResult extends IntersectionResult {
    public final PointSide intersection;
    public final DirectionV2 normal;

    public PointSideIntersectionResult(final PointSide intersection, final DirectionV2 normal) {
        this.intersection = intersection;
        this.normal = normal;
    }
}
