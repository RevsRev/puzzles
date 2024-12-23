package com.rev.aoc.framework.io.display;

import com.rev.aoc.framework.io.display.format.AocResultColumnFormatter;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterDay;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterError;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterSolution;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterTime;
import com.rev.aoc.framework.io.display.format.AocResultColumnFormatterYear;
import com.rev.aoc.framework.io.display.format.ColumnFormatter;
import com.rev.aoc.framework.problem.AocPart;

import java.io.PrintWriter;

public class Printer<T> {
    private static final char WHITE_SPACE = ' ';
    private static final char SEPARATOR = '-';
    private static final char DELIMETER = '|';

    public static final AocResultColumnFormatter[] AOC_RESULT_COLS = new AocResultColumnFormatter[]{
            new AocResultColumnFormatterYear("Year", 6, WHITE_SPACE),
            new AocResultColumnFormatterDay("Day", 6, WHITE_SPACE),
            new AocResultColumnFormatterSolution("PartOne", 45, WHITE_SPACE, AocPart.ONE),
            new AocResultColumnFormatterTime("PartOneTime (ms)", 20, WHITE_SPACE, AocPart.ONE),
            new AocResultColumnFormatterSolution("PartTwo", 45, WHITE_SPACE, AocPart.TWO),
            new AocResultColumnFormatterTime("PartTwoTime (ms)", 20, WHITE_SPACE, AocPart.TWO),
            new AocResultColumnFormatterError("Error", 50, WHITE_SPACE),
    };
    private final ColumnFormatter<T>[] cols;

    private boolean firstPass = true;
    private PrintWriter printWriter = new PrintWriter(System.out);

    public Printer(final ColumnFormatter<T>[] cols) {
        this.cols = cols;
    }

    public final void printResult(final T result) {
        if (firstPass) {
            printHeader();
            firstPass = false;
        }
        printWriter.println(format(result));
        printWriter.flush();
    }

    public final void printSeparator() {
        printWriter.println(separator());
        printWriter.flush();
    }
    private void printHeader() {
        printSeparator();
        printWriter.println(header());
        printWriter.flush();
        printSeparator();
    }
    private String format(final T result) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            sb.append(DELIMETER);
            sb.append(cols[i].format(result));
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
    private String header() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            sb.append(DELIMETER);
            sb.append(cols[i].header());
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
    private String separator() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            sb.append(DELIMETER);
            sb.append(cols[i].separator(SEPARATOR));
        }
        sb.append(DELIMETER);
        return sb.toString();
    }
}
