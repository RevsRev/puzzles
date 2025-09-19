package com.rev.puzzles.framework.framework.problem;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;

public interface Problem<T> {
    T solve(ProblemResourceLoader<?> resourceLoader) throws ProblemExecutionException;
}
