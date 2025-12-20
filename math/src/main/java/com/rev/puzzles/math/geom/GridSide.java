package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.linalg.matrix.Mat2;
import com.rev.puzzles.math.linalg.vec.Vec2;

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
            //TODO - parallel case
            return null;
        }

        final Mat2 mat = new Mat2(
                (start.x() - end.x()), - (other.start.x() - other.end.x()),
                (start.y() - end.y()), - (other.start.y() - other.end.y()));

        double det = mat.det();
        if (Math.abs(det) < EPSILON) {
            return new EmptyIntersectionResult();
        }

        final Vec2 differences = new Vec2(other.start.x() - start.x(), other.start.y() - start.y());
        final Vec2 intersectionParams = mat.inverse().mult(differences);

        final Vec2 thisGradient = new Vec2(
                start.x() - end.x(),
                start.y() - end.y()
        );
        final Vec2 intersection = new Vec2(start.x(), start.y()).plus(thisGradient.mult(intersectionParams.getX()));

        if (withinEpsilonOfInteger(intersection.getX()) && withinEpsilonOfInteger(intersection.getY())) {
            final Point maybeIntersection = new Point(
                    Math.round(intersection.getX()), Math.round(intersection.getY())
            );
            if (this.contains(maybeIntersection) && other.contains(maybeIntersection)) {
                return new PointIntersectionResult(
                        maybeIntersection
                );
            }
        }
        return new EmptyIntersectionResult();
    }

    private static boolean withinEpsilonOfInteger(double x) {
        return Math.abs(x - Math.round(x)) < EPSILON;
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
}
