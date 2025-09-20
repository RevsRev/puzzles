package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.io.SingleFileLoader;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;

import java.util.List;

public final class LeetResourceLoader implements ResourceLoader<LeetCoordinate> {

    private final SingleFileLoader<LeetCoordinate> singleFileLoader;

    public LeetResourceLoader(final SingleFileLoader<LeetCoordinate> singleFileLoader) {
        this.singleFileLoader = singleFileLoader;
    }

    @Override
    public ProblemResourceLoader<List<String>> getProblemResourceLoader(final LeetCoordinate coordinate) {
        return () -> {
            try {
                return singleFileLoader.load(coordinate);
            } catch (Exception e) {
                throw new ProblemExecutionException(e.getMessage(), e);
            }
        };
    }
}
