package com.rev.aoc;

import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.ExecutorListener;
import com.rev.aoc.framework.io.display.Printer;
import com.rev.aoc.framework.problem.ProblemResult;

import static com.rev.aoc.io.display.AocDisplayConfig.AOC_RESULT_COLS;

public final class AocExecutionListenerPrinter implements ExecutorListener<AocCoordinate> {

    private final Printer<ProblemResult<AocCoordinate, ?>> printer = new Printer<>(AOC_RESULT_COLS);

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
