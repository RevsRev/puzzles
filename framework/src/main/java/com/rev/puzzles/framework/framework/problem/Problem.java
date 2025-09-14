package com.rev.puzzles.framework.framework.problem;

public interface Problem<T> {
    T solve(ResourceLoader resourceLoader) throws ProblemExecutionException;
}
