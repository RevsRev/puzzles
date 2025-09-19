package com.rev.puzzles.framework.framework;

import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import lombok.Setter;

import java.util.List;
import java.util.SortedMap;

public final class DefaultProblemEngine<C extends ProblemCoordinate<C>> implements ProblemEngine<C> {

    private final C firstCoordinate;
    private final C secondCoordinate;
    private final ProblemLoader<C> problemLoader;
    private final ProblemExecutor<C> executor;

    @Setter
    private boolean debug = false;

    public DefaultProblemEngine(final ProblemLoader<C> problemLoader,
                            final ProblemExecutor<C> problemExecutor,
                            final C firstCoordinate,
                            final C secondCoordinate
    ) {
        this.firstCoordinate = firstCoordinate;
        this.secondCoordinate = secondCoordinate;
        this.problemLoader = problemLoader;
        this.executor = problemExecutor;
    }


    @Override
    public void run() {
        SortedMap<C, Problem<?>> problemsInRange =
                problemLoader.loadProblemsInRange(firstCoordinate, secondCoordinate);
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
