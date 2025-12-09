package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.geom.result.PointSideIntersectionResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PointPolygon {

    final Map<PointSide, DirectionV2> sidesAndNormals;
    private final long windingNumber;

    private PointPolygon(final Map<PointSide, DirectionV2> sidesAndNormals, long windingNumber) {
        this.sidesAndNormals = sidesAndNormals;
        this.windingNumber = windingNumber;
    }

    public Set<PointSide> sides() {
        return new HashSet<>(sidesAndNormals.keySet());
    }

    public static PointPolygon rectangle(final Point corner, final Point oppositeCorner) {
        if (corner.equals(oppositeCorner)) {
            throw new IllegalArgumentException("Can't have the corner and opposite corner the same! %s".formatted(corner));
        }

        final Point first = corner;
        final Point second = new Point(corner.x(), oppositeCorner.y());
        final Point third = oppositeCorner;
        final Point fourth = new Point(oppositeCorner.x(), corner.y());

        //degenerate cases, our "rectangle" is a line
        if (first.equals(second)) {
            return create(
                    List.of(
                            PointSide.of(second, third),
                            PointSide.of(third, second)
                    ),
                    1
            );
        } else if (second.equals(third)) {
            return create(
                    List.of(
                            PointSide.of(first, second),
                            PointSide.of(second, first)
                    ),
                    1
            );
        }

        try {
            return create(List.of(
                    PointSide.of(first, second),
                    PointSide.of(second, third),
                    PointSide.of(third, fourth),
                    PointSide.of(fourth, first)
            ));
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not construct a rectangle with corners %s, %s, %s, %s".formatted(first,
                    second,
                    third,
                    fourth
            ), e);
        }
    }

    public static PointPolygon create(final List<PointSide> sides) {
        return create(sides, 0);
    }

    public static PointPolygon create(final List<PointSide> sides, int ignoreWindingNumberDegeneracy) {
        if (sides.isEmpty()) {
            throw new IllegalArgumentException("Cannot construct a polygon without any sides");
        }

        long windingNumber = 0;
        PointSide previousSide = sides.get(sides.size() - 1);
        for (int i = 0; i < sides.size(); i++) {
            final PointSide side = sides.get(i);
            if (!side.start().equals(previousSide.end())) {
                throw new IllegalArgumentException("The start of one side must be the end of the previous");
            }

            DirectionV2 previousDirection = previousSide.direction();
            DirectionV2 nextDirection = side.direction();

            if (previousDirection.next() == nextDirection) {
                // clockwise
                windingNumber += 1;
            } else if (previousDirection.previous() == nextDirection) {
                // anti clockwise
                windingNumber -= 1;
            } else {
                //filthy hack
                if (ignoreWindingNumberDegeneracy == 0) {
                    throw new IllegalArgumentException("Consectuive sides cannot be (anti)parallel");
                }
            }

            previousSide = side;
        }

        windingNumber = windingNumber / 4;

        if (ignoreWindingNumberDegeneracy != 0) {
            windingNumber = ignoreWindingNumberDegeneracy;
        }

        if (windingNumber == 0) {
            throw new IllegalArgumentException("Winding number was zero. Problem with input");
        }

        if (windingNumber != -1 && windingNumber != 1) {
            throw new IllegalArgumentException("Winding number magnitude was greater than 1! How can we wind two or more times?!");
        }

        //Now that we know the winding number, we can calculate sides and normals
        Map<PointSide, DirectionV2> sidesAndNormals = new HashMap<>();
        for (int i = 0; i < sides.size(); i++) {
            final PointSide side = sides.get(i);
            final DirectionV2 direction = side.direction();
            final DirectionV2 normal = windingNumber == 1 ? direction.previous() : direction.next();
            sidesAndNormals.put(side, normal);
        }

        return new PointPolygon(sidesAndNormals, windingNumber);
    }

    public boolean isInteriorOf(final PointPolygon maybeExterior) {
        boolean atLeastOneNonEmptyIntersectionResult = false;

        for (final Map.Entry<PointSide, DirectionV2> interiorSideAndNormal : sidesAndNormals.entrySet()) {
            final PointSide interiorSide = interiorSideAndNormal.getKey();
            final DirectionV2 interiorNormal = interiorSideAndNormal.getValue();

            for (final Map.Entry<PointSide, DirectionV2> maybeExteriorSideAndNormal :
                    maybeExterior.sidesAndNormals.entrySet()) {
                final PointSide maybeExteriorSide = maybeExteriorSideAndNormal.getKey();
                final DirectionV2 maybeExteriorNormal = maybeExteriorSideAndNormal.getValue();

                final IntersectionResult intersectionResult = maybeExteriorSide.intersect(interiorSide, maybeExteriorNormal);

                switch (intersectionResult) {
                    case EmptyIntersectionResult ignored -> {
                    }
                    case PointIntersectionResult pointIntersectionResult -> {
                        final Point intersection = pointIntersectionResult.intersection;
                        //TODO - This is broken, need to fix and then should be able to solve AOC 2025 9 2
                        if (intersection.equals(interiorSide.start())) {
//                            if (interiorSide.direction() == interiorNormal) {
//                                return false;
//                            }
                            atLeastOneNonEmptyIntersectionResult = true;
                            return true;
                        }
                        else if (intersection.equals(interiorSide.end())) {
//                            if (interiorSide.direction().opposite() == interiorNormal) {
//                                return false;
//                            }
                            atLeastOneNonEmptyIntersectionResult = true;
                            return true;
                        } else if (intersection.equals(maybeExteriorSide.start())) {
                            atLeastOneNonEmptyIntersectionResult = true;
                            return true;
                        } else if (intersection.equals(maybeExteriorSide.end())) {
                            atLeastOneNonEmptyIntersectionResult = true;
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case PointSideIntersectionResult pointSideIntersectionResult -> {
                        if (!pointSideIntersectionResult.normal.equals(interiorNormal)) {
                            return false;
                        }
                        atLeastOneNonEmptyIntersectionResult = true;
                    }
                }
            }
        }

        if (atLeastOneNonEmptyIntersectionResult) {
            return true;
        }


        //No intersections - are we completely interior or exterior?
        //TODO - Implement this properly?!
        return true;
    }
}
