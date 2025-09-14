package com.rev.puzzles.framework.framework.problem;

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

    public ProblemExecutionException(final Throwable cause) {
        super(cause);
    }
}
