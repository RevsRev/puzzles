package com.rev.aoc.framework.problem;

import com.rev.aoc.Main;
import com.rev.aoc.vis.VisualisationException;

import java.io.IOException;
import java.util.List;

public abstract class AocProblem {

    public abstract AocCoordinate getCoordinate();
    protected abstract long partOneImpl();
    protected abstract long partTwoImpl();

    public final long partOne() {
        try {
            return partOneImpl();
        } catch (Exception e) {
            return fail("one", e);
        }
    }

    public final long partTwo() {
        try {
            return partTwoImpl();
        } catch (Exception e) {
            return fail("two", e);
        }
    }

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem() throws VisualisationException {
        String msg = String.format("%s does not have visualisation implemented%n", getCoordinate());
        throw new VisualisationException(msg);
    }

    private long fail(final String part, final Exception e) {
        String message = String.format("Execution of problem %s part %s failed", getCoordinate(), part);
        throw new ProblemExecutionException(message, e);
    }

    protected final List<String> loadResources() {
        try {
            return Main.getInputLoader().load(getCoordinate());
        } catch (IOException e) {
            String msg = String.format("Could not load resource for problem %s", getCoordinate());
            throw new ProblemExecutionException(msg, e);
        }
    }
}
