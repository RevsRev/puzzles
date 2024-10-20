package com.rev.aoc.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public final class ResourceReaderTest {

    @Test
    public void testLoadResourceExists() {
        try {
            List<String> lines = ResourceReader.readLines("ResourceReaderTest.txt");
            Assertions.assertEquals(List.of("test", "reading", "lines"), lines);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testLoadResourceNotExists() {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> ResourceReader.readLines("ResourceReaderTestNotExist.txt"));
    }

}
