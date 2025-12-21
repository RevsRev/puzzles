package com.rev.puzzles.math.geom;

import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
public enum DirectionV2 implements Iterable<DirectionV2> {

    UP(0, 1), RIGHT(1, 0), DOWN(0, -1), LEFT(-1, 0);

    private static final DirectionV2[] DIRECTIONS =
            new DirectionV2[]{DirectionV2.UP, DirectionV2.RIGHT, DirectionV2.DOWN, DirectionV2.LEFT};

    private static final List<DirectionV2> DIRECTIONS_LIST = List.of(DIRECTIONS);

    private final int i;
    private final int j;

    DirectionV2(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public Iterator<DirectionV2> iterator() {
        return new DirectionIterator(this);
    }

    public DirectionV2 previous() {
        return DirectionV2.previous(this);
    }

    public DirectionV2 next() {
        return DirectionV2.next(this);
    }

    public DirectionV2 opposite() {
        return DirectionV2.opposite(this);
    }

    public boolean perpendicularTo(final DirectionV2 other) {
        return this == other.next() || this == other.previous();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static DirectionV2 of(int i, int j) {
        if (i == 0 && j == 1) {
            return UP;
        }
        if (i == 0 && j == -1) {
            return DOWN;
        }
        if (i == 1 && j == 0) {
            return RIGHT;
        }
        if (i == -1 && j == 0) {
            return LEFT;
        }
        throw new NoSuchElementException(String.format("No direction for (i,j) = (%s,%s)", i, j));
    }

    public static DirectionV2 get(int dirIndex) {
        return DIRECTIONS[dirIndex];
    }

    public static DirectionV2 opposite(final DirectionV2 d) {
        return add(d, 2);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static DirectionV2 previous(final DirectionV2 d) {
        return add(d, 3);
    }

    public static DirectionV2 next(final DirectionV2 d) {
        return add(d, 1);
    }

    public static DirectionV2 add(final DirectionV2 d, final int amount) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i] == d) {
                return DIRECTIONS[(i + amount) % 4];
            }
        }
        return null;
    }

    private static final class DirectionIterator implements Iterator<DirectionV2> {
        private DirectionV2 start;
        private DirectionV2 current;

        private DirectionIterator(final DirectionV2 direction) {
            start = direction;
            current = direction;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public DirectionV2 next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            DirectionV2 curr = current;
            current = DirectionV2.next(current);
            if (current == start) {
                current = null;
            }
            return curr;
        }
    }
}
