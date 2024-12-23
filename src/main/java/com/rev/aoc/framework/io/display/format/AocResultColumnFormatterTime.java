package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocPart;
import com.rev.aoc.framework.problem.AocResult;

public final class AocResultColumnFormatterTime extends AocResultColumnFormatter {
    public static final int NANOS_IN_MILLI = 1000 * 1000;
    private final AocPart part;

    public AocResultColumnFormatterTime(final String header, int width, char padChar, final AocPart part) {
        super(header, width, padChar);
        this.part = part;
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected String formatImpl(final AocResult result) {
        long time = getTime(result);
        if (time == -1) {
            return "";
        }
        return Long.toString(time / NANOS_IN_MILLI);
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected String getColor(final AocResult result) {
        long time = getTime(result);
        if (time == -1) {
            return BLACK;
        }
        if (time < 500 * NANOS_IN_MILLI) {
            return GREEN;
        }
        return RED;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private long getTime(final AocResult<?, ?> result) {
        if (result.getError().isPresent()) {
            return -1;
        }
        if (AocPart.ONE.equals(part)) {
            if (result.getPartOneTime().isEmpty()) {
                return -1;
            }
            return result.getPartOneTime().get();
        }
        if (result.getPartTwoTime().isEmpty()) {
            return -1;
        }
        return result.getPartTwoTime().get();
    }
}
