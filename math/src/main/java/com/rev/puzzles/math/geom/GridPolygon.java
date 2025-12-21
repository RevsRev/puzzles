package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.linalg.matrix.Mat2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static com.rev.puzzles.math.geom.Direction.LEFT;

@SuppressWarnings("checkstyle:VisibilityModifier")
public final class GridPolygon {

    final List<PolygonSide> sides;

    final int windingNumber;
    private List<PolygonSide> leftSortedSides = null;

    private final Map<GridPoint, Boolean> containsCache = new HashMap<>();

    GridPolygon(final List<PolygonSide> sides, final int windingNumber) {
        this.sides = sides;
        this.windingNumber = windingNumber;
    }

    public List<PolygonSide> getSides() {
        return new ArrayList<>(sides);
    }

    public PolygonSide getAnExtremalBoundarySide(final Direction direction) {
        final Comparator<PolygonSide> comparator = PolygonSide.extremalSideComparator(direction);
        return sides.stream().max(comparator).orElseThrow();
    }

    public boolean contains(final GridPoint point) {
        if (containsCache.containsKey(point)) {
            return containsCache.get(point);
        }

        int boundaryCrossings = 0;
        Direction lastCrossing = null;
        for (int i = 0; i < sides.size(); i++) {
            final PolygonSide side = sides.get(i);
            if (side.side.contains(point)) {
                containsCache.put(point, true);
                return true;
            }
            if ((side.normal == LEFT || side.normal == LEFT.opposite()) && side.side.minX() <= point.x()) {
                if (side.side.minY() <= point.y() && point.y() <= side.side.maxY()) {
                    if (lastCrossing == null) {
                        boundaryCrossings++;
                    } else {
                        //if side.normal == lastCrossing, we've hit a degenerate edge case so we skip the count
                        if (side.normal != lastCrossing) {
                            boundaryCrossings++;
                        }
                    }
                    lastCrossing = side.normal;
                }
            }
        }

        final boolean contains = boundaryCrossings % 2 == 1;
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
                if (!maybeExterior.contains(maybeInteriorSide.side.start()) || !maybeExterior.contains(
                        maybeInteriorSide.side.end())) {
                    return false;
                }

                final IntersectionResult intersect = maybeExteriorSide.side.intersect(maybeInteriorSide.side);
                switch (intersect) {
                    case EmptyIntersectionResult emptyIntersectionResult -> {
                        final GridPoint pointToTest = maybeInteriorSide.side.start();
                        if (!maybeExterior.contains(pointToTest)) {
                            return false;
                        }
                    }
                    case PointIntersectionResult pointIntersectionResult -> {
                        if (maybeInteriorSide.normal.perpendicularTo(maybeExteriorSide.normal)) {
                            final GridPoint intersection = pointIntersectionResult.intersection();
                            if (!intersection.equals(maybeExteriorSide.side.start()) && !intersection.equals(
                                    maybeExteriorSide.side.end()) && !intersection.equals(
                                    maybeInteriorSide.side.start()) && !intersection.equals(
                                    maybeInteriorSide.side.end())) {
                                return false;
                            }
                        }
                    }
                    default -> {
                    }
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
        return -Math.round(area / 2);
    }

    public static final class PolygonSide {

        final GridSide side;
        final Direction normal;

        public PolygonSide(final GridSide side, final Direction normal) {
            this.side = side;
            this.normal = normal;
        }

        public GridSide side() {
            return side;
        }

        public Direction normal() {
            return normal;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            final PolygonSide that = (PolygonSide) obj;
            return Objects.equals(this.side, that.side) && Objects.equals(this.normal, that.normal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(side, normal);
        }

        @Override
        public String toString() {
            return "PolygonSide[" + "side=" + side + ", " + "normal=" + normal + ']';
        }

        public static Comparator<PolygonSide> extremalSideComparator(final Direction direction) {
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

            final Comparator<PolygonSide> normalComparator = Comparator.comparingInt(normalComparatorFunc::apply);

            final Comparator<PolygonSide> sideExtremityComparator = switch (direction) {
                case UP -> Comparator.comparingLong(ps -> ps.side.maxY());
                case RIGHT -> Comparator.comparingLong(ps -> ps.side.maxX());
                case DOWN -> Comparator.comparingLong(ps -> -ps.side.minY());
                case LEFT -> Comparator.comparingLong(ps -> -ps.side.minX());
            };

            final Comparator<PolygonSide> sideLengthComparator = Comparator.comparingLong(s -> s.side.length());

            return normalComparator.thenComparing(sideExtremityComparator).thenComparing(sideLengthComparator);
        }

    }
}
