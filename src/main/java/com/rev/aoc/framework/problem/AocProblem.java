package com.rev.aoc.framework.problem;

import com.rev.aoc.Main;
import com.rev.aoc.vis.VisualisationException;

import java.io.IOException;
import java.util.List;

public abstract class AocProblem<P1, P2> {

    public abstract AocCoordinate getCoordinate();
    protected abstract P1 partOneImpl();
    protected abstract P2 partTwoImpl();

    public final P1 partOne() {
        try {
            return partOneImpl();
        } catch (Exception e) {
            String message = String.format("Execution of problem %s part one failed", getCoordinate());
            throw new ProblemExecutionException(message, e);
        }
    }

    public final P2 partTwo() {
        try {
            return partTwoImpl();
        } catch (Exception e) {
            String message = String.format("Execution of problem %s part two failed", getCoordinate());
            throw new ProblemExecutionException(message, e);
        }
    }

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem() throws VisualisationException {
        String msg = String.format("%s does not have visualisation implemented%n", getCoordinate());
        throw new VisualisationException(msg);
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
