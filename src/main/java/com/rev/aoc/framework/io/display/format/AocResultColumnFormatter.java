package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocResult;

public abstract class AocResultColumnFormatter {

    private final String header;
    private final int width;
    private final char padChar;

    public AocResultColumnFormatter(final String header, int width, char padChar) {
        this.header = header;
        this.width = width;
        this.padChar = padChar;
    }

    public final String format(final AocResult result) {
        String formatted = formatImpl(result);
        return pad(formatted, padChar);
    }

    public final String separator(final char separatorChar) {
        return pad("", separatorChar);
    }

    public final String header() {
        return pad(header, padChar);
    }

    protected abstract String formatImpl(AocResult result);

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
