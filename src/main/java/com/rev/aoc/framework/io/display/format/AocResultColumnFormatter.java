package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocResult;

public abstract class AocResultColumnFormatter {

    protected static final String COLOR_START_END = "\u001B[";
    protected static final String BLACK = "0m";
    protected static final String RED = "31m";
    protected static final String GREEN = "32m";

    private final String header;
    private final int width;
    private final char padChar;

    public AocResultColumnFormatter(final String header, int width, char padChar) {
        this.header = header;
        this.width = width;
        this.padChar = padChar;
    }

    protected abstract String formatImpl(AocResult result);

    public final String format(final AocResult result) {
        String s = formatImpl(result);
        s = pad(s, padChar);
        return COLOR_START_END + getColor(result) + s + COLOR_START_END + BLACK;
    }

    /**
     * Override this to change the colour of a cell based on its contents :)
     */
    protected String getColor(final AocResult result) {
        return BLACK;
    }


    public final String separator(final char separatorChar) {
        return pad("", separatorChar);
    }
    public final String header() {
        return pad(header, padChar);
    }

    private String pad(final Object obj, final char separatorChar) {
        String s = obj.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(separatorChar);
        if (s.length() > width - 1) {
            s = s.substring(0, width - 1);
        }
        sb.append(s);

        int space = width - s.length() - 1;
        for (int i = 0; i < space; i++) {
            sb.append(separatorChar);
        }
        return sb.toString();
    }
}
