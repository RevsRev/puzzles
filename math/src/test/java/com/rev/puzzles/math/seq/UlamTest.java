package com.rev.puzzles.math.seq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UlamTest {

    @Test
    public void shouldGiveCorrectUlamSequence() {
        final Ulam ulam = Ulam.create(1, 2);
        assertEquals(1, ulam.at(0));
        assertEquals(2, ulam.at(1));
        assertEquals(3, ulam.at(2));
        assertEquals(4, ulam.at(3));
        assertEquals(6, ulam.at(4));
        assertEquals(8, ulam.at(5));
        assertEquals(11, ulam.at(6));
    }

    @Test
    public void shouldGiveCorrectUlam2Sequence() {
        for (int n = 2; n <= 10; n++) {
            final Ulam ulam2 = Ulam.create(2, 2 * n + 1);
            final Ulam ulamSlow = new Ulam.UlamAb(2, 2 * n + 1);
            for (int i = 0; i < 1000; i++) {
                assertEquals(ulamSlow.at(i), ulam2.at(i), "Comparison failed for U(2, %s) at index %s".formatted(2 * n + 1, i));
            }
        }
    }
}