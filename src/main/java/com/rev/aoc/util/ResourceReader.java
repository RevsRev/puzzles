package com.rev.aoc.util;

import com.rev.aoc.AocCoordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class ResourceReader {

    private static final String AOC_RESOURCES_PATH = "";
    private static final int TEN = 10;

    private ResourceReader() {
    }

    public static List<String> readLines(final AocCoordinate coordinate) throws IOException {
        String resource = String.format("%s/y%s/D%s.txt", AOC_RESOURCES_PATH,
                coordinate.getYear(), pad(coordinate.getDay()));
        return readLines(resource);
    }

    public static List<String> readLines(final String resourcePath) throws IOException {
        return readLines(resourcePath, ResourceReader.class.getClassLoader());
    }

    public static List<String> readLines(final String resourcePath, final ClassLoader classLoader) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream is = null;
        try {
            is = classLoader.getResourceAsStream(resourcePath);
            if (is == null) {
                throw new FileNotFoundException(resourcePath);
            }
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            while (line != null) {
                lines.add(line);
                line = r.readLine();
            }
            return lines;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static String pad(final int day) {
        if (day < TEN) {
            return "0" + day;
        }
        return "" + day;
    }
}
