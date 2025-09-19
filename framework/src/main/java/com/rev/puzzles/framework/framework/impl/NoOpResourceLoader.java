package com.rev.puzzles.framework.framework.impl;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;

public final class NoOpResourceLoader<C extends ProblemCoordinate<C>> implements ResourceLoader<C> {
    @Override
    public ProblemResourceLoader<?> getProblemResourceLoader(final C coordinate) {
        return null;
    }
}
