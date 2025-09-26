package com.rev.puzzles.utils.arr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArrayUtilsTest {

    @Test
    public void testReversed() {
        assertArrayEquals(new Integer[]{1, 2, 3}, ArrayUtils.reversed(new Integer[]{3, 2, 1}));
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, ArrayUtils.reversed(new Integer[]{4, 3, 2, 1}));
    }

}