package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UlamTest {

    @Test
    public void shouldGiveCorrectUlamSequence() {
        final Ulam ulam = new Ulam(1, 2);
        assertEquals(1, ulam.at(0));
        assertEquals(2, ulam.at(1));
        assertEquals(3, ulam.at(2));
        assertEquals(4, ulam.at(3));
        assertEquals(6, ulam.at(4));
        assertEquals(8, ulam.at(5));
        assertEquals(11, ulam.at(6));
    }

}