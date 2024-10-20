package com.rev.aoc.problems.y2023;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;

import java.util.List;

public final class D01 extends AocProblem {

    //TODO - Change checkstyle to ignore this magic numbers
    private static final int YEAR = 2023;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(YEAR, 1);
    }

    @Override
    public long partOneImpl() {
        List<String> trebLines = loadResources();
        long calibrationSum = 0;
        for (int i = 0; i < trebLines.size(); i++) {
            String line = trebLines.get(i);
            calibrationSum += getCalibrationNumber(line);
        }
        return calibrationSum;
    }

    @Override
    public long partTwoImpl() {
        return 0;
    }

    private long getCalibrationNumber(final String line) {
        String formattedLine = format(line);
        StringBuilder sbConfigurationNumber = new StringBuilder();
        for (int i = 0; i < formattedLine.length(); i++) {
            char ch = formattedLine.charAt(i);
            if (Character.isDigit(ch)) {
                sbConfigurationNumber.append(ch);
                break;
            }
        }

        for (int i = formattedLine.length() - 1; i >= 0; i--) {
            char ch = formattedLine.charAt(i);
            if (Character.isDigit(ch)) {
                sbConfigurationNumber.append(ch);
                break;
            }
        }
        return Long.parseLong(sbConfigurationNumber.toString());
    }

    private String format(final String line) {
        return line;
    }
}
