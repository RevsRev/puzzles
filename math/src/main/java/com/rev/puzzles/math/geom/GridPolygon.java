package com.rev.puzzles.math.geom;

import java.util.ArrayList;
import java.util.List;

public class GridPolygon {

    final List<GridSide> sides;

    private GridPolygon(List<GridSide> sides) {
        this.sides = sides;
    }

    public static GridPolygon createFromGridSquareCorners(final List<GridPoint> points) {
        if (points == null || points.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (points.size() == 1) {
            return square(points.getFirst());
        }

        return null;
    }

    private static GridPolygon square(GridPoint first) {
        final List<GridSide> sides = new ArrayList<>();

        sides.add(GridSide.create(new GridPoint(first.x(), first.y()), new GridPoint(first.x(), first.y() + 1)));
        sides.add(GridSide.create(new GridPoint(first.x(), first.y() + 1), new GridPoint(first.x() + 1, first.y() + 1)));
        sides.add(GridSide.create(new GridPoint(first.x() + 1, first.y() + 1), new GridPoint(first.x() + 1, first.y())));
        sides.add(GridSide.create(new GridPoint(first.x() + 1, first.y()), new GridPoint(first.x(), first.y())));

        return new GridPolygon(sides);
    }
}
