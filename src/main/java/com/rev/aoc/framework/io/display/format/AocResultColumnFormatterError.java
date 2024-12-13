package com.rev.aoc.framework.io.display.format;

import com.rev.aoc.framework.problem.AocResult;

public final class AocResultColumnFormatterError extends AocResultColumnFormatter {
    public AocResultColumnFormatterError(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final AocResult result) {
        if (result.getError().isPresent()) {
            return result.getError().get().toString();
        }
        return "";
    }
}
