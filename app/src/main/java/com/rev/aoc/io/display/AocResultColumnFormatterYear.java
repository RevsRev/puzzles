package com.rev.aoc.io.display;

import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.io.display.format.ResultColumnFormatter;
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
