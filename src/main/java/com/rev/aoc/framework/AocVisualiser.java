package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.ResourceLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public final class AocVisualiser {

    public List<Throwable> visualise(final SortedMap<AocCoordinate, AocProblem<?, ?>> problems) {
        List<Throwable> errors = new ArrayList<>();
        problems.forEach((coord, problem) -> {
            try {
                final ResourceLoader resourceLoader = AocProblem.loadResources(coord);
                problem.visualiseProblem(resourceLoader);
            } catch (Exception e) {
                errors.add(e);
            }
        });
        return errors;
    }

}
