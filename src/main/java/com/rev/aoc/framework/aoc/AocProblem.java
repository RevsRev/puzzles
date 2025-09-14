package com.rev.aoc.framework.aoc;

import com.rev.aoc.framework.problem.ResourceLoader;
import com.rev.aoc.vis.VisualisationException;

public abstract class AocProblem {

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem(final ResourceLoader resourceLoader) throws VisualisationException {
        String msg = String.format("Visualisation is not implemented%n");
        throw new VisualisationException(msg);
    }

}
