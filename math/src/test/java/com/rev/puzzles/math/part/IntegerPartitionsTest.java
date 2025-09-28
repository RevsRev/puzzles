package com.rev.puzzles.math.part;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerPartitionsTest {

    @Test
    public void testIntegerPartitions() {
        final IntegerPartitions partitions = IntegerPartitions.create();

        assertEquals(BigInteger.valueOf(1L), partitions.partitions(BigInteger.valueOf(0)));
        assertEquals(BigInteger.valueOf(1L), partitions.partitions(BigInteger.valueOf(1)));
        assertEquals(BigInteger.valueOf(2L), partitions.partitions(BigInteger.valueOf(2)));
        assertEquals(BigInteger.valueOf(3L), partitions.partitions(BigInteger.valueOf(3)));
        assertEquals(BigInteger.valueOf(5L), partitions.partitions(BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(7L), partitions.partitions(BigInteger.valueOf(5)));
        assertEquals(BigInteger.valueOf(11L), partitions.partitions(BigInteger.valueOf(6)));
    }

}