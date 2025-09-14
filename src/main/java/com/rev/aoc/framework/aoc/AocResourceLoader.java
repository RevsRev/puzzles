package com.rev.aoc.framework.aoc;

import com.rev.aoc.Main;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.io.IOException;

public final class AocResourceLoader {

    private AocResourceLoader() {
    }

    public static ResourceLoader loadResources(final AocCoordinate coordinate) {
        return () -> {
            try {
                return Main.getInputLoader().load(coordinate);
            } catch (IOException e) {
                String msg = String.format("Could not load resource for problem %s", coordinate);
                throw new ProblemExecutionException(msg, e);
            }
        };
    }
}
