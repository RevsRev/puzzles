package com.rev.aoc.util;

import com.rev.aoc.framework.io.load.ResourceReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public final class ResourceReaderTest {

    private static final String RESOURCE_READER_TEST_DIRECTORY = "src/test/resources/";

    @Test
    public void testLoadResourceExists() {
        try {
            ResourceReader reader = new ResourceReader(RESOURCE_READER_TEST_DIRECTORY);
            List<String> lines = reader.readLines("ResourceReaderTest.txt");
            Assertions.assertEquals(List.of("test", "reading", "lines"), lines);
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    public void testLoadResourceNotExists() {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> (new ResourceReader(RESOURCE_READER_TEST_DIRECTORY)).readLines("ResourceReaderTestNotExist.txt"));
    }

}
