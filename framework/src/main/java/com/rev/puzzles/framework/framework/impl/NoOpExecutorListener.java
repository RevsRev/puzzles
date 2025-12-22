package com.rev.puzzles.framework.framework.impl;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public class NoOpExecutorListener<C extends ProblemCoordinate<C>> implements ExecutorListener<C> {
    @Override
    public void executorStarted() {
    }

    @Override
    public void problemSolved(final ProblemResult<C, ?> result) {
    }

    @Override
    public void executorStopped() {
    }

    @Override
    public void problemStarted(final C coordinate) {

    }
}
