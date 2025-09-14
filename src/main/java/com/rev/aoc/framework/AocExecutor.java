package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.Problem;
import com.rev.aoc.framework.problem.ProblemResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class AocExecutor implements ProblemExecutor<AocCoordinate> {

    private final ExecutorListener<AocCoordinate> executorListener;

    public AocExecutor(final AocPart part,
                       final ExecutorListener<AocCoordinate> executorListener) {
        this.executorListener = executorListener;
    }

    @Override
    public List<Throwable> solve(final Iterable<Map.Entry<AocCoordinate, Problem<?>>> problems) {
        executorListener.executorStarted();
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<AocCoordinate, Problem<?>> problem : problems) {
            ProblemResult<AocCoordinate, ?> result = solve(problem.getKey(), problem.getValue());
            result.getError().ifPresent(errors::add);
            executorListener.executorSolved(result);
        }
        executorListener.executorStopped();
        return errors;
    }

    private <P1> ProblemResult<AocCoordinate, P1> solve(final AocCoordinate coordinate, final Problem<P1> problem) {
        try {
            long time = System.nanoTime();
            P1 result = problem.solve(AocProblem.loadResources(coordinate));
            time = System.nanoTime() - time;
            return new ProblemResult<>(coordinate, Optional.of(result), Optional.of(time), Optional.empty());
        } catch (Throwable t) {
            return new ProblemResult<>(coordinate,
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(t));
        }
    }
}
