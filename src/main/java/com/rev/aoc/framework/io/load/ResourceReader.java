package com.rev.aoc.framework.io.load;

import com.rev.aoc.framework.problem.AocCoordinate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class ResourceReader {
    private static final int TEN = 10;

    private final String basePath;

    public ResourceReader(final String basePath) {
        this.basePath = basePath;
    }

    public List<String> readLines(final AocCoordinate coordinate) throws IOException {
        return readLines(String.format("y%s/D%s.txt", coordinate.getYear(), pad(coordinate.getDay())));
    }

    public List<String> readLines(final String fileName) throws IOException {
        String fullyQualifiedName = String.format("%s/%s", basePath, fileName);
        return readLines(fullyQualifiedName, ResourceReader.class.getClassLoader());
    }

    public List<String> readLines(final String resourcePath, final ClassLoader classLoader) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream is = null;
        try {
            is = new FileInputStream(resourcePath);
//            is = classLoader.getResourceAsStream(resourcePath);
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
