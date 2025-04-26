package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.emu.Emulator;
import com.rev.aoc.util.geom.PointRectangle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public final class D07 extends AocProblem<Long, Long> {

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 7);
    }

    @Override
    protected Long partOneImpl() {
        Emulator<Long> emulator = Emulator.create(loadResources());
        return emulator.getSignal("a");
    }

    @Override
    protected Long partTwoImpl() {
        Emulator<Long> emulator = Emulator.create(loadResources());
        long aSignal = emulator.getSignal("a");
        emulator.clearSignals();
        emulator.setSignal("b", aSignal);
        return emulator.getSignal("a");
    }

}
