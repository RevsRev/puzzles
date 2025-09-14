package com.rev.aoc.framework.problem;

public interface Problem<T> {
    T solve(ResourceLoader resourceLoader) throws ProblemExecutionException;
}
