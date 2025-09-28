package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import static com.rev.puzzles.math.seq.Pentagonal.pentagonal;
import static org.junit.jupiter.api.Assertions.*;

class PentagonalTest {

    @Test
    public void testPentagonal() {
        assertEquals(1, pentagonal(1));
        assertEquals(5, pentagonal(2));
        assertEquals(12, pentagonal(3));
    }

}