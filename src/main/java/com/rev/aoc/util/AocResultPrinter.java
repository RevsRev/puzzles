package com.rev.aoc.util;

import com.rev.aoc.AocCoordinate;

import java.io.PrintWriter;

public final class AocResultPrinter {

    private static final char WHITE_SPACE = ' ';
    private static final char SEPARATOR = '-';
    private static final char DELIMETER = '|';
    private static final int[] COL_WIDTHS = new int[]{6, 6, 20, 20, 20, 20, 50};

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
        printWriter.println(format(
                new String[]{"Year", "Day", "PartOne", "PartOneTime", "PartTwo", "PartTwoTime", "Error"})
        );
        printWriter.flush();
        printSeparator();
    }
    public void printSeparator() {
        printWriter.println(format(new String[]{"", "", "", "", "", "", ""}, SEPARATOR));
        printWriter.flush();
    }

    private String format(final AocResult result) {
        Object[] cols;
        AocCoordinate coord = result.getCoordinate();
        if (result.getError().isPresent()) {
            cols = new Object[]{coord.getYear(), coord.getDay(), "", "", result.getError().get()};
            return format(cols);
        } else {
            Object p1 = result.getPartOne().isPresent() ? result.getPartOne().get() : "";
            Object p1Time = result.getPartOneTime().isPresent() ? result.getPartOneTime().get() : "";
            Object p2 = result.getPartTwo().isPresent() ? result.getPartTwo().get() : "";
            Object p2Time = result.getPartTwoTime().isPresent() ? result.getPartTwoTime().get() : "";
            cols = new Object[]{coord.getYear(), coord.getDay(), p1, p1Time, p2, p2Time, ""};
            return format(cols);
        }
    }

    private String format(final Object[] cols) {
        return format(cols, WHITE_SPACE);
    }

    private String format(final Object[] cols, final char padChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cols.length; i++) {
            sb.append(DELIMETER);
            sb.append(pad(cols[i], COL_WIDTHS[i], padChar));
        }
        sb.append(DELIMETER);
        return sb.toString();
    }

    private String pad(final Object obj, final int width, final char padChar) {
        String s = obj.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(padChar);
        if (s.length() > width - 1) {
            s = s.substring(0, width - 1);
        }
        sb.append(s);

        int space = width - s.length() - 1;
        for (int i = 0; i < space; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

}
