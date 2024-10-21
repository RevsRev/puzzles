package com.rev.aoc.problems;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.util.ResourceReader;

import java.io.IOException;
import java.util.List;

public abstract class AocProblem {
    public abstract AocCoordinate getCoordinate();

    public final long partOne() {
        try {
            return partOneImpl();
        } catch (Exception e) {
            return fail("one", e);
        }
    }

    public final long partTwo() {
        try {
            return partTwoImpl();
        } catch (Exception e) {
            return fail("two", e);
        }
    }

    private long fail(final String part, final Exception e) {
        String message = String.format("Execution of problem %s part %s failed", getCoordinate(), part);
        throw new ProblemExecutionException(message, e);
    }


    protected abstract long partOneImpl();
    protected abstract long partTwoImpl();

    protected final List<String> loadResources() {
        try {
            return ResourceReader.readLines(getCoordinate());
        } catch (IOException e) {
            String msg = String.format("Could not load resource for problem %s", getCoordinate());
            throw new ProblemExecutionException(msg, e);
        }
    }
}
