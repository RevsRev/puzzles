package com.rev.puzzles.app.io.display;

import com.rev.puzzles.aoc.framework.AocCoordinate;
import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;

public final class AocResultColumnFormatterSolution extends ResultColumnFormatter<AocCoordinate> {

    public AocResultColumnFormatterSolution(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            return "";
        }
        if (result.getProblemResult().isEmpty()) {
            return "";
        }

        return result.getProblemResult().get().toString();
    }
}
