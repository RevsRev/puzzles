package com.rev.puzzles.math.geom.result;

import com.rev.puzzles.math.geom.Point;

public final class PointIntersectionResult extends IntersectionResult {
    public final Point intersection;

    public PointIntersectionResult(final Point intersection) {
        this.intersection = intersection;
    }
}
