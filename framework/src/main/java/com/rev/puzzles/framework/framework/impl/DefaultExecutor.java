package com.rev.puzzles.framework.framework.impl;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class DefaultExecutor<C extends ProblemCoordinate<C>> implements ProblemExecutor<C> {

    private final ExecutorListener<C> executorListener;
    private final ResourceLoader<C> resourceLoader;

    public DefaultExecutor(
            final ExecutorListener<C> executorListener,
            final ResourceLoader<C> resourceLoader) {
        this.executorListener = executorListener;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<Throwable> solve(final Iterable<Map.Entry<C, Problem<?>>> problems) {
        executorListener.executorStarted();
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<C, Problem<?>> problem : problems) {
            ProblemResult<C, ?> result = solve(problem.getKey(), problem.getValue());
            result.getError().ifPresent(errors::add);
            executorListener.executorSolved(result);
        }
        executorListener.executorStopped();
        return errors;
    }

    private <P1> ProblemResult<C, P1> solve(final C coordinate, final Problem<P1> problem) {
        try {
            long time = System.nanoTime();
            P1 result = problem.solve(resourceLoader.getProblemResourceLoader(coordinate));
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
