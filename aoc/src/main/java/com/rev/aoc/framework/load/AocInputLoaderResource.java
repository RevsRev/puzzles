package com.rev.aoc.framework.load;


import com.rev.aoc.framework.AocCoordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class AocInputLoaderResource implements AocInputLoader {

    @Override
    public List<String> load(final AocCoordinate coordinate) throws IOException {
        return load(String.format("y%s/D%s.txt", coordinate.getYear(), AocInputLoader.pad(coordinate.getDay())));
    }

    private List<String> load(final String path) throws IOException {
        String fullyQualifiedName = String.format("%s/%s", "problems", path);
        return load(fullyQualifiedName, AocInputLoaderResource.class.getClassLoader());
    }
    private List<String> load(final String resourcePath, final ClassLoader classLoader) throws IOException {

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
}
