package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.emu.Emulator;

public final class D07 extends AocProblem<Long, Long> {

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 7);
    }

    @Override
    protected Long partOneImpl() {
        final Emulator<Long> emulator = Emulator.create(loadResources());
        return emulator.getSignal("a");
    }

    @Override
    protected Long partTwoImpl() {
        final Emulator<Long> emulator = Emulator.create(loadResources());
        final long aSignal = emulator.getSignal("a");
        emulator.clearSignals();
        emulator.setSignal("b", aSignal);
        return emulator.getSignal("a");
    }

}
