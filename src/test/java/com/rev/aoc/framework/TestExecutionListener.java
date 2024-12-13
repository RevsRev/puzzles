package com.rev.aoc.framework;

import com.rev.aoc.framework.problem.AocResult;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class TestExecutionListener implements ExecutorListener {

    private final List<AocResult> results = new ArrayList<>();

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(AocResult result) {
        results.add(result);
    }

    @Override
    public void executorStopped() {
        //do nothing
    }
}
