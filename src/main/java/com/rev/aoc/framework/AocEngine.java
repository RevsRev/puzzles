package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.Problem;
import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.SortedMap;

public final class AocEngine implements Runnable {

    private final AocCoordinate firstAocCoordinate;
    private final AocCoordinate secondAocCoordinate;
    private final ProblemLoader<AocCoordinate> problemLoader;
    private final ProblemExecutor<AocCoordinate> executor;
//    private final AocVisualiser visualiser;

    @Setter
    private boolean debug = false;
    @Setter
    private boolean visualise = false;

    public AocEngine(final AocCoordinate firstAocCoordinate,
                     final AocCoordinate secondAocCoordinate,
                     final AocPart part) {
        this.firstAocCoordinate = firstAocCoordinate;
        this.secondAocCoordinate = secondAocCoordinate;
        this.problemLoader = new AocProblemLoader();
//        this.visualiser = new AocVisualiser();
        this.executor = new AocExecutor(part, new ExecutorListenerPrinter());
    }


    @Override
    public void run() {
        SortedMap<AocCoordinate, Problem<?>> problemsInRange =
                problemLoader.loadProblemsInRange(firstAocCoordinate, secondAocCoordinate);
        if (problemsInRange == null) {
            return;
        }
        if (visualise) {
            throw new NotImplementedException("Visualisation is currently not implemented");
//            List<Throwable> errors = visualiser.visualise(problemsInRange);
//            printErrors(errors);
//            return;
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
