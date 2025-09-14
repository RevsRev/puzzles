package com.rev.aoc.framework.problem;

import lombok.Getter;

import java.util.Optional;

@Getter
public final class ProblemResult<C extends ProblemCoordinate<C>, T> {
    private final C coordinate;
    private final Optional<T> problemResult;
    private final Optional<Long> executionTime;
    private final Optional<Throwable> error;

    public ProblemResult(
            final C coordinate,
            final Optional<T> problemResult,
            final Optional<Long> executionTime,
            final Optional<Throwable> error) {
        this.coordinate = coordinate;
        this.problemResult = problemResult;
        this.executionTime = executionTime;
        this.error = error;
    }

    public static <C extends ProblemCoordinate<C>, T> ProblemResult<C, T> empty(final C coordinate) {
        return new ProblemResult<>(coordinate, Optional.empty(), Optional.empty(), Optional.empty());
    }

}
