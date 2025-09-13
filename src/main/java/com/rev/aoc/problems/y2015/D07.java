package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.util.emu.WiredEmulator;

public final class D07 extends AocProblem<Long, Long> {

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 7);
    }

    @Override
    @AocProblemI(year = 2015, day = 7, part = 1)
    protected Long partOneImpl() {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(loadResources());
        return wiredEmulator.getSignal("a");
    }

    @Override
    @AocProblemI(year = 2015, day = 7, part = 2)
    protected Long partTwoImpl() {
        final WiredEmulator<Long> wiredEmulator = WiredEmulator.create(loadResources());
        final long aSignal = wiredEmulator.getSignal("a");
        wiredEmulator.clearSignals();
        wiredEmulator.setSignal("b", aSignal);
        return wiredEmulator.getSignal("a");
    }

}
