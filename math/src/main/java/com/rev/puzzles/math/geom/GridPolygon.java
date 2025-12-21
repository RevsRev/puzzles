package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.linalg.matrix.Mat2;

import java.util.*;
import java.util.function.Function;

import static com.rev.puzzles.math.geom.DirectionV2.LEFT;

public class GridPolygon {

    final List<PolygonSide> sides;
    final int windingNumber;

    private List<PolygonSide> leftSortedSides = null;
    private final Map<GridPoint, Boolean> containsCache = new HashMap<>();

    GridPolygon(List<PolygonSide> sides, int windingNumber) {
        this.sides = sides;
        this.windingNumber = windingNumber;
    }

    public PolygonSide getAnExtremalBoundarySide(final DirectionV2 direction) {
        final Comparator<PolygonSide> comparator = PolygonSide.extremalSideComparator(direction);
        return sides.stream().max(comparator).orElseThrow();
    }

    public boolean contains(final GridPoint point) {
        final List<PolygonSide> sortedSides = getLeftSortedSides();

        if (containsCache.containsKey(point)) {
            return containsCache.get(point);
        }

        int timesEntered = 0;
        int timesExited = 0;
        for (final PolygonSide side : sortedSides) {
            if (side.side.contains(point)) {
                containsCache.put(point, true);
                return true;
            }
            if ((side.normal == LEFT || side.normal == LEFT.opposite()) && side.side.minX() <= point.x()) {
                if (side.side.minY() <= point.y() && point.y() <= side.side.maxY()) {
                    timesEntered = side.normal == LEFT ? timesEntered : timesEntered + 1;
                    timesExited = side.normal == LEFT ? timesExited + 1 : timesExited;
                }
            }
        }

        //The equality case is always false: we've either started outside the polygon and gone in / out same number of times,
        //or we started on a horizontal side (which would return true), but this is caught by the early return.
        boolean contains = timesExited > timesEntered;
        containsCache.put(point, contains);
        return contains;
    }

    private List<PolygonSide> getLeftSortedSides() {
        if (leftSortedSides == null) {
            leftSortedSides = sides.stream().sorted(PolygonSide.extremalSideComparator(LEFT).reversed()).toList();
        }
        return leftSortedSides;
    }

    public boolean isInteriorOf(final GridPolygon maybeExterior) {

        for (PolygonSide maybeExteriorSide : maybeExterior.sides) {
            for (PolygonSide maybeInteriorSide : sides) {
                final IntersectionResult intersect = maybeExteriorSide.side.intersect(maybeInteriorSide.side);
                switch (intersect) {
                    case EmptyIntersectionResult emptyIntersectionResult -> {
                        final GridPoint pointToTest = maybeInteriorSide.side.start();
                        if (!maybeExterior.contains(pointToTest)) {
                            return false;
                        }
                    }
                    default -> {}
                }
            }
        }
        return true;
    }

    public long area() {
        double area = 0;
        //(shoelace formula)
        for (PolygonSide side : sides) {
            final GridPoint start = side.side.start();
            final GridPoint end = side.side.end();

            final Mat2 mat = new Mat2(start.x(), end.x(), start.y(), end.y());
            area += mat.det();
        }
        //- sign because all my shapes are oriented clockwise, but shoelace assumes counter clockwise
        return - Math.round(area / 2);
    }

    public static final class PolygonSide {

        final GridSide side;
        final DirectionV2 normal;

        public PolygonSide(GridSide side, DirectionV2 normal) {
            this.side = side;
            this.normal = normal;
        }

        public GridSide side() {
            return side;
        }

        public DirectionV2 normal() {
            return normal;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (PolygonSide) obj;
            return Objects.equals(this.side, that.side) &&
                    Objects.equals(this.normal, that.normal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(side, normal);
        }

        @Override
        public String toString() {
            return "PolygonSide[" +
                    "side=" + side + ", " +
                    "normal=" + normal + ']';
        }

        public static Comparator<PolygonSide> extremalSideComparator(final DirectionV2 direction) {
            final Function<PolygonSide, Integer> normalComparatorFunc = s -> {
                if (s.normal.equals(direction)) {
                    return 3;
                }
                if (s.normal.equals(direction.opposite())) {
                    return 2;
                }
                if (s.normal.equals(direction.next())) {
                    return 1;
                }
                return 0;
            };

            Comparator<PolygonSide> normalComparator = Comparator.comparingInt(normalComparatorFunc::apply);

            final Comparator<PolygonSide> sideExtremityComparator = switch (direction) {
                case UP -> Comparator.comparingLong(ps -> ps.side.maxY());
                case RIGHT -> Comparator.comparingLong(ps -> ps.side.maxX());
                case DOWN -> Comparator.comparingLong(ps -> - ps.side.minY());
                case LEFT -> Comparator.comparingLong(ps -> - ps.side.minX());
            };

            final Comparator<PolygonSide> sideLengthComparator = Comparator.comparingLong(s -> s.side.length());

            return normalComparator.thenComparing(sideExtremityComparator).thenComparing(sideLengthComparator);
        }

    }
}
