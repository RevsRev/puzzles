package com.rev.puzzles.framework.vis;

public final class VisualisationException extends Exception {

    public VisualisationException(final String msg) {
        super(msg);
    }

    public VisualisationException(final Throwable t) {
        super(t);
    }

    public VisualisationException(final String msg, final Throwable t) {
        super(msg, t);
    }
}
