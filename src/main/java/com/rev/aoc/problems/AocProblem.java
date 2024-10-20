package com.rev.aoc.problems;

import com.rev.aoc.AocCoordinate;

public abstract class AocProblem {
    public static final String AOC_RESOURCES_PATH = "";

    public abstract AocCoordinate getCoordinate();

    public abstract long solvePartOne();
    public abstract long solvePartTwo();
}
