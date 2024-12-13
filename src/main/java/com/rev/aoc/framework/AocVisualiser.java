package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.vis.VisualisationException;

import java.util.ArrayList;
import java.util.List;

public final class AocVisualiser {

    public List<Throwable> visualise(final Iterable<AocProblem> problems) {
        List<Throwable> errors = new ArrayList<>();
        for (AocProblem problem : problems) {
            try {
                problem.visualiseProblem();
            } catch (VisualisationException e) {
                errors.add(e);
            }
        }
        return errors;
    }

}
