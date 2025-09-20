package com.rev.puzzles.euler.framework.display;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class PeResultColumnFormatterNumber extends ResultColumnFormatter<PeCoordinate> {

    public PeResultColumnFormatterNumber(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<PeCoordinate, ?> result) {
        return Integer.toString(result.getCoordinate().number());
    }
}
