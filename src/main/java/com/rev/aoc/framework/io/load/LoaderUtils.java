package com.rev.aoc.framework.io.load;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public static <T> T[][] loadResourcesAsMatrix(final List<String> lines,
                                                  final T[][] emptyArray,
                                                  final Function<String, T[]> splitter) {
        T[][] values = Arrays.copyOf(emptyArray, lines.size());
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            values[i] = splitter.apply(line);
        }
        return values;
    }

    public static int[] findOneAndOnly(final char[][] arr, char c) {
        int height = arr.length;
        int width = arr[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (arr[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static <T> T[][] emptyArray(final T[][] unitEmptyArray,
                                       int height,
                                       int width,
                                       final Supplier<T> initializer) {
        T[][] retval = Arrays.copyOf(unitEmptyArray, height);
        for (int i = 0; i < height; i++) {
            retval[i] = Arrays.copyOf(unitEmptyArray[0], width);
            for (int j = 0; j < width; j++) {
                retval[i][j] = initializer.get();
            }
        }
        return retval;
    }
}
