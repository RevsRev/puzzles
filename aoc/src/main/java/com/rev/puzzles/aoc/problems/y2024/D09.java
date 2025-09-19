package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.load.LoaderUtils;
import com.rev.puzzles.aoc.framework.AocProblemI;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.rev.puzzles.framework.framework.ProblemResourceLoader;

public final class D09 {

    public static final int EMPTY_VALUE = -1;
    public static final int NOT_FOUND_INDEX = -1;

    @AocProblemI(year = 2024, day = 9, part = 1)
    public Long partOneImpl(final ProblemResourceLoader resourceLoader) {
        char[] diskMap = LoaderUtils.loadResourcesAsCharArray(resourceLoader.resources());
        int[] disk = loadDisk(diskMap);
        moveFileBlocksPartOne(disk);
        return checksum(disk);
    }

    @AocProblemI(year = 2024, day = 9, part = 2)
    public Long partTwoImpl(final ProblemResourceLoader resourceLoader) {
        char[] diskMap = LoaderUtils.loadResourcesAsCharArray(resourceLoader.resources());
        int[] disk = loadDisk(diskMap);
        moveFileBlocksPartTwo(disk);
        return checksum(disk);
    }

    private int[] loadDisk(final char[] diskMap) {
        int size = 0;
        for (int i = 0; i < diskMap.length; i++) {
            size += Character.getNumericValue(diskMap[i]);
        }
        int[] disk = new int[size];
        int index = 0;
        int numFiles = 0;
        for (int i = 0; i < diskMap.length; i++) {
            int id = i % 2 == 0 ? numFiles : EMPTY_VALUE;
            for (int j = 0; j < Character.getNumericValue(diskMap[i]); j++) {
                disk[index] = id;
                index++;
            }
            if (i % 2 == 0) {
                numFiles++;
            }
        }
        return disk;
    }

    private void moveFileBlocksPartOne(final int[] disk) {
        int start = 0;
        int end = disk.length - 1;
        while (start < end) {
            while (start < end && disk[start] != EMPTY_VALUE) {
                start++;
            }
            while (start < end && disk[end] == EMPTY_VALUE) {
                end--;
            }
            disk[start] = disk[end];
            disk[end] = EMPTY_VALUE;
        }
    }

    private void moveFileBlocksPartTwo(final int[] disk) {
        TreeMap<Integer, Integer> spacesByIndex = new TreeMap<>();
        int index = 0;
        while (index < disk.length) {
            while (index < disk.length && disk[index] != EMPTY_VALUE) {
                index++;
            }
            int spaceSize = 0;
            while (index + spaceSize < disk.length && disk[index + spaceSize] == EMPTY_VALUE) {
                spaceSize++;
            }
            spacesByIndex.put(index, spaceSize);
            index += spaceSize;
        }

        index = disk.length - 1;
        while (index >= 0) {
            while (index >= 0 && disk[index] == EMPTY_VALUE) {
                index--;
            }
            if (index == NOT_FOUND_INDEX) {
                break;
            }
            int blockSize = 0;
            int id = disk[index];
            while (index - blockSize >= 0 && disk[index - blockSize] == id) {
                blockSize++;
            }

            Iterator<Map.Entry<Integer, Integer>> it = spacesByIndex.entrySet().iterator();
            int emptyIndex = NOT_FOUND_INDEX;
            int emptySize = NOT_FOUND_INDEX;
            while (it.hasNext()) {
                Map.Entry<Integer, Integer> entry = it.next();
                if (entry.getKey() > index) {
                    break;
                }
                if (entry.getValue() >= blockSize) {
                    emptyIndex = entry.getKey();
                    emptySize = entry.getValue();
                    break;
                }
            }

            if (emptyIndex != NOT_FOUND_INDEX) {
                for (int i = emptyIndex; i < emptyIndex + blockSize; i++) {
                    disk[i] = id;
                }
                spacesByIndex.remove(emptyIndex);
                int remaining = emptySize - blockSize;
                if (remaining > 0) {
                    spacesByIndex.put(emptyIndex + blockSize, remaining);
                }
                for (int i = index; i > index - blockSize; i--) {
                    disk[i] = EMPTY_VALUE;
                }
            }
            index -= blockSize;
        }
    }

    private long checksum(final int[] disk) {
        long checksum = 0;
        for (int i = 0; i < disk.length; i++) {
            if (disk[i] == EMPTY_VALUE) {
                continue;
            }
            checksum += i * disk[i];
        }
        return checksum;
    }
}
