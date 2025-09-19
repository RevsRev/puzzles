package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;

public final class LeetResourceLoader implements ResourceLoader<LeetCoordinate> {
    @Override
    public ProblemResourceLoader<Object[]> getProblemResourceLoader(final LeetCoordinate coordinate) {
        return null;
    }
}
