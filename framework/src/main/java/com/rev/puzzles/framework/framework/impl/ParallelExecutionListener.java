package com.rev.puzzles.framework.framework.impl;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class ParallelExecutionListener<C extends ProblemCoordinate<C>> implements ExecutorListener<C> {

    private final ExecutorListener<C> delegate;
    private final PriorityQueue<C> started = new PriorityQueue<>();
    private final PriorityQueue<ProblemResult<C, ?>> solved =
            new PriorityQueue<>(Comparator.comparing(ProblemResult::getCoordinate));

    public ParallelExecutionListener(final ExecutorListener<C> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void executorStarted() {
        delegate.executorStarted();
    }

    @Override
    public synchronized void problemSolved(final ProblemResult<C, ?> result) {
        solved.add(result);
        while (!started.isEmpty() && !solved.isEmpty() && started.peek().equals(solved.peek().getCoordinate())) {
            started.poll();
            delegate.problemSolved(solved.poll());
        }
    }

    @Override
    public void executorStopped() {
        delegate.executorStopped();
    }

    @Override
    public synchronized void problemStarted(final C coordinate) {
        started.add(coordinate);
    }
}
