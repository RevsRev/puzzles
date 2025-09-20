package com.rev.puzzles.euler.framework.display;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;


public final class PeResultColumnFormatterTime extends ResultColumnFormatter<PeCoordinate> {
    public static final int NANOS_IN_MILLI = 1000 * 1000;

    public PeResultColumnFormatterTime(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<PeCoordinate, ?> result) {
        final long time = getTime(result);
        if (time == -1) {
            return "";
        }
        return Long.toString(time / NANOS_IN_MILLI);
    }

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    protected String getColor(final ProblemResult<PeCoordinate, ?> result) {
        final long time = getTime(result);
        if (time == -1) {
            return BLACK;
        }
        if (time < 500 * NANOS_IN_MILLI) {
            return GREEN;
        }
        return RED;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private long getTime(final ProblemResult<PeCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            return -1;
        }
        return result.getExecutionTime().orElse(-1L);
    }
}
