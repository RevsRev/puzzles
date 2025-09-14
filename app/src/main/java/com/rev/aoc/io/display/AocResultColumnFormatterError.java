package com.rev.aoc.io.display;

import com.rev.aoc.framework.AocCoordinate;
import com.rev.aoc.framework.io.display.format.ResultColumnFormatter;
import com.rev.aoc.framework.problem.ProblemResult;

public final class AocResultColumnFormatterError extends ResultColumnFormatter<AocCoordinate> {
    public AocResultColumnFormatterError(final String header, int width, char padChar) {
        super(header, width, padChar);
    }

    @Override
    protected String formatImpl(final ProblemResult<AocCoordinate, ?> result) {
        if (result.getError().isPresent()) {
            if (result.getError().get().getCause() != null) {
                String message = result.getError().get().getCause().getMessage();
                if (message == null) {
                    return result.getError().get().getCause().toString();
                }
                return message;
            }
            return result.getError().get().toString();
        }
        return "";
    }
}
