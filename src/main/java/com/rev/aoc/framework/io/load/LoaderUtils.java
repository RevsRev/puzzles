package com.rev.aoc.framework.io.load;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class LoaderUtils {

    private LoaderUtils() {
    }

    public static char[][] loadResourcesAsCharMatrix(final List<String> lines) {
        return linesToCharMatrix(lines);
    }

    public static char[][] linesToCharMatrix(final List<String> lines) {
        char[][] arr = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            arr[i] = lines.get(i).toCharArray();
        }
        return arr;
    }

    public static int[][] loadResourcesAsIntMatrix(final List<String> lines) {
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

    public static char[] loadResourcesAsCharArray(final List<String> lines) {
        return linesToCharArray(lines);
    }

    public static char[] linesToCharArray(final List<String> lines) {
        char[][] charMatrix = linesToCharMatrix(lines);
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

    public static <T> T[][] loadResourcesAsArray(final List<String> lines,
                                                 final T[][] emptyArray,
                                                 final Function<String, T[]> splitter) {
        T[][] values = Arrays.copyOf(emptyArray, lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            values[i] = splitter.apply(line);
        }
        return values;
    }
}
