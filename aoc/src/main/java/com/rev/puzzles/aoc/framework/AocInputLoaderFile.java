package com.rev.puzzles.aoc.framework;


import com.rev.puzzles.aoc.framework.load.AocInputLoader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class AocInputLoaderFile implements AocInputLoader {

    private final String basePath = problemsBasePath();

    private static String problemsBasePath() {
        final String problemsProperty = System.getProperty("aoc.problems");
        if (problemsProperty != null) {
            return problemsProperty;
        }
        return String.format("%s/%s", System.getProperty("user.dir"), "problems/aoc");
    }

    @Override
    public List<String> load(final AocCoordinate coordinate) throws IOException {
        final String fullyQualifiedName = String.format("%s/y%s/D%s.txt",
                basePath,
                coordinate.getYear(),
                AocInputLoader.pad(coordinate.getDay()));
        return load(fullyQualifiedName);
    }

    private List<String> load(final String filePath) throws IOException {

        final List<String> lines = new ArrayList<>();
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (is == null) {
                throw new FileNotFoundException(filePath);
            }
            final InputStreamReader isr = new InputStreamReader(is);
            final BufferedReader r = new BufferedReader(isr);
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
}
