package com.rev.puzzles.math.geom;

import java.util.List;
import java.util.Objects;

public class GridPolygon {

    final List<PolygonSide> sides;
    final int windingNumber;

    GridPolygon(List<PolygonSide> sides, int windingNumber) {
        this.sides = sides;
        this.windingNumber = windingNumber;
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

        }
}
