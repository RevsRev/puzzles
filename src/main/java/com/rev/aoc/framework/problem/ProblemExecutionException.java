package com.rev.aoc.framework.problem;

public final class ProblemExecutionException extends RuntimeException {

    public ProblemExecutionException() {
        super();
    }

    public ProblemExecutionException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public ProblemExecutionException(final String msg) {
        super(msg);
    }
}
