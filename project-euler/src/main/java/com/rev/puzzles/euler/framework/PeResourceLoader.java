package com.rev.puzzles.euler.framework;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.io.SingleFileLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.util.List;

public final class PeResourceLoader implements ResourceLoader<PeCoordinate> {

    private final SingleFileLoader<PeCoordinate> singleFileLoader;

    public PeResourceLoader(final SingleFileLoader<PeCoordinate> singleFileLoader) {
        this.singleFileLoader = singleFileLoader;
    }

    @Override
    public ProblemResourceLoader<List<String>> getProblemResourceLoader(final PeCoordinate coordinate) {
        return () -> {
            try {
                return singleFileLoader.load(coordinate);
            } catch (Exception e) {
                throw new ProblemExecutionException(e.getMessage(), e);
            }
        };
    }
}
