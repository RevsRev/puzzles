package com.rev.puzzles.leet.framework.display;

import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
import com.rev.puzzles.leet.framework.LeetCoordinate;

public final class LeetResultColumnFormatterSolution extends ResultColumnFormatter<LeetCoordinate> {

    public LeetResultColumnFormatterSolution(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<LeetCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            return "";
        }
        if (result.getProblemResult().isEmpty()) {
            return "";
        }

        return result.getProblemResult().get().toString();
    }
}
