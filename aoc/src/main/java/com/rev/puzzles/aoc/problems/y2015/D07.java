package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.util.emu.WiredEmulator;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;

public final class D07 {

    @AocProblemI(year = 2015, day = 7, part = 1)
    public Long partOneImpl(final ProblemResourceLoader resourceLoader) {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(resourceLoader.resources());
        return wiredEmulator.getSignal("a");
    }

    @AocProblemI(year = 2015, day = 7, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader resourceLoader) {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(resourceLoader.resources());
        final long aSignal = wiredEmulator.getSignal("a");
        wiredEmulator.clearSignals();
        wiredEmulator.setSignal("b", aSignal);
        return wiredEmulator.getSignal("a");
    }

}
