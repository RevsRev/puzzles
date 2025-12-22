package com.rev.puzzles.aoc.framework;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
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
    public void problemSolved(final ProblemResult<AocCoordinate, ?> result) {
        results.add(result);
    }

    @Override
    public void executorStopped() {
        //do nothing
    }

    @Override
    public void problemStarted(final AocCoordinate coordinate) {

    }
}
