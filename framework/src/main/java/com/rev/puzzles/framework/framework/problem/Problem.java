package com.rev.puzzles.framework.framework.problem;

import com.rev.puzzles.framework.framework.ResourceLoader;

public interface Problem<T> {
    T solve(ResourceLoader resourceLoader) throws ProblemExecutionException;
}
