package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.ProblemCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public class NoOpExecutorListener<C extends ProblemCoordinate<C>> implements ExecutorListener<C> {
    @Override
    public void executorStarted() {
    }

    @Override
    public void executorSolved(final ProblemResult<C, ?> result) {
    }

    @Override
    public void executorStopped() {
    }
}
