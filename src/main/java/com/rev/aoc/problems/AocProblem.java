package com.rev.aoc.problems;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.util.ResourceReader;
import com.rev.aoc.vis.VisualisationException;

import java.io.IOException;
import java.util.List;

public abstract class AocProblem {

    public abstract AocCoordinate getCoordinate();
    protected abstract long partOneImpl();
    protected abstract long partTwoImpl();

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

    /**
     * Override to visualise a particular problem.
     */
    public void visualiseProblem() throws VisualisationException {
        String msg = String.format("%s does not have visualisation implemented%n", getCoordinate());
        throw new VisualisationException(msg);
    }

    private long fail(final String part, final Exception e) {
        String message = String.format("Execution of problem %s part %s failed", getCoordinate(), part);
        throw new ProblemExecutionException(message, e);
    }

    protected final char[][] loadResourcesAsCharMatrix() {
        List<String> lines = loadResources();
        char[][] arr = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            arr[i] = lines.get(i).toCharArray();
        }
        return arr;
    }

    protected final int[][] loadResourcesAsIntMatrix() {
        List<String> lines = loadResources();
        int[][] arr = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            arr[i] = new int[line.length()];
            for (int j = 0; j < lines.size(); j++) {
                arr[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }
        return arr;
    }

    protected final char[] loadResourcesAsCharArray() {
        char[][] charMatrix = loadResourcesAsCharMatrix();
        int size = 0;
        for (int i = 0; i < charMatrix.length; i++) {
            size += charMatrix[i].length;
        }
        char[] retval = new char[size];
        for (int i = 0; i < charMatrix.length; i++) {
            for (int j = 0; j < charMatrix[i].length; j++) {
                retval[i * charMatrix[i].length + j] = charMatrix[i][j];
            }
        }
        return retval;
    }

    protected final List<String> loadResources() {
        try {
            return ResourceReader.readLines(getCoordinate());
        } catch (IOException e) {
            String msg = String.format("Could not load resource for problem %s", getCoordinate());
            throw new ProblemExecutionException(msg, e);
        }
    }
}
