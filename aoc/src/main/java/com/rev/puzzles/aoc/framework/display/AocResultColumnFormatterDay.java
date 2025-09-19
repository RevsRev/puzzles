package com.rev.puzzles.aoc.framework.display;

import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class AocResultColumnFormatterDay extends ResultColumnFormatter<AocCoordinate> {

    public AocResultColumnFormatterDay(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        return Integer.toString(result.getCoordinate().getDay());
    }
}
