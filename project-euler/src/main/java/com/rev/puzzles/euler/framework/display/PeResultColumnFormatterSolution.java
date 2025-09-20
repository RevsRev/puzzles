package com.rev.puzzles.euler.framework.display;

import com.rev.puzzles.euler.framework.PeCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class PeResultColumnFormatterSolution extends ResultColumnFormatter<PeCoordinate> {

    public PeResultColumnFormatterSolution(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<PeCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            return "";
        }
        if (result.getProblemResult().isEmpty()) {
            return "";
        }

        return result.getProblemResult().get().toString();
    }
}
