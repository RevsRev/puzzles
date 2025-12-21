package com.rev.puzzles.math.geom;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class GridPolygon {

    final List<PolygonSide> sides;
    final int windingNumber;

    GridPolygon(List<PolygonSide> sides, int windingNumber) {
        this.sides = sides;
        this.windingNumber = windingNumber;
    }

    public PolygonSide getAnExtremalBoundarySide(final DirectionV2 direction) {
        final Comparator<PolygonSide> comparator = PolygonSide.extremalSideComparator(direction);
        return sides.stream().max(comparator).orElseThrow();
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
            final Function<PolygonSide, Integer> normalComparatorFunc = s -> s.normal.equals(direction) ? 1 : 0;

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
