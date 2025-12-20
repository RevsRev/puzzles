package com.rev.puzzles.math.geom;

import java.util.ArrayList;
import java.util.List;

import static com.rev.puzzles.math.geom.DirectionV2.*;
import static com.rev.puzzles.math.geom.DirectionV2.DOWN;

public class GridPolygon {

    final List<PolygonSide> sides;

    private GridPolygon(List<PolygonSide> sides) {
        this.sides = sides;
    }

    public static GridPolygon createFromGridSquareCorners(final List<GridPoint> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (points.size() == 1) {
            return square(points.getFirst());
        }

        if (points.size() == 2) {
            return rectangle(points.get(0), points.get(1));
        }

        return null;
    }

    private static GridPolygon rectangle(final GridPoint first, final GridPoint second) {
        if (first.x() != second.x() && first.y() != second.y()) {
            throw new IllegalArgumentException("Cannot construct rectangle from points not on a horizontal/vertical line");
        }

        final GridSide s = GridSide.create(first, second);
        final long minX = Math.min(s.start().x(), s.end().x());
        final long maxX = Math.max(s.start().x(), s.end().x()) + 1;

        final long minY = Math.min(s.start().y(), s.end().y());
        final long maxY = Math.max(s.start().y(), s.end().y()) + 1;

        return rectangle(minX, minY, maxY, maxX);
    }

    private static GridPolygon square(GridPoint first) {
        return rectangle(first.x(), first.y(), first.y() + 1, first.x() + 1);
    }

    private static GridPolygon rectangle(long minX, long minY, long maxY, long maxX) {
        final List<PolygonSide> sides = new ArrayList<>();

        sides.add(new PolygonSide(GridSide.create(new GridPoint(minX, minY), new GridPoint(minX, maxY)), LEFT));
        sides.add(new PolygonSide(GridSide.create(new GridPoint(minX, maxY), new GridPoint(maxX, maxY)), UP));
        sides.add(new PolygonSide(GridSide.create(new GridPoint(maxX, maxY), new GridPoint(maxX, minY)), RIGHT));
        sides.add(new PolygonSide(GridSide.create(new GridPoint(maxX, minY), new GridPoint(minX, minY)), DOWN));

        return new GridPolygon(sides);
    }

    public record PolygonSide(GridSide side, DirectionV2 normal) {
    }
}
