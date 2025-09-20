package com.rev.puzzles.euler.framework;

import com.rev.puzzles.framework.framework.ExecutorListener;
import com.rev.puzzles.framework.framework.io.display.Printer;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

import static com.rev.puzzles.euler.framework.display.PeDisplayConfig.PE_RESULT_COLS;


public final class PeExecutionListenerPrinter implements ExecutorListener<PeCoordinate> {

    private final Printer<ProblemResult<PeCoordinate, ?>> printer = new Printer<>(PE_RESULT_COLS);

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(final ProblemResult<PeCoordinate, ?> result) {
        printer.printResult(result);
    }

    @Override
    public void executorStopped() {
        printer.printSeparator();
    }
}
