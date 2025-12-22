package com.rev.puzzles.leet.framework;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.io.display.Printer;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
import com.rev.puzzles.leet.framework.display.LeetDisplayConfig;


public final class LeetExecutionListenerPrinter implements ExecutorListener<LeetCoordinate> {

    private final Printer<ProblemResult<LeetCoordinate, ?>> printer = new Printer<>(LeetDisplayConfig.LEET_RESULT_COLS);

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void problemSolved(final ProblemResult<LeetCoordinate, ?> result) {
        printer.printResult(result);
    }

    @Override
    public void executorStopped() {
        printer.printSeparator();
    }

    @Override
    public void problemStarted(final LeetCoordinate coordinate) {

    }
}
