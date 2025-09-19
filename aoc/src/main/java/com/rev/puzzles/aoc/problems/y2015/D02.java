package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.ProblemResourceLoader;

import java.util.ArrayList;
import java.util.List;

public final class D02 {


    @AocProblemI(year = 2015, day = 2, part = 1)
    public Long partOneImpl(final ProblemResourceLoader resourceLoader) {
        List<Box> boxes = parseBoxes(resourceLoader.resources());
        long areaNeeded = 0;
        for (Box b : boxes) {
            long area = 2 * (b.l * b.w + b.l * b.h + b.w * b.h);
            long slack = Math.min(b.l * b.w, Math.min(b.l * b.h, b.w * b.h));
            areaNeeded += (area + slack);
        }
        return areaNeeded;
    }

    @AocProblemI(year = 2015, day = 2, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader resourceLoader) {
        List<Box> boxes = parseBoxes(resourceLoader.resources());
        long ribbonNeeded = 0;
        for (Box b : boxes) {
            long volume = b.l * b.w * b.h;
            long smallestPerimeter = 2 * Math.min(b.l + b.w, Math.min(b.l + b.h, b.w + b.h));
            ribbonNeeded += (volume + smallestPerimeter);
        }
        return ribbonNeeded;
    }

    private List<Box> parseBoxes(final List<String> inputs) {
        final List<Box> boxes = new ArrayList<>();
        for (String s : inputs) {
            String[] lwh = s.split("x");
            boxes.add(new Box(Integer.parseInt(lwh[0]), Integer.parseInt(lwh[1]), Integer.parseInt(lwh[2])));
        }
        return boxes;
    }

    private record Box(long l, long w, long h) {
    }
}
