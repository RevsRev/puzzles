package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.ProblemCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public interface ExecutorListener<C extends ProblemCoordinate<C>> {
    void executorStarted();
    void executorSolved(ProblemResult<C, ?> result);
    void executorStopped();
}
