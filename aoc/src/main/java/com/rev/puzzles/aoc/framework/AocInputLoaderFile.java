package com.rev.puzzles.aoc.framework;


import com.rev.puzzles.aoc.framework.load.AocInputLoader;
import com.rev.puzzles.framework.framework.io.SingleFileLoader;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

public final class AocInputLoaderFile implements AocInputLoader {

    private final SingleFileLoader<AocCoordinate>
            fileLoader = new SingleFileLoader<>(problemsBasePath(), coordPathMapper());

    private static String problemsBasePath() {
        final String problemsProperty = System.getProperty("aoc.problems");
        if (problemsProperty != null) {
            return problemsProperty;
        }
        return String.format("%s/%s", System.getProperty("user.dir"), "problems/aoc");
    }

    private static Function<AocCoordinate, String> coordPathMapper() {
        return c -> String.format("y%s/D%s.txt",
                c.getYear(),
                AocInputLoader.pad(c.getDay()));
    }

    @Override
    public List<String> load(final AocCoordinate coordinate) throws IOException {
        return fileLoader.load(coordinate);
    }
}
