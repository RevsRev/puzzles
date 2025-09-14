package com.rev.puzzles.framework.util.geom;

import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
public enum Direction implements Iterable<Direction> {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private static final Direction[] DIRECTIONS = new Direction[]{
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN,
            Direction.LEFT};

    private static final List<Direction> DIRECTIONS_LIST = List.of(DIRECTIONS);

    private final int i;
    private final int j;

    Direction(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public Iterator<Direction> iterator() {
        return new DirectionIterator(this);
    }

    public Direction previous() {
        return Direction.previous(this);
    }

    public Direction next() {
        return Direction.next(this);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static Direction of(int i, int j) {
        if (i == -1 && j == 0) {
            return UP;
        }
        if (i == 1 && j == 0) {
            return DOWN;
        }
        if (i == 0 && j == 1) {
            return RIGHT;
        }
        if (i == 0 && j == -1) {
            return LEFT;
        }
        throw new NoSuchElementException(String.format("No direction for (i,j) = (%s,%s)", i, j));
    }

    public static Direction get(int dirIndex) {
        return DIRECTIONS[dirIndex];
    }

    public static Direction opposite(final Direction d) {
        return add(d, 2);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static Direction previous(final Direction d) {
        return add(d, 3);
    }

    public static Direction next(final Direction d) {
        return add(d, 1);
    }

    public static Direction add(final Direction d, final int amount) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i] == d) {
                return DIRECTIONS[(i + amount) % 4];
            }
        }
        return null;
    }

    private static final class DirectionIterator implements Iterator<Direction> {
        private Direction start;
        private Direction current;

        private DirectionIterator(final Direction direction) {
            start = direction;
            current = direction;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Direction next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Direction curr = current;
            current = Direction.next(current);
            if (current == start) {
                current = null;
            }
            return curr;
        }
    }
}
