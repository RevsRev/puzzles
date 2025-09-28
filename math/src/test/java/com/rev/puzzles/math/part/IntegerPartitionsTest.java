package com.rev.puzzles.math.part;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerPartitionsTest {

    @Test
    public void testIntegerPartitions() {
        final IntegerPartitions partitions = IntegerPartitions.create();

        assertEquals(1L, partitions.partitions(0));
        assertEquals(1L, partitions.partitions(1));
        assertEquals(2L, partitions.partitions(2));
        assertEquals(3L, partitions.partitions(3));
        assertEquals(5L, partitions.partitions(4));
        assertEquals(7L, partitions.partitions(5));
        assertEquals(11L, partitions.partitions(6));
    }

}