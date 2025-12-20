package com.rev.puzzles.math.geom;

import static com.rev.puzzles.math.geom.DirectionV2.*;
import static com.rev.puzzles.math.geom.DirectionV2.RIGHT;

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

    public boolean contains(final Point p) {
        return switch (direction) {
            case UP -> p.x() == start.x() && start.y() <= p.y() && p.y() <= end.y();
            case RIGHT -> p.y() == start.y() && start.x() <= p.x() && p.x() <= end.x();
            case DOWN -> p.x() == start.x() && start.y() >= p.y() && p.y() >= end.y();
            case LEFT -> p.y() == start.y() && start.x() >= p.x() && p.x() >= end.x();
        };
    }

    public Point start() {
        return start;
    }

    public Point end() {
        return end;
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
