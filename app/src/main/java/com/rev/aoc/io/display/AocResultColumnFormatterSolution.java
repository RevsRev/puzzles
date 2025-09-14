package com.rev.aoc.io.display;

import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.io.display.format.ResultColumnFormatter;
import com.rev.aoc.framework.problem.ProblemResult;

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
