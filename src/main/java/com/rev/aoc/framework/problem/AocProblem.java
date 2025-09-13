package com.rev.aoc.framework.problem;

import com.rev.aoc.Main;
import com.rev.aoc.vis.VisualisationException;

import java.io.IOException;

public abstract class AocProblem<P1, P2> {

    public abstract AocCoordinate getCoordinate();

    protected abstract P1 partOneImpl(ResourceLoader resourceLoader);

    protected abstract P2 partTwoImpl(ResourceLoader resourceLoader);

    public final Problem<P1> partOne() {
        return solveExceptionally(this::partOneImpl, getCoordinate(), "one");
    }

    public final Problem<P2> partTwo() {
        return solveExceptionally(this::partTwoImpl, getCoordinate(), "two");
    }

    public static <T, C> Problem<T> solveExceptionally(
            final Problem<T> problem,
            final ProblemCoordinate<C> coordinate,
            final String part) {
        return (resourceLoader) -> {
            try {
                return problem.solve(resourceLoader);
            } catch (Exception e) {
                String message = String.format("Execution of problem %s part %s failed", coordinate, part);
                throw new ProblemExecutionException(message, e);
            }
        };
    }

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem(final ResourceLoader resourceLoader) throws VisualisationException {
        String msg = String.format("%s does not have visualisation implemented%n", getCoordinate());
        throw new VisualisationException(msg);
    }

    public static ResourceLoader loadResources(final AocCoordinate coordinate) {
        return () -> {
            try {
                return Main.getInputLoader().load(coordinate);
            } catch (IOException e) {
                String msg = String.format("Could not load resource for problem %s", coordinate);
                throw new ProblemExecutionException(msg, e);
            }
        };
    }
}
