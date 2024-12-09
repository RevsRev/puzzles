package com.rev.aoc.problems.y2024;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;

public final class D09 extends AocProblem {

    public static final int EMPTY_VALUE = -1;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 9);
    }

    @Override
    protected long partOneImpl() {
        char[] diskMap = loadResourcesAsCharArray();
        int[] disk = loadDisk(diskMap);
        moveFileBlocksPartOne(disk);
        return checksum(disk);
    }

    @Override
    protected long partTwoImpl() {
        char[] diskMap = loadResourcesAsCharArray();
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
        int start = 0;
        int end = disk.length - 1;
        while (start < end) {
            while (start < end && disk[start] != EMPTY_VALUE) {
                start++;
            }
            while (start < end && disk[end] == EMPTY_VALUE) {
                end--;
            }

            if (start >= end) {
                break;
            }

            int freeSpace = 0;
            while (disk[start + freeSpace] == EMPTY_VALUE) {
                freeSpace++;
            }
            int fileSize = 0;
            int id = disk[end];
            while (disk[end - fileSize] == id) {
                fileSize++;
            }
            if (freeSpace >= fileSize) {
                for (int i = start; i < start + fileSize; i++) {
                    disk[i] = id;
                }
                for (int i = end - fileSize + 1; i <= end; i++) {
                    disk[i] = EMPTY_VALUE;
                }
                start = start + fileSize;
                end = end - fileSize;
            } else {
                end = end - fileSize;
            }
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
