package com.rev.puzzles.framework.framework;

import com.rev.puzzles.framework.framework.problem.ProblemCoordinate;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public interface ExecutorListener<C extends ProblemCoordinate<C>> {
    void executorStarted();
    void executorSolved(ProblemResult<C, ?> result);
    void executorStopped();
}
