package com.rev.aoc.util;

import com.rev.aoc.framework.io.load.AocInputLoaderFile;
import com.rev.aoc.framework.aoc.AocCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public final class AocInputLoaderFileTest {

    @Test
    public void testLoadResourceExists() {
        try {
            AocInputLoaderFile reader = new AocInputLoaderFile();
            List<String> lines = reader.load(new AocCoordinate(2010, 1, 0));
            Assertions.assertEquals(
                    List.of("This problem does not exist.", "This file is used to test loading resources!"), lines);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testLoadResourceNotExists() {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> (new AocInputLoaderFile()).load(new AocCoordinate(2010, 2, 0)));
    }

}
