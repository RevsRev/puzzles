package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.ProblemResult;

public final class AocResultColumnFormatterYear extends ResultColumnFormatter<AocCoordinate> {
    public AocResultColumnFormatterYear(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        return Integer.toString(result.getCoordinate().getYear());
    }

}
