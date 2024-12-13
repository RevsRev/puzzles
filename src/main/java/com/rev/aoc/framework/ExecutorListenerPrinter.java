package com.rev.aoc.framework;

import com.rev.aoc.framework.io.AocResultPrinter;
import com.rev.aoc.framework.problem.AocResult;

public final class ExecutorListenerPrinter implements ExecutorListener {

    private final AocResultPrinter printer = new AocResultPrinter();

    @Override
    public void executorStarted() {
        //do nothing
    }

    @Override
    public void executorSolved(final AocResult result) {
        printer.printResult(result);
    }

    @Override
    public void executorStopped() {
        printer.printSeparator();
    }
}
