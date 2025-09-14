package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.Problem;
import com.rev.aoc.framework.problem.ProblemCoordinate;
import lombok.Setter;

import java.util.List;
import java.util.SortedMap;

public final class ProblemEngine<C extends ProblemCoordinate<C>> implements Runnable {

    private final C firstAocCoordinate;
    private final C secondAocCoordinate;
    private final ProblemLoader<C> problemLoader;
    private final ProblemExecutor<C> executor;

    @Setter
    private boolean debug = false;

    public ProblemEngine(final ProblemLoader<C> problemLoader,
                         final ProblemExecutor<C> problemExecutor,
                         final C firstAocCoordinate,
                         final C secondAocCoordinate
    ) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
        this.problemLoader = problemLoader;
        this.executor = problemExecutor;
    }


    @Override
    public void run() {
        SortedMap<C, Problem<?>> problemsInRange =
                problemLoader.loadProblemsInRange(firstAocCoordinate, secondAocCoordinate);
        if (problemsInRange == null) {
            return;
        }

        List<Throwable> errors = executor.solve(problemsInRange.entrySet());
        printErrors(errors);
    }

    private void printErrors(final List<Throwable> errors) {
        for (int i = 0; i < errors.size() && debug; i++) {
            errors.get(i).printStackTrace(System.out);
        }
    }
}
