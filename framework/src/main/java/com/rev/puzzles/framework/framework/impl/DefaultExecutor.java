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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class DefaultExecutor<C extends ProblemCoordinate<C>> implements ProblemExecutor<C> {

    private final ExecutorListener<C> executorListener;
    private final ResourceLoader<C> resourceLoader;
    private final ExecutorService executorService;

    private DefaultExecutor(final ExecutorListener<C> executorListener, final ResourceLoader<C> resourceLoader,
                            final ExecutorService executorService) {
        this.executorListener = executorListener;
        this.resourceLoader = resourceLoader;
        this.executorService = executorService;
    }

    public static <C extends ProblemCoordinate<C>> DefaultExecutor<C> create(final ExecutorListener<C> executorListener,
                                                                             final ResourceLoader<C> resourceLoader) {
        return new DefaultExecutor<>(new ParallelExecutionListener<>(executorListener), resourceLoader,
                Executors.newSingleThreadExecutor());
    }

    @Override
    public List<Throwable> solve(final Iterable<Map.Entry<C, Problem<?>>> problems) {
        executorListener.executorStarted();
        final List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Throwable> errors = new ArrayList<>();
        for (Map.Entry<C, Problem<?>> problem : problems) {
            futures.add(solve(problem.getKey(), errors, problem.getValue()));
        }
        CompletableFuture<Void> waitForEverything = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        try {
            waitForEverything.get(100, TimeUnit.SECONDS);
        } catch (final InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (final TimeoutException e) {
            System.out.println("Timed out before all problems could be executed");
        } finally {
            executorListener.executorStopped();
        }
        return errors;
    }

    private CompletableFuture<Void> solve(
            final C coordinate,
            final List<Throwable> errors,
            final Problem<?> problem) {
        executorListener.problemStarted(coordinate);
        return CompletableFuture.supplyAsync(() -> solve(coordinate, problem), executorService)
                .thenAccept(result -> handleSuccess(result, errors)).exceptionally(e -> {
                    handleException(e, coordinate);
                    return null;
                });
    }

    private void handleSuccess(final ProblemResult<C, ?> result, final List<Throwable> errors) {
        result.getError().ifPresent(errors::add);
        executorListener.problemSolved(result);
    }

    private void handleException(final Throwable e, final C coordinate) {
        ProblemResult<C, Object> errorResult = new ProblemResult<>(coordinate, Optional.empty(), Optional.empty(),
                Optional.of(new ProblemExecutionException(e)));
        executorListener.problemSolved(errorResult);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    private <P1> ProblemResult<C, P1> solve(final C coordinate, final Problem<P1> problem) {
        try {
            long time = System.nanoTime();
            P1 result = problem.solve(resourceLoader.getProblemResourceLoader(coordinate));
            time = System.nanoTime() - time;
            return new ProblemResult<>(coordinate, Optional.of(result), Optional.of(time), Optional.empty());
        } catch (final Throwable t) {
            return new ProblemResult<>(coordinate, Optional.empty(), Optional.empty(), Optional.of(t));
        }
    }
}
