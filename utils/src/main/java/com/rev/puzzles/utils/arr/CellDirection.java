package com.rev.puzzles.utils.arr;

import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
//TODO - Remove in favour of V2
public enum CellDirection implements Iterable<CellDirection> {

    UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

    private static final CellDirection[] DIRECTIONS =
            new CellDirection[]{CellDirection.UP, CellDirection.RIGHT, CellDirection.DOWN, CellDirection.LEFT};

    private static final List<CellDirection> DIRECTIONS_LIST = List.of(DIRECTIONS);

    private final int i;
    private final int j;

    CellDirection(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public Iterator<CellDirection> iterator() {
        return new DirectionIterator(this);
    }

    public CellDirection previous() {
        return CellDirection.previous(this);
    }

    public CellDirection next() {
        return CellDirection.next(this);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static CellDirection of(int i, int j) {
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

    public static CellDirection get(int dirIndex) {
        return DIRECTIONS[dirIndex];
    }

    public static CellDirection opposite(final CellDirection d) {
        return add(d, 2);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    public static CellDirection previous(final CellDirection d) {
        return add(d, 3);
    }

    public static CellDirection next(final CellDirection d) {
        return add(d, 1);
    }

    public static CellDirection add(final CellDirection d, final int amount) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            if (DIRECTIONS[i] == d) {
                return DIRECTIONS[(i + amount) % 4];
            }
        }
        return null;
    }

    private static final class DirectionIterator implements Iterator<CellDirection> {
        private CellDirection start;
        private CellDirection current;

        private DirectionIterator(final CellDirection direction) {
            start = direction;
            current = direction;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public CellDirection next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            CellDirection curr = current;
            current = CellDirection.next(current);
            if (current == start) {
                current = null;
            }
            return curr;
        }
    }
}
