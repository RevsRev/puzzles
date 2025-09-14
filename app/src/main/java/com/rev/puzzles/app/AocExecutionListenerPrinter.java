package com.rev.puzzles.app;

import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.io.display.Printer;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

import static com.rev.puzzles.app.io.display.AocDisplayConfig.AOC_RESULT_COLS;

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
