package com.rev.aoc.framework.aoc;

import com.rev.aoc.Main;
import com.rev.aoc.framework.problem.ProblemExecutionException;
import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.vis.VisualisationException;

import java.io.IOException;

public abstract class AocProblem<P1, P2> {

    public abstract P1 partOneImpl(ResourceLoader resourceLoader);
    public abstract P2 partTwoImpl(ResourceLoader resourceLoader);

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem(final ResourceLoader resourceLoader) throws VisualisationException {
        String msg = String.format("Visualisation is not implemented%n");
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
