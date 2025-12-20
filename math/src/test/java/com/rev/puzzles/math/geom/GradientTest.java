package com.rev.puzzles.math.geom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GradientTest {

    @Test
    void shouldBeParallel() {
        final Gradient first = Gradient.create(1, 2);
        final Gradient second = Gradient.create(3, 6);
        assertTrue(first.parallel(second));
    }

    @Test
    void shouldNotBeParallel() {
        final Gradient first = Gradient.create(1, 2);
        final Gradient second = Gradient.create(3, 7);
        assertFalse(first.parallel(second));
    }

    @Test
    void shouldBePerpendicular() {
        final Gradient first = Gradient.create(1, 2);
        final Gradient second = Gradient.create(-2, 1);
        assertTrue(first.perpendicular(second));
    }

    @Test
    void shouldNotBePerpendicular() {
        final Gradient first = Gradient.create(1, 2);
        final Gradient second = Gradient.create(-2, 4);
        assertFalse(first.perpendicular(second));
    }
}