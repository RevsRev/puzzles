package com.rev.puzzles.framework.framework.impl;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.ProblemExecutor;
import com.rev.puzzles.framework.framework.ResourceLoader;
import com.rev.puzzles.framework.framework.problem.Problem;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class DefaultExecutor<C extends ProblemCoordinate<C>> implements ProblemExecutor<C> {

    private final ExecutorListener<C> executorListener;
    private final ResourceLoader<C> resourceLoader;
    private final ExecutorService executorService;

    private DefaultExecutor(
            final ExecutorListener<C> executorListener,
            final ResourceLoader<C> resourceLoader,
            final ExecutorService executorService) {
        this.executorListener = executorListener;
        this.resourceLoader = resourceLoader;
        this.executorService = executorService;
    }

    public static <C extends ProblemCoordinate<C>> DefaultExecutor<C> create(
            final ExecutorListener<C> executorListener,
            final ResourceLoader<C> resourceLoader) {
        return new DefaultExecutor<>(executorListener, resourceLoader, Executors.newSingleThreadExecutor());
    }

    @Override
    public List<Throwable> solve(final Iterable<Map.Entry<C, Problem<?>>> problems) {
        executorListener.executorStarted();
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<C, Problem<?>> problem : problems) {
            ProblemResult<C, ?> result;
            try {
                result = solve(problem.getKey(), problem.getValue()).get();
            } catch (InterruptedException | ExecutionException e) {
                result = new ProblemResult<>(problem.getKey(), Optional.empty(), Optional.empty(),
                        Optional.of(new ProblemExecutionException(e)));
            }
            result.getError().ifPresent(errors::add);
            executorListener.executorSolved(result);
        }
        executorListener.executorStopped();
        return errors;
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    private <P1> Future<ProblemResult<C, P1>> solve(final C coordinate, final Problem<P1> problem) {
        return executorService.submit(() -> {
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
        });
    }
}
