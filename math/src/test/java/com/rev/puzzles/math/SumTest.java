package com.rev.puzzles.math;

import com.rev.puzzles.math.sum.Sum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumTest {

    @Test
    public void testSum1toN() {
        long sum = 0;
        final long n = 1234;
        for (long i = 1; i <= n; i++) {
            sum += i;
        }
        assertEquals(sum, Sum.sum1toN(n));
    }

    @Test
    public void testSumMtoN() {
        long sum = 0;
        final long m = 426;
        final long n = 986;
        for (long i = m; i <= n; i++) {
            sum += i;
        }
        assertEquals(sum, Sum.sumMtoN(m, n));
    }

    @Test
    public void testSumSq1ToN() {
        assertEquals(385, Sum.sumSq1toN(10));
    }

}