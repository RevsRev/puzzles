package com.rev.puzzles.leet.framework.display;

import com.rev.puzzles.framework.framework.io.display.format.ResultColumnFormatter;
import com.rev.puzzles.framework.framework.problem.ProblemResult;
import com.rev.puzzles.leet.framework.LeetCoordinate;

public final class LeetResultColumnFormatterNumber extends ResultColumnFormatter<LeetCoordinate> {

    public LeetResultColumnFormatterNumber(final String header, final int width, final char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<LeetCoordinate, ?> result) {
        return Integer.toString(result.getCoordinate().number());
    }
}
