package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.emu.WiredEmulator;

import com.rev.aoc.framework.problem.ResourceLoader;

public final class D07 extends AocProblem<Long, Long> {

    @Override
    @AocProblemI(year = 2015, day = 7, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(resourceLoader.resources());
        return wiredEmulator.getSignal("a");
    }

    @Override
    @AocProblemI(year = 2015, day = 7, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(resourceLoader.resources());
        final long aSignal = wiredEmulator.getSignal("a");
        wiredEmulator.clearSignals();
        wiredEmulator.setSignal("b", aSignal);
        return wiredEmulator.getSignal("a");
    }

}
