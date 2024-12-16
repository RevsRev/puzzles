package com.rev.aoc.util.geom;

import lombok.Getter;

@Getter
public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    public static final Direction[] DIRECTIONS = new Direction[]{
            Direction.UP,
            Direction.RIGHT,
            Direction.DOWN,
            Direction.LEFT};

    private final int i;
    private final int j;

    Direction(int i, int j) {
        this.i = i;
        this.j = j;
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
                return DIRECTIONS[ (i + amount) % 4];
            }
        }
        return null;
    }
}
