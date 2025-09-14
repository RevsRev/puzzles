package com.rev.aoc.framework;

import com.rev.aoc.framework.aoc.AocCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class TestExecutionListener implements ExecutorListener<AocCoordinate> {

    private final List<ProblemResult<AocCoordinate, ?>> results = new ArrayList<>();

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(final ProblemResult<AocCoordinate, ?> result) {
        results.add(result);
    }

    @Override
    public void executorStopped() {
        //do nothing
    }
}
