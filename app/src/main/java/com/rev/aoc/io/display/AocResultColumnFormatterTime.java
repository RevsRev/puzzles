package com.rev.aoc.io.display;

import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.io.display.format.ResultColumnFormatter;
import com.rev.aoc.framework.problem.ProblemResult;

public final class AocResultColumnFormatterTime extends ResultColumnFormatter<AocCoordinate> {
    public static final int NANOS_IN_MILLI = 1000 * 1000;

    public AocResultColumnFormatterTime(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        long time = getTime(result);
        if (time == -1) {
            return "";
        }
        return Long.toString(time / NANOS_IN_MILLI);
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected String getColor(final ProblemResult<AocCoordinate, ?> result) {
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
    private long getTime(final ProblemResult<AocCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            return -1;
        }
        return result.getExecutionTime().orElse(-1L);
    }
}
