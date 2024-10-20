package com.rev.aoc.problems.y2023;

import com.rev.aoc.AocCoordinate;
import com.rev.aoc.problems.AocProblem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class D01 extends AocProblem {

    //TODO - Change checkstyle to ignore this magic numbers
    private static final int YEAR = 2023;
    public static final int TEN = 10;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(YEAR, 1);
    }

    @Override
    public long solvePartOne() {
        try {
            List<String> trebLines = loadResources();
            long calibrationSum = 0;
            for (int i = 0; i < trebLines.size(); i++) {
                String line = trebLines.get(i);
                calibrationSum += getCalibrationNumber(line);
            }
            return calibrationSum;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public long solvePartTwo() {
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

    private List<String> loadResources() throws IOException {
        AocCoordinate coordinate = getCoordinate();
        String resource = String.format("%s/y%s/D%s.txt", AOC_RESOURCES_PATH,
                coordinate.getYear(), pad(coordinate.getDay()));
        return readLines(resource);
    }

    private String pad(final int day) {
        if (day < TEN) {
            return "0" + day;
        }
        return "" + day;
    }

    private List<String> readLines(final String resourcePath) throws IOException {
        List<String> lines = new ArrayList<>();

        InputStream is = null;
        try {
            is = this.getClass().getResourceAsStream(resourcePath);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            while (line != null) {
                lines.add(line);
                line = r.readLine();
            }
            return lines;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
