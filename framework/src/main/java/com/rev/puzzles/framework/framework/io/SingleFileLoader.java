package com.rev.puzzles.framework.framework.io;

import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class SingleFileLoader<C extends ProblemCoordinate<C>> {
    private final String basePath;
    private final Function<C, String> coordinatePathMapper;

    public SingleFileLoader(final String basePath, final Function<C, String> coordinatePathMapper) {
        this.basePath = basePath;
        this.coordinatePathMapper = coordinatePathMapper;
    }

    public List<String> load(final C coordinate) throws IOException {
        final String fullyQualifiedName =
                String.format("%s/%s", basePath, coordinatePathMapper.apply(coordinate));
        return load(fullyQualifiedName);
    }

    private List<String> load(final String filePath) throws IOException {

        final List<String> lines = new ArrayList<String>();
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
