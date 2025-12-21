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

        ConstructionIterationPolicy iterationPolicy = ConstructionIterationPolicy.factory(points);
        DirectionV2 normal = LEFT;
        final List<PolygonSide> polygonSides = new ArrayList<>();
        int windingNumber = 0;

        while (polygonSides.isEmpty() || !polygonSides.getFirst().side.start().equals(polygonSides.getLast().side.end())) {

            GridPolygon first = iterationPolicy.current();
            GridPolygon second = iterationPolicy.next();

            final DirectionV2 previousNormal = normal;
            final PolygonSide sideConsidered = first.sides.stream().filter(s -> s.normal.equals(previousNormal)).findFirst().orElseThrow();
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
                            if (polygonSides.isEmpty()) {
                                polygonSides.add(sideConsidered);
                            } else {
                                polygonSides.add(new PolygonSide(GridSide.create(polygonSides.getLast().side().end(), sideConsidered.side().end()), sideConsidered.normal()));
                            }
                            normal = maybeNextSide.normal();
                        } else {
                            final PolygonSide maybeNextSideOpposite = second.sides.stream().filter(s -> s.normal.equals(sideConsidered.normal.next().opposite())).findFirst().orElseThrow();

                            IntersectionResult secondIntersectResult = maybeNextSideOpposite.side.intersect(sideConsidered.side);
                            switch (secondIntersectResult) {
                                case PointIntersectionResult result -> {
                                    if (polygonSides.isEmpty()) {
                                        polygonSides.add(new PolygonSide(GridSide.create(sideConsidered.side().start(), result.intersection()), sideConsidered.normal));
                                    } else {
                                        polygonSides.add(new PolygonSide(GridSide.create(polygonSides.getLast().side().end(), result.intersection()), sideConsidered.normal()));
                                    }
                                    normal = maybeNextSideOpposite.normal();
                                }
                                default -> throw new IllegalStateException();
                            }
                        }

                        iterationPolicy.progress();
                    } else {
                        if (polygonSides.isEmpty()) {
                            polygonSides.add(sideConsidered);
                        } else {
                            polygonSides.add(new PolygonSide(GridSide.create(polygonSides.getLast().side().end(), sideConsidered.side().end()), sideConsidered.normal()));
                        }
                        normal = normal.next();
                    }
                }
                case PointSideIntersectionResult pointSideIntersectionResult -> throw new IllegalStateException();
            }

            if (previousNormal.next() == normal) {
                windingNumber++;
            } else {
                windingNumber--;
            }
        }

        if (windingNumber % 4 != 0) {
            throw new IllegalStateException("Non integer winding number!");
        }

        return new GridPolygon(polygonSides, windingNumber / 4);
    }

    private static int getALeftMostSide(List<GridPolygon> rectangles) {
        PolygonSide candidateLeftSide = rectangles.getFirst().sides.stream().filter(s -> s.normal.equals(LEFT)).findFirst().orElseThrow();
        int index = 0;
        for (int i = 1; i < rectangles.size(); i++) {
            GridPolygon rectangle = rectangles.get(i);
            final PolygonSide side = rectangle.sides.stream().filter(s -> s.normal.equals(LEFT)).findFirst().orElseThrow();

            //we're more left, or we're the same left and equal length (to avoid nasty edge case of starting on one side that is a sub-side of a longer side)
            if (side.side.start().x() < candidateLeftSide.side.start().x()
                    || side.side.start().x() == candidateLeftSide.side.start().x() && side.side.length() > candidateLeftSide.side.length()) {
                candidateLeftSide = side;
                index = i;
            }
        }
        return index;
    }

    private static List<GridPolygon> parsePointsToRectanglesWithClockwiseWindingNumber(List<GridPoint> points) {

        int pointsWindingNumber = 0;
        DirectionV2 pointsDirection = null;
        final List<GridPolygon> rectangles = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            final GridPoint first = points.get(i);
            final GridPoint second = points.get(i + 1);

            final GridSide gridSide = GridSide.create(first, second);
            if (pointsDirection != null) {
                pointsWindingNumber = pointsDirection.next().equals(gridSide.direction()) ? pointsWindingNumber + 1 : pointsWindingNumber - 1;
            }
            pointsDirection = gridSide.direction();

            final GridPolygon rectangle = rectangle(first, second);
            rectangles.add(rectangle);
        }

        //We have an "enclosed" shape, so we want to make sure we traverse it with a positive winding number
        if (points.getFirst().equals(points.getLast())) {
            final GridSide firstSide = GridSide.create(points.get(0), points.get(1));
            pointsWindingNumber = pointsDirection.next().equals(firstSide.direction()) ? pointsWindingNumber + 1 : pointsWindingNumber - 1;

            if (pointsWindingNumber % 4 != 0) {
                throw new IllegalStateException();
            }

            if (pointsWindingNumber < 0) {
                return rectangles.reversed();
            }
        }
        return rectangles;
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

    private interface ConstructionIterationPolicy {
        GridPolygon current();
        GridPolygon next();
        void progress();

        private static ConstructionIterationPolicy factory(final List<GridPoint> points) {
            boolean closedPointsList = points.getFirst().equals(points.getLast());
            final List<GridPolygon> rectangles = parsePointsToRectanglesWithClockwiseWindingNumber(points);
            final int index = getALeftMostSide(rectangles);
            if (closedPointsList) {
                return new ClosedPointsConstructionIterationPolicy(rectangles, index);
            }
            final boolean ascending = index != rectangles.size() - 1;
            return new OpenPointsConstructionIterationPolicy(rectangles, index, ascending);
        }
    }

    private static class ClosedPointsConstructionIterationPolicy implements ConstructionIterationPolicy {

        private List<GridPolygon> rectangles;
        private int index;

        private ClosedPointsConstructionIterationPolicy(List<GridPolygon> rectangles, int index) {
            this.rectangles = rectangles;
            this.index = index;
        }

        @Override
        public GridPolygon current() {
            return rectangles.get(index);
        }

        @Override
        public GridPolygon next() {
            return rectangles.get((index + 1) % rectangles.size());
        }

        @Override
        public void progress() {
            index = (index + 1) % rectangles.size();
        }
    }

    private static class OpenPointsConstructionIterationPolicy implements ConstructionIterationPolicy {

        private List<GridPolygon> rectangles;
        private int index;
        private boolean ascending;

        public OpenPointsConstructionIterationPolicy(final List<GridPolygon> rectangles, final int index, final boolean ascending) {
            this.rectangles = rectangles;
            this.index = index;
            this.ascending = ascending;
        }

        @Override
        public GridPolygon current() {
            return rectangles.get(index);
        }

        @Override
        public GridPolygon next() {
            return rectangles.get(ascending ? index + 1 : index - 1);
        }

        @Override
        public void progress() {
            index = ascending ? index + 1 : index - 1;
            if (ascending && index == rectangles.size() - 1) {
                ascending = false;
            } else if (!ascending && index == 0) {
                ascending = true;
            }
        }
    }
}
