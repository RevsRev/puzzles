package com.rev.puzzles.aoc.framework;

import com.rev.puzzles.aoc.framework.display.AocDisplayConfig;
import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.io.display.Printer;
import com.rev.puzzles.framework.framework.problem.ProblemResult;


public final class AocExecutionListenerPrinter implements ExecutorListener<AocCoordinate> {

    private final Printer<ProblemResult<AocCoordinate, ?>> printer = new Printer<>(AocDisplayConfig.AOC_RESULT_COLS);

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void problemSolved(final ProblemResult<AocCoordinate, ?> result) {
        printer.printResult(result);
    }

    @Override
    public void executorStopped() {
        printer.printSeparator();
    }

    @Override
    public void problemStarted(final AocCoordinate coordinate) {

    }
}
