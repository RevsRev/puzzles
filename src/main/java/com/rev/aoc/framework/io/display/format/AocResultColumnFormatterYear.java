package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocResult;

public final class AocResultColumnFormatterYear extends AocResultColumnFormatter {
    public AocResultColumnFormatterYear(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final AocResult<?, ?> result) {
        return Integer.toString(result.getCoordinate().getYear());
    }
}
