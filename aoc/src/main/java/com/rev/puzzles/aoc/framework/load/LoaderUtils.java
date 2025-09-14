package com.rev.puzzles.aoc.framework.load;

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

    public static int[][] loadResourcesAsIntMatrix(final List<String> lines,
                                                   final Function<String, String[]> lineSplitter) {
        return loadResourcesAsIntMatrix(lines, lineSplitter, Integer::parseInt);
    }
    public static int[][] loadResourcesAsIntMatrix(final List<String> lines,
                                                   final Function<String, String[]> lineSplitter,
                                                   final Function<String, Integer> stringParser) {
        int[][] arr = new int[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] split = lineSplitter.apply(line);
            arr[i] = new int[split.length];
            for (int j = 0; j < split.length; j++) {
                arr[i][j] = stringParser.apply(split[j]);
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

    public static int[] linesToIntArray(final List<String> lines, final Function<String, String[]> lineSplitter) {
        int[][] intMatrix = loadResourcesAsIntMatrix(lines, lineSplitter);
        int size = 0;
        for (int i = 0; i < intMatrix.length; i++) {
            size += intMatrix[i].length;
        }
        int[] retval = new int[size];
        for (int i = 0; i < intMatrix.length; i++) {
            for (int j = 0; j < intMatrix[i].length; j++) {
                retval[i * intMatrix[i].length + j] = intMatrix[i][j];
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

    public static int[] findOne(final char[][] arr, char c) {
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
    public static int[] findOne(final int[][] arr, int c) {
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

    public static int[][] emptyIntMatrix(int height,
                                         int width) {
        int[][] retval = new int[height][width];
        for (int i = 0; i < height; i++) {
            retval[i] = new int[width];
            for (int j = 0; j < width; j++) {
                retval[i][j] = 0;
            }
        }
        return retval;
    }

    public static <T> T[][] emptyMatrix(final T[][] unitEmptyArray,
                                        int height,
                                        int width,
                                        final Supplier<T> initializer) {
        T[][] retval = Arrays.copyOf(unitEmptyArray, height);
        for (int i = 0; i < height; i++) {
            retval[i] = emptyArray(unitEmptyArray[0], width, initializer);
        }
        return retval;
    }

    public static <T> T[] emptyArray(final T[] unitEmptyArray,
                                        int length,
                                        final Supplier<T> initializer) {
        T[] retval = Arrays.copyOf(unitEmptyArray, length);
        for (int i = 0; i < length; i++) {
            retval[i] = initializer.get();
        }
        return retval;
    }
}
