package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.aoc.AocCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public final class AocResultColumnFormatterDay extends ResultColumnFormatter<AocCoordinate> {

    public AocResultColumnFormatterDay(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        return Integer.toString(result.getCoordinate().getDay());
    }
}
