package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.geom.result.PointSideIntersectionResult;

import java.util.ArrayList;
import java.util.List;

import static com.rev.puzzles.math.geom.DirectionV2.*;

public class GridPolygon {

    final List<PolygonSide> sides;
    final int windingNumber;

    private GridPolygon(List<PolygonSide> sides, int windingNumber) {
        this.sides = sides;
        this.windingNumber = windingNumber;
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

        final boolean enclosed = points.getFirst().equals(points.getLast());

        final List<GridPolygon> rectangles = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            final GridPoint first = points.get(i);
            final GridPoint second = points.get(i + 1);

            final GridPolygon rectangle = rectangle(first, second);
            rectangles.add(rectangle);
        }

        //we've got all our constituent rectangles in order, now we just need to fix up the sides
        DirectionV2 normal = LEFT;
        for (int i = 0; i < rectangles.getFirst().sides.size(); i++) {
            if (rectangles.getFirst().sides.get(i).side.length() > 1) {
                normal = rectangles.getFirst().sides.get(i).normal;
                break;
            }
        }
        final List<PolygonSide> polygonSides = new ArrayList<>();

        boolean ascending = true;
        int index = 0;
        while (polygonSides.isEmpty() || !polygonSides.getFirst().side.start().equals(polygonSides.getLast().side.end())) {

            GridPolygon first = rectangles.get(index);
            GridPolygon second = rectangles.get(ascending ? index + 1 : index - 1);

            final DirectionV2 finalNormal = normal;
            final PolygonSide sideConsidered = first.sides.stream().filter(s -> s.normal.equals(finalNormal)).findFirst().orElseThrow();
            final PolygonSide maybeNextSide = second.sides.stream().filter(s -> s.normal.equals(sideConsidered.normal.next())).findFirst().orElseThrow();

            final IntersectionResult intersectionResult = maybeNextSide.side.intersect(sideConsidered.side);

            switch (intersectionResult) {
                case EmptyIntersectionResult emptyIntersectionResult -> {
                    //No intersections with next rectangle, so we need to go around this one.
                    polygonSides.add(sideConsidered);
                    normal = normal.next();
                }
                case PointIntersectionResult pointIntersectionResult -> {
                    //Rectangles have +ve winding number, i.e. we're going clockwise.
                    GridPoint intersection = pointIntersectionResult.intersection();

                    if (intersection.equals(sideConsidered.side.end())) {
                        if (intersection.equals(maybeNextSide.side.start())) {
                            polygonSides.add(sideConsidered);
                            normal = maybeNextSide.normal();
                        } else {
                            final PolygonSide maybeNextSideOpposite = second.sides.stream().filter(s -> s.normal.equals(sideConsidered.normal.next().opposite())).findFirst().orElseThrow();

                            IntersectionResult secondIntersectResult = maybeNextSideOpposite.side.intersect(sideConsidered.side);
                            switch (secondIntersectResult) {
                                case PointIntersectionResult result -> {
                                    polygonSides.add(new PolygonSide(GridSide.create(sideConsidered.side().start(), result.intersection()), sideConsidered.normal));
                                    normal = maybeNextSideOpposite.normal();
                                }
                                default -> throw new IllegalStateException();
                            }
                        }
                    } else {
                        if (polygonSides.isEmpty()) {
                            PolygonSide sideToAdd = sideConsidered;
                            for (GridPolygon rectangle : rectangles) {
                                if (rectangle == first) {
                                    continue;
                                }

                                for (PolygonSide side : rectangle.sides) {
                                    if (side.side.intersect(sideConsidered.side) instanceof PointIntersectionResult firstIntersect && firstIntersect.intersection().equals(sideConsidered.side.start())) {
                                        if (firstIntersect.intersection().equals(side.side.end())) {
                                            sideToAdd = sideConsidered;
                                        } else {
                                            PolygonSide opposite = rectangle.sides.stream().filter(s -> s.normal == side.normal.opposite()).findFirst().orElseThrow();
                                            switch (opposite.side.intersect(sideConsidered.side)) {
                                                case PointIntersectionResult secondIntersect -> sideToAdd = new PolygonSide(GridSide.create(secondIntersect.intersection(), sideConsidered.side().end()), sideConsidered.normal);
                                                default -> throw new IllegalStateException();
                                            }
                                        }
                                    }
                                }
                            }
                            polygonSides.add(sideToAdd);
                        } else {
                            polygonSides.add(new PolygonSide(GridSide.create(polygonSides.getLast().side().end(), sideConsidered.side().end()), sideConsidered.normal()));
                        }
                        normal = normal.next();
                        continue;
                    }

                    index = ascending ? index + 1 : index - 1;

                    if (ascending && index == rectangles.size() - 1) {
                        ascending = false;
                    } else if (!ascending && index == 0) {
                        ascending = true;
                    }
                }
                case PointSideIntersectionResult pointSideIntersectionResult -> throw new IllegalStateException();
            }
        }

        return new GridPolygon(polygonSides, 1);
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

        return new GridPolygon(sides, 1);
    }

    public record PolygonSide(GridSide side, DirectionV2 normal) {
    }
}
