package com.rev.aoc.framework.problem;

public final class ProblemExecutionException extends RuntimeException {

    public ProblemExecutionException() {
        super();
    }

    public ProblemExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
