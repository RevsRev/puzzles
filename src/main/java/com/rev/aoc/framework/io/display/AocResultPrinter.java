package com.rev.aoc.framework.io.display;

import com.rev.aoc.framework.io.display.format.AocResultColumnFormatter;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterDay;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterError;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterSolution;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterTime;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterYear;
import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocResult;

import java.io.PrintWriter;

public final class AocResultPrinter {

    private static final char WHITE_SPACE = ' ';
    private static final char SEPARATOR = '-';
    private static final char DELIMETER = '|';

    private static final AocResultColumnFormatter[] COLS = new AocResultColumnFormatter[]{
            new AocResultColumnFormatterYear("Year", 6, WHITE_SPACE),
            new AocResultColumnFormatterDay("Day", 6, WHITE_SPACE),
            new AocResultColumnFormatterSolution("PartOne", 20, WHITE_SPACE, AocPart.ONE),
            new AocResultColumnFormatterTime("PartOneTime", 20, WHITE_SPACE, AocPart.ONE),
            new AocResultColumnFormatterSolution("PartTwo", 20, WHITE_SPACE, AocPart.TWO),
            new AocResultColumnFormatterTime("PartTwoTime", 20, WHITE_SPACE, AocPart.TWO),
            new AocResultColumnFormatterError("Error", 50, WHITE_SPACE),
    };

    private boolean firstPass = true;
    private PrintWriter printWriter = new PrintWriter(System.out);

    public void printResult(final AocResult result) {
        if (firstPass) {
            printHeader();
            firstPass = false;
        }
        printWriter.println(format(result));
        printWriter.flush();
    }

    private void printHeader() {
        printSeparator();
        printWriter.println(header());
        printWriter.flush();
        printSeparator();
    }
    public void printSeparator() {
        printWriter.println(separator());
        printWriter.flush();
    }

    private String format(final AocResult result) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < COLS.length; i++) {
            sb.append(DELIMETER);
            sb.append(COLS[i].format(result));
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
    private String header() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < COLS.length; i++) {
            sb.append(DELIMETER);
            sb.append(COLS[i].header());
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
    private String separator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < COLS.length; i++) {
            sb.append(DELIMETER);
            sb.append(COLS[i].separator(SEPARATOR));
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
}
