package com.rev.puzzles.math.ntheory.digit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DigitTest {

    @Test
    public void digitProductTest() {
        Assertions.assertEquals(54, Digit.digitProduct(1239));
    }

}