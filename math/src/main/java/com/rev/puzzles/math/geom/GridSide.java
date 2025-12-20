package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointSideIntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.linalg.matrix.Mat2;
import com.rev.puzzles.math.linalg.vec.Vec2;

import java.util.Objects;

import static com.rev.puzzles.math.geom.DirectionV2.*;

public final class GridSide {
    private static final double EPSILON = 0.0001;

    private final Point start;
    private final Point end;
    private final DirectionV2 direction;

    private GridSide(final Point start, final Point end, DirectionV2 direction) {
        this.start = start;
        this.end = end;
        this.direction = direction;
    }

    public static GridSide create(final Point start, final Point end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException("Start and end must be different!");
        }

        final DirectionV2 direction;
        if (start.x() == end.x()) {
            direction = start.y() < end.y() ? UP : DOWN;
        } else if (start.y() == end.y()) {
            direction = start.x() < end.x() ? RIGHT : LEFT;
        } else {
            throw new IllegalArgumentException("GridSide is only designed for horizontal/vertical sides");
        }

        return new GridSide(start, end, direction);
    }

    public Point start() {
        return start;
    }

    public Point end() {
        return end;
    }

    public boolean contains(final Point p) {
        return switch (direction) {
            case UP -> p.x() == start.x() && start.y() <= p.y() && p.y() <= end.y();
            case RIGHT -> p.y() == start.y() && start.x() <= p.x() && p.x() <= end.x();
            case DOWN -> p.x() == start.x() && start.y() >= p.y() && p.y() >= end.y();
            case LEFT -> p.y() == start.y() && start.x() >= p.x() && p.x() >= end.x();
        };
    }


    public IntersectionResult intersect(final GridSide other) {
        if (direction.equals(other.direction) || direction.equals(other.direction.opposite())) {
            return parallelIntersect(this, other);
        }
        return pointIntersect(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GridSide gridSide = (GridSide) o;
        return Objects.equals(start, gridSide.start) && Objects.equals(end, gridSide.end) && direction == gridSide.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, direction);
    }

    private static IntersectionResult pointIntersect(final GridSide first, final GridSide second) {
        final Mat2 mat = new Mat2(
                (first.start.x() - first.end.x()), - (second.start.x() - second.end.x()),
                (first.start.y() - first.end.y()), - (second.start.y() - second.end.y()));

        double det = mat.det();
        if (Math.abs(det) < EPSILON) {
            return new EmptyIntersectionResult();
        }

        final Vec2 differences = new Vec2(second.start.x() - first.start.x(), second.start.y() - first.start.y());
        final Vec2 intersectionParams = mat.inverse().mult(differences);

        final Vec2 thisGradient = new Vec2(
                first.start.x() - first.end.x(),
                first.start.y() - first.end.y()
        );
        final Vec2 intersection = new Vec2(first.start.x(), first.start.y()).plus(thisGradient.mult(intersectionParams.getX()));

        if (withinEpsilonOfInteger(intersection.getX()) && withinEpsilonOfInteger(intersection.getY())) {
            final Point maybeIntersection = new Point(
                    Math.round(intersection.getX()), Math.round(intersection.getY())
            );
            if (first.contains(maybeIntersection) && second.contains(maybeIntersection)) {
                return new PointIntersectionResult(
                        maybeIntersection
                );
            }
        }
        return new EmptyIntersectionResult();
    }

    private static IntersectionResult parallelIntersect(final GridSide first, final GridSide second) {
        if ((first.direction.equals(LEFT) || first.direction.equals(RIGHT)) && first.start.y() == second.start.y()) {
            final GridSide thisOriented = withOrientation(first, RIGHT);
            final GridSide otherOriented = withOrientation(second, RIGHT);

            final long startX = Math.max(thisOriented.start.x(), otherOriented.start.x());
            final long endX = Math.min(thisOriented.end.x(), otherOriented.end.x());
            final Point start = new Point(startX, thisOriented.start.y());
            final Point end = new Point(endX, thisOriented.start.y());
            if (start.equals(end)) {
                return new PointIntersectionResult(start);
            } else if (startX < endX) {
                return new PointSideIntersectionResult(
                        withOrientation(GridSide.create(
                                start,
                                end
                        ), first.direction)
                );
            }
        } else if (first.start.x() == second.start.x()){
            final GridSide thisOriented = withOrientation(first, UP);
            final GridSide otherOriented = withOrientation(second, UP);

            final long startY = Math.max(thisOriented.start.y(), otherOriented.start.y());
            final long endY = Math.min(thisOriented.end.y(), otherOriented.end.y());
            final Point start = new Point(thisOriented.start.x(), startY);
            final Point end = new Point(thisOriented.start.x(), endY);
            if (start.equals(end)) {
                return new PointIntersectionResult(start);
            } else if (startY < endY) {
                return new PointSideIntersectionResult(
                        withOrientation(GridSide.create(
                                start,
                                end
                        ), first.direction)
                );
            }
        }
        return new EmptyIntersectionResult();
    }

    private static GridSide withOrientation(final GridSide gs, final DirectionV2 direction) {
        if (!direction.equals(gs.direction) && !direction.equals(gs.direction.opposite())) {
            throw new IllegalArgumentException();
        }

        if (gs.direction.equals(direction)) {
            return new GridSide(gs.start, gs.end, direction);
        }

        return new GridSide(gs.end, gs.start, direction);
    }

    private static boolean withinEpsilonOfInteger(double x) {
        return Math.abs(x - Math.round(x)) < EPSILON;
    }
}
