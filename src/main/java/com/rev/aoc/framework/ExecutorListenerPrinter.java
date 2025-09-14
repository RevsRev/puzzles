package com.rev.aoc.framework;

import com.rev.aoc.framework.io.display.Printer;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public final class ExecutorListenerPrinter implements ExecutorListener<AocCoordinate> {

    private final Printer<ProblemResult<AocCoordinate, ?>> printer = new Printer<>(Printer.AOC_RESULT_COLS);

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(final ProblemResult<AocCoordinate, ?> result) {
        printer.printResult(result);
    }

    @Override
    public void executorStopped() {
        printer.printSeparator();
    }
}
