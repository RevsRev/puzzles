package com.rev.puzzles.math.geom.result;

public abstract sealed class IntersectionResult
        permits EmptyIntersectionResult, PointIntersectionResult,
        PointSideIntersectionResult {
}
