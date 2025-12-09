package com.rev.puzzles.math.geom;

import com.rev.puzzles.math.geom.result.EmptyIntersectionResult;
import com.rev.puzzles.math.geom.result.IntersectionResult;
import com.rev.puzzles.math.geom.result.PointIntersectionResult;
import com.rev.puzzles.math.geom.result.PointSideIntersectionResult;
import com.rev.puzzles.math.linalg.matrix.Mat2;
import com.rev.puzzles.math.linalg.vec.Vec2;

import java.util.Objects;

import static com.rev.puzzles.math.geom.DirectionV2.DOWN;
import static com.rev.puzzles.math.geom.DirectionV2.LEFT;
import static com.rev.puzzles.math.geom.DirectionV2.RIGHT;
import static com.rev.puzzles.math.geom.DirectionV2.UP;

public final class PointSide {
    private final Point start;
    private final Point end;
    private final DirectionV2 direction;

    private PointSide(Point start, Point end, final DirectionV2 direction) {
        this.start = start;
        this.end = end;
        this.direction = direction;
    }

    public static PointSide of(final Point start, final Point end) {
        if (start.equals(end)) {
            throw new IllegalArgumentException("PointSides must contain a distinct start and end: %s".formatted(start));
        }

        if (start.x() == end.x()) {
            DirectionV2 direction = start.y() < end.y() ? UP : DOWN;
            return new PointSide(start, end, direction);
        } else if (start.y() == end.y()) {
            DirectionV2 direction = start.x() < end.x() ? RIGHT : LEFT;
            return new PointSide(start, end, direction);
        }
        throw new IllegalArgumentException("PointSide is designed for horizontal and vertical sides only");
    }

    public IntersectionResult intersect(final PointSide other, final DirectionV2 pointSideNormal) {

        //fill me in
        if (direction.equals(other.direction) || direction.opposite().equals(other.direction)) {
            return intersectParallel(other, pointSideNormal);
        }

        final Vec2 startDifferences = new Vec2(
                other.start.x() - start.x(),
                other.start.y() - start.y()
        );

        final Mat2 mat = new Mat2(
                end.x() - start.x(), -(other.end.x() - other.start.x()),
                end.y() - start.y(), -(other.end.y() - other.start.y())

        );

        //TODO - extract epsilon?!
        final double epsilon = 0.000001;
        if (Math.abs(mat.det()) < epsilon) {
            return new EmptyIntersectionResult();
        }

        final Vec2 intersectionParams = mat.inverse().mult(startDifferences);
        final Vec2 intersection = new Vec2(
                start.x() + intersectionParams.getX() * (end.x() - start.x()),
                start.y() + intersectionParams.getX() * (end.y() - start.y())

        );

        if (withinEpsilonOfInteger(intersection.getX(), epsilon)
                && withinEpsilonOfInteger(intersection.getY(), epsilon)) {
            final Point point = new Point(Math.round(intersection.getX()), Math.round(intersection.getY()));
            if (contains(point) && other.contains(point)) {
                return new PointIntersectionResult(point);
            }
        }
        return new EmptyIntersectionResult();
    }

    public boolean contains(final Point p) {
        return start.x() <= p.x() && p.x() <= end.x() && start.y() <= p.y() && p.y() <= end.x();
    }

    private IntersectionResult intersectParallel(final PointSide other, final DirectionV2 pointSideNormal) {
        if (direction.equals(RIGHT) && start.y() == other.start.y()) {
            if (other.direction.equals(RIGHT)) {
                //parallel
                final long newStart = Math.max(start.x(), other.start.x());
                final long newEnd = Math.min(end.x(), other.end.x());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(newStart, start.y()), new Point(newEnd, start.y())),
                            pointSideNormal);
                }
            }
            else {
                final long newStart = Math.max(start.x(), other.end.x());
                final long newEnd = Math.min(end.x(), other.start.x());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(newStart, start.y()), new Point(newEnd, start.y())),
                            pointSideNormal);
                }
            }
        } else if (direction.equals(LEFT) && start.y() == end.y()) {
            if (other.direction.equals(LEFT)) {
                //parallel
                final long newStart = Math.max(start.x(), other.start.x());
                final long newEnd = Math.min(end.x(), other.end.x());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(newStart, start.y()), new Point(newEnd, start.y())),
                            pointSideNormal);
                }
            }
            else {
                final long newStart = Math.max(start.x(), other.end.x());
                final long newEnd = Math.min(end.x(), other.start.x());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(newStart, start.y()), new Point(newEnd, start.y())),
                            pointSideNormal);
                }
            }

        } else if (direction.equals(UP) && start.x() == other.start.x()) {
            if (other.direction.equals(UP)) {
                //parallel
                final long newStart = Math.max(start.y(), other.start.y());
                final long newEnd = Math.min(end.y(), other.end.y());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(start.x(), newStart), new Point(start.x(), newEnd)),
                            pointSideNormal);
                }
            }
            else {
                final long newStart = Math.max(start.y(), other.end.y());
                final long newEnd = Math.min(end.y(), other.start.y());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(start.x(), newStart), new Point(start.x(), newEnd)),
                            pointSideNormal);
                }
            }
        } else if (start.x() == other.start.x()){
            if (other.direction.equals(DOWN)) {
                //parallel
                final long newStart = Math.max(start.y(), other.start.y());
                final long newEnd = Math.min(end.y(), other.end.y());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(start.x(), newStart), new Point(start.x(), newEnd)),
                            pointSideNormal);
                }
            }
            else {
                final long newStart = Math.max(start.y(), other.end.y());
                final long newEnd = Math.min(end.y(), other.start.y());

                if (newEnd < newStart) {
                    return new EmptyIntersectionResult();
                } else if (newEnd == newStart) {
                    return new PointIntersectionResult(new Point(newStart, newEnd));
                } else {
                    return new PointSideIntersectionResult(
                            PointSide.of(new Point(start.x(), newStart), new Point(start.x(), newEnd)),
                            pointSideNormal);
                }
            }
        }
        return new EmptyIntersectionResult();
    }

    private boolean withinEpsilonOfInteger(double value, double epsilon) {
        return Math.abs(value - Math.floor(value)) < epsilon || Math.abs(value - Math.ceil(value)) < epsilon;
    }

    public Point start() {
        return start;
    }

    public Point end() {
        return end;
    }

    public DirectionV2 direction() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (PointSide) obj;
        return Objects.equals(this.start, that.start) && Objects.equals(this.end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "PointSide[" + "start=" + start + ", " + "end=" + end + ']';
    }

}
