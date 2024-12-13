package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocResult;

public interface ExecutorListener {
    void executorStarted();
    void executorSolved(AocResult result);
    void executorStopped();
}
